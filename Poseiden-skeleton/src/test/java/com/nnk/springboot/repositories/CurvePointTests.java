package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * Class that test {@link CurvePointRepository}
 *
 * @author Christine Duarte
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointTests {
    /**
     * An Instance of CurvePointRepository
     */
    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Method that test save, update, find and delete a {@link CurvePoint}
     */
    @Test
    public void curvePointTest() {
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(10)
                .term(10d)
                .value(30d)
                .build();

        // Save
        curvePoint = curvePointRepository.save(curvePoint);
        Assert.assertNotNull(curvePoint.getId());
        Assert.assertTrue(curvePoint.getCurveId() == 10);

        // Update
        curvePoint.setCurveId(20);
        curvePoint = curvePointRepository.save(curvePoint);
        Assert.assertTrue(curvePoint.getCurveId() == 20);

        // Find
        List<CurvePoint> listResult = curvePointRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = curvePoint.getId();
        curvePointRepository.delete(curvePoint);
        Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
        Assert.assertFalse(curvePointList.isPresent());
    }
}
