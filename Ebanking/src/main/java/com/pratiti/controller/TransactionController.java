package com.pratiti.controller;

import java.awt.PageAttributes.MediaType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pratiti.entity.Transaction;
import com.pratiti.exception.CustomerServiceException;
import com.pratiti.exception.TransactionException;
import com.pratiti.model.Dates;
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
			status.setDate(transactionData.getTransactionDate());
			status.setStatus(true);
			
		}
		catch(TransactionException e) {
			status.setMesssageIfAny(e.getMessage());
			status.setStatus(false);
			
		}
		return status;
		
	}
	
	@GetMapping("/gettransaction")
	public List<Transaction> getTransaction(@RequestParam int customerid){
		
			List<Transaction>transaction= transactionService.getTransactions(customerid);
			return transaction;
		
		
	}
	
	@PostMapping("/toandfromtrans" )
	public List<Transaction> toAndfFromTrans(@RequestBody Dates dates){
		
		List<Transaction>transaction= transactionService.getTransactionsToAndFrom(dates);
		return transaction;
	}
	

}
