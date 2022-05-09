package tdtu.spring.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tdtu.spring.models.Project;
import tdtu.spring.services.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService service;

	@GetMapping("")
	public String showProjectList(Model model) {
		model.addAttribute("projects", service.findAll());
		return "home";
	}

	@GetMapping("/add")
	public String showAddProject(Model model) {
		model.addAttribute("project", new Project());
		return "add-project";
	}

	@PostMapping("/add")
	public String saveProject(@ModelAttribute("project") Project project) {

		String name = project.getName();
		String description = project.getDescription();
		double targetFund = project.getTargetFund();		

		Project newProject = new Project(name, description, targetFund);
		service.save(newProject);
		return "redirect:/projects";
	}

	@GetMapping("/update/{id}")
	public String showUpdateProject(Model model, @PathVariable(name = "id") int id) {
		Project project = service.get(id);
		model.addAttribute("project", project);
		return "update-project";
	}

	@PostMapping("/update")
	public String updateProject(@ModelAttribute("project") Project project) {
		int id = project.getId();
		String name = project.getName();
		String description = project.getDescription();
		double targetFund = project.getTargetFund();

		Project updatedProject = new Project(name, description, targetFund);
		updatedProject.setId(id);
		service.update(updatedProject);
		
		return "redirect:/projects";
	}

	@GetMapping("/delete/{id}")
	public String deleteProject(Model model, @PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/projects";
	}
	
	//////////// test pagination
	@GetMapping("/project-list")
  public String listProjects(
    Model model, 
    @RequestParam("page") Optional<Integer> page, 
    @RequestParam("size") Optional<Integer> size) {
      int currentPage = page.orElse(1);
      int pageSize = size.orElse(3);

      Page<Project> projectPage = service.findPaginatedProject(PageRequest.of(currentPage - 1, pageSize));

      model.addAttribute("projectPage", projectPage);

      int totalPages = projectPage.getTotalPages();
      if (totalPages > 0) {
          List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
              .boxed()
              .collect(Collectors.toList());
          model.addAttribute("pageNumbers", pageNumbers);
      }

      return "home";
  }
	
}
