package tdtu.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tdtu.spring.models.Account;
import tdtu.spring.services.AccountService;

@Controller
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService service;

//	@GetMapping("")
//	public String showAccountList(Model model) {
//		model.addAttribute("accounts", service.findAll());
//		return "home";
//	}

	@GetMapping("/add")
	public String showAddAccount(Model model) {
		model.addAttribute("account", new Account());
		return "add-account";
	}

	@PostMapping("/add")
	public String saveAccount(@ModelAttribute("account") Account account) {
		String name = account.getName();
		String username = account.getUsername();
		String password = account.getPassword();
		String role = account.getRole();

		Account newAccount = new Account(name, username, password, role);
		service.save(newAccount);
		return "add-account";
	}

//	@GetMapping("/update/{id}")
//	public String showUpdateAccount(Model model, @PathVariable(name = "id") int id) {
//		Account account = service.get(id);
//		model.addAttribute("account", account);
//		return "update-account";
//	}
//
//	@PostMapping("/update")
//	public String updateAccount(@ModelAttribute("account") Account account) {
//		int id = account.getId();
//		String name = account.getName();
//		String accountName = account.getUsername();
//		String password = account.getPassword();
//
//		Account updatedAccount = new Account(name, accountName, password);
//		updatedAccount.setId(id);
//		service.update(updatedAccount);
//
//		return "redirect:/accounts";
//	}
//
//	@GetMapping("/delete/{id}")
//	public String deleteAccount(Model model, @PathVariable(name = "id") int id) {
//		service.delete(id);
//		return "redirect:/accounts";
//	}
}
