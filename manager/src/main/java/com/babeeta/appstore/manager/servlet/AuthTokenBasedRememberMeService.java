package com.babeeta.appstore.manager.servlet;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component("authTokenBasedRememberMeService")
public class AuthTokenBasedRememberMeService extends AbstractRememberMeServices {

	private AuthTokenRepository authTokenRepository;

	public AuthTokenBasedRememberMeService() {
		super();
		setKey("android-manager.shanghubay.com");
	}

	@Override
	protected void onLoginSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication successfulAuthentication) {
		String token = UUID.randomUUID().toString();
		authTokenRepository.save(token, successfulAuthentication.getName());
		setCookie(new String[] { token }, getTokenValiditySeconds(),
				request, response);
	}

	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens,
			HttpServletRequest request, HttpServletResponse response)
			throws RememberMeAuthenticationException, UsernameNotFoundException {
		String token = extractRememberMeCookie(request);
		if (Strings.isNullOrEmpty(token)) {
			return null;
		} else {
			token = decodeCookie(token)[0];
			String username = authTokenRepository.find(token);
			return getUserDetailsService().loadUserByUsername(username);
		}
	}

	@Autowired
	@Required
	public void setAuthTokenRepository(AuthTokenRepository authTokenRepository) {
		this.authTokenRepository = authTokenRepository;
	}

	@Override
	@Autowired
	@Required
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		// TODO Auto-generated method stub
		super.setUserDetailsService(userDetailsService);
	}
}
