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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import tdtu.spring.models.Account;
import tdtu.spring.models.CustomUser;
import tdtu.spring.models.Project;
import tdtu.spring.services.AccountService;
import tdtu.spring.services.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private AccountService accountService;

	@GetMapping("")
	public String showProjectList(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(9);

		Page<Project> projectPage = projectService.findPaginatedProject(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("projectPage", projectPage);

		int totalPages = projectPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "running-projects";
	}

	@GetMapping("/add")
	public String showAddProject(Model model) {
		model.addAttribute("project", new Project());
		return "add-project";
	}

	@PostMapping("/add")
	public String saveProject(@ModelAttribute("project") Project project,
			@RequestParam("uploadImage") MultipartFile multipartFile) {

		// get logged in user
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String name = project.getName();
		String description = project.getDescription();
		int targetFund = project.getTargetFund();
		String image = "/images/events/" + StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Account account = accountService.get(user.getUserId());

		Project newProject = new Project(name, description, targetFund, account, image);
		projectService.save(newProject);
		return "redirect:/accounts/detail";
	}

	@GetMapping("/update/{id}")
	public String showUpdateProject(Model model, @PathVariable(name = "id") int id) {
		Project project = projectService.get(id);
		model.addAttribute("project", project);
		return "update-project";
	}

	@PostMapping("/update")
	public String updateProject(@ModelAttribute("project") Project project) {

		int id = project.getId();
		String name = project.getName();
		String description = project.getDescription();
		int targetFund = project.getTargetFund();

		Project updatedProject = new Project(name, description, targetFund);
		updatedProject.setId(id);
		projectService.update(updatedProject);

		return "redirect:/accounts/detail";
	}

	@GetMapping("/delete/{id}")
	public String deleteProject(Model model, @PathVariable(name = "id") int id) {
		projectService.delete(id);
		return "redirect:/accounts/detail";
	}

	@GetMapping("/retrieve/{projectId}")
	public String retrieveFund(@PathVariable(name = "projectId") int projectId) {
		// only project owner can retrieve its funding
		
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = accountService.get(user.getUserId());
		int currentBalance = account.getBalance();

		Project project = projectService.get(projectId);
		int ownerId = project.getAccount().getId();
		int currentFund = project.getCurrentFund();
		String status = project.getStatus();
		
		if (status.equals("Running") && ownerId == account.getId()) {
			accountService.updateBalance(account.getId(), currentBalance + currentFund);
			projectService.updateStatus(projectId, "Finished");
		} else {

		}

		return "redirect:/accounts/detail";
	}
	

}
