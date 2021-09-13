package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RatingNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class of service that manage {@link Rating} entity
 *
 * @author Christine Duarte
 */
@Service
@Slf4j
public class RatingService implements IRatingService {

    /**
     * An instance Of {@link RatingRepository}
     */
    private RatingRepository ratingRepository;

    /**
     * Constructor
     *
     * @param ratingRepository An instanec of {@link RatingRepository}
     */
    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Method that get all {@link Rating}
     *
     * @return A list of {@link Rating}
     */
    @Override
    public List<Rating> getRatings() {
        log.info("Service: displaying Ratings");

        return ratingRepository.findAll();
    }

    /**
     * Method that get a {@link Rating} by Id
     *
     * @param id An Integer containing the id of the Rating
     * @return An instance of {@link Rating}
     */
    @Override
    public Rating getRatingById(Integer id) {
        Rating rating = ratingRepository.getById(id);
        if (rating == null) {
            log.error("Service: Rating NOT FOUND with ID: " + id);
            throw new RatingNotFoundException("Rating not found");
        }
        log.info("Service: Rating found with ID: " + id);

        return rating;
    }

    /**
     * Method which add a {@link Rating}
     *
     * @param rating An instance {@link Rating}
     * @return The {@link Rating} saved
     */
    @Override
    public Rating addRating(Rating rating) {
        log.info("Service: rating saved");
        return ratingRepository.save(rating);
    }

    /**
     * Method which update a {@link Rating}
     *
     * @param rating An instance {@link Rating}
     * @return the {@link Rating} updated
     */
    @Override
    public Rating updateRating(Rating rating) {
        Rating ratingToUpdate = ratingRepository.getById(rating.getId());
        if (ratingToUpdate == null) {
            log.error("Service: Rating NOT FOUND with ID: " + rating.getId());
            throw new RatingNotFoundException("Rating not found");
        }
        ratingToUpdate.setMoodysRating(rating.getMoodysRating());
        ratingToUpdate.setSandPRating(rating.getSandPRating());
        ratingToUpdate.setFitchRating(rating.getFitchRating());
        ratingToUpdate.setOrderNumber(rating.getOrderNumber());
        log.info("Service: Rating updated with ID: " + rating.getId());

        return ratingRepository.save(ratingToUpdate);
    }

    /**
     * Method that delete a {@link Rating}
     *
     * @param id An integer containing the id
     * @return A String containing "Rating deleted"
     */
    @Override
    public String deleteRating(Integer id) {
        ratingRepository.deleteById(id);
        log.info("Service: Rating deleted iwth ID:" + id);

        return "Rating deleted";
    }
}
