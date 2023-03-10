package com.pratiti.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratiti.entity.Customer;
import com.pratiti.entity.Customer.Status;
import com.pratiti.exception.CustomerServiceException;
import com.pratiti.repository.CustomerRepository;
import com.pratiti.service.AccountService;
import com.pratiti.service.CustomerService;
import com.pratiti.service.TransactionService;

@RestController
@CrossOrigin
public class AdminController {
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private CustomerRepository custrepo;
	@GetMapping("/changestatus")
	public Status changeStatus(@RequestParam int customerid)
	{
		
		 Customer cust=customerService.getCustomer(customerid);
		 System.out.println(cust);
		 if(cust.getStatus()!=Status.ACTIVE) {
		     
			 cust.setStatus(Status.ACTIVE);
			 custrepo.save(cust);
			 
		 }
		 else
		 {
			 cust.setStatus(Status.INACTIVE);
			 custrepo.save(cust);
			
		 }
		 return cust.getStatus();
		 
	}

    @GetMapping("/customer")
    public List<Customer> getAllCustomers() {
    	return customerService.getAllCustomers();
        
    }

	
	
	

}
