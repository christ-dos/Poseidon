package com.nnk.springboot.IT;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = {"/dataTest.sql"}, executionPhase = BEFORE_TEST_METHOD)
public class LoginTestIT {

    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcLogin;

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
        mockMvcLogin = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getAllUserArticlesTest() throws Exception {
        //GIVEN
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
                .andExpect(model().attribute("users", hasItem(hasProperty("role", is("ADMIN")))))
                .andExpect(model().attribute("users", hasItem(hasProperty("role", is("USER")))))
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
                .andExpect(model().attribute("errorMsg", "You are not authorized for the requested data."))
                .andDo(print());
    }

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
