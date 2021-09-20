package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that handles database queries for rating
 *
 * @author Christine Duarte
 */
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
