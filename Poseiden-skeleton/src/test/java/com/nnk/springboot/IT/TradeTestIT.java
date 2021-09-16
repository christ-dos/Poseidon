package com.nnk.springboot.IT;

import com.nnk.springboot.domain.Trade;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = {"/dataTest.sql"},executionPhase = BEFORE_TEST_METHOD)
public class TradeTestIT {
    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcTrade;

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
        mockMvcTrade = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getHomeTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcTrade.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getAddTradeFormTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcTrade.perform(get("/trade/add")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postValidate_whenFieldsHasNoError_thenRedirectToViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcTrade.perform(post("/trade/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("buyQuantity", String.valueOf(10.0)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(model().attributeHasNoErrors())
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postValidate_whenFieldsHasErrors_thenRedirectToViewAdd() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcTrade.perform(post("/trade/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "")
                        .param("type", "")
                        .param("buyQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeHasErrors())
                .andExpect(model().errorCount(3))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(model().attributeHasFieldErrorCode("trade", "account", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("trade", "type", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("trade", "buyQuantity", "NotNull"))
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcTrade.perform(get("/trade/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateTradeTest_whenFieldsHasNoErrors_thenRedirectViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcTrade.perform(MockMvcRequestBuilders.post("/trade/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account","Account")
                        .param("type","Type")
                        .param("buyQuantity","10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateTradeTest_whenFieldsHasErrors_thenRedirectViewUpdate() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcTrade.perform(MockMvcRequestBuilders.post("/trade/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account","")
                        .param("type","")
                        .param("buyQuantity",""))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().errorCount(3))
                .andExpect(model().attributeHasFieldErrorCode("trade", "account", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("trade", "type", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("trade", "buyQuantity", "NotNull"))
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void deleteTradeTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcTrade.perform(MockMvcRequestBuilders.get("/trade/delete/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }
}
