package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

public interface ICurvePointService {
    List<CurvePoint> getCurvePoints();

    Optional<CurvePoint> getCurvePointById(Integer id);

    CurvePoint addCurvePoint(CurvePoint curvePoint);

    CurvePoint updateCurvePoint(CurvePoint curvePoint);

    String deleteCurvePoint(Integer id);
}
