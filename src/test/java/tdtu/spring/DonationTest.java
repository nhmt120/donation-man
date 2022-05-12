package tdtu.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import tdtu.spring.models.Account;
import tdtu.spring.models.Donation;
import tdtu.spring.models.Project;

public class DonationTest extends DonationManApplicationTests {
	@Test
	void addDonation() {

		Account a = new Account("Vinced", "vc", "123");
		Project p = new Project("Project yhies", "edcrfc", 235);
		accountRepository.save(a);
		projectRepository.save(p);
		
		Donation donation = new Donation(150);
		donation.setAccount(a);
		donation.setProject(p);
		
		Donation donation2 = new Donation(250);
		donation2.setAccount(a);
		donation2.setProject(p);
		
		donationRepository.save(donation);
		donationRepository.save(donation2);
		List<Donation> donations = donationRepository.findAll();
		
		System.out.println("Size" + donations.size());
		System.out.println("===================================================================================================");
		System.out.println(donations.get(0));
		System.out.println(donations.get(1));
		System.out.println("===================================================================================================");

		assertEquals(2, donations.size());
	}
	
	@Test
	void getSumAmount() {
		int amount = donationRepository.getTotalAmountByProjectId(44);
		assertEquals(2, amount);
	}
}
