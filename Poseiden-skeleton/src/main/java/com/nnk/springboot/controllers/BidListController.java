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


@Controller
@Slf4j
public class BidListController {
    // TODO: Inject Bid service
    @Autowired
    private IBidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.getBidLists());
        log.info("Controller: displaying List of BidList");
        return "bidList/list";
        // TODO: call service find all bids to show to the view
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        log.info("Controller: request to add a BidList");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListService.addBidList(bid);
            log.info("Controller: redirection to bidList list");
            return "redirect:/bidList/list";
        }
        log.error("Controller: Has error in form");
        return "bidList/add";
        // TODO: check data valid and save to db, after saving return bid list
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, BindingResult result, Model model) {
        BidList bidList = null;
        try {
            bidList = bidListService.getBidListById(id);
        } catch (BidListNotFoundException ex) {
            result.rejectValue("id", ex.getMessage());
        }
        model.addAttribute("bidlist", bidList);
        return "bidList/update";
        // TODO: get Bid by Id and to model then show to the form
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidlis/update";
        }
        bidListService.updateBidList(bidList);
        model.addAttribute("bidLists", bidListService.getBidLists());
        return "redirect:/bidList/list";
        // TODO: check required fields, if valid call service to update Bid and return list Bid

    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        model.addAttribute("bidLists", bidListService.getBidLists());
        log.info("Controller: BidList deleted");
        return "redirect:/bidList/list";
        // TODO: Find Bid by Id and delete the bid, return to Bid list
    }
}
