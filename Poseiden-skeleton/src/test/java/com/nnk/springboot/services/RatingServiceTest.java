package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.BidListAlreadyExistException;
import com.nnk.springboot.exceptions.BidListNotFoundException;
import com.nnk.springboot.exceptions.RatingAlreadyExistException;
import com.nnk.springboot.exceptions.RatingNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    private RatingService ratingServiceTest;

    @Mock
    private RatingRepository ratingRepositoryMock;

    private Rating ratingTest;

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

    @Test
    public void getRatingsTest_whenListOfRatingContainThreeElements_thenReturnSizeIsGreaterThanZero() {
        //GIVEN
        List<Rating> ratings = new ArrayList<>(
                Arrays.asList(Rating.builder().moodysRating("Moodys Rating").sandPRating("Sand PRating").fitchRating("Fitch Rating").orderNumber(10).build(),
                        Rating.builder().moodysRating("Moodys Rating1").sandPRating("Sand PRating1").fitchRating("Fitch Rating1").orderNumber(20).build(),
                        Rating.builder().moodysRating("Moodys Rating2").sandPRating("Sand PRating2").fitchRating("Fitch Rating2").orderNumber(30).build()));
        when(ratingRepositoryMock.findAll()).thenReturn(ratings);
        //WHEN
        List<Rating> ratingsResult =ratingServiceTest.getRatings();
        //THEN
        verify(ratingRepositoryMock, times(1)).findAll();
        assertEquals(ratings, ratingsResult);
        assertTrue(ratingsResult.size() > 0);
    }

    @Test
    public void getRatingByIdTest_whenRatingFound_thenReturnRating() {
        //GIVEN
        when(ratingRepositoryMock.getById(isA(Integer.class))).thenReturn(ratingTest);
        //WHEN
        Rating ratingByIdResult = ratingServiceTest.getRatingById(ratingTest.getId());
        //THEN
        verify(ratingRepositoryMock, times(1)).getById(isA(Integer.class));
        assertNotNull(ratingByIdResult);
        assertEquals("Moodys Rating", ratingByIdResult.getMoodysRating());
        assertEquals("Sand PRating", ratingByIdResult.getSandPRating());
        assertEquals("Fitch Rating", ratingByIdResult.getFitchRating());
        assertEquals(10, ratingByIdResult.getOrderNumber());
    }

    @Test
    public void getRatingByIdTest_whenRatingNotFound_thenRatingNotFoundException() {
        //GIVEN
        when(ratingRepositoryMock.getById(isA(Integer.class))).thenReturn(null);
        //WHEN
        //THEN
        verify(ratingRepositoryMock, times(0)).getById(isA(Integer.class));
        assertThrows(RatingNotFoundException.class, () -> ratingServiceTest.getRatingById(ratingTest.getId()));
    }

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

    @Test
    public void addRatingTest_whenRatingAlreadyExistInDb_thenThrowRatingAlreadyExistException() {
        //GIVEN
        when(ratingRepositoryMock.getById(isA(Integer.class))).thenReturn(ratingTest);
        //WHEN
        //THEN
        verify(ratingRepositoryMock, times(0)).save(isA(Rating.class));
        assertThrows(RatingAlreadyExistException.class, () -> ratingServiceTest.addRating(ratingTest));
    }

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

    @Test
    public void deleteRatingTest_whenRatingExist_ThenReturnMessageRatingDeleted() {
        //GIVEN
        when(ratingRepositoryMock.getById(isA(Integer.class))).thenReturn(ratingTest);
        //WHEN
        String messageResult = ratingServiceTest.deleteRating(ratingTest.getId());
        //THEN
        verify(ratingRepositoryMock,times(1)).delete(isA(Rating.class));
        assertEquals("Rating deleted", messageResult);
    }


}
