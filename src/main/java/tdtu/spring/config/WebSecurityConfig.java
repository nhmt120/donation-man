package tdtu.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import tdtu.spring.services.AccountService;
import tdtu.spring.services.UserDetailsServiceImpl;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  AccountService accountService;
  
	@Autowired
	UserDetailsServiceImpl userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http
              .authorizeRequests()
                .antMatchers("/", "/accounts/add").permitAll() // allow everyone to access these mappings
                .anyRequest().authenticated() // every other request has to be authenticated to get access to 
                .and()
              .formLogin()
              	.loginProcessingUrl("/j_spring_security_check")
              	.loginPage("/login") // allow authentication with this custom login form
              	.defaultSuccessUrl("/", true)
              	.failureUrl("/login?error=true")
              	.usernameParameter("username")//
        				.passwordParameter("password")
              	.permitAll() // Tất cả đều được truy cập vào địa chỉ này
              	.and()
              .logout()
              	.permitAll();
  }
  
  @Override
  public void configure(WebSecurity web) throws Exception {
  	// ignore resource static files 
      web
              .ignoring()
              	.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", 
            								 "/images/**","/plugins/**","/fonts/**","/webfonts/**","/scss/**")
              	;
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
      // Password encoder for Spring Security to encode password
      return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService) // provide a userService for Spring Security
          .passwordEncoder(passwordEncoder()); // and provide Password Encoder object
  }
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// TODO Auto-generated method stub
//		super.configure(http);
//		http
//				.authorizeRequests()
//					.antMatchers("/", "/accounts/add").permitAll()
//					.anyRequest().authenticated()
//					.and()
//				.formLogin()
//					.loginPage("/login").permitAll()
//					.and()
//				.logout()
//					.permitAll();
//	}
	
//	@Bean
//	@Override
//	public UserDetailsService userDetailsService() {
//		UserDetails user =
//			 User.withDefaultPasswordEncoder()
//				.username("Raito")
//				.password("rai")
//				.roles("USER")
//				.build();
//
//		return new InMemoryUserDetailsManager(user);
//	}
	
//	@Autowired
//  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//      auth
//          .inMemoryAuthentication()
//              .withUser("user").password("user123").roles("USER").and()
//              .withUser("admin").password("admin123").roles("USER", "ADMIN");
//  }

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		super.configure(auth);
//		auth
//			.inMemoryAuthentication()
//      	.withUser("user").password("user123").roles("USER").and()
//      	.withUser("admin").password("admin123").roles("USER", "ADMIN");
//	}
//	
//	

}
