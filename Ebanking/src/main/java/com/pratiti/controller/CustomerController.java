package com.pratiti.controller;


import javax.security.auth.login.AccountException;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratiti.entity.Account;
import com.pratiti.entity.Beneficiary;
import com.pratiti.entity.Customer;
import com.pratiti.entity.Transaction;
import com.pratiti.exception.CustomerServiceException;
import com.pratiti.model.AccountStatementDetail;
import com.pratiti.model.BeneficiaryData;
import com.pratiti.model.CustomerLoginData;
import com.pratiti.model.LoginStatus;
import com.pratiti.model.PasswordDetail;
import com.pratiti.model.RegisterEbanking;
import com.pratiti.model.RegistrationStatus;
import com.pratiti.model.Status;
import com.pratiti.service.AccountService;
import com.pratiti.service.CustomerService;
import com.pratiti.service.TransactionService;

@RestController
@CrossOrigin
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;

	@PostMapping("/createaccount")
	public RegistrationStatus register(@RequestBody Customer customer) {
		RegistrationStatus status = new RegistrationStatus();
		try {
			int id = customerService.register(customer);
			status.setRegisterCustomerId(id);
			status.setStatus(true);
			status.setMesssageIfAny("Registration successfull , admin will verify soon");
			return status;

		} catch (CustomerServiceException e) {
			status.setMesssageIfAny(e.getMessage());
			status.setStatus(false);

			return status;

		}
	}


//	@PostMapping("/login")
//	public LoginStatus login(@RequestBody CustomerLoginData customerData) {
//		
//	}

	@GetMapping("/verifyaccount")
	public RegistrationStatus verifyAccount(@RequestParam("accountnumber") int accountNumber) {
		RegistrationStatus status = new RegistrationStatus();
		try {
			customerService.verifyAccount(accountNumber);
			status.setMesssageIfAny("Otp is sent to registered email");
			status.setStatus(true);
		} catch (CustomerServiceException e) {
			status.setMesssageIfAny(e.getMessage());
			status.setStatus(false);
		}
		return status;

	}

	@PostMapping("/registerebanking")
	// for register internet banking we get the account number verify it by sending
	// opt to mail
	// then we ask for user to enter username and password as he wish then we store
	// the data inside the account table and from there we can get the customer id
	public RegistrationStatus registerEbanking(@RequestBody RegisterEbanking registerEbanking) {
		RegistrationStatus status = new RegistrationStatus();
		try {
			Account acc = new Account();
			acc.setAccountId(registerEbanking.getAccountNumber());
			acc.setUsername(registerEbanking.getUsername());
			acc.setPassword(registerEbanking.getPassword());
			acc.setTransactionPin(registerEbanking.getTransactionPin());
			customerService.registerForEbanking(acc, registerEbanking);
			status.setMesssageIfAny("Registration succesfull!");
			status.setStatus(true);
		} catch (CustomerServiceException e) {
			status.setMesssageIfAny(e.getMessage());
			status.setStatus(false);
		}
		return status;
	}

	@PostMapping("/login")
	public LoginStatus login(@RequestBody CustomerLoginData customerLoginData) {

		LoginStatus status = new LoginStatus();
		try {
			Customer cust = customerService.login(customerLoginData.getUsername(), customerLoginData.getPassword());
			Account custno = accountService.findByUsername(customerLoginData.getUsername());

			status.setMes("successfully login");
			status.setStatus(true);
			status.setCustomerid(custno.getCustomer().getCustomerId());

		} catch (CustomerServiceException e) {
			status.setMes(e.getMessage());
		}
		return status;

	}

	@GetMapping("/generateOtp")
	public Status generateOtp(@RequestParam int accountNumber) {
		Status status = new Status();

		try {
			status = accountService.forgotpassword(accountNumber);
			if (status.getStatus() == true) {
				System.out.println("generate otp");
			}

		} catch (CustomerServiceException e) {
			System.out.println(e.getMessage());
		}

		return status;

	}

	@GetMapping("/accountstatement")
	public AccountStatementDetail accountStatement(@RequestParam String username) {
		AccountStatementDetail accountdetails = new AccountStatementDetail();

		try {
			accountdetails = accountService.findDetails(username);

		} catch (CustomerServiceException e) {
			System.out.println(e.getMessage());
		}
		return accountdetails;

	}

	@GetMapping("/userprofile")
	public Customer customerdetail(@RequestParam int customerid) {
		Customer cust = new Customer();
		try {
			cust = customerService.customerdetail(customerid);
			return cust;
		} catch (Exception e) {
			System.out.println(e);
		}
		return cust;

	}

	@PostMapping("/changepassword")
	public LoginStatus changepassword(@RequestBody PasswordDetail passwordDetail) {

		System.out.println(passwordDetail);
		LoginStatus stat = new LoginStatus();
		try {

			stat = customerService.changepassword(passwordDetail);
			

		} catch (CustomerServiceException e) {
			System.out.println(e.getMessage());
		}
		return stat;

	}
	
	@PostMapping("/addbeneficiary")
	public Status addBeneficiary(@RequestBody BeneficiaryData beneficiaryData) {
		Status status=new Status();
		try {
			//int id =beneficiaryData.getCutomerId();
			//Customer cust=customerService.getCustomer(id);
			Beneficiary beneficiary=new Beneficiary();
			beneficiary.setAccountNumber(beneficiaryData.getAccountNumber());
			beneficiary.setName(beneficiaryData.getName());
			beneficiary.setNickName(beneficiaryData.getNickName());
//			beneficiary.setCustomer(cust);
			customerService.addBeneficiary(beneficiary);
			status.setMesssageIfAny("Successfully added beneficiary !");
			status.setStatus(true);
			
		}
		catch(CustomerServiceException e) {
			status.setMesssageIfAny(e.getMessage());
			status.setStatus(false);
		}
		return status;
		
	}
	@GetMapping("/fetchaccount")
	public Account fetchAccount(@RequestParam("customerid") int id) {
		 Account acc=customerService.getAccount(id);
		 return acc;
	}
	
	@GetMapping("/fetchbeneficiaries")
	public List<Beneficiary> fetchBeneficiary(@RequestParam("customerid") int id) {
		return customerService.fetchBenficiary(id);
	}


	
	
//	@PostMapping("/forgot-user-id")
//	@PostMapping("/forgot-password")
//	@PostMapping("/set-new-password")
//	
//	

}
