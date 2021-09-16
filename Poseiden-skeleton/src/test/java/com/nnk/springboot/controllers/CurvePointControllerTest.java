package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
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

@WebMvcTest(CurvePointController.class)
@AutoConfigureMockMvc
public class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvcCurvePoint;

    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    private CurvePointRepository curvePointRepository;

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getHomeTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcCurvePoint.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getAddBidFormTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcCurvePoint.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postValidate_whenFieldsHasNoError_thenRedirectToViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcCurvePoint.perform(post("/curvePoint/validate").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("curveId", String.valueOf(12)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(model().attributeHasNoErrors())
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postValidate_whenFieldsCurveIdIsNull_thenRedirectToViewAdd() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcCurvePoint.perform(post("/curvePoint/validate").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("curveId", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeHasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "curveId", "NotNull"))
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest() throws Exception {
        //GIVEN
        CurvePoint curvePoint = CurvePoint.builder()
                .id(1)
                .curveId(12)
                .term(14.0)
                .value(10.0)
                .build();
        when(curvePointRepository.getById(anyInt())).thenReturn(curvePoint);
        when(curvePointService.getCurvePointById(anyInt())).thenReturn(curvePoint);
        //WHEN
        //THEN
        mockMvcCurvePoint.perform(get("/curvePoint/update/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateCurvePointTest_whenFieldsHasNotErrors_thenRedirectViewList() throws Exception {
        //GIVEN
        CurvePoint curvePoint = CurvePoint.builder()
                .id(1)
                .curveId(12)
                .term(14.0)
                .value(10.0)
                .build();
        when(curvePointRepository.getById(1)).thenReturn(curvePoint);
        when(curvePointService.updateCurvePoint(isA(CurvePoint.class))).thenReturn(curvePoint);
        //WHEN
        //THEN
        mockMvcCurvePoint.perform(MockMvcRequestBuilders.post("/curvePoint/update/1").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("curveId", String.valueOf(12)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateCurvePointTest_whenFieldCurvePointIdIsNull_thenReturnViewUpdate() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcCurvePoint.perform(MockMvcRequestBuilders.post("/curvePoint/update/1").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("curveId", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrorCode("curvePoint", "curveId", "NotNull"))

                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void deleteCurvePointTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcCurvePoint.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }


}


