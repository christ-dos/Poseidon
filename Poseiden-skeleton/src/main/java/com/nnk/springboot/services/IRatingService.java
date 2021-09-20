package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RatingNotFoundException;

import java.util.List;

/**
 * An Interface for {@link RatingService}
 *
 * @author Christine Duarte
 */
public interface IRatingService {
    /**
     * Method that get all {@link Rating}
     *
     * @return A list of {@link Rating}
     */
    List<Rating> getRatings();

    /**
     * Method that get a {@link Rating} by Id
     *
     * @param id An Integer containing the id of the Rating
     * @return An Optional of {@link Rating}
     */
    Rating getRatingById(Integer id) throws RatingNotFoundException;

    /**
     * Method which add a {@link Rating}
     *
     * @param rating An instance {@link Rating}
     * @return The {@link Rating} saved
     */
    Rating addRating(Rating rating);

    /**
     * Method which update a {@link Rating}
     *
     * @param rating An instance {@link Rating}
     * @return the {@link Rating} updated
     */
    Rating updateRating(Rating rating);

    /**
     * Method that delete a {@link Rating}
     *
     * @param id An integer containing the id
     * @return A String containing "Rating deleted"
     */
    String deleteRating(Integer id);
}
