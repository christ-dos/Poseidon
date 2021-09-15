package com.nnk.springboot.IT;

import com.nnk.springboot.domain.Rating;
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
public class RatingTestIT {

        /**
         * An instance of {@link MockMvc} that permit simulate a request HTTP
         */
        @Autowired
        private MockMvc mockMvcRating;

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
            mockMvcRating = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();
        }

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

        @Test
        public void getShowUpdateFormTest() throws Exception {
            //GIVEN
            //WHEN
            //THEN
            mockMvcRating.perform(get("/rating/update/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("rating/update"))
                    .andExpect(model().attributeDoesNotExist())
                    .andDo(print());
        }

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