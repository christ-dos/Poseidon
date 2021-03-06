package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RatingNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Class that test {@link RatingService}
 *
 * @author Christine Duarte
 */
@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {
    /**
     * An Instance of RatingService
     */
    private RatingService ratingServiceTest;

    /**
     * A mock of RatingRepository
     */
    @Mock
    private RatingRepository ratingRepositoryMock;

    /**
     * An attribute of Rating
     */
    private Rating ratingTest;

    /**
     * Method that initialize instances to perform each test
     */
    @BeforeEach
    public void setPerTest() {
        ratingServiceTest = new RatingService(ratingRepositoryMock);

        ratingTest = Rating.builder()
                .id(1)
                .moodysRating("Moodys Rating")
                .sandPRating("Sand PRating")
                .fitchRating("Fitch Rating")
                .orderNumber(10)
                .build();
    }

    /**
     * Method that test get all {@link Rating}
     * then return a list with three elements
     */
    @Test
    public void getRatingsTest_whenListOfRatingContainThreeElements_thenReturnSizeIsGreaterThanZero() {
        //GIVEN
        List<Rating> ratings = new ArrayList<>(
                Arrays.asList(
                        Rating.builder().moodysRating("Moodys Rating").sandPRating("Sand PRating").fitchRating("Fitch Rating").orderNumber(10).build(),
                        Rating.builder().moodysRating("Moodys Rating1").sandPRating("Sand PRating1").fitchRating("Fitch Rating1").orderNumber(20).build(),
                        Rating.builder().moodysRating("Moodys Rating2").sandPRating("Sand PRating2").fitchRating("Fitch Rating2").orderNumber(30).build()));
        when(ratingRepositoryMock.findAll()).thenReturn(ratings);
        //WHEN
        List<Rating> ratingsResult = ratingServiceTest.getRatings();
        //THEN
        verify(ratingRepositoryMock, times(1)).findAll();
        assertEquals(ratings, ratingsResult);
        assertTrue(ratingsResult.size() > 0);
    }

    /**
     * Method that test get rating by id
     * when rating is found in database
     */
    @Test
    public void getRatingByIdTest_whenRatingFound_thenReturnRating() {
        //GIVEN
        when(ratingRepositoryMock.findById(isA(Integer.class))).thenReturn(Optional.ofNullable(ratingTest));
        //WHEN
        Rating ratingByIdResult = ratingServiceTest.getRatingById(ratingTest.getId());
        //THEN
        verify(ratingRepositoryMock, times(1)).findById(isA(Integer.class));
        assertNotNull(ratingByIdResult);
        assertEquals("Moodys Rating", ratingByIdResult.getMoodysRating());
        assertEquals("Sand PRating", ratingByIdResult.getSandPRating());
        assertEquals("Fitch Rating", ratingByIdResult.getFitchRating());
        assertEquals(10, ratingByIdResult.getOrderNumber());
    }

    /**
     * Method that test get rating by id
     * when rating not found in database
     * then return a {@link RatingNotFoundException}
     */
    @Test
    public void getRatingByIdTest_whenRatingNotFound_thenRatingNotFoundException() {
        //GIVEN
        when(ratingRepositoryMock.findById(isA(Integer.class))).thenReturn(Optional.empty());
        //WHEN
        //THEN
        verify(ratingRepositoryMock, times(0)).getById(isA(Integer.class));
        assertThrows(RatingNotFoundException.class, () -> ratingServiceTest.getRatingById(ratingTest.getId()));
    }

    /**
     * Method that test add a rating
     * when rating is not recorded in database
     */
    @Test
    public void addRatingTest_whenRatingNotRecordedInDb_thenReturnRatingAdded() {
        //GIVEN
        when(ratingRepositoryMock.save(isA(Rating.class))).thenReturn(ratingTest);
        //WHEN
        Rating ratingResult = ratingServiceTest.addRating(ratingTest);
        //THEN
        verify(ratingRepositoryMock, times(1)).save(isA(Rating.class));
        assertEquals("Moodys Rating", ratingResult.getMoodysRating());
        assertEquals("Sand PRating", ratingResult.getSandPRating());
        assertEquals("Fitch Rating", ratingResult.getFitchRating());
        assertEquals(10, ratingResult.getOrderNumber());
    }

    /**
     * Method that test update a rating
     * when rating exist in database
     */
    @Test
    public void updateRatingTest_whenRatingExist_thenReturnRatingUpdated() {
        //GIVEN
        Rating ratingTestUpdated = Rating.builder()
                .id(1)
                .moodysRating("Moodys Rating5")
                .sandPRating("Sand PRating5")
                .fitchRating("Fitch Rating5")
                .orderNumber(20)
                .build();

        when(ratingRepositoryMock.getById(isA(Integer.class))).thenReturn(ratingTest);
        when(ratingRepositoryMock.save(isA(Rating.class))).thenReturn(ratingTestUpdated);
        //WHEN
        Rating ratingUpdatedResult = ratingServiceTest.updateRating(ratingTestUpdated);
        //THEN
        verify(ratingRepositoryMock, times(1)).save(isA(Rating.class));
        assertEquals("Moodys Rating5", ratingUpdatedResult.getMoodysRating());
        assertEquals("Sand PRating5", ratingUpdatedResult.getSandPRating());
        assertEquals("Fitch Rating5", ratingUpdatedResult.getFitchRating());
        assertEquals(20, ratingUpdatedResult.getOrderNumber());
    }

    /**
     * Method that test update a rating
     * when rating not exist in database
     * then throw {@link RatingNotFoundException}
     */
    @Test
    public void updateRatingTest_whenRatingNotExist_thenThrowRatingNotFoundException() {
        //GIVEN
        when(ratingRepositoryMock.getById(anyInt())).thenReturn(null);
        //WHEN
        //THEN
        verify(ratingRepositoryMock, times(0)).save(isA(Rating.class));
        assertThrows(RatingNotFoundException.class, () -> ratingServiceTest.updateRating(ratingTest));
    }

    /**
     * Method that test deletion by id of a rating
     */
    @Test
    public void deleteRatingTest_whenRatingExist_ThenReturnMessageRatingDeleted() {
        //GIVEN
        Integer id = 1;
        //WHEN
        String messageResult = ratingServiceTest.deleteRating(id);
        //THEN
        verify(ratingRepositoryMock, times(1)).deleteById(anyInt());
        assertEquals("Rating deleted", messageResult);
    }
}
