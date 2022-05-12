package tdtu.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tdtu.spring.models.Account;

@Configuration
public class ApplicationConfig {
	
	@Bean
	public Account account() {
		return new Account("Minh", "vince", "123");
	}
	
}
