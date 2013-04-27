package com.babeeta.appstore.manager.service.impl.broadcast.state;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.babeeta.appstore.manager.service.impl.broadcast.Job;

public abstract class AbstractJob<K> implements Job {
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractJob.class);

	private boolean interrupted = false;

	private final K key;

	private final Lock lock = new ReentrantLock();

	public AbstractJob(K key) {
		super();
		this.key = key;
	}

	protected K getKey() {
		return key;
	}

	protected boolean isInterrupted() {
		return interrupted;
	}

	@Override
	public void interrupt() {
		interrupted = true;
		// 等待任务的退出， 1分钟超时
		try {
			if (!lock.tryLock(1L, TimeUnit.MINUTES)) {
				logger.warn("[{}]Interruption of callbacking failed.", getKey());
			}
		} catch (InterruptedException e) {
			logger.error(
					"[{}]Thread interruption occured while interrupting callback operation.",
					getKey());
		}
	}

	@Override
	public void onException(Throwable t) {
		logger.error("[{}]", key, t);
	}

	public abstract void process();

	@Override
	public void run() {
		if (lock.tryLock()) {
			logger.info("[{}] started.", key);
			long timeBegin = System.currentTimeMillis();
			try {
				process();
			} finally {
				lock.unlock();
				logger.info("[{}]Job finished. {}ms", key,
						System.currentTimeMillis() - timeBegin);
			}
		}
	}

}