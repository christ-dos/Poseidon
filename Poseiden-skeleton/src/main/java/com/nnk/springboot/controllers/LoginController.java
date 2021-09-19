package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class Controller that manage BidList's requests
 *
 * @author Christine Duarte
 */
@Controller
@RequestMapping("app")
public class LoginController {
    /**
     * Dependency  {@link UserRepository} injected
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Method which displaying the view login
     *
     * @return A String containing the name of the view
     */
    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * Method GET that find all Users
     *
     * @return A String containing the name of the view
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Method which displayed the view 403
     * if the user not has authorization
     *
     * @return A String containing the name of the view
     */
    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

    /**
     * Method which displayed the view 404
     * when data is not found
     *
     * @return A String containing the name of the view
     */
    @GetMapping("404")
    public String error404() {
        return "404";
    }
}
