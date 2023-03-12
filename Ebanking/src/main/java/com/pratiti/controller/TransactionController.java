package com.pratiti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pratiti.entity.Transaction;
import com.pratiti.exception.TransactionException;
import com.pratiti.model.TransactionData;
import com.pratiti.model.TransactionStatus;
import com.pratiti.service.TransactionService;

@RestController
@CrossOrigin
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;

	
	
	@PostMapping("/transaction")
	public TransactionStatus initiateTransaction(@RequestBody TransactionData transactionData) {
		TransactionStatus status=new TransactionStatus();
		try {
			int id =transactionService.transaction(transactionData);
			status.setTransactionId(id);
			status.setFromAccount(transactionData.getFromAccount());
			status.setToAccount(transactionData.getToAccount());
			status.setRemarks(transactionData.getRemark());
			status.setMesssageIfAny("successfull transaction");
			status.setTransactionMode(transactionData.getTransactionType());
			status.setAmount(transactionData.getAmount());
			status.setDate(transactionData.getTransactionDate());
			status.setStatus(true);
			
		}
		catch(TransactionException e) {
			status.setMesssageIfAny(e.getMessage());
			status.setStatus(false);
			
		}
		return status;
		
	}
	
	
	

}
