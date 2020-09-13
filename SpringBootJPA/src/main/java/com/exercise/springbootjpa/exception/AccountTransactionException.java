package com.exercise.springbootjpa.exception;

public class AccountTransactionException extends Exception {
	
	private static final long serialVersionUID = -3128681006635769411L;
	
	public AccountTransactionException(String message) {
		super(message);
	}

}
