package com.babeeta.appstore.manager.servlet;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

@Component
public class InMemoryAuthTokenRepository implements AuthTokenRepository {

	private final ConcurrentMap<String, String> tokenStore = new ConcurrentHashMap<String, String>();

	@Override
	public String find(String token) {
		return tokenStore.get(token);
	}

	@Override
	public void save(String token, String name) {
		tokenStore.put(token, name);
	}

}
