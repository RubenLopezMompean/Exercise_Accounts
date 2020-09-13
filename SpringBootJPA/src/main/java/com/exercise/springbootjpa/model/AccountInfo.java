package com.exercise.springbootjpa.model;

public class AccountInfo {
	
	private Long id;
	private String fullName;
	private String currency;
	private double balance;
	private int treasury;
	
	public AccountInfo() {
		
	}

	public AccountInfo(Long id, String fullName, String currency, double balance, int treasury) {
		this.id = id;
		this.fullName = fullName;
		this.currency = currency;
		this.balance = balance;
		this.treasury = treasury;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getTreasury() {
		return treasury;
	}

	public void setTreasury(int treasury) {
		this.treasury = treasury;
	}

}
