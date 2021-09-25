package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * Class that test {@link RuleNameRepository}
 *
 * @author Christine Duarte
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleTests {

    /**
     * An Instance of RuleNameRepository
     */
    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Method that test save, update, find and delete a {@link RuleName}
     */
    @Test
    public void ruleTest() {
        RuleName rule = RuleName.builder()
                .name("Rule Name")
                .description("Description")
                .json("Json")
                .template("Template")
                .sqlStr("SQL")
                .sqlPart("SQL Part")
                .build();
        // Save
        rule = ruleNameRepository.save(rule);
        Assert.assertNotNull(rule.getId());
        Assert.assertTrue(rule.getName().equals("Rule Name"));

        // Update
        rule.setName("Rule Name Update");
        rule = ruleNameRepository.save(rule);
        Assert.assertTrue(rule.getName().equals("Rule Name Update"));

        // Find
        List<RuleName> listResult = ruleNameRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = rule.getId();
        ruleNameRepository.delete(rule);
        Optional<RuleName> ruleList = ruleNameRepository.findById(id);
        Assert.assertFalse(ruleList.isPresent());
    }
}
