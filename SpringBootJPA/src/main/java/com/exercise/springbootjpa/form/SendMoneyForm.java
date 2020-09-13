package com.exercise.springbootjpa.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class SendMoneyForm {
	
	private Long fromAccountId;
	private Long toAccountId;
	private String currency;
	private Double amount;
	
	public SendMoneyForm() {
		
	}
	
	public SendMoneyForm(Long fromAccountId, Long toAccountId, Double amount) {
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
	}

	@NotNull(message = "Account id is mandatory")
	public Long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(Long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	@Digits(integer = 12, fraction = 2)
	public Long getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(Long toAccountId) {
		this.toAccountId = toAccountId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@NotNull(message = "Amount is mandatory")
	@Digits(integer = 12, fraction = 2)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
