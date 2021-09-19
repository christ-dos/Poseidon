package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RatingNotFoundException;

import java.util.List;

public interface IRatingService {
    List<Rating> getRatings();

    Rating getRatingById(Integer id) throws RatingNotFoundException;

    Rating addRating(Rating rating);

    Rating updateRating(Rating rating);

    String deleteRating(Integer id);
}
