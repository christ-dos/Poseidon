package com.nnk.springboot.controllers;

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

/**
 * Class Controller that manage Rating's requests
 *
 * @author Christine Duarte
 */
@Controller
@Slf4j
public class RatingController {
    /**
     * Dependency  {@link IRatingService} injected
     */
    @Autowired
    private IRatingService ratingService;

    /**
     * Method GET which displayed the view with the list of all {@link Rating}
     *
     * @param model Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.getRatings());
        log.info("Controller: displaying List of Rating");
        return "rating/list";
    }

    /**
     * Method GET which permit adding A {@link Rating}
     *
     * @param rating An instance fo {@link Rating}
     * @return A String containing the name of the view
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        log.info("Controller: request to add a rating");
        return "rating/add";
    }

    /**
     * Method POST which valid entry in the form for rating
     *
     * @param rating An instance fo {@link Rating}
     * @param result  An Interface that permit check validity of entries on fields with annotation @Valid
     * @param model   Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
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

    /**
     * Method GET which permit displaying the {@link Rating} to update
     *
     * @param id    An Integer containing the id of BidList to update
     * @param model Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            Rating rating = ratingService.getRatingById(id);
            model.addAttribute("rating",rating);
            log.info("Controller: Rating found with id: " + id);
        } catch (RatingNotFoundException ex) {
            log.error("Controller: Rating NOT found with id: " + id);
            return "redirect:/app/404";
        }
        return "rating/update";
    }

    /**
     * Method POST that permit update a {@link Rating}
     *
     * @param id      An Integer containing the id of BidList to update
     * @param rating An instance of {@link Rating}
     * @param result  An Interface that permit check validity of entries on fields with annotation @Valid
     * @param model   Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
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

    /**
     * Method GET which delete a {@link Rating}
     *
     * @param id    An Integer containing the id of BidList to delete
     * @param model Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        model.addAttribute("ratings", ratingService.getRatings());
        log.info("Controller: Rating deleted");
        return "redirect:/rating/list";
    }
}
