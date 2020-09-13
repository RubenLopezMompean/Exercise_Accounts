package com.exercise.springbootjpa.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.exercise.springbootjpa.dao.AccountDAO;
import com.exercise.springbootjpa.exception.AccountTransactionException;
import com.exercise.springbootjpa.form.CreateAccountForm;
import com.exercise.springbootjpa.form.SendMoneyForm;
import com.exercise.springbootjpa.model.AccountInfo;

@Controller
public class MainController {
	
	@Autowired
	private AccountDAO accountDAO;

	@RequestMapping(value="/", method=RequestMethod.GET, produces = "text/html")
	public String showAccounts(Model model) {
		List<AccountInfo> list = accountDAO.listAccountInfo();
		
		model.addAttribute("accountInfos", list);
		
		return "accountsPage";
	}
	
	@RequestMapping(value="/sendMoney", method=RequestMethod.GET, produces = "text/html")
	public String viewSendMoneyPage(Model model) {
		SendMoneyForm form = new SendMoneyForm();
		List<AccountInfo> list = accountDAO.listAccountInfo();
		
		model.addAttribute("accountInfos", list);
		
		model.addAttribute("sendMoneyForm", form);
		
		return "sendMoneyPage";
	}
	
	@RequestMapping(value="/sendMoney", method=RequestMethod.POST, produces = "text/html")
	public String processSendMoneyPage(Model model, @Valid SendMoneyForm sendMoneyForm) {
		
		System.out.println("Send Money: " + sendMoneyForm.getAmount());
		
		try {
			accountDAO.sendMoney(sendMoneyForm.getFromAccountId(), sendMoneyForm.getToAccountId(), sendMoneyForm.getAmount());
		} catch(AccountTransactionException e) {
			model.addAttribute("errorMessage", "Error: " + e.getMessage());
			return "/sendMoneyPage";
		}
				
		return "redirect:/";
	}
	
	@RequestMapping(value="/createAccount", method=RequestMethod.GET, produces = "text/html")
	public String viewCreateAccountPage(Model model) {
		
		return "createAccount";
	}
	
	@RequestMapping(value="/createAccount", method=RequestMethod.POST, produces = "text/html")
	public String processCreateAccountPage(Model model, @Valid CreateAccountForm createAccountForm) {
		
		System.out.println("Create account");
		
		try {
			accountDAO.insertAccount(createAccountForm);
		} catch(AccountTransactionException e) {
			model.addAttribute("errorMessage", "Error: " + e.getMessage());
			return "/createAccount";
		}
				
		return "redirect:/";
	}
	
	@RequestMapping(value="/deleteAccount", method=RequestMethod.POST, produces = "text/html")
	public String processDeleteAccount(Model model, @RequestParam String id) {
		
		System.out.println("Delete account");
		
		try {
			accountDAO.deleteAccount(Long.valueOf(id));
		} catch(AccountTransactionException e) {
			model.addAttribute("errorMessage", "Error: " + e.getMessage());
			return "redirect:/";
		}
		return "redirect:/";
	}

}
