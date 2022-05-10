package tdtu.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import tdtu.spring.models.Account;

public class AccountTest extends DonationManApplicationTests {
	@Test
	void getAccountList() {

		List<Account> accounts = accountRepository.findAll();
		assertEquals(3, accounts.size());
		System.out.println("===================================================================================================");
		System.out.println(accounts.get(1));
		System.out.println("===================================================================================================");
	}
}
