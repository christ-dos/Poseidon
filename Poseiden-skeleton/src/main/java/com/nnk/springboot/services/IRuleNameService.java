package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RuleNameNotFoundException;

import java.util.List;

/**
 * An Interface for {@link RuleNameService}
 *
 * @author Christine Duarte
 */
public interface IRuleNameService {
    /**
     * Method that get all {@link RuleName}
     *
     * @return A list of {@link RuleName}
     */
    List<RuleName> getRulesNames();

    /**
     * Method that get a {@link RuleName} by Id
     *
     * @param id An Integer containing the id of the RuleName
     * @return An instance of {@link RuleName}
     */
    RuleName getRuleNameById(Integer id) throws RuleNameNotFoundException;

    /**
     * Method which add a {@link RuleName}
     *
     * @param ruleName An instance {@link RuleName}
     * @return The {@link RuleName} saved
     */
    RuleName addRuleName(RuleName ruleName);

    /**
     * Method which update a {@link RuleName}
     *
     * @param ruleName An instance {@link RuleName}
     * @return the {@link RuleName} updated
     */
    RuleName updateRuleName(RuleName ruleName);

    /**
     * Method that delete a {@link RuleName}
     *
     * @param id An integer containing the id
     * @return A String containing "RuleName deleted"
     */
    String deleteRuleName(Integer id);
}
