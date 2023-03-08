package com.pratiti.model;

public class BeneficiaryData {
	
	private int accountNumber;
	private String name;
	private String nickName;
	private int customerId;
	
	public int getCutomerId() {
		return customerId;
	}
	public void setCutomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	

}
