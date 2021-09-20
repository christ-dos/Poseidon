package com.nnk.springboot.IT;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.CurvePointNotFoundException;
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
 * Class of Integration test for {@link CurvePoint}
 *
 * @author Christine Duarte
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = {"/dataTest.sql"}, executionPhase = BEFORE_TEST_METHOD)
public class CurvePointTestIT {
    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcCurvePoint;

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
        mockMvcCurvePoint = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

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
                        .param("curveId", String.valueOf(12))
                        .param("term", String.valueOf(14.0))
                        .param("value", String.valueOf(10.)))
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
        //WHEN
        //THEN
        mockMvcCurvePoint.perform(get("/curvePoint/update/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
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
    public void postUpdateCurvePointTest_whenFieldsHasNoErrors_thenRedirectViewList() throws Exception {
        //GIVEN
        CurvePoint curvePoint = CurvePoint.builder()
                .id(1)
                .curveId(12)
                .term(14.0)
                .value(10.0)
                .build();
        //WHEN
        //THEN
        mockMvcCurvePoint.perform(MockMvcRequestBuilders.post("/curvePoint/update/1").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("curveId", String.valueOf(32))
                        .param("term", String.valueOf(34.0))
                        .param("value", String.valueOf(30.0)))
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
