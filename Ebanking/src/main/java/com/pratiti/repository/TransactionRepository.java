package com.pratiti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratiti.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
}
