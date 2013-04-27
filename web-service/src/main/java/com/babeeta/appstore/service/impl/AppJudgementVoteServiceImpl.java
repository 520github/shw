package com.babeeta.appstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.AppJudgementVoteDao;
import com.babeeta.appstore.entity.AppJudgementVote;
import com.babeeta.appstore.service.AppJudgementVoteService;

@Service("appJudgementVoteService")
public class AppJudgementVoteServiceImpl implements AppJudgementVoteService {

	@Autowired
	private AppJudgementVoteDao appJudgementVoteDao;

	@Override
	public AppJudgementVote getAppJudgementVote(String authToken,
			String appJudgementId) {
		return appJudgementVoteDao.getAppJudgementVote(authToken,
				appJudgementId);
	}

}
