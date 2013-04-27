package com.babeeta.appstore.manager.service.impl.broadcast;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class PlaygroundImplTest {
	private final class MockJob implements Job {
		private final AtomicInteger interruptedCounter;
		private final Lock lock;
		private final AtomicInteger runCounter;
		private final Lock runLock = new ReentrantLock(true);

		private MockJob(AtomicInteger runCounter,
				AtomicInteger interruptedCounter, Lock lock) {
			this.runCounter = runCounter;
			this.interruptedCounter = interruptedCounter;
			this.lock = lock;
		}

		protected Lock getRunLock() {
			return runLock;
		}

		@Override
		public void interrupt() {
			interruptedCounter.getAndIncrement();
		}

		@Override
		public void onException(Throwable t) {

		}

		@Override
		public void run() {
			runLock.lock();
			lock.lock();
			runCounter.getAndIncrement();
			lock.unlock();
			runLock.unlock();
		}
	}

	private final PlaygroundImpl<Object> playgroundImpl = new PlaygroundImpl<Object>();

	@Test
	public void test() throws InterruptedException {
		Job job = EasyMock.createMock(Job.class);
		job.run();
		EasyMock.expectLastCall().once();
		EasyMock.replay(job);

		Object key = new Object();
		playgroundImpl.play(job, key);

		Thread.sleep(100L);

		EasyMock.verify(job);
	}

	@Test
	public void testCancel() throws Exception {
		ThreadPoolExecutor e = (ThreadPoolExecutor) ReflectionTestUtils
				.getField(playgroundImpl, "executor");
		e.setCorePoolSize(1);
		e.setMaximumPoolSize(1);
		e.prestartAllCoreThreads();
		assertEquals(1, e.getPoolSize());

		final Lock lock = new ReentrantLock(true);
		lock.lock();
		final AtomicInteger interruptedCounter = new AtomicInteger();
		final AtomicInteger runCounter = new AtomicInteger();

		MockJob firstJob = new MockJob(runCounter, interruptedCounter, lock);
		Job sencodJob = new MockJob(runCounter, interruptedCounter, lock);

		Object first, second;

		playgroundImpl.play(firstJob, first = new Object());
		playgroundImpl.play(sencodJob, second = new Object());

		while (firstJob.getRunLock().tryLock()) {
			firstJob.getRunLock().unlock();
			Thread.sleep(100L);
		}

		assertEquals(0, interruptedCounter.intValue());

		playgroundImpl.interrupt(second);

		assertEquals(1, interruptedCounter.intValue());

		playgroundImpl.interrupt(first);
		lock.unlock();

		firstJob.getRunLock().lock();
		firstJob.getRunLock().unlock();

		assertEquals(2, interruptedCounter.intValue());
		assertEquals(1, runCounter.intValue());

	}

	@Test
	public void testOnException() throws Exception {
		Job job = EasyMock.createMock(Job.class);
		job.run();
		EasyMock.expectLastCall()
				.andThrow(
						new RuntimeException(
								"Don't panic. This is an expected exception."))
				.once();

		job.onException(EasyMock.isA(Throwable.class));
		EasyMock.expectLastCall().once();

		EasyMock.replay(job);

		playgroundImpl.play(job, new Object());

		Thread.sleep(100L);

		Assert.assertTrue(((Map<?, ?>) ReflectionTestUtils.getField(
				playgroundImpl,
				"playerMap")).isEmpty());
	}
}