package com.pratiti.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.pratiti.entity.Account;
import com.pratiti.entity.Beneficiary;
import com.pratiti.entity.Customer;
import com.pratiti.entity.Customer.Status;
import com.pratiti.exception.CustomerServiceException;
import com.pratiti.model.LoginStatus;
import com.pratiti.model.PasswordDetail;
import com.pratiti.model.RegisterEbanking;
import com.pratiti.repository.AccountRepository;
import com.pratiti.repository.BeneficiaryRepository;
import com.pratiti.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRespository;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;

	private int registrationOtp;// here we save the otp send to customer for verification

	public int register(Customer customer) {
		if (!customerRespository.existsByAddharCardNumber(customer.getAddharCardNumber())) {
			customer.setStatus(Status.INACTIVE);
			customer.getAddress().setCustomer(customer);// here we are setting the customer column refernce in Address
														// table
			customerRespository.save(customer);
			return customer.getCustomerId();
		} else {
			throw new CustomerServiceException("User already registered");
		}

	}

	private char[] OTP(int len) {
		String numbers = "0123456789";

		Random rndm_method = new Random();

		char[] otp = new char[len];

		for (int i = 0; i < len; i++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		}
		return otp;
	}

	public void verifyAccount(int accountNumber) {
		Optional<Account> acc = accountRepository.findById(accountNumber);
		if (acc.isPresent()) {
			char[] otp = OTP(4);
			registrationOtp = Integer.parseInt(new String(otp));

			System.out.println("your otp is " + registrationOtp);

		} else {

			throw new CustomerServiceException("Account number does not exixt..");
		}

	}

//	public void sendEmail(String toEmail,String subject,String body) {
//		SimpleMailMessage message=new SimpleMailMessage();
//		message.setFrom("amitthelkar7620@gmail.com");
//		message.setTo(toEmail);
//		message.setText(body);
//		message.setSubject(subject);
//		mailSender.send(message);
//		
//		System.out.println("mail sent succesfully....");
//	}

	public void registerForEbanking(Account accountData, RegisterEbanking registerData) {
		Optional<Account> account = accountRepository.findById(accountData.getAccountId());
		if (account.isPresent()) {
			if (registerData.getOtp() == 1234) {
				Account acc = account.get();
				accountRepository.save(accountData);
			} else {
				throw new CustomerServiceException("Please enter correct otp.");
			}

		} else {
			throw new CustomerServiceException("Your account Does not exists contact to admin.");

		}

	}

	public Customer login(String username, String password) {
		if (accountRepository.existsByUsername(username)) {
			Optional<Account> account = accountRepository.findByUsername(username);
			Account accountdata = account.get();
			if (accountdata.getCustomer().getStatus() != Status.INACTIVE) {
				if (password.equals(accountdata.getPassword())) {
					System.out.println("successfully login");
					return accountdata.getCustomer();
				} else {
					throw new CustomerServiceException("Enter correct password");

				}
			} else {
				throw new CustomerServiceException("Account is Locked");

			}

		} else {
			System.out.println("user not exist");
			throw new CustomerServiceException("user not exist");
		}

	}


	public Customer customerdetail(int customerid) {
System.out.println(customerid);
		Optional<Customer> cust = customerRespository.findById(customerid);
		Customer customer = cust.get();
		return customer;
	}

	public LoginStatus changepassword(PasswordDetail passdetail) {

		LoginStatus status = new LoginStatus();
		if (accountRepository.existsByUsername(passdetail.getUsername())) {
			Optional<Account> acc = accountRepository.findByUsername(passdetail.getUsername());
			Account account = acc.get();

			if (passdetail.getOldpassword().equals(account.getPassword())) {
				if (passdetail.getPassword().equals(passdetail.getConfirmpassword())) {
					status.setStatus(true);
					status.setMes("password changed successfully");
					account.setPassword(passdetail.getPassword());

					accountRepository.save(account);
					System.out.println(account.getPassword());

					System.out.println("password changed successfully");

				} else {
					status.setMes("Password Not Match");
					throw new CustomerServiceException("Password Not Match");
				}

			} else {
				status.setMes("Enter Valid Old Password");
				throw new CustomerServiceException("Enter Valid Old Password");
			}
		} else {
			status.setMes("user not present");
			throw new CustomerServiceException("user not present");
		}
		return status;

	}

	
	public Customer getCustomer(int id) {
		Optional<Customer> customer=customerRespository.findById(id);
		if(customer.isPresent()) {
			return customer.get();
		}
		else {
			throw new CustomerServiceException("No such customer id exists");
		}
		
	}

	public void addBeneficiary(Beneficiary beneficiary) {
		beneficiaryRepository.save(beneficiary);	
	}
	
	
	


}
