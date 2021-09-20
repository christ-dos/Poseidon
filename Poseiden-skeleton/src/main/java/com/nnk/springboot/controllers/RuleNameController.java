package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RuleNameNotFoundException;
import com.nnk.springboot.services.IRuleNameService;
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
 * Class Controller that manage RuleName's requests
 *
 * @author Christine Duarte
 */
@Controller
@Slf4j
public class RuleNameController {
    /**
     * Dependency  {@link IRuleNameService} injected
     */
    @Autowired
    private IRuleNameService ruleNameService;

    /**
     * Method GET which displayed the view with the list of all {@link RuleName}
     *
     * @param model Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.getRulesNames());
        log.info("Controller: displaying List of RuleName");
        return "ruleName/list";
    }

    /**
     * Method GET which permit adding A {@link RuleName}
     *
     * @param ruleName An instance fo {@link RuleName}
     * @return A String containing the name of the view
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        log.info("Controller: request to add a ruleName");
        return "ruleName/add";
    }

    /**
     * Method POST which valid entry in the form for RuleName
     *
     * @param ruleName An instance fo {@link RuleName}
     * @param result   An Interface that permit check validity of entries on fields with annotation @Valid
     * @param model    Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Controller: Has error in form");
            return "ruleName/add";
        }
        ruleNameService.addRuleName(ruleName);
        log.info("Controller: redirection to ruleName list");
        return "redirect:/ruleName/list";
    }

    /**
     * Method GET which permit displaying the {@link RuleName} to update
     *
     * @param id    An Integer containing the id of BidList to update
     * @param model Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            RuleName ruleName = ruleNameService.getRuleNameById(id);
            model.addAttribute("ruleName", ruleName);
            log.info("Controller: RuleName found with id: " + id);
        } catch (RuleNameNotFoundException ex) {
            log.error("Controller: RuleName NOT found with id: " + id);
            return "redirect:/app/404";
        }
        return "ruleName/update";
    }

    /**
     * Method POST that permit update a {@link RuleName}
     *
     * @param id       An Integer containing the id of BidList to update
     * @param ruleName An instance of {@link RuleName}
     * @param result   An Interface that permit check validity of entries on fields with annotation @Valid
     * @param model    Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameService.updateRuleName(ruleName);
        model.addAttribute("ruleNames", ruleNameService.getRulesNames());
        log.info("Controller: RuleName updated with: " + id);
        return "redirect:/ruleName/list";
    }

    /**
     * Method GET which delete a {@link RuleName}
     *
     * @param id    An Integer containing the id of BidList to delete
     * @param model Interface that defines a support for model attributes
     * @return A String containing the name of the view
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleName(id);
        model.addAttribute("ruleNames", ruleNameService.getRulesNames());
        log.info("Controller: RuleName deleted");
        return "redirect:/ruleName/list";
    }
}
