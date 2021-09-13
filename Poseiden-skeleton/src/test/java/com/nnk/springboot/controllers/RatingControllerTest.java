package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
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

@WebMvcTest(RatingController.class)
@AutoConfigureMockMvc
public class RatingControllerTest {
    /**
     * An instance of {@link MockMvc} that permit simulate a request HTTP
     */
    @Autowired
    private MockMvc mockMvcRating;

    @MockBean
    private RatingService ratingServiceMock;

    @MockBean
    private RatingRepository ratingRepositoryMock;

    @Test
    public void getHomeTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRating.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andDo(print());
    }

    @Test
    public void getAddRatingFormTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRating.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postValidate_whenFieldsHasNoError_thenRedirectToViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRating.perform(post("/rating/validate").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", String.valueOf(10)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(model().attributeHasNoErrors())
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postValidate_whenFieldsHasErrors_thenReturnToViewAdd() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRating.perform(post("/rating/validate").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeHasErrors())
                .andExpect(model().errorCount(4))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(model().attributeHasFieldErrorCode("rating", "moodysRating", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("rating", "sandPRating", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("rating", "fitchRating", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("rating", "orderNumber", "NotNull"))
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void getShowUpdateFormTest() throws Exception {
        //GIVEN
        Rating rating = Rating.builder()
                .moodysRating("moodysRating")
                .fitchRating("fitchRating")
                .sandPRating("sandPRating")
                .orderNumber(10).build();
        when(ratingRepositoryMock.getById(anyInt())).thenReturn(rating);
        when(ratingServiceMock.getRatingById(anyInt())).thenReturn(rating);
        //WHEN
        //THEN
        mockMvcRating.perform(get("/rating/update/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateRatingTest_whenFieldsHasNotErrors_thenRedirectViewList() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRating.perform(MockMvcRequestBuilders.post("/rating/update/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("moodysRating","MoodysRating")
                        .param("fitchRating","FitchRating")
                        .param("sandPRating","MoodysRating")
                        .param("orderNumber", String.valueOf(10)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void postUpdateRatingTest_whenFieldsHasErrors_thenReturnViewUpdate() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRating.perform(MockMvcRequestBuilders.post("/rating/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("moodysRating","")
                        .param("fitchRating","")
                        .param("sandPRating","")
                        .param("orderNumber", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().errorCount(4))
                .andExpect(model().attributeHasFieldErrorCode("rating", "moodysRating", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("rating", "fitchRating", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("rating", "sandPRating", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("rating", "orderNumber", "NotNull"))
                .andDo(print());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "3f7d314e-60f7-4843-804d-785b72c4e8fe")
    @Test
    public void deleteRatingTest() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        mockMvcRating.perform(MockMvcRequestBuilders.get("/rating/delete/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(model().attributeDoesNotExist())
                .andDo(print());
    }





}
