package com.babeeta.appstore.manager.service.impl.apns;

import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;

/**
 * 基于ThreadLocal的ApnsService工厂实现
 * 
 * @author leon
 * 
 */
@Component
public class ApnsServiceFactoryImpl implements ApnsServiceFactory {

	private static final String KEYSTORE_PRODUCTION = "apns/pushproduct.p12";

	private static final String KEYSTORE_SANDBOX = "apns/pushsandbox.p12";
	private String password;
	private boolean sandBox;
	private final ThreadLocal<ApnsService> threadLocal = new ThreadLocal<ApnsService>() {
		@Override
		protected ApnsService initialValue() {
			InputStream stream = Thread
					.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(
							sandBox ? KEYSTORE_SANDBOX : KEYSTORE_PRODUCTION);

			ApnsServiceBuilder builder = APNS.newService()
					.withCert(stream, password)
					.withReconnectPolicy(new ApnsTimeBasedReconnectPolicy());

			if (sandBox) {
				return builder.withSandboxDestination().build();
			} else {
				return builder.withProductionDestination().build();
			}
		}

	};

	@Override
	public ApnsService getInstance() {
		return threadLocal.get();
	}

	public boolean isSandBox() {
		return sandBox;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSandBox(boolean sandBox) {
		this.sandBox = sandBox;
	}

}
