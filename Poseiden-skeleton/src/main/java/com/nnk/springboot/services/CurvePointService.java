package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.CurvePointNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Class of service that manage {@link CurvePoint} entity
 *
 * @author Christine Duarte
 */
@Service
@Slf4j
public class CurvePointService implements ICurvePointService {

    /**
     * An instance Of {@link CurvePointRepository}
     */
    private final CurvePointRepository curvePointRepository;

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
    @Override
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
    @Override
    public CurvePoint getCurvePointById(Integer id) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        if (!curvePoint.isPresent()) {
            log.error("Service: CurvePoint NOT FOUND with ID: " + id);
            throw new CurvePointNotFoundException("CurvePoint not found");
        }
        log.info("Service: CurvePoint found with ID: " + id);
        return curvePoint.get();
    }

    /**
     * Method which add a {@link CurvePoint}
     *
     * @param curvePoint An instance {@link CurvePoint}
     * @return The {@link CurvePoint} saved
     */
    @Override
    public CurvePoint addCurvePoint(CurvePoint curvePoint) {
        curvePoint.setCreationDate(Timestamp.from(Instant.now()));
        log.info("Service: CurvePoint saved");
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Method which update a {@link CurvePoint}
     *
     * @param curvePoint An instance {@link CurvePoint}
     * @return the {@link CurvePoint} updated
     */
    @Override
    public CurvePoint updateCurvePoint(CurvePoint curvePoint) {
        CurvePoint curvePointToUpdate = curvePointRepository.getById(curvePoint.getId());
        if (curvePointToUpdate == null) {
            log.error("Service: CurvePoint NOT FOUND with ID: " + curvePoint.getId());
            throw new CurvePointNotFoundException("CurvePoint not found");
        }
        curvePointToUpdate.setCurveId(curvePoint.getCurveId());
        curvePointToUpdate.setTerm(curvePoint.getTerm());
        curvePointToUpdate.setValue(curvePoint.getValue());

        return curvePointRepository.save(curvePointToUpdate);
    }

    /**
     * Method that delete a {@link CurvePoint }
     * @param id An Integer containing the id if the curvePoint
     * @return A String containing "CurvePoint  deleted"
     */
    @Override
    public String deleteCurvePoint(Integer id) {
        curvePointRepository.deleteById(id);
        log.info("Service: BidList deleted with ID: " + id);

        return "CurvePoint deleted";
    }

}
