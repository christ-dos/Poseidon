package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.MyUserDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc
public class LoginControllerTest {

    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcLogin;

    @MockBean
    private UserRepository userRepositoryMock;
    /**
     * A mock of {@link MyUserDetailsService}
     */
    @MockBean
    private MyUserDetailsService myUserDetailsServiceMock;


    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getAllUserArticlesTest() throws Exception {
        //GIVEN
        List<User> usersList = new ArrayList<>(
                Arrays.asList(
                        new User(1,"user","user123","User","ROLE_ADMIN"),
                        new User(2,"admin","admin123","Administrator","ROLE_USER")
                ));
        when(userRepositoryMock.findAll()).thenReturn(usersList);
        //WHEN
        //THEN
        mockMvcLogin.perform(get("/app/secure/article-details"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users",hasItem(hasProperty("id",is(1)))))
                .andExpect(model().attribute("users",hasItem(hasProperty("id",is(2)))))
                .andExpect(model().attribute("users",hasItem(hasProperty("username",is("user")))))
                .andExpect(model().attribute("users",hasItem(hasProperty("username",is("admin")))))
                .andExpect(model().attribute("users",hasItem(hasProperty("role",is("ROLE_ADMIN")))))
                .andExpect(model().attribute("users",hasItem(hasProperty("role",is("ROLE_USER")))))
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getDefaultAfterLoginTest_whenUsernameIsAdmin_thenRedirectSlash() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcLogin.perform(get("/app/default")
                        .param("role", "ROLE_ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "user", roles = "USER", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getDefaultAfterLoginTest_whenUsernameIsUser_thenRedirectInBidListList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcLogin.perform(get("/app/default")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("role", "ROLE_USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getDefaultAfterLoginTest_whenUsernameIsAdmin_thenRedirectInEndpointSlash() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcLogin.perform(get("/app/default")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("role", "ROLE_ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "user1", roles = "USER1", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getErrorTest_whenCredentialsIsWrong_thenRedirect403() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcLogin.perform(get("/app/error")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("role", "ROLE_USER1"))
                .andExpect(status().isOk())
                .andExpect(view().name("403"))
                .andExpect(model().attribute("errorMsg","You are not authorized for the requested data."))
                .andDo(print());
    }
}
