package tdtu.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

//	@GetMapping("/add")
//	public String showAddProject(Model model) {
//		model.addAttribute("project", new Project());
//		return "add-project";
//	}
//
//	@PostMapping("/add")
//	public String saveProject(@ModelAttribute("project") Project project) {
//
//		String name = project.getName();
//		String description = project.getDescription();
//		int targetFund = project.getTargetFund();
//		int currentFund = project.getCurrentFund();
//
//		Project newProject = new Project(name, description, targetFund, currentFund);
//		service.save(newProject);
//		return "redirect:/projects";
//	}
//
//	@GetMapping("/update/{id}")
//	public String showUpdateProject(Model model, @PathVariable(name = "id") int id) {
//		Project project = service.get(id);
//		model.addAttribute("project", project);
//		return "update-project";
//	}
//
//	@PostMapping("/update")
//	public String updateProject(@ModelAttribute("project") Project project) {
//		String name = project.getName();
//		String description = project.getDescription();
//		int targetFund = project.getTargetFund();
//		int currentFund = project.getCurrentFund();
//
//		Project updatedProject = new Project(name, description, targetFund, currentFund);
//		service.update(updatedProject);
//		return "redirect:/projects";
//	}
//
//	@GetMapping("/delete/{id}")
//	public String deleteProject(Model model, @PathVariable(name = "id") int id) {
//		service.delete(id);
//		return "redirect:/projects";
//	}
}
