package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.BidListNotFoundException;
import com.nnk.springboot.services.IBidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@Slf4j
public class BidListController {
    @Autowired
    private IBidListService bidListService;

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
    public String showUpdateForm(@PathVariable("id") @Valid Integer id, BidList bidList, BindingResult result,
                                 Model model) {
        try {
            bidList = bidListService.getBidListById(id).orElseThrow(() -> new BidListNotFoundException("Invalid BidListId: " + id));
            model.addAttribute("bidList",bidList);
            log.info("Controller: BidList found with id: " + id);
        } catch (BidListNotFoundException ex) {
            result.rejectValue("bidListId", "BidListNotFound", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());
            log.error("Controller: BidList NOT found with id: " + id);
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
