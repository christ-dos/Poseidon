package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;

import java.util.List;

public interface IRuleNameService {
    List<RuleName> getRulesNames();

    RuleName getRuleNameById(Integer id);

    RuleName addRuleName(RuleName ruleName);

    RuleName updateRuleName(RuleName ruleName);

    String deleteRuleName(Integer id);
}
