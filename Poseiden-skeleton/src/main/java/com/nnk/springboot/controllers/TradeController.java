package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.TradeNotFoundException;
import com.nnk.springboot.services.ITradeService;
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
public class TradeController {
    @Autowired
    ITradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.getTrades());
        log.info("Controller: displaying List of Trade");
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        log.info("Controller: request to add a trade");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Controller: Has error in form");
            return "trade/add";
        }
        tradeService.addTrade(trade);
        log.info("Controller: redirection to trade list");
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            Trade trade = tradeService.getTradeById(id);
            model.addAttribute("trade", trade);
            log.info("Controller: Trade found with id: " + id);
        } catch (TradeNotFoundException e) {
            log.error("Controller: Trade NOT found with id: " + id);
            return "redirect:/app/404";
        }
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeService.updateTrade(trade);
        model.addAttribute("trades", tradeService.getTrades());
        log.info("Controller: Trade updated with: " + id);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTrade(id);
        model.addAttribute("trades", tradeService.getTrades());
        log.info("Controller: Trade deleted");
        return "redirect:/trade/list";
    }
}
