package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RatingNotFoundException;
import com.nnk.springboot.services.IRatingService;
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

@Controller
@Slf4j
public class RatingController {
    @Autowired
    private IRatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.getRatings());
        log.info("Controller: displaying List of Rating");
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        log.info("Controller: request to add a rating");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Controller: Has error in form");
            return "rating/add";
        }
        ratingService.addRating(rating);
        log.info("Controller: redirection to rating list");
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Rating rating, BindingResult result, Model model) {
        try {
            rating = ratingService.getRatingById(id).orElseThrow(()->new RatingNotFoundException("Invalid Rating: " + id));
            model.addAttribute("rating",rating);
            log.info("Controller: Rating found with id: " + id);
        } catch (RatingNotFoundException ex) {
            result.rejectValue("id", "RatingNotFound", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());
            log.error("Controller: Rating NOT found with id: " + id);

        }
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        rating.setId(id);
        ratingService.updateRating(rating);
        model.addAttribute("ratings", ratingService.getRatings());
        log.info("Controller: Rating updated with: " + id);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        model.addAttribute("ratings", ratingService.getRatings());
        log.info("Controller: Rating deleted");
        return "redirect:/rating/list";
    }
}
