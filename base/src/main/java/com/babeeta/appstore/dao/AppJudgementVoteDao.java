package com.babeeta.appstore.dao;

import com.babeeta.appstore.entity.AppJudgementVote;

public interface AppJudgementVoteDao {

	AppJudgementVote getAppJudgementVote(String authToken, String appJudgementId);

	AppJudgementVote save(AppJudgementVote appJudgementVote);

	boolean voted(String authToken, String appJudgementId);

}
