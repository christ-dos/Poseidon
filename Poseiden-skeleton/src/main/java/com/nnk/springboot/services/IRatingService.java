package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface IRatingService {
    List<Rating> getRatings();

    Rating getRatingById(Integer id);

    Rating addRating(Rating rating);

    Rating updateRating(Rating rating);

    String deleteRating(Integer id);
}
