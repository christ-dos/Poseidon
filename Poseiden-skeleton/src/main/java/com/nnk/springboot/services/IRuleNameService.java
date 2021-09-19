package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;

import java.util.List;
import java.util.Optional;

public interface IRuleNameService {
    List<RuleName> getRulesNames();

    Optional<RuleName> getRuleNameById(Integer id);

    RuleName addRuleName(RuleName ruleName);

    RuleName updateRuleName(RuleName ruleName);

    String deleteRuleName(Integer id);
}
