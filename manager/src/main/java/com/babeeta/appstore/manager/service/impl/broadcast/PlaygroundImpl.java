package com.babeeta.appstore.manager.service.impl.broadcast;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("broadcastPlayground")
@Scope("singleton")
public class PlaygroundImpl<K> implements Playground<K> {
	private final class JobPlayer implements Runnable {

		final Job job;
		final K key;

		JobPlayer(Job job, K key) {
			super();
			this.job = job;
			this.key = key;
		}

		@Override
		public void run() {
			job.run();
		}

	}

	public static final Logger logger = LoggerFactory
			.getLogger(PlaygroundImpl.class);

	private final ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime
			.getRuntime().availableProcessors() + 1, Runtime.getRuntime()
			.availableProcessors() * 2, 10, TimeUnit.MINUTES,
			new LinkedBlockingQueue<Runnable>()) {

		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			@SuppressWarnings("unchecked")
			JobPlayer player = (JobPlayer) r;
			playerMap.remove(player.key);
			if (t != null) {
				player.job.onException(t);
			}
		}
	};

	private final ConcurrentHashMap<K, JobPlayer> playerMap = new ConcurrentHashMap<K, JobPlayer>();

	public int getActiveWorkers() {
		return executor.getActiveCount();
	}

	public int getMaxWorkerCount() {
		return executor.getCorePoolSize();
	}

	public int getQueueLength() {
		return (int) executor.getTaskCount();
	}

	@Override
	public void interrupt(K key) {
		JobPlayer player = playerMap.remove(key);
		if (player == null) {
			return;
		}
		executor.remove(player);
		player.job.interrupt();
	}

	@Override
	public void play(Job job, K key) {
		if (playerMap.contains(key)) {
			return;
		}
		JobPlayer player = new JobPlayer(job, key);
		playerMap.put(key, player);
		executor.execute(player);
	}

	@PreDestroy
	public void shutdown() throws InterruptedException {
		logger.info("Shuting down playground.");
		executor.shutdown();
		for (K key : playerMap.keySet()) {
			this.interrupt(key);
		}
		logger.info("All jobs are interrupted.");
	}
}