package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RuleNameNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class of service that manage {@link RuleName} entity
 *
 * @author Christine Duarte
 */
@Service
@Slf4j
public class RuleNameService implements IRuleNameService {
    /**
     * An instance Of {@link RuleNameRepository}
     */
    private RuleNameRepository ruleNameRepository;

    /**
     * Constructor
     *
     * @param ruleNameRepository An instance of {@link RuleNameRepository}
     */
    @Autowired
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * Method that get all {@link RuleName}
     *
     * @return A list of {@link RuleName}
     */
    @Override
    public List<RuleName> getRulesNames() {
        log.info("Service: displaying RuleName");
        return ruleNameRepository.findAll();
    }

    /**
     * Method that get a {@link RuleName} by Id
     *
     * @param id An Integer containing the id of the RuleName
     * @return An instance of {@link RuleName}
     */
    @Override
    public RuleName getRuleNameById(Integer id) {
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);
        if (!ruleName.isPresent()) {
            log.error("Service: Rule name NOT FOUND with ID: " + id);
            throw new RuleNameNotFoundException("Rule name not found");
        }
        log.info("Service: Trade found with ID: " + id);
        return ruleName.get();
    }

    /**
     * Method which add a {@link RuleName}
     *
     * @param ruleName An instance {@link RuleName}
     * @return The {@link RuleName} saved
     */
    @Override
    public RuleName addRuleName(RuleName ruleName) {
        log.info("Service: Rule name saved");
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Method which update a {@link RuleName}
     *
     * @param ruleName An instance {@link RuleName}
     * @return the {@link RuleName} updated
     */
    @Override
    public RuleName updateRuleName(RuleName ruleName) {
        RuleName ruleNameToUpdate = ruleNameRepository.getById(ruleName.getId());
        if (ruleNameToUpdate == null) {
            log.error("Service: RuleName NOT FOUND with ID: " + ruleName.getId());
            throw new RuleNameNotFoundException("RuleName not found");
        }
        ruleNameToUpdate.setName(ruleName.getName());
        ruleNameToUpdate.setDescription(ruleName.getDescription());
        ruleNameToUpdate.setJson(ruleName.getJson());
        ruleNameToUpdate.setTemplate(ruleName.getTemplate());
        ruleNameToUpdate.setSqlStr(ruleName.getSqlStr());
        ruleNameToUpdate.setSqlPart(ruleName.getSqlPart());
        log.info("Service: RuleName updated with ID: " + ruleName.getId());

        return ruleNameRepository.save(ruleNameToUpdate);
    }

    /**
     * Method that delete a {@link RuleName}
     *
     * @param id An integer containing the id
     * @return A String containing "RuleName deleted"
     */
    @Override
    public String deleteRuleName(Integer id) {
        ruleNameRepository.deleteById(id);
        log.info("Service: RuleName deleted with ID:" + id);

        return "RuleName deleted";
    }
}
