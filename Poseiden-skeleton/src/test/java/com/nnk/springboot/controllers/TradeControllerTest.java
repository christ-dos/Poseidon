package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.TradeNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.security.MyUserDetailsService;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Class that test {@link TradeController}
 *
 * @author Christine Duarte
 */
@WebMvcTest(TradeController.class)
@AutoConfigureMockMvc
public class TradeControllerTest {
    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcTrade;

    /**
     * A mock of {@link TradeService}
     */
    @MockBean
    private TradeService tradeServiceMock;

    /**
     * A mock of {@link TradeRepository}
     */
    @MockBean
    private TradeRepository tradeRepositoryMock;

    /**
     * A mock of {@link MyUserDetailsService}
     */
    @MockBean
    private MyUserDetailsService myUserDetailsServiceMock;

    /**
     * Method that test get view list for trade when uri is "/trade/list"
     *
     * @throws Exception
     */
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

    /**
     * Method that test get the form for add a trade to list
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getAddTradeFormTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcTrade.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for add a trade
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

    /**
     * Method that test the submission of the form for add a trade
     * when has error in form, fields "account" and "type" are blank
     * and "buyQuantity" is null
     *
     * @throws Exception
     */
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

    /**
     * Method that test get the view update that displayed the trade to update
     * when trade exist in dataBase
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest() throws Exception {
        //GIVEN
        Trade trade = Trade.builder()
                .tradeId(1)
                .account("Account")
                .type("Type")
                .buyQuantity(10.0)
                .build();
        when(tradeRepositoryMock.getById(anyInt())).thenReturn(trade);
        when(tradeServiceMock.getTradeById(anyInt())).thenReturn(trade);
        //WHEN
        //THEN
        mockMvcTrade.perform(get("/trade/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test get the view update that displayed the trade to update
     * when trade not exist in dataBase
     * then throw a {@link TradeNotFoundException}
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest_whenIdIs14AndNotExist_thenThrowBidListNotFoundException() throws Exception {
        //GIVEN
        when(tradeServiceMock.getTradeById(anyInt())).thenThrow(new TradeNotFoundException("Trade Not found"));
        //WHEN
        //THEN
        mockMvcTrade.perform(MockMvcRequestBuilders.get("/trade/update/14")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("id", String.valueOf(14))
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("buyQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/app/404"))
                .andExpect(model().attributeHasNoErrors())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for update a trade
     * when has no error in form
     * then redirect to view list with the trade updated
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateTradeTest_whenFieldsHasNoErrors_thenRedirectViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcTrade.perform(MockMvcRequestBuilders.post("/trade/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("buyQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for update a trade
     * when has error in form, fields "account", and "type" are blank
     * and "buyQuantity" is null
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateTradeTest_whenFieldsHasErrors_thenRedirectViewUpdate() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcTrade.perform(MockMvcRequestBuilders.post("/trade/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "")
                        .param("type", "")
                        .param("buyQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().errorCount(3))
                .andExpect(model().attributeHasFieldErrorCode("trade", "account", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("trade", "type", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("trade", "buyQuantity", "NotNull"))
                .andDo(print());
    }

    /**
     * Method that test the deletion of a trade by id
     *
     * @throws Exception
     */
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
