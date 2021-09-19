package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

import java.util.List;
import java.util.Optional;

public interface IRatingService {
    List<Rating> getRatings();

    Optional<Rating> getRatingById(Integer id);

    Rating addRating(Rating rating);

    Rating updateRating(Rating rating);

    String deleteRating(Integer id);
}
