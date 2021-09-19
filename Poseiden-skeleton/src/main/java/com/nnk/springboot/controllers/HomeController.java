package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class Controller that display the view home
 *
 * @author Christine Duarte
 */
@Controller
public class HomeController {
    /**
     * Method which displayed the view home
     *
     * @param model Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }

    /**
     * Method which displayed the view bidList/list
     *
     * @param model Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        return "redirect:/bidList/list";
    }
}
