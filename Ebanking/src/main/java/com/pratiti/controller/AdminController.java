package com.pratiti.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratiti.entity.Account;
import com.pratiti.entity.Customer;
import com.pratiti.entity.Customer.Status;
import com.pratiti.exception.CustomerServiceException;
import com.pratiti.model.CustomerLoginData;
import com.pratiti.model.LoginStatus;
//import com.pratiti.model.Status;
import com.pratiti.repository.CustomerRepository;
import com.pratiti.service.AccountService;
import com.pratiti.service.AdminService;
import com.pratiti.service.CustomerService;
import com.pratiti.service.TransactionService;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


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
	
	@PostMapping("/adminlogin")
	public LoginStatus login(@RequestBody CustomerLoginData customerLoginData) {

		LoginStatus status = new LoginStatus();
		try {
			AdminService adminservice=new AdminService();
			status =adminservice.login(customerLoginData.getUsername(), customerLoginData.getPassword());
			status.setMesssageIfAny("successfully login");
			status.setStatus(true);
			
		} catch (CustomerServiceException e) {
			status.setMesssageIfAny(e.getMessage());
		}
		return status;

	}
	
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
