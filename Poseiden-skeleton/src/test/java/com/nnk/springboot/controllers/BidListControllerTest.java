package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.security.MyUserDetailsService;
import com.nnk.springboot.services.BidListService;
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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BidListController.class)
@AutoConfigureMockMvc
public class BidListControllerTest {
    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcBidList;

    @MockBean
    private BidListService bidListServiceMock;

    @MockBean
    private BidListRepository bidListRepositoryMock;

    /**
     * A mock of {@link MyUserDetailsService}
     */
    @MockBean
    private MyUserDetailsService myUserDetailsServiceMock;

    @WithMockUser(username = "user", roles = "USER", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getHomeTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(get("/bidList/list").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getAddBidFormTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(get("/bidList/add").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postValidate_whenFieldsHasNoError_thenRedirectToViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(post("/bidList/validate").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "Account")
                        .param("type", "Type"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(model().attributeHasNoErrors())
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postValidate_whenFieldsAccountAndTypeHasError_thenReturnToViewAdd() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(post("/bidList/validate").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "")
                        .param("type", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeHasErrors())
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrorCode("bidList", "account", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("bidList", "type", "NotBlank"))
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest() throws Exception {
        //GIVEN
        BidList bidList = BidList.builder()
                .bidListId(1)
                .account("account")
                .type("type")
                .bidQuantity(10d).build();
        when(bidListRepositoryMock.getById(anyInt())).thenReturn(bidList);
        when(bidListServiceMock.getBidListById(anyInt())).thenReturn(bidList);
        //WHEN
        //THEN
        mockMvcBidList.perform(get("/bidList/update/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateBidTest_whenFieldsHasNotErrors_thenRedirectViewList() throws Exception {
        //GIVEN
        BidList bidList = BidList.builder()
                .bidListId(1)
                .account("account")
                .type("type")
                .bidQuantity(10d).build();
        when(bidListRepositoryMock.getById(1)).thenReturn(bidList);
        when(bidListServiceMock.updateBidList(isA(BidList.class))).thenReturn(bidList);
        //WHEN
        //THEN
        mockMvcBidList.perform(MockMvcRequestBuilders.post("/bidList/update/1").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("account", "account")
                        .param("type", "type"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

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

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void deleteBidTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcBidList.perform(MockMvcRequestBuilders.get("/bidList/delete/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

}
