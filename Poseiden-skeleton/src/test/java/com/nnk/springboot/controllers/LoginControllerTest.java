package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.MyUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Class that test {@link LoginController}
 *
 * @author Christine Duarte
 */
@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc
public class LoginControllerTest {

    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcLogin;

    /**
     * A mock of {@link UserRepository}
     */
    @MockBean
    private UserRepository userRepositoryMock;
    /**
     * A mock of {@link MyUserDetailsService}
     */
    @MockBean
    private MyUserDetailsService myUserDetailsServiceMock;

    /**
     * Method that test get all users in uri is "/app/secure/article-details"
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getAllUserArticlesTest() throws Exception {
        //GIVEN
        List<User> usersList = new ArrayList<>(
                Arrays.asList(
                        new User(1, "user", "user123", "User", "ROLE_ADMIN"),
                        new User(2, "admin", "admin123", "Administrator", "ROLE_USER")
                ));
        when(userRepositoryMock.findAll()).thenReturn(usersList);
        //WHEN
        //THEN
        mockMvcLogin.perform(get("/app/secure/article-details"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasItem(hasProperty("id", is(1)))))
                .andExpect(model().attribute("users", hasItem(hasProperty("id", is(2)))))
                .andExpect(model().attribute("users", hasItem(hasProperty("username", is("user")))))
                .andExpect(model().attribute("users", hasItem(hasProperty("username", is("admin")))))
                .andExpect(model().attribute("users", hasItem(hasProperty("role", is("ROLE_ADMIN")))))
                .andExpect(model().attribute("users", hasItem(hasProperty("role", is("ROLE_USER")))))
                .andDo(print());
    }

    /**
     * Method that test when user has not authorization the view 403 is displayed
     *
     * @throws Exception
     */
    @WithMockUser(username = "user1", roles = "USER1", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getErrorTest_whenUserHasNotAuthorization_thenRedirect403View() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcLogin.perform(get("/app/error")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("403"))
                .andExpect(model().attribute("errorMsg", "You are not authorized for the requested data."))
                .andDo(print());
    }

    /**
     * Method that test when user has not found the view 404 is displayed
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getError404Test_whenUserIsNotFound_thenRedirect404View() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcLogin.perform(get("/app/404")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("404"))
                .andDo(print());
    }
}
