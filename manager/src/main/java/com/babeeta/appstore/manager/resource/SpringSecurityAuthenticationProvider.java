package com.babeeta.appstore.manager.resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("authenticationProvider")
public class SpringSecurityAuthenticationProvider implements
		AuthenticationProvider {

	@Override
	public String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
