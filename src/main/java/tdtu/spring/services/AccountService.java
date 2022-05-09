package tdtu.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tdtu.spring.models.Account;
import tdtu.spring.models.Account;
import tdtu.spring.repositories.AccountRepository;

@Service
@Transactional
@Component
public class AccountService {

	@Autowired
	@Lazy
	private AccountRepository repo;

	public List<Account> findAll() {
		return repo.findAll();
	}

	public Account save(Account account) {
		return repo.save(account);
	}

	public Account get(int id) {
		return repo.findById(id).get();
	}

	public void delete(int id) {
		repo.deleteById(id);
	}
	
	public void update(Account account) {
		repo.updateById(account.getId(), account.getName(), account.getUsername(), account.getPassword(), account.getRole());
	}

	public void updateBalance(int id, double newBalance) {
		repo.updateBalanceById(id, newBalance);
	}
}
