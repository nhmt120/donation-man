package tdtu.spring.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		repo.updateById(account.getId(), account.getName(), account.getUsername(), account.getRole(), account.getBalance());
	}

	public void updateBalance(int id, int newBalance) {
		repo.updateBalanceById(id, newBalance);
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////		Account account = repo.findByUsername(username);
////		if (account == null) {
////			throw new UsernameNotFoundException(username + " not found.");
////		}
//////		return account.map(UserDetailsImpl::new).get();
////		return new AccountDetails(account);
//		
//		Account account = repo.findByUsername(username);
//
//		if (account == null) {
//			System.out.println("User not found! " + username);
//			throw new UsernameNotFoundException("User " + username + " was not found in the database");
//		}
//
//		System.out.println("Found User: " + account);
//
////		// [ROLE_USER, ROLE_ADMIN,..]
////		List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
//
////		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
////		if (roleNames != null) {
////			for (String role : roleNames) {
////				// ROLE_USER, ROLE_ADMIN,..
////				GrantedAuthority authority = new SimpleGrantedAuthority(role);
////				grantList.add(authority);
////			}
////		}
//
//		UserDetails userDetails = (UserDetails) new User(account.getUsername(), //
//				account.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
//
//		return userDetails;
//		
//	}
}
