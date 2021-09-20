package com.nnk.springboot.IT;

import com.nnk.springboot.exceptions.BidListNotFoundException;
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
 * Class of Integration test for {BidList}
 *
 * @author Christine Duarte
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = {"/dataTest.sql"}, executionPhase = BEFORE_TEST_METHOD)
public class BidListTestIT {

    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcBidList;

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
        mockMvcBidList = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * Method that test get view list for bidList when uri is "/bidList/list"
     *
     * @throws Exception
     */
    @WithMockUser(username = "user", roles = "USER", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getHomeTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andDo(print());
    }

    /**
     * Method that test get the form for add a bidList to list
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getAddBidFormTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for add a BidList
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
        mockMvcBidList.perform(post("/bidList/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "Account1")
                        .param("type", "Type1")
                        .param("bidQuantity", String.valueOf(20.0)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(model().attributeHasNoErrors())
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for add a BidList
     * when has error in form, fields "account" and "type" are blank
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postValidate_whenFieldsAccountAndTypeHasError_thenReturnToViewAdd() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(post("/bidList/validate").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", String.valueOf(10.0)))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeHasErrors())
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrorCode("bidList", "account", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("bidList", "type", "NotBlank"))
                .andDo(print());
    }

    /**
     * Method that test get the view update that displayed the BidList to update
     * when BidList exist in dataBase
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(get("/bidList/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test get the view update that displayed the BidList to update
     * when BidList not exist in dataBase
     * then throw a {@link BidListNotFoundException}
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest_whenIs14AndNotExist_thenThrowBidListNotFoundException() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(MockMvcRequestBuilders.get("/bidList/update/14").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("bidListId", String.valueOf(14))
                        .param("account", "Account")
                        .param("type", "Type"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/app/404"))
                .andExpect(model().attributeHasNoErrors())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for update a BidList
     * when has no error in form
     * then redirect to view list with the bidList updated
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateBidTest_whenFieldsHasNoErrors_thenRedirectViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(MockMvcRequestBuilders.post("/bidList/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "AccountUpdated")
                        .param("type", "TypeUpdated")
                        .param("bidQuantity", String.valueOf(20d)))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for update a BidList
     * when has error in form, fields "account", and "type" are blank
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateBidTest_whenFieldsHasErrors_thenReturnViewUpdate() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(MockMvcRequestBuilders.post("/bidList/update/1").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "")
                        .param("type", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrorCode("bidList", "account", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("bidList", "type", "NotBlank"))
                .andDo(print());
    }

    /**
     * Method that test the deletion of a BidList by id
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void deleteBidTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(MockMvcRequestBuilders.get("/bidList/delete/2")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }
}
