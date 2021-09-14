package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcUser;

    @MockBean
    private UserService userServiceMock;

    @MockBean
    private UserRepository userRepositoryMock;

    @Test
    public void getHomeTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcUser.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
                .andDo(print());
    }

    @Test
    public void getAddUserFormTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcUser.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postValidate_whenFieldsHasNoError_thenRedirectToViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcUser.perform(post("/user/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "Username")
                        .param("password", "Password")
                        .param("fullname", "Fullname")
                        .param("role", "Role"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(model().attributeHasNoErrors())
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @Test
    public void postValidate_whenFieldsHasError_thenRedirectToViewAdd() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcUser.perform(post("/user/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "")
                        .param("role", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeHasErrors())
                .andExpect(model().errorCount(4))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("user", "fullname", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("user", "role", "NotBlank"))
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest() throws Exception {
        //GIVEN
       User user = User.builder()
               .fullname("Fullname")
               .username("Username")
               .password("Password")
               .role("Role")
               .build();
        when(userRepositoryMock.findById(anyInt())).thenReturn(Optional.of(user));
        when(userServiceMock.getUserById(anyInt())).thenReturn(Optional.of(user));
        //WHEN
        //THEN
        mockMvcUser.perform(get("/user/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateUserTest_whenFieldsHasNoErrors_thenRedirectViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcUser.perform(MockMvcRequestBuilders.post("/user/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "Username")
                        .param("password", "Password")
                        .param("fullname", "Fullname")
                        .param("role", "Role"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateUserTest_whenFieldsHasErrors_thenRedirectViewUpdate() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcUser.perform(MockMvcRequestBuilders.post("/user/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "")
                        .param("role", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().errorCount(4))
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("user", "fullname", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("user", "role", "NotBlank"))
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void deleteUserTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcUser.perform(MockMvcRequestBuilders.get("/user/delete/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(view().name("redirect:/user² /list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }





}
