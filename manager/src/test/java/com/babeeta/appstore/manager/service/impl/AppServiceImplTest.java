package com.babeeta.appstore.manager.service.impl;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.babeeta.appstore.dao.AppDao;
import com.babeeta.appstore.dao.EntityNotFoundException;
import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.manager.service.LockConflictException;

public class AppServiceImplTest {

	private AppServiceImpl appServiceImpl = new AppServiceImpl();
	private AppDao appDao = null;

	@Before
	public void setup() {
		appDao = EasyMock.createMock(AppDao.class);
		appServiceImpl.setAppDao(appDao);
	}

	@Test
	public void 正常锁定() {
		App sample = new App();
		sample.setStatus(App.AppStatus.unprocessed);
		EasyMock.expect(appDao.lock("5", "editor"))
				.andReturn("editor")
				.once();
		EasyMock.expect(appDao.findByAppId("5"))
				.andReturn(sample)
				.once();
		EasyMock.replay(appDao);

		assertSame(sample, appServiceImpl.lock("5", "editor"));
	}

	@Test(expected = LockConflictException.class)
	public void 已经被其他编辑锁定() {
		App sample = new App();
		sample.setStatus(App.AppStatus.unprocessed);
		EasyMock.expect(appDao.findByAppId("5"))
		.andReturn(sample)
		.once();
		EasyMock.expect(appDao.lock("5", "editor"))
				.andReturn("anotherEditor")
				.once();
		EasyMock.replay(appDao);
		appServiceImpl.lock("5", "editor");
	}

	@Test(expected = EntityNotFoundException.class)
	public void 锁定不存在的数据() {
		App sample = new App();
		sample.setStatus(App.AppStatus.unprocessed);
		EasyMock.expect(appDao.findByAppId("5"))
		.andReturn(sample)
		.once();
		EasyMock.expect(appDao.lock("5", "editor"))
				.andThrow(new EntityNotFoundException(App.class, "5"))
				.once();
		EasyMock.replay(appDao);
		appServiceImpl.lock("5", "editor");
	}
}
