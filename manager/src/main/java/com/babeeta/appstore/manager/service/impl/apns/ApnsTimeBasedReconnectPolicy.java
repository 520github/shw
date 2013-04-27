package com.babeeta.appstore.manager.service.impl.apns;

import java.util.concurrent.TimeUnit;

import com.notnoop.apns.ReconnectPolicy;

/**
 * Apns的重连策略
 * 
 * @author leon
 * 
 */
public class ApnsTimeBasedReconnectPolicy implements ReconnectPolicy {

	private long lastRunning = 0;
	// 默认5分钟
	private final long period;

	public ApnsTimeBasedReconnectPolicy() {
		period = 1000 * 60 * 5;
	}

	public ApnsTimeBasedReconnectPolicy(long period, TimeUnit timeUnit) {
		this.period = timeUnit.convert(period, TimeUnit.MILLISECONDS);
	}

	@Override
	public ReconnectPolicy copy() {
		return null;
	}

	public long getPeriod() {
		return period;
	}

	@Override
	public void reconnected() {
		lastRunning = System.currentTimeMillis();
	}

	@Override
	public boolean shouldReconnect() {
		return System.currentTimeMillis() - lastRunning > period;
	}

}