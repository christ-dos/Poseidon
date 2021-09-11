package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface ICurvePointService {
    List<CurvePoint> getCurvePoints();

    CurvePoint getCurvePointById(Integer id);

    CurvePoint addCurvePoint(CurvePoint curvePoint);

    CurvePoint updateCurvePoint(CurvePoint curvePoint);

    String deleteCurvePoint(CurvePoint curvePoint);
}
