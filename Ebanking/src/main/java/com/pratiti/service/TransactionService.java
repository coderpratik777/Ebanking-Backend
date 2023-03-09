package com.pratiti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.pratiti.entity.Account;
import com.pratiti.entity.Customer;
import com.pratiti.entity.Transaction;
import com.pratiti.exception.TransactionException;
import com.pratiti.model.TransactionData;
import com.pratiti.repository.AccountRepository;
import com.pratiti.repository.CustomerRepository;
import com.pratiti.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepoository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public int transaction(TransactionData data) {
		Optional<Account> account=accountRepository.findById(data.getFromAccount());
		if(account.isPresent()) {
			Account acc=new Account();
			acc=account.get();
			float balance=acc.getCurrentBalance();
			if(data.getAmount()<balance) {
				float updatedBalance=balance-data.getAmount();
				acc.setCurrentBalance(updatedBalance);
				Transaction transaction=new Transaction();
				transaction.setTransactionAmount(data.getAmount());
				transaction.setTransactionDate(data.getTransactionDate());
				transaction.setTransactionType(data.getTransactionType());
				transaction.setAccount(acc);
				transaction.setCustomer(acc.getCustomer());
				
				transactionRepoository.save(transaction);
				return transaction.getTransactionId();
				
			}
			else {
				throw new TransactionException("Balance is insuffiecient");
			}
			
			
		}
		else {
			throw new TransactionException("Something error occured!");
		}
	}
	
	
	
	
	
	
	

}
