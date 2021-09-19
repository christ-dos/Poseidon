package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RuleNameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IRuleNameService {
    List<RuleName> getRulesNames();

    RuleName getRuleNameById(Integer id) throws RuleNameNotFoundException;

    RuleName addRuleName(RuleName ruleName);

    RuleName updateRuleName(RuleName ruleName);

    String deleteRuleName(Integer id);
}
