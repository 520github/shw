package com.babeeta.appstore.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.BroadcastDao;
import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.entity.Broadcast.Status;
import com.babeeta.appstore.manager.resource.PagedResult;
import com.babeeta.appstore.manager.service.BroadcastController;
import com.babeeta.appstore.manager.service.BroadcastService;
import com.babeeta.appstore.manager.service.impl.broadcast.BroadcastControllerFactory;

@Service
public class BroadcastServiceImpl implements BroadcastService {

	@Autowired
	private BroadcastControllerFactory broadcastControlFactory;

	@Autowired
	private BroadcastDao broadcastDao;

	@Override
	public BroadcastController createNewBroadcast(Broadcast broadcast) {
		broadcast.setStatus(Status.InEden);
		broadcastDao.save(broadcast);
		BroadcastController bc = broadcastControlFactory.getInstance(broadcast
				.getId());
		bc.run("启动");
		return bc;
	}

	@Override
	public PagedResult<Broadcast> findAll(int limit, int offset) {
		return new PagedResult<Broadcast>().setResult(broadcastDao.findAll(
				limit, offset));
	}

	@Override
	public BroadcastController findBroadcastControllerById(String id) {
		return broadcastControlFactory.getInstance(id);
	}

}
