package tdtu.spring.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import tdtu.spring.models.Account;
import tdtu.spring.models.CustomUser;
import tdtu.spring.models.Donation;
import tdtu.spring.models.Project;
import tdtu.spring.services.AccountService;
import tdtu.spring.services.DonationService;
import tdtu.spring.services.ProjectService;

@Controller
@RequestMapping("/donations")
public class DonationController {
//	
//	@Autowired
//	private Account account;

	@Autowired
	private DonationService donationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ProjectService projectService;

//	@GetMapping("")
//	public String showDonationList(Model model) {
//		model.addAttribute("donations", donationService.findAll());
//		return "home";
//	}

	@GetMapping("")
	public String showAccountDonationList(Model model) {
		
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = accountService.get(user.getUserId());
		
		List<Donation> donations = donationService.findByAccountId(account.getId());
		System.out.println(donations);
		
//		sumAmount(projectId, accountId);
		
		
//		model.addAttribute("account", donationService);
//		model.addAttribute("projects", donationService);
//		model.addAttribute("donations", donationService);
		return "donation-list";
	}

//	@GetMapping("/add")
//	public String showAddDonation(Model model) {
//		model.addAttribute("donation", new Donation());
//		return "add-donation";
//	}
//
	@GetMapping("/add/{projectId}")
	public String saveDonation(@RequestParam("amount") int amount, @ModelAttribute(value = "donation") Donation donation, @PathVariable int projectId) {
		
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
//		int amount = donation.getAmount();
		int accountId = user.getUserId();
		
		System.out.println("==============================================================");
		System.out.println(user.getUserId());
		System.out.println("==============================================================");
		
		Project project = projectService.get(projectId);
		Account account = accountService.get(accountId);
		
		if(account.getBalance() < amount) {
			Map<String, Object> uriVariables = new HashMap<>();
			uriVariables.put("insufficient", "true");
			return "redirect:/projects/" + projectId + "?insufficient=true";
		}

		Donation newDonation = new Donation(amount);
		newDonation.setProject(project);
		newDonation.setAccount(account);
		
		int newBalance = account.getBalance() - amount;

		donationService.save(newDonation);
		int newfund = donationService.sumAmount(projectId);
		int dnum = donationService.countDonation(projectId);
		projectService.updateCurrentFund(projectId, newfund);
		projectService.updateDonationNum(projectId, dnum);
		accountService.updateBalance(accountId, newBalance);
		
		return "redirect:/project/" + projectId;
	}

}
