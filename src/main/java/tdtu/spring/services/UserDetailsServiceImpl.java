package tdtu.spring.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tdtu.spring.models.Account;
import tdtu.spring.models.CustomUser;
import tdtu.spring.repositories.AccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	AccountRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Account account = repo.findByUsername(username);

		if (account == null) {
			System.out.println("User not found! " + username);
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}

		System.out.println("Found User: " + account);

		
		// get roles
//	// [ROLE_USER, ROLE_ADMIN,..]
//	List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());

//	List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//	if (roleNames != null) {
//		for (String role : roleNames) {
//			// ROLE_USER, ROLE_ADMIN,..
//			GrantedAuthority authority = new SimpleGrantedAuthority(role);
//			grantList.add(authority);
//		}
//	}
		Set<String> roles = account.getRoles();
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//		grantList.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		for (String role : roles) {
			grantList.add(new SimpleGrantedAuthority(role));
		}

		UserDetails userDetails = (UserDetails) new CustomUser(account.getUsername(), //
				account.getPassword(), grantList, account.getId());

		return userDetails;

	}

}
