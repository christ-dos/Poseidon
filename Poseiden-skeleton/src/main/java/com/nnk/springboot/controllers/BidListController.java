package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.BidListNotFoundException;
import com.nnk.springboot.services.IBidListService;
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
 * Class Controller that manage BidList's requests
 *
 * @author Christine Duarte
 */
@Controller
@Slf4j
public class BidListController {
    /**
     * Dependency  {@link IBidListService} injected
     */
    @Autowired
    private IBidListService bidListService;

    /**
     * Method GET which displayed the view with the list of all BidList
     *
     * @param model Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.getBidLists());
        log.info("Controller: displaying List of BidList");
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList) {
        log.info("Controller: request to add a BidList");
        return "bidList/add";
    }


    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Controller: Has error in form");
            return "bidList/add";
        }
        bidListService.addBidList(bidList);
        log.info("Controller: redirection to bidList list");
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") @Valid Integer id, Model model) {
        try {
           BidList bidList = bidListService.getBidListById(id);
            model.addAttribute("bidList", bidList);
            log.info("Controller: BidList found with id: " + id);
        } catch (BidListNotFoundException ex) {
            log.error("Controller: BidList NOT found with id: " + id);
            return "redirect:/app/404";
        }
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidListService.updateBidList(bidList);
        model.addAttribute("bidLists", bidListService.getBidLists());
        log.info("Controller: BidList updated with: " + id);
        return "redirect:/bidList/list";

    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        model.addAttribute("bidLists", bidListService.getBidLists());
        log.info("Controller: BidList deleted");
        return "redirect:/bidList/list";
    }
}
