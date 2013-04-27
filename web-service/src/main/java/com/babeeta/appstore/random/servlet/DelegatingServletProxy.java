package com.babeeta.appstore.random.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 代理servlet
 * 
 * @author papertiger
 * 
 */
public class DelegatingServletProxy extends GenericServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2857687000011730674L;
	private Servlet proxy;
	private String targetBean;

	private void getServletBean() {
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		this.proxy = (Servlet) wac.getBean(targetBean);
	}

	@Override
	public void init() throws ServletException {
		this.targetBean = getServletName();
		getServletBean();
		proxy.init(getServletConfig());
	}

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		proxy.service(req, res);

	}
}
