package com.nnk.springboot.IT;

import com.nnk.springboot.exceptions.UserNotFoundException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Class of Integration test for User
 *
 * @author Christine Duarte
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = {"/dataTest.sql"}, executionPhase = BEFORE_TEST_METHOD)
public class UserTestIT {
    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcUser;

    /**
     * An instance of {@link WebApplicationContext}
     */
    @Autowired
    private WebApplicationContext context;

    /**
     * Method that build the mockMvc with the context and springSecurity
     */
    @Before
    public void setup() {
        mockMvcUser = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * Method that test get view list for user when uri is "/user/list"
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
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

    /**
     * Method that test get the form for add a user to list
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
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

    /**
     * Method that test the submission of the form for add a user
     * when has no error in fields of form
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postValidate_whenFieldsHasNoError_thenRedirectToViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcUser.perform(post("/user/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "Username")
                        .param("password", "Pa8547925**")
                        .param("fullname", "Fullname")
                        .param("role", "ROLE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(model().attributeHasNoErrors())
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for add a user
     * when has error in form, fields "username", "fullname", "role" are blank
     * and  "password" is not valid
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
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
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "ValidPassword"))
                .andExpect(model().attributeHasFieldErrorCode("user", "fullname", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("user", "role", "NotBlank"))
                .andDo(print());
    }

    /**
     * Method that test get the view update that displayed the user to update
     * when user exist in dataBase
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcUser.perform(get("/user/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test get the view update that displayed the user to update
     * when user not exist in dataBase
     * then throw a {@link UserNotFoundException}
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest_whenIs14AndNotExist_thenThrowBidListNotFoundException() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcUser.perform(MockMvcRequestBuilders.get("/user/update/14").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("id", String.valueOf(14))
                        .param("username", "UserName")
                        .param("password", "Password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/app/404"))
                .andExpect(model().attributeHasNoErrors())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for update a user
     * when has no error in form
     * then redirect to view list with the user updated
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateUserTest_whenFieldsHasNoErrors_thenRedirectViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcUser.perform(MockMvcRequestBuilders.post("/user/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "UsernameUpdated")
                        .param("password", "Pa4569817**")
                        .param("fullname", "FullnameUpdated")
                        .param("role", "RoleUpdated"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for update a user
     * when has error in form, fields "username", "fullname", "role" are blank
     * and  "password" is not valid
     *
     * @throws Exception
     */
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
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "ValidPassword"))
                .andExpect(model().attributeHasFieldErrorCode("user", "fullname", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("user", "role", "NotBlank"))
                .andDo(print());
    }

    /**
     * Method that test the deletion of a user by id
     *
     * @throws Exception
     */
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
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }
}
