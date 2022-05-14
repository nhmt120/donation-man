package tdtu.spring.controllers;

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
import org.springframework.web.bind.annotation.ResponseBody;

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

//	@GetMapping("/add")
//	public String showAddDonation(Model model) {
//		model.addAttribute("donation", new Donation());
//		return "add-donation";
//	}
//
	@PostMapping("/add/{projectId}")
	public String saveDonation(@ModelAttribute(value = "donation") Donation donation, @PathVariable int projectId) {

		int amount = donation.getAmount();
//		int accountId = userDetails.getId();
		
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		System.out.println("==============================================================");
		System.out.println(user.getUserId());
		System.out.println("==============================================================");
		
		Project project = projectService.get(projectId);
		Account account = accountService.get(2);

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
