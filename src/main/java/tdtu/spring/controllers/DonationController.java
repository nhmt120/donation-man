package tdtu.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tdtu.spring.models.Account;
import tdtu.spring.models.Donation;
import tdtu.spring.models.Project;
import tdtu.spring.services.AccountService;
import tdtu.spring.services.DonationService;
import tdtu.spring.services.ProjectService;

@Controller
@RequestMapping("/donations")
public class DonationController {
	
	@Autowired
	private Account account;

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

//	@GetMapping("/add")
//	public String showAddDonation(Model model) {
//		model.addAttribute("donation", new Donation());
//		return "add-donation";
//	}
//
	@PostMapping("/add/{projectId}")
	public String saveDonation(@ModelAttribute(value = "donation") Donation donation, @PathVariable int projectId) {

		int amount = donation.getAmount();
//		int accountId = account.getId();

		Project project = projectService.get(projectId); // set 2 for testing
		Account account = accountService.get(28); // set 2 for testing

		Donation newDonation = new Donation(amount);
		newDonation.setProject(project);
		newDonation.setAccount(account);

		donationService.save(newDonation);
		int newfund = donationService.sumAmount(projectId);
		int dnum = donationService.countDonation(projectId);
		projectService.updateCurrentFund(projectId, newfund);
		projectService.updateDonationNum(projectId, dnum);
		
		return "redirect:/projects/" + projectId;
	}

}
