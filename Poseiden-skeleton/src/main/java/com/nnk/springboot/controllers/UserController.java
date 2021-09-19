package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.services.IUserService;
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
 * Class Controller that manage User's requests
 *
 * @author Christine Duarte
 */
@Controller
@Slf4j
public class UserController {
    /**
     * Dependency  {@link IUserService} injected
     */
    @Autowired
    private IUserService userService;

    /**
     * Method GET which displayed the view with the list of all {@link User}
     *
     * @param model Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.getUsers());
        log.info("Controller: displaying List of User");
        return "user/list";
    }

    /**
     * Method GET which permit adding A {@link User}
     *
     * @param user An instance fo {@link User}
     * @return A String containing the name of the view
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        log.info("Controller: request to add a user");
        return "user/add";
    }

    /**
     * Method POST which valid entry in the form for User
     *
     * @param user   An instance fo {@link User}
     * @param result An Interface that permit check validity of entries on fields with annotation @Valid
     * @param model  Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userService.addUser(user);
            model.addAttribute("users", userService.getUsers());
            log.info("Controller: redirection to user list");
            return "redirect:/user/list";
        }
        log.error("Controller: Has error in form");
        return "user/add";
    }

    /**
     * Method GET which permit displaying the {@link User} to update
     *
     * @param id    An Integer containing the id of BidList to update
     * @param model Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            user.setPassword("");
            log.info("Controller: User found with id: " + id);
        } catch (UserNotFoundException ex) {
            log.error("Controller: User NOT found with id: " + id);
            return "redirect:/app/404";
        }
        return "user/update";
    }

    /**
     * Method POST that permit update a {@link User}
     *
     * @param id     An Integer containing the id of BidList to update
     * @param user   An instance of {@link User}
     * @param result An Interface that permit check validity of entries on fields with annotation @Valid
     * @param model  Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        user.setId(id);
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        log.info("Controller: User updated with: " + id);
        return "redirect:/user/list";
    }

    /**
     * Method GET which delete a {@link User}
     *
     * @param id    An Integer containing the id of BidList to delete
     * @param model Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("users", userService.getUsers());
        log.info("Controller: User deleted");
        return "redirect:/user/list";
    }
}
