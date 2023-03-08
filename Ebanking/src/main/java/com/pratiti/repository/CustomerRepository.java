package com.pratiti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratiti.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	
	boolean existsByAddharCardNumber(int addharCardNumber);

}
