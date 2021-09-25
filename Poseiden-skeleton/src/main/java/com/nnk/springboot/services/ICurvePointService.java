package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.CurvePointNotFoundException;

import java.util.List;

/**
 * An Interface for {@link CurvePointService}
 *
 * @author Christine Duarte
 */
public interface ICurvePointService {
    /**
     * Method that get all {@link CurvePoint}
     *
     * @return A list of {@link CurvePoint}
     */
    List<CurvePoint> getCurvePoints();

    /**
     * Method that get a {@link CurvePoint} by Id
     *
     * @param id An Integer containing the id of the curvePoint
     * @return An instance of {@link CurvePoint}
     */
    CurvePoint getCurvePointById(Integer id) throws CurvePointNotFoundException;

    /**
     * Method which add a {@link CurvePoint}
     *
     * @param curvePoint An instance {@link CurvePoint}
     * @return The {@link CurvePoint} saved
     */
    CurvePoint addCurvePoint(CurvePoint curvePoint);

    /**
     * Method which update a {@link CurvePoint}
     *
     * @param curvePoint An instance {@link CurvePoint}
     * @return the {@link CurvePoint} updated
     */
    CurvePoint updateCurvePoint(CurvePoint curvePoint);

    /**
     * Method that delete a {@link CurvePoint }
     *
     * @param id An Integer containing the id if the curvePoint
     * @return A String containing "CurvePoint  deleted"
     */
    String deleteCurvePoint(Integer id);
}
