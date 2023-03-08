package com.pratiti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratiti.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{


	boolean findByAccountId(int accountNumber);
	
	

}
