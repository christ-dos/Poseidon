package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.CurvePointNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Class that test {@link CurvePointService}
 *
 * @author Christine Duarte
 */
@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {
    /**
     * An Instance of CurvePointService
     */
    private CurvePointService curvePointServiceTest;

    /**
     * A mock of CurvePointRepository
     */
    @Mock
    private CurvePointRepository curvePointRepositoryMock;

    /**
     * An attribute of CurvePoint
     */
    private CurvePoint curvePointTest;

    /**
     * Method that initialize instances to perform each test
     */
    @BeforeEach
    public void setPerTest() {
        curvePointServiceTest = new CurvePointService(curvePointRepositoryMock);
        curvePointTest = CurvePoint.builder()
                .id(1)
                .curveId(10)
                .term(10d)
                .value(30d)
                .build();
    }

    /**
     * Method that test get all {@link CurvePoint}
     * then return a list with three elements
     */
    @Test
    public void getCurvePoints_whenListOfCurvePointContainThreeElements_thenReturnSizeIsGreaterThanZero() {
        //GIVEN
        List<CurvePoint> curvePointList = new ArrayList<>(
                Arrays.asList(CurvePoint.builder().curveId(10).term(10d).value(10d).build(),
                        CurvePoint.builder().curveId(11).term(20d).value(20d).build(),
                        CurvePoint.builder().curveId(12).term(30d).value(30d).build()));
        when(curvePointRepositoryMock.findAll()).thenReturn(curvePointList);
        //WHEN
        List<CurvePoint> curvePointsResult = curvePointServiceTest.getCurvePoints();
        //THEN
        verify(curvePointRepositoryMock, times(1)).findAll();
        assertEquals(curvePointList, curvePointsResult);
        assertTrue(curvePointsResult.size() > 0);
    }

    /**
     * Method that test get curvePoint by id
     * when curvePoint is found in database
     */
    @Test
    public void getCurvePointByIdTest_whenCurvePointIsFound_thenReturnCurvePoint() {
        //GIVEN
        when(curvePointRepositoryMock.findById(isA(Integer.class))).thenReturn(Optional.ofNullable(curvePointTest));
        //WHEN
        CurvePoint curvePointResult = curvePointServiceTest.getCurvePointById(curvePointTest.getId());
        //THEN
        assertNotNull(curvePointResult);
        assertEquals(1, curvePointResult.getId());
        assertEquals(10d, curvePointResult.getTerm());
        assertEquals(30d, curvePointResult.getValue());
    }

    /**
     * Method that test get curvePoint by id
     * when curvePoint not found in database
     * then return a {@link CurvePointNotFoundException}
     */
    @Test
    public void getCurvePointByIdTest_whenCurvePointNotFound_thenThrowCurvePointNotFoundException() {
        //GIVEN
        when(curvePointRepositoryMock.findById(isA(Integer.class))).thenReturn(Optional.empty());
        //WHEN
        //THEN
        verify(curvePointRepositoryMock, times(0)).getById(isA(Integer.class));
        assertThrows(CurvePointNotFoundException.class, () -> curvePointServiceTest.getCurvePointById(curvePointTest.getId()));

    }

    /**
     * Method that test add a curvePoint
     * when curvePoint is not recorded in database
     */
    @Test
    public void addCurvePointTest_whenCurvePointNotRecordedInDb_thenReturnCurvePointAdded() {
        //GIVEN
        when(curvePointRepositoryMock.save(isA(CurvePoint.class))).thenReturn(curvePointTest);
        //WHEN
        CurvePoint curvePointResult = curvePointServiceTest.addCurvePoint(curvePointTest);
        //THEN
        verify(curvePointRepositoryMock, times(1)).save(isA(CurvePoint.class));
        assertEquals(1, curvePointResult.getId());
        assertEquals(10, curvePointResult.getCurveId());
        assertEquals(10d, curvePointResult.getTerm());
        assertEquals(30d, curvePointResult.getValue());
        assertNotNull(curvePointResult.getCreationDate());
    }

    /**
     * Method that test update a curvePoint
     * when curvePoint exist in database
     */
    @Test
    public void updateCurvePointTest_whenCurvePointExist_thenReturnCurvePointUpdated() {
        //GIVEN
        CurvePoint curvePointTestUpdated = CurvePoint.builder()
                .id(1)
                .curveId(20)
                .term(20d)
                .value(30d)
                .build();

        when(curvePointRepositoryMock.getById(isA(Integer.class))).thenReturn(curvePointTest);
        when(curvePointRepositoryMock.save(isA(CurvePoint.class))).thenReturn(curvePointTestUpdated);
        CurvePoint curvePointUpdated = curvePointServiceTest.updateCurvePoint(curvePointTestUpdated);
        //THEN
        verify(curvePointRepositoryMock, times(1)).save(isA(CurvePoint.class));
        assertEquals(20, curvePointUpdated.getCurveId());
        assertEquals(20d, curvePointUpdated.getTerm());
        assertEquals(30d, curvePointUpdated.getValue());

    }

    /**
     * Method that test update a curvePoint
     * when curvePoint not exist in database
     * then throw {@link CurvePointNotFoundException}
     */
    @Test
    public void updateCurvePointTest_whenCurvePointNotExist_thenThrowCurvePointNotFoundException() {
        //GIVEN
        when(curvePointRepositoryMock.getById(anyInt())).thenReturn(null);
        //WHEN
        //THEN
        verify(curvePointRepositoryMock, times(0)).save(isA(CurvePoint.class));
        assertThrows(CurvePointNotFoundException.class, () -> curvePointServiceTest.updateCurvePoint(curvePointTest));
    }

    /**
     * Method that test deletion by id of a curvePoint
     */
    @Test
    public void deleteCurvePointTest_whenCurvePointExist_ThenReturnMessageCurvePointDeleted() {
        //GIVEN
        Integer id = 1;
        //WHEN
        String messageResult = curvePointServiceTest.deleteCurvePoint(id);
        //THEN
        verify(curvePointRepositoryMock, times(1)).deleteById(anyInt());
        assertEquals("CurvePoint deleted", messageResult);
    }
}
