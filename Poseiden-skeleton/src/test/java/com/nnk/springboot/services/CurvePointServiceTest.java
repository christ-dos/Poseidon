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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    private CurvePointService curvePointServiceTest;

    @Mock
    private CurvePointRepository curvePointRepositoryMock;

    CurvePoint curvePointTest;

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

    @Test
    public void getCurvePointByIdTest_whenCurvePointIsFound_thenReturnCurvePoint() {
        //GIVEN
        when(curvePointRepositoryMock.getById(isA(Integer.class))).thenReturn(curvePointTest);
        //WHEN
        CurvePoint curvePointResult = curvePointServiceTest.getCurvePointById(curvePointTest.getId());
        //THEN
        assertNotNull(curvePointResult);
        assertEquals(1, curvePointResult.getId());
        assertEquals(10d, curvePointResult.getTerm());
        assertEquals(30d, curvePointResult.getValue());
    }

    @Test
    public void getCurvePointByIdTest_whenCurvePointNotFound_thenThrowCurvePointNotFoundException() {
        //GIVEN
        when(curvePointRepositoryMock.getById(isA(Integer.class))).thenReturn(null);
        //WHEN
        //THEN
        verify(curvePointRepositoryMock, times(0)).getById(isA(Integer.class));
        assertThrows(CurvePointNotFoundException.class, () -> curvePointServiceTest.getCurvePointById(curvePointTest.getId()));

    }

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

    @Test
    public void updateCurvePointTest_whenCurvePointNotExist_thenThrowCurvePointNotFoundException() {
        //GIVEN
        when(curvePointRepositoryMock.getById(anyInt())).thenReturn(null);
        //WHEN
        //THEN
        verify(curvePointRepositoryMock, times(0)).save(isA(CurvePoint.class));
        assertThrows(CurvePointNotFoundException.class, () -> curvePointServiceTest.updateCurvePoint(curvePointTest));
    }

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
