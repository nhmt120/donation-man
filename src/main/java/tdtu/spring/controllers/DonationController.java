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
import tdtu.spring.models.Donation;
import tdtu.spring.models.Project;
import tdtu.spring.services.AccountService;
import tdtu.spring.services.DonationService;
import tdtu.spring.services.ProjectService;

@Controller
@RequestMapping("/donations")
public class DonationController {

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
	@PostMapping("/add")
	public String saveDonation(@ModelAttribute("donation") Donation donation) {

		int amount = donation.getAmount();
//	int accountId = donation.getAccount().getId();
//	int projectId = donation.getProject().getId();

		Project project = projectService.get(2); // set 2 for testing
		Account account = accountService.get(2); // set 2 for testing

		Donation newDonation = new Donation(amount);
		newDonation.setProject(project);
		newDonation.setAccount(account);

		donationService.save(newDonation);
		return "redirect:/";
	}

}
