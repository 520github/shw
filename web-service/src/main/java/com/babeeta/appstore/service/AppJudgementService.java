package com.babeeta.appstore.service;

import java.util.List;

import com.babeeta.appstore.entity.AppJudgement;
import com.babeeta.appstore.entity.AppJudgement.Candidate;

public interface AppJudgementService {

	AppJudgement getAppJudgementById(String id);

	List<AppJudgement> getAppJudgementHistoryList();

	List<AppJudgement> getAppJudgementOngoingList();

	List<Candidate> getCandidates(String appJudgementId);

	void voteAppJudgement(String authToken, String appId, String judgementId);

}
