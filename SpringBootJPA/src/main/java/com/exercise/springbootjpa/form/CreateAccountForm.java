package com.exercise.springbootjpa.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateAccountForm {

	private String fullName;
	private String currency;
	private Double amount;
	private int treasury;
	
	public CreateAccountForm() {
		
	}
	
	public CreateAccountForm(String fullName, String currency, Double amount, int treasury) {
		this.fullName = fullName;
		this.currency = currency;
		this.amount = amount;
		this.treasury = treasury;
	}
	
	@NotBlank(message = "Name is mandatory")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@NotBlank(message = "Currency is mandatory")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@NotNull(message = "Amount is mandatory")
	@Min(0)
	@Digits(integer = 12, fraction = 2)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@NotNull(message = "Amount is mandatory")
	public int getTreasury() {
		return treasury;
	}

	public void setTreasury(int treasury) {
		this.treasury = treasury;
	}
	
}
