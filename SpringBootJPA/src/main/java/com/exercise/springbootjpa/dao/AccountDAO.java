package com.exercise.springbootjpa.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.exercise.springbootjpa.entity.Account;
import com.exercise.springbootjpa.exception.AccountTransactionException;
import com.exercise.springbootjpa.form.CreateAccountForm;
import com.exercise.springbootjpa.model.AccountInfo;

@Repository
public class AccountDAO {
	
	@Autowired
	private EntityManager entityManager;
	
	public AccountDAO() {
	}
	
	public Account findById(Long id) {
		return this.entityManager.find(Account.class, id);
	}
	
	public List<AccountInfo> listAccountInfo() {
		String sql = "Select new " + AccountInfo.class.getName() 
				+ "(e.id, e.fullName, e.currency, e.balance, e.treasury) " //
				+ " from " + Account.class.getName() + " e ";
		Query query = entityManager.createQuery(sql, AccountInfo.class);
		
		return query.getResultList();
	}
	
	@Transactional
	public void insertAccount(CreateAccountForm createAccountForm) throws AccountTransactionException {
		String query = ("INSERT INTO account (Full_Name, Currency, Balance, Treasury) VALUES (?,?,?,?)");
		entityManager.createNativeQuery(query)
			.setParameter(1, createAccountForm.getFullName())
			.setParameter(2, createAccountForm.getCurrency())
			.setParameter(3, createAccountForm.getAmount())
			.setParameter(4, createAccountForm.getTreasury())
			.executeUpdate();
    }
	
	@Transactional
	public void deleteAccount(Long id) throws AccountTransactionException {
		String query = ("DELETE FROM account WHERE ID = ?");
		entityManager.createNativeQuery(query).setParameter(1, id).executeUpdate();
	}
	
	@Transactional(propagation = Propagation.MANDATORY)
	public void addAmount(Long id, double amount) throws AccountTransactionException {
		Account account = this.findById(id);
		
		if(account == null) {
			throw new AccountTransactionException("Account not found " + id);
		}
		
		double newBalance = account.getBalance() + amount;
		
		if((newBalance < 0) && (account.getTreasury() == 0)) {
			throw new AccountTransactionException("Only treasury accounts can have a negative balance. The money in the account '" + id + "' is not enough(" + account.getBalance() + ")");
		}
		account.setBalance(newBalance);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AccountTransactionException.class)
	public void sendMoney(Long fromAccountId, Long toAccountId, Double amount) throws AccountTransactionException {
		Account fromAccount = this.findById(fromAccountId);
		Account toAccount = this.findById(toAccountId);
		BigDecimal amountBD = BigDecimal.valueOf(amount);
		
		Double fromAmount = amount;
		
		if(!fromAccount.getCurrency().equals(toAccount.getCurrency())) {
			if(fromAccount.getCurrency().equals("E") && toAccount.getCurrency().equals("D")) {
				amountBD = amountBD.multiply(BigDecimal.valueOf(1.10));
				amountBD = amountBD.setScale(0, RoundingMode.HALF_DOWN);
				amount = amountBD.doubleValue();
			} else if(fromAccount.getCurrency().equals("E") && toAccount.getCurrency().equals("P")) {
				amountBD = amountBD.multiply(BigDecimal.valueOf(0.90));
				amountBD = amountBD.setScale(0, RoundingMode.HALF_DOWN);
				amount = amountBD.doubleValue();
			} else if(fromAccount.getCurrency().equals("D") && toAccount.getCurrency().equals("E")) {
				amountBD = amountBD.multiply(BigDecimal.valueOf(0.90));
				amountBD = amountBD.setScale(0, RoundingMode.HALF_DOWN);
				amount = amountBD.doubleValue();
			} else if(fromAccount.getCurrency().equals("D") && toAccount.getCurrency().equals("P")) {
				amountBD = amountBD.multiply(BigDecimal.valueOf(1.10 * 0.90));
				amountBD = amountBD.setScale(0, RoundingMode.HALF_DOWN);
				amount = amountBD.doubleValue();
			} else if(fromAccount.getCurrency().equals("P") && toAccount.getCurrency().equals("E")) {
				amountBD = amountBD.multiply(BigDecimal.valueOf(1.10));
				amountBD = amountBD.setScale(0, RoundingMode.HALF_DOWN);
				amount = amountBD.doubleValue();
			} else if(fromAccount.getCurrency().equals("P") && toAccount.getCurrency().equals("D")) {
				amountBD = amountBD.multiply(BigDecimal.valueOf(1.10 * 1.10));
				amountBD = amountBD.setScale(0, RoundingMode.HALF_DOWN);
				amount = amountBD.doubleValue();
			}
		}
		addAmount(toAccountId, amount);
		addAmount(fromAccountId, -fromAmount);
	}

}
