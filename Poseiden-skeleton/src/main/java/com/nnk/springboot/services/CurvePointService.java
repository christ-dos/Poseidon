package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.BidListAlreadyExistException;
import com.nnk.springboot.exceptions.BidListNotFoundException;
import com.nnk.springboot.exceptions.CurvePointAlreadyExistException;
import com.nnk.springboot.exceptions.CurvePointNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * Class of service that manage {@link CurvePoint} entity
 *
 * @author Christine Duarte
 */
@Service
@Slf4j
public class CurvePointService {

    /**
     * An instance Of {@link CurvePointRepository}
     */
    private CurvePointRepository curvePointRepository;

    /**
     * Constructor
     *
     * @param curvePointRepository An instanec of {@link CurvePointRepository}
     */
    @Autowired
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Method that get all {@link CurvePoint}
     *
     * @return A list of {@link CurvePoint}
     */
    public List<CurvePoint> getCurvePoints() {
        log.info("Service: displaying CurvePoints");
        return curvePointRepository.findAll();
    }

    /**
     * Method that get a {@link CurvePoint} by Id
     *
     * @param id An Integer containing the id of the curvePoint
     * @return An instance of {@link CurvePoint}
     */
    public CurvePoint getCurvePointById(Integer id) {
        CurvePoint curvePoint = curvePointRepository.getById(id);
        if (curvePoint == null) {
            log.error("Service: CurvePoint NOT FOUND with ID: " + id);
            throw new CurvePointNotFoundException("CurvePoint not found");
        }
        log.info("Service: CurvePoint found with ID: " + id);
        return curvePoint;
    }

    /**
     * Method which add a {@link CurvePoint}
     *
     * @param curvePoint An instance {@link CurvePoint}
     * @return The {@link CurvePoint} saved
     */
    public CurvePoint addCurvePoint(CurvePoint curvePoint) {
        CurvePoint curvePointToAdd = curvePointRepository.getById(curvePoint.getId());
        if(curvePointToAdd != null){
            log.error("Service: CurvePoint already exist!");
            throw new CurvePointAlreadyExistException("The CurvePoint that you try to add already exist");
        }
        curvePoint.setCreationDate(Timestamp.from(Instant.now()));
        log.info("Service: CurvePoint saved");
        return curvePointRepository.save(curvePoint);
    }



}
