package com.babeeta.appstore.dao.impl;

import com.babeeta.appstore.dao.AppJudgementVoteDao;
import com.babeeta.appstore.entity.AppJudgementVote;

public class AppJudgementVoteDaoImpl extends BasicDaoImpl implements
		AppJudgementVoteDao {

	@Override
	public AppJudgementVote getAppJudgementVote(String authToken,
			String appJudgementId) {
		return datastore.createQuery(AppJudgementVote.class).field("authToken")
				.equal(authToken).field("appJudgementId").equal(appJudgementId)
				.get();

	}

	@Override
	public AppJudgementVote save(AppJudgementVote appJudgementVote) {
		datastore.save(appJudgementVote);
		return appJudgementVote;
	}

	@Override
	public boolean voted(String authToken, String appJudgementId) {
		return datastore.createQuery(AppJudgementVote.class).field("authToken")
				.equal(authToken).field("appJudgementId").equal(appJudgementId)
				.countAll() > 0 ? true : false;
	}

}
