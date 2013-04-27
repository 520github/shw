package com.babeeta.appstore.manager.servlet;

public interface AuthTokenRepository {

	String find(String token);

	void save(String token, String name);

}
