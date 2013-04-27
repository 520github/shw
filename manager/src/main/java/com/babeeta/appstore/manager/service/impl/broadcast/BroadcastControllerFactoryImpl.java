package com.babeeta.appstore.manager.service.impl.broadcast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.BroadcastDao;
import com.babeeta.appstore.manager.service.BroadcastController;
import com.babeeta.appstore.manager.service.impl.broadcast.state.BroadcastStateFactory;

@Service
@Scope("singleton")
public class BroadcastControllerFactoryImpl implements
		BroadcastControllerFactory {

	@Autowired
	private BroadcastDao broadcastDao;

	@Autowired
	private BroadcastStateFactory broadcastStateFactory;

	@Override
	public BroadcastController getInstance(String id) {
		return new BroadcastControllerImpl(broadcastDao.findById(id),
				broadcastStateFactory);
	}
}