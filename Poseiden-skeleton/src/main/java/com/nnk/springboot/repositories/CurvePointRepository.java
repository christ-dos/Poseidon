package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Interface that handles database queries for curvePoint
 *
 * @author Christine Duarte
 */
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
