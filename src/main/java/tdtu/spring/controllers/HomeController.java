package tdtu.spring.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import tdtu.spring.models.Account;
import tdtu.spring.models.CustomUser;
import tdtu.spring.models.Donation;
import tdtu.spring.models.Project;
import tdtu.spring.services.AccountService;
import tdtu.spring.services.ProjectService;

@Controller
public class HomeController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private AccountService accountService;

	@GetMapping("/")
	public String showProjectList(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(3);

		Page<Project> projectPage = projectService.findPaginatedProject(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("projectPage", projectPage);

		int totalPages = projectPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "home";
	}

	@GetMapping("/projects/{id}")
	public String showProjectDetail(Model model, @PathVariable(name = "id") int id) {
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		int accountId = user.getUserId();

		Account account = accountService.get(accountId);
		Project project = projectService.get(id);
		model.addAttribute("project", project);
		model.addAttribute("account", account);
		model.addAttribute("donation", new Donation());
		return "project";
	}

	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}
}
