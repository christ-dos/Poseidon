package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that handles database queries for bidList
 *
 * @author Christine Duarte
 */
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
