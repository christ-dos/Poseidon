package com.nnk.springboot.IT;

import com.nnk.springboot.exceptions.RuleNameNotFoundException;
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
 * Class of Integration test for RuleName
 *
 * @author Christine Duarte
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = {"/dataTest.sql"}, executionPhase = BEFORE_TEST_METHOD)
public class RuleNameTestIT {

    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcRuleName;

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
        mockMvcRuleName = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * Method that test get view list for ruleName when uri is "/ruleName/list"
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getHomeTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRuleName.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andDo(print());
    }

    /**
     * Method that test get the form for add a ruleName to list
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getAddRuleNameFormTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRuleName.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for add a ruleName
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
        mockMvcRuleName.perform(post("/ruleName/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("name", "Name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(model().attributeHasNoErrors())
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for add a ruleName
     * when has error in form, field "name" is blank
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postValidate_whenFieldsHasError_thenRedirectToViewAdd() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRuleName.perform(post("/ruleName/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeHasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "name", "NotBlank"))
                .andDo(print());
    }

    /**
     * Method that test get the view update that displayed the ruleName to update
     * when ruleName exist in dataBase
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRuleName.perform(get("/ruleName/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test get the view update that displayed the ruleName to update
     * when ruleName not exist in dataBase
     * then throw a {@link RuleNameNotFoundException}
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest_whenIs14AndNotExist_thenThrowBidListNotFoundException() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRuleName.perform(MockMvcRequestBuilders.get("/ruleName/update/14")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("id", String.valueOf(14))
                        .param("name", "Name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/app/404"))
                .andExpect(model().attributeHasNoErrors())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for update a ruleName
     * when has no error in form
     * then redirect to view list with the ruleName updated
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateRuleNameTest_whenFieldsHasNoErrors_thenRedirectViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRuleName.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("name", "Name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for update a ruleName
     * when has error in form, field "name" is blank
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateRuleNameTest_whenFieldsHasErrors_thenRedirectViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRuleName.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "name", "NotBlank"))
                .andDo(print());
    }

    /**
     * Method that test the deletion of a ruleName by id
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void deleteRuleNameTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRuleName.perform(MockMvcRequestBuilders.get("/ruleName/delete/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }
}
