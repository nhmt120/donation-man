package tdtu.spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/balance")
	public String addBalance(@RequestParam("amount") int amount) {

		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int accountId = user.getUserId();
		Account currentAccount = accountService.get(accountId);
		int currentAmount = currentAccount.getBalance();
		int newAmount = currentAmount + amount;
		accountService.updateBalance(accountId, newAmount);
		return "redirect:/accounts/detail";
	}

	@GetMapping("/register")
	public String showAddAccount(Model model) {
		model.addAttribute("account", new Account());
		
		System.out.println("show register heerererererer");
		return "register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute("account") Account account) {
		String name = account.getName();
		String username = account.getUsername();
		String password = passwordEncoder.encode(account.getPassword());
		String role = account.getRole();
		
		Account newAccount = new Account(name, username, password, role);
		accountService.save(newAccount);
		
		return "redirect:/login";
	}

	@GetMapping("/update/{id}")
	public String showUpdateAccount(Model model, @PathVariable(name = "id") int id) {
		Account account = accountService.get(id);
		model.addAttribute("account", account);
		
		return "update-account";
	}

	@PostMapping("/update")
	public String updateAccount(@ModelAttribute("account") Account account) {
//		int id = account.getId();
//		String name = account.getName();
//		String accountName = account.getUsername();
//
//		Account updatedAccount = new Account(name, accountName, password);
//		updatedAccount.setId(id);
		accountService.update(account);

		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean isAdmin = accountService.get(user.getUserId()).hasRole("admin");

		if (isAdmin) {
			return "redirect:/admin/accounts";
		}

		return "redirect:/accounts/detail";

	}

//
	@GetMapping("/delete/{id}")
	public String deleteAccount(Model model, @PathVariable(name = "id") int id) {
		accountService.delete(id);
		return "redirect:/admin/accounts";
	}
}
