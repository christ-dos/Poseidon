package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.CurvePointNotFoundException;

import java.util.List;

public interface ICurvePointService {
    List<CurvePoint> getCurvePoints();

    CurvePoint getCurvePointById(Integer id) throws CurvePointNotFoundException;

    CurvePoint addCurvePoint(CurvePoint curvePoint);

    CurvePoint updateCurvePoint(CurvePoint curvePoint);

    String deleteCurvePoint(Integer id);
}
