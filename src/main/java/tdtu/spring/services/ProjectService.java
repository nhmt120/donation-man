package tdtu.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tdtu.spring.models.Project;
import tdtu.spring.repositories.ProjectRepository;

@Service
@Transactional
@Component
public class ProjectService {

	@Autowired
	@Lazy
	private ProjectRepository repo;

	public List<Project> findAll() {
		return repo.findAll();
	}

	public Project save(Project project) {
		return repo.save(project);
	}

	public Project get(int id) {
		return repo.findById(id).get();
	}

	public void delete(int id) {
		repo.deleteById(id);
	}

	public void update(Project project) {
		repo.updateById(project.getId(), project.getName(), project.getDescription(), project.getTargetFund(), project.getCurrentFund());
	}
}
