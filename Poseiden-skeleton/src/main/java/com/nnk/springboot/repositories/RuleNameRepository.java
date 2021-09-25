package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that handles database queries for ruleName
 *
 * @author Christine Duarte
 */
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
