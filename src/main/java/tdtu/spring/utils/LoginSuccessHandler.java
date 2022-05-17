package tdtu.spring.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import tdtu.spring.models.Account;
import tdtu.spring.models.CustomUser;
import tdtu.spring.services.AccountService;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	AccountService accountService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

		CustomUser user = (CustomUser) authentication.getPrincipal();
		Account account = accountService.get(user.getUserId());

		String redirectURL = request.getContextPath();

		if (account.hasRole("admin")) {

			redirectURL = "";
		} else {

			redirectURL = "";
		}

		response.sendRedirect(redirectURL);

	}

}