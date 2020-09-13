package com.exercise.springbootjpa.test.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeast;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.exercise.springbootjpa.dao.AccountDAO;
import com.exercise.springbootjpa.entity.Account;
import com.exercise.springbootjpa.exception.AccountTransactionException;
import com.exercise.springbootjpa.model.AccountInfo;


@RunWith(MockitoJUnitRunner.class)
public class AccountDAOTest {

	private static final Long ACCOUNT_INFO_ID = 12345L;

	private static final String FULL_NAME = "Paul";

	private static final String CURRENCY = "E";

	private static final double BALANCE = 5000.00;

	private static final int TREASURY = 1;

	private static final Long ACCOUNT_INFO_ID_2 = 12346L;

	private static final String FULL_NAME_2 = "Maria";

	private static final double BALANCE_2 = 3000.00;

	private static final int TREASURY_2 = 0;

	private static final Double AMOUNT = 100.00;

	private static final Double AMOUNT_DOLLAR_110 = 110.00;

	private static final Double AMOUNT_DOLLAR_90 = 90.00;

	private static final Double AMOUNT_DOLLAR_121 = 121.00;

	private static final Double AMOUNT_POUND_90 = 90.00;

	private static final Double AMOUNT_POUND_99 = 99.00;

	private static final Double AMOUNT_POUND_110 = 110.00;

	@InjectMocks
	private AccountDAO accountDAO;
	
	@Mock
	private EntityManager entityManager;
	
//	@Mock
//	private AccountDAO accountDAOMock;
	
	@Before
	public void init() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Test
	public void listAccountInfoTest() throws AccountTransactionException {
		
		String sql = "Select new " + AccountInfo.class.getName() 
				+ "(e.id, e.fullName, e.currency, e.balance, e.treasury) " //
				+ " from " + Account.class.getName() + " e ";
		TypedQuery<AccountInfo> queryByMock = (TypedQuery<AccountInfo>) Mockito.mock(TypedQuery.class);
		Mockito.when(entityManager.createQuery(sql,AccountInfo.class)).thenReturn(queryByMock);
		List<AccountInfo> accountInfo = listAccountInfo();
		Mockito.when(queryByMock.getResultList()).thenReturn(accountInfo);
		List<AccountInfo> response = accountDAO.listAccountInfo();
		assertEquals(accountInfo, response);
		assertEquals(response.size(), 2);
	}
	
	
	
