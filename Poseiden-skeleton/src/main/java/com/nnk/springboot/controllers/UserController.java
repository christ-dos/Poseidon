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

@Controller
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.getUsers());
        log.info("Controller: displaying List of User");
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user) {
        log.info("Controller: request to add a user");
        return "user/add";
    }

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

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, User user, BindingResult result, Model model) {
        try {
            user = userService.getUserById(id).orElseThrow(() -> new UserNotFoundException("Invalid user ID: " + id));
            model.addAttribute("user", user);
            user.setPassword("");
            log.info("Controller: User found with id: " + id);
        } catch (UserNotFoundException ex) {
            result.rejectValue("id", "UserNotFound", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());
            log.error("Controller: User NOT found with id: " + id);
        }
        // TODO: get User by Id and to model then show to the form
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        try {
            userService.updateUser(user);
        } catch (UserNotFoundException ex) {
            result.rejectValue("id", "userNotFound", ex.getMessage());


        }
        model.addAttribute("users", userService.getUsers());
        log.info("Controller: User updated with: " + id);
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
//        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.deleteUser(id);
        model.addAttribute("users", userService.getUsers());
        return "redirect:/user/list";
    }
}
