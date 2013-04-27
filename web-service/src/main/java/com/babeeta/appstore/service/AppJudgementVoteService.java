package com.babeeta.appstore.service;

import com.babeeta.appstore.entity.AppJudgementVote;

public interface AppJudgementVoteService {
	AppJudgementVote getAppJudgementVote(String authToken, String appJudgementId);

}