	@Test
	public void sendMoneyWhenCurrencyEqualsTest() throws AccountTransactionException {
		
		AccountDAO accountDAOMock = Mockito.spy(accountDAO);

		Account accountInfo_one = new Account();
		accountInfo_one.setId(1L);
		accountInfo_one.setCurrency("E");
		
		Account accountInfo_two = new Account();
		accountInfo_two.setId(2L);
		accountInfo_two.setCurrency("E");

		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID)).thenReturn(accountInfo_one);
		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID_2)).thenReturn(accountInfo_two);
		Mockito.doNothing().when(accountDAOMock).addAmount(anyLong(), anyDouble());
		
		accountDAOMock.sendMoney(ACCOUNT_INFO_ID, ACCOUNT_INFO_ID_2, AMOUNT);
		
		Mockito.verify(accountDAOMock, atLeast(2)).findById(ACCOUNT_INFO_ID);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID_2, AMOUNT);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID, -AMOUNT);
		
	}
	
	@Test
	public void sendMoneyWhenCurrencyNotEquals_E_to_D_Test() throws AccountTransactionException {
		
		AccountDAO accountDAOMock = Mockito.spy(accountDAO);

		Account accountInfo_one = new Account();
		accountInfo_one.setId(1L);
		accountInfo_one.setCurrency("E");
		
		Account accountInfo_two = new Account();
		accountInfo_two.setId(2L);
		accountInfo_two.setCurrency("D");

		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID)).thenReturn(accountInfo_one);
		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID_2)).thenReturn(accountInfo_two);
		Mockito.doNothing().when(accountDAOMock).addAmount(anyLong(), anyDouble());
		
		accountDAOMock.sendMoney(ACCOUNT_INFO_ID, ACCOUNT_INFO_ID_2, AMOUNT);
		
		Mockito.verify(accountDAOMock, atLeast(2)).findById(ACCOUNT_INFO_ID);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID_2, AMOUNT_DOLLAR_110);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID, -AMOUNT);
		
	}
	
	@Test
	public void sendMoneyWhenCurrencyNotEquals_E_to_P_Test() throws AccountTransactionException {
		
		AccountDAO accountDAOMock = Mockito.spy(accountDAO);

		Account accountInfo_one = new Account();
		accountInfo_one.setId(1L);
		accountInfo_one.setCurrency("E");
		
		Account accountInfo_two = new Account();
		accountInfo_two.setId(2L);
		accountInfo_two.setCurrency("P");

		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID)).thenReturn(accountInfo_one);
		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID_2)).thenReturn(accountInfo_two);
		Mockito.doNothing().when(accountDAOMock).addAmount(anyLong(), anyDouble());
		
		accountDAOMock.sendMoney(ACCOUNT_INFO_ID, ACCOUNT_INFO_ID_2, AMOUNT);
		
		Mockito.verify(accountDAOMock, atLeast(2)).findById(ACCOUNT_INFO_ID);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID_2, AMOUNT_POUND_90);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID, -AMOUNT);
		
	}
	
	@Test
	public void sendMoneyWhenCurrencyNotEquals_D_to_E_Test() throws AccountTransactionException {
		
		AccountDAO accountDAOMock = Mockito.spy(accountDAO);

		Account accountInfo_one = new Account();
		accountInfo_one.setId(1L);
		accountInfo_one.setCurrency("D");
		
		Account accountInfo_two = new Account();
		accountInfo_two.setId(2L);
		accountInfo_two.setCurrency("E");

		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID)).thenReturn(accountInfo_one);
		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID_2)).thenReturn(accountInfo_two);
		Mockito.doNothing().when(accountDAOMock).addAmount(anyLong(), anyDouble());
		
		accountDAOMock.sendMoney(ACCOUNT_INFO_ID, ACCOUNT_INFO_ID_2, AMOUNT);
		
		Mockito.verify(accountDAOMock, atLeast(2)).findById(ACCOUNT_INFO_ID);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID_2, AMOUNT_DOLLAR_90);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID, -AMOUNT);
		
	}
	
	@Test
	public void sendMoneyWhenCurrencyNotEquals_D_to_P_Test() throws AccountTransactionException {
		
		AccountDAO accountDAOMock = Mockito.spy(accountDAO);

		Account accountInfo_one = new Account();
		accountInfo_one.setId(1L);
		accountInfo_one.setCurrency("D");
		
		Account accountInfo_two = new Account();
		accountInfo_two.setId(2L);
		accountInfo_two.setCurrency("P");

		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID)).thenReturn(accountInfo_one);
		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID_2)).thenReturn(accountInfo_two);
		Mockito.doNothing().when(accountDAOMock).addAmount(anyLong(), anyDouble());
		
		accountDAOMock.sendMoney(ACCOUNT_INFO_ID, ACCOUNT_INFO_ID_2, AMOUNT);
		
		Mockito.verify(accountDAOMock, atLeast(2)).findById(ACCOUNT_INFO_ID);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID_2, AMOUNT_POUND_99);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID, -AMOUNT);
		
	}
	
	@Test
	public void sendMoneyWhenCurrencyNotEquals_P_to_E_Test() throws AccountTransactionException {
		
		AccountDAO accountDAOMock = Mockito.spy(accountDAO);

		Account accountInfo_one = new Account();
		accountInfo_one.setId(1L);
		accountInfo_one.setCurrency("P");
		
		Account accountInfo_two = new Account();
		accountInfo_two.setId(2L);
		accountInfo_two.setCurrency("E");

		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID)).thenReturn(accountInfo_one);
		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID_2)).thenReturn(accountInfo_two);
		Mockito.doNothing().when(accountDAOMock).addAmount(anyLong(), anyDouble());
		
		accountDAOMock.sendMoney(ACCOUNT_INFO_ID, ACCOUNT_INFO_ID_2, AMOUNT);
		
		Mockito.verify(accountDAOMock, atLeast(2)).findById(ACCOUNT_INFO_ID);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID_2, AMOUNT_POUND_110);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID, -AMOUNT);
		
	}
	
	@Test
	public void sendMoneyWhenCurrencyNotEquals_P_to_D_Test() throws AccountTransactionException {
		
		AccountDAO accountDAOMock = Mockito.spy(accountDAO);

		Account accountInfo_one = new Account();
		accountInfo_one.setId(1L);
		accountInfo_one.setCurrency("P");
		
		Account accountInfo_two = new Account();
		accountInfo_two.setId(2L);
		accountInfo_two.setCurrency("D");

		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID)).thenReturn(accountInfo_one);
		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID_2)).thenReturn(accountInfo_two);
		Mockito.doNothing().when(accountDAOMock).addAmount(anyLong(), anyDouble());
		
		accountDAOMock.sendMoney(ACCOUNT_INFO_ID, ACCOUNT_INFO_ID_2, AMOUNT);
		
		Mockito.verify(accountDAOMock, atLeast(2)).findById(ACCOUNT_INFO_ID);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID_2, AMOUNT_DOLLAR_121);
		Mockito.verify(accountDAOMock).addAmount(ACCOUNT_INFO_ID, -AMOUNT);
		
	}
	
	@Test
	public void addAmountWhenFindByIdAndReturnNullTest() throws AccountTransactionException {
		
		AccountDAO accountDAOMock = Mockito.spy(accountDAO);
		
		Account accountInfo_one = new Account();
		accountInfo_one.setId(ACCOUNT_INFO_ID);
		accountInfo_one.setCurrency("E");
		
		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID)).thenReturn(null);
		
		try {
			accountDAO.addAmount(ACCOUNT_INFO_ID, AMOUNT);
		} catch(AccountTransactionException e) {
			assertEquals(e.getMessage(), "Account not found " + ACCOUNT_INFO_ID);
		} finally {
			Mockito.verify(accountDAOMock).findById(ACCOUNT_INFO_ID);
		}
	}
	
	@Test
	public void addAmountWhenNotTreasuryTest() throws AccountTransactionException {
		
		AccountDAO accountDAOMock = Mockito.spy(accountDAO);
		
		Account accountInfo_one = new Account();
		accountInfo_one.setId(ACCOUNT_INFO_ID);
		accountInfo_one.setCurrency("E");
		accountInfo_one.setBalance(BALANCE);
		accountInfo_one.setTreasury(0);
		
		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID)).thenReturn(accountInfo_one);
		
		accountDAO.addAmount(ACCOUNT_INFO_ID, AMOUNT);
		Mockito.verify(accountDAOMock).findById(ACCOUNT_INFO_ID);
	}
	
	@Test
	public void addAmountWhenTreasuryAndBalanceNegativeTest() throws AccountTransactionException {
		
		AccountDAO accountDAOMock = Mockito.spy(accountDAO);
		
		Account accountInfo_one = new Account();
		accountInfo_one.setId(ACCOUNT_INFO_ID);
		accountInfo_one.setCurrency("E");
		accountInfo_one.setBalance(-BALANCE);
		accountInfo_one.setTreasury(0);
		
		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID)).thenReturn(accountInfo_one);
		
		try {
			accountDAO.addAmount(ACCOUNT_INFO_ID, AMOUNT);
		} catch(AccountTransactionException e) {
			assertEquals(e.getMessage(), "Only treasury accounts can have a negative balance. The money in the account '" + ACCOUNT_INFO_ID + "' is not enough(" + -BALANCE + ")");
		} finally {
			Mockito.verify(accountDAOMock).findById(ACCOUNT_INFO_ID);
		}
	}
	
	@Test
	public void addAmountTest() throws AccountTransactionException {
		
		AccountDAO accountDAOMock = Mockito.spy(accountDAO);
		
		Account accountInfo_one = new Account();
		accountInfo_one.setId(ACCOUNT_INFO_ID);
		accountInfo_one.setCurrency("E");
		accountInfo_one.setBalance(BALANCE);
		accountInfo_one.setTreasury(1);
		
		Mockito.when(accountDAOMock.findById(ACCOUNT_INFO_ID)).thenReturn(accountInfo_one);
		
		accountDAO.addAmount(ACCOUNT_INFO_ID, AMOUNT);
		Mockito.verify(accountDAOMock).findById(ACCOUNT_INFO_ID);
	}
	
	private List<AccountInfo> listAccountInfo() {
		
		List<AccountInfo> accountInfoList = new ArrayList<>();
		AccountInfo accountInfo_one = new AccountInfo(ACCOUNT_INFO_ID, FULL_NAME, CURRENCY, BALANCE, TREASURY);
		
		AccountInfo accountInfo_two = new AccountInfo(ACCOUNT_INFO_ID_2, FULL_NAME_2, CURRENCY, BALANCE_2, TREASURY_2);

		accountInfoList.add(accountInfo_one);
		accountInfoList.add(accountInfo_two);
		
		return accountInfoList;
	}
}
