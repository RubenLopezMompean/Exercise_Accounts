package com.exercise.springbootjpa.test.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import com.exercise.springbootjpa.controller.MainController;
import com.exercise.springbootjpa.dao.AccountDAO;
import com.exercise.springbootjpa.exception.AccountTransactionException;
import com.exercise.springbootjpa.form.CreateAccountForm;
import com.exercise.springbootjpa.form.SendMoneyForm;
import com.exercise.springbootjpa.model.AccountInfo;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

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
	
	private static final String RESPONSE_ACCOUNTS_PAGE = "accountsPage";
	
	private static final String RESPONSE_SEND_MONEY_PAGE_GET_OK = "sendMoneyPage";
	
	private static final String RESPONSE_SEND_MONEY_PAGE_POST_OK = "redirect:/";
	
	private static final String RESPONSE_SEND_MONEY_PAGE_POST_ERROR = "/sendMoneyPage";

	private static final String RESPONSE_CREATE_ACCOUNT_GET = "createAccount";

	private static final String RESPONSE_CREATE_ACCOUNT_POST_OK = "redirect:/";

	private static final String RESPONSE_CREATE_ACCOUNT_POST_ERROR = "/createAccount";
	
	private static final String RESPONSE_PROCESS_DELETE_ACCOUNT = "redirect:/";

	@InjectMocks
	private MainController mainController;
	
	@Mock
	private AccountDAO accountDAO;
	
	@Mock
	private Model model;
	
	
	@Before
	public void init() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void showAccountsTest() {
		List<AccountInfo> accountInfoList = listAccountInfo();
		Mockito.when(accountDAO.listAccountInfo()).thenReturn(accountInfoList);
		String response = mainController.showAccounts(model);
		Mockito.verify(accountDAO).listAccountInfo();
		Mockito.verify(model).addAttribute("accountInfos", accountInfoList);
		assertEquals(RESPONSE_ACCOUNTS_PAGE, response);
	}
	
	@Test
	public void viewSendMoneyPageTest() {

		List<AccountInfo> accountInfoList = listAccountInfo();
		Mockito.when(accountDAO.listAccountInfo()).thenReturn(accountInfoList);
		String response = mainController.viewSendMoneyPage(model);
		Mockito.verify(accountDAO).listAccountInfo();
		Mockito.verify(model).addAttribute("accountInfos", accountInfoList);
		assertEquals(RESPONSE_SEND_MONEY_PAGE_GET_OK, response);
	}

	@Test
	public void processSendMoneyPageTest() throws AccountTransactionException {
		
		SendMoneyForm sendMoneyForm = buildSendMoneyForm();
		Mockito.doNothing().when(accountDAO).sendMoney(ACCOUNT_INFO_ID, ACCOUNT_INFO_ID_2, AMOUNT);
		String response = mainController.processSendMoneyPage(model, sendMoneyForm);
		Mockito.verify(accountDAO).sendMoney(ACCOUNT_INFO_ID, ACCOUNT_INFO_ID_2, AMOUNT);
		assertEquals(RESPONSE_SEND_MONEY_PAGE_POST_OK, response);
	}

	@Test
	public void processSendMoneyPage_returnAccountTransactionExceptionTest() throws AccountTransactionException {
		
		SendMoneyForm sendMoneyForm = buildSendMoneyForm();
		Mockito.doThrow(AccountTransactionException.class).when(accountDAO).sendMoney(ACCOUNT_INFO_ID, ACCOUNT_INFO_ID_2, AMOUNT);
		String response = mainController.processSendMoneyPage(model, sendMoneyForm);
		Mockito.verify(accountDAO).sendMoney(ACCOUNT_INFO_ID, ACCOUNT_INFO_ID_2, AMOUNT);
		assertEquals(RESPONSE_SEND_MONEY_PAGE_POST_ERROR, response);
	}
	
	@Test
	public void viewCreateAccountPageTest() {

		String response = mainController.viewCreateAccountPage(model);
		assertEquals(RESPONSE_CREATE_ACCOUNT_GET, response);
	}

	@Test
	public void processCreateAccountPageTest() throws AccountTransactionException {
		
		CreateAccountForm createAccountForm = buildCreateAccountForm();
		Mockito.doNothing().when(accountDAO).insertAccount(createAccountForm);
		String response = mainController.processCreateAccountPage(model, createAccountForm);
		Mockito.verify(accountDAO).insertAccount(createAccountForm);
		assertEquals(RESPONSE_CREATE_ACCOUNT_POST_OK, response);
	}

	@Test
	public void processCreateAccountPageTest_returnException() throws AccountTransactionException {
		
		CreateAccountForm createAccountForm = buildCreateAccountForm();
		Mockito.doThrow(AccountTransactionException.class).when(accountDAO).insertAccount(createAccountForm);
		String response = mainController.processCreateAccountPage(model, createAccountForm);
		Mockito.verify(accountDAO).insertAccount(createAccountForm);
		assertEquals(RESPONSE_CREATE_ACCOUNT_POST_ERROR, response);
	}

	@Test
	public void processDeleteAccountTest() throws AccountTransactionException {
		
		Mockito.doNothing().when(accountDAO).deleteAccount(ACCOUNT_INFO_ID);
		String response = mainController.processDeleteAccount(model, String.valueOf(ACCOUNT_INFO_ID));
		Mockito.verify(accountDAO).deleteAccount(ACCOUNT_INFO_ID);
		assertEquals(RESPONSE_PROCESS_DELETE_ACCOUNT, response);
	}

	@Test
	public void processDeleteAccountTest_returnAccountTransactionException() throws AccountTransactionException {
		
		Mockito.doThrow(AccountTransactionException.class).when(accountDAO).deleteAccount(ACCOUNT_INFO_ID);
		String response = mainController.processDeleteAccount(model, String.valueOf(ACCOUNT_INFO_ID));
		Mockito.verify(accountDAO).deleteAccount(ACCOUNT_INFO_ID);
		assertEquals(RESPONSE_PROCESS_DELETE_ACCOUNT, response);
	}
	
	private List<AccountInfo> listAccountInfo() {
		List<AccountInfo> accountInfoList = new ArrayList<>();
		AccountInfo accountInfo_one = new AccountInfo(ACCOUNT_INFO_ID, FULL_NAME, CURRENCY, BALANCE, TREASURY);
		
		AccountInfo accountInfo_two = new AccountInfo(ACCOUNT_INFO_ID_2, FULL_NAME_2, CURRENCY, BALANCE_2, TREASURY_2);

		accountInfoList.add(accountInfo_one);
		accountInfoList.add(accountInfo_two);
		
		return accountInfoList;
	}
	
	private SendMoneyForm buildSendMoneyForm() {
		return new SendMoneyForm(ACCOUNT_INFO_ID, ACCOUNT_INFO_ID_2, AMOUNT);
	}
	
	private CreateAccountForm buildCreateAccountForm() {
		return new CreateAccountForm(FULL_NAME, CURRENCY, AMOUNT, TREASURY);
	}
	
	
}
