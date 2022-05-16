package tdtu.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tdtu.spring.models.Account;
import tdtu.spring.models.CustomUser;
import tdtu.spring.models.Project;
import tdtu.spring.services.AccountService;
import tdtu.spring.services.ProjectService;

@Controller
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

//	@GetMapping("")
//	public String showAccountList(Model model) {
//		model.addAttribute("accounts", accountService.findAll());
//		return "home";
//	}

	@GetMapping("/detail")
	public String showAccountDetail(Model model) {
		
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int accountId = user.getUserId();
		Account account = accountService.get(accountId);
		List<Project> projects = projectService.findByAccountId(accountId);
		System.out.println(projects);
		model.addAttribute("account", account);
		model.addAttribute("projects", projects);
		
		return "account";
	}

	@PostMapping("/balance")
	public String addBalance(@ModelAttribute(value = "account") Account account) {

		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		int accountId = user.getUserId();

		Account currentAccount = accountService.get(accountId);

		int currentAmount = currentAccount.getBalance();
		int addAmount = account.getBalance();

		int newAmount = currentAmount + addAmount;

		accountService.updateBalance(accountId, newAmount);

		return "redirect:/accounts/detail";
	}

	@GetMapping("/add")
	public String showAddAccount(Model model) {
		model.addAttribute("account", new Account());
		return "add-account";
	}

	@PostMapping("/add")
	public String saveAccount(@ModelAttribute("account") Account account) {
		String name = account.getName();
		String username = account.getUsername();
		String password = passwordEncoder.encode(account.getPassword());
		String role = account.getRole();

		Account newAccount = new Account(name, username, password, role);
		accountService.save(newAccount);

//		System.out.println(newAccount);

		return "redirect:/login";
	}

//	@GetMapping("/update/{id}")
//	public String showUpdateAccount(Model model, @PathVariable(name = "id") int id) {
//		Account account = accountService.get(id);
//		model.addAttribute("account", account);
//		return "update-account";
//	}
//
	@PostMapping("/update")
	public String updateAccount(@ModelAttribute("account") Account account) {
//		int id = account.getId();
//		String name = account.getName();
//		String accountName = account.getUsername();
//
//		Account updatedAccount = new Account(name, accountName, password);
//		updatedAccount.setId(id);
		accountService.update(account);

		return "redirect:/accounts/detail";
	}
//
//	@GetMapping("/delete/{id}")
//	public String deleteAccount(Model model, @PathVariable(name = "id") int id) {
//		accountService.delete(id);
//		return "redirect:/accounts";
//	}
}
