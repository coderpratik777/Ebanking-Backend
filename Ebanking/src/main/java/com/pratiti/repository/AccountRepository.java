package com.pratiti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratiti.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{


	boolean findByAccountId(int accountNumber);
	
	boolean existsByUsername(String username);// verify user is present or not

	public Optional<Account> findByUsername(String username);// return user details

	boolean existsByAccountId(int accountnumber);
	
	
	

}
