package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.BidListNotFoundException;
import com.nnk.springboot.exceptions.CurvePointNotFoundException;
import com.nnk.springboot.services.ICurvePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@Slf4j
public class CurvePointController {
    @Autowired
    private ICurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.getCurvePoints());
        log.info("Controller: displaying List of CurvePoint");
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        log.info("Controller: request to add a CurvePoint");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Controller: Has error in form");
            return "curvePoint/add";
        }
        curvePointService.addCurvePoint(curvePoint);
        log.info("Controller: redirection to curve point list");
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            CurvePoint curvePoint = curvePointService.getCurvePointById(id);
            model.addAttribute("curvePoint",curvePoint);
            log.info("Controller: CurvePoint found with id: " + id);
        } catch (CurvePointNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            log.error("Controller: CurvePoint NOT found with id: " + id);
            return "redirect:/app/404";
        }
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePoint.setId(id);
        curvePointService.updateCurvePoint(curvePoint);
        model.addAttribute("curvePoints", curvePointService.getCurvePoints());
        log.info("Controller: CurvePoint updated with: " + id);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curvePointService.deleteCurvePoint(id);
        model.addAttribute("curvePoints", curvePointService.getCurvePoints());
        log.info("Controller: CurvePoint deleted");
        return "redirect:/curvePoint/list";
    }
}
