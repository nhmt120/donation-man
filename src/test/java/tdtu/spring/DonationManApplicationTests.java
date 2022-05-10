package tdtu.spring;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tdtu.spring.models.Account;
import tdtu.spring.models.Project;
import tdtu.spring.repositories.AccountRepository;
import tdtu.spring.repositories.DonationRepository;
import tdtu.spring.repositories.ProjectRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DonationManApplicationTests {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	DonationRepository donationRepository;

	@BeforeAll
	void beforeAll() {
		accountRepository.saveAll(generateAccounts());
		projectRepository.saveAll(generateProjects());
//		accountRepository.findAll().forEach(System.out::println);
	}

	private static List<Account> generateAccounts() {
		List<Account> accounts = new ArrayList<>();

		Account john = new Account("John", "john", "john123");
		accounts.add(john);
		Account vince = new Account("Vince", "vince", "vince");
		accounts.add(vince);
		Account real = new Account("Real", "real", "really");
		accounts.add(real);

		return accounts;
	}
	
	private static List<Project> generateProjects() {
		List<Project> projects = new ArrayList<>();

		Project p1 = new Project("Project 01", "Donate for Vincent", 250000);
		projects.add(p1);
		Project p2 = new Project("Project Two", "Help Vince buy a 3D printer", 7000000);
		projects.add(p2);
		Project p3 = new Project("Hello Project", "Say hi", 2);
		projects.add(p3);
		Project p4 = new Project("Hello Lorem Ipsum", "Lo lo rem ip ip sum", 19999);
		projects.add(p4);
		Project p5 = new Project("Another Project", "yo", 10);
		projects.add(p5);
		return projects;
	}
	
	@AfterAll
	void afterAll() {
		donationRepository.deleteAll();
		accountRepository.deleteAll();
		projectRepository.deleteAll();
	}

}
