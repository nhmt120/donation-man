package tdtu.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import tdtu.spring.models.Project;

public class ProjectTest extends DonationManApplicationTests {
	@Test
	void getProjectList() {

		List<Project> projects = projectRepository.findAll();
		assertEquals(5, projects.size());
		System.out.println("===================================================================================================");
		System.out.println(projects.get(1));
		System.out.println("===================================================================================================");
	}
}
