package tdtu.spring.models;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

    private final int userId;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, int userId) {
        super(username, password, authorities);
        this.userId = userId;
    }

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return super.toString() + "accountId: " + userId;
		}

		public int getUserId() {
			return userId;
		}
    
}