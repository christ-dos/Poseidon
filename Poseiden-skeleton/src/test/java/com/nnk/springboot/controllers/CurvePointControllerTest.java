package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.CurvePointNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.security.MyUserDetailsService;
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

/**
 * Class that test {@link CurvePointController}
 *
 * @author Christine Duarte
 */
@WebMvcTest(CurvePointController.class)
@AutoConfigureMockMvc
public class CurvePointControllerTest {

    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcCurvePoint;

    /**
     * A mock of {@link CurvePointService}
     */
    @MockBean
    private CurvePointService curvePointService;

    /**
     * A mock of {@link CurvePointRepository}
     */
    @MockBean
    private CurvePointRepository curvePointRepository;

    /**
     * A mock of {@link MyUserDetailsService}
     */
    @MockBean
    private MyUserDetailsService myUserDetailsServiceMock;

    /**
     * Method that test get view list for curvePoint when the uri is "/curvePoint/list"
     *
     * @throws Exception
     */
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

    /**
     * Method that test get the form for add a curvePoint to list
     *
     * @throws Exception
     */
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

    /**
     * Method that test the submission of the form for add a curvePoint
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
        mockMvcCurvePoint.perform(post("/curvePoint/validate").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("curveId", String.valueOf(12)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(model().attributeHasNoErrors())
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for add a BidList
     * when has error in form, field "curveId" is null
     *
     * @throws Exception
     */
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

    /**
     * Method that test get the view update that displayed the curvePoint to update
     * when curvePoint exist in dataBase
     *
     * @throws Exception
     */
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
        mockMvcCurvePoint.perform(get("/curvePoint/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    /**
     * Method that test get the view update that displayed the curvePoint to update
     * when curvePoint not exist in dataBase
     * then throw a {@link CurvePointNotFoundException}
     *
     * @throws Exception
     */
    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest_whenIs14AndNotExist_thenThrowBidListNotFoundException() throws Exception {
        //GIVEN
        when(curvePointService.getCurvePointById(anyInt())).thenThrow(new CurvePointNotFoundException("CurvePoint not found"));
        //WHEN
        //THEN
        mockMvcCurvePoint.perform(MockMvcRequestBuilders.get("/curvePoint/update/14")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("id", String.valueOf(14))
                        .param("curveId", String.valueOf(10)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/app/404"))
                .andExpect(model().attributeHasNoErrors())
                .andDo(print());
    }

    /**
     * Method that test the submission of the form for update a curvePoint
     * when has no error in form
     * then redirect to view list with the curvePoint updated
     *
     * @throws Exception
     */
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

    /**
     * Method that test the submission of the form for update a curvePoint
     * when has error in form, field "curveId" is null
     *
     * @throws Exception
     */
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

    /**
     * Method that test the deletion of a curvePoint by id
     *
     * @throws Exception
     */
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


