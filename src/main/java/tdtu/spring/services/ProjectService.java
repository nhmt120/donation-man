package tdtu.spring.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	
	public Page<Project> findPaginatedProject(Pageable pageable) {
		List<Project> projects = findAll();
		
    int pageSize = pageable.getPageSize();
    int currentPage = pageable.getPageNumber();
    int startItem = currentPage * pageSize;
    List<Project> list;

    if (projects.size() < startItem) {
        list = Collections.emptyList();
    } else {
        int toIndex = Math.min(startItem + pageSize, projects.size());
        list = projects.subList(startItem, toIndex);
			}

			Page<Project> projectPage = new PageImpl<Project>(list, PageRequest.of(currentPage, pageSize), projects.size());

    return projectPage;
}
}
