package com.babeeta.appstore.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.AppDao;
import com.babeeta.appstore.dao.AppJudgementDao;
import com.babeeta.appstore.dao.AppJudgementVoteDao;
import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.AppJudgement;
import com.babeeta.appstore.entity.AppJudgement.Candidate;
import com.babeeta.appstore.entity.AppJudgementVote;
import com.babeeta.appstore.service.AppJudgementService;

@Service("appJudgementService")
public class AppJudgementServiceImpl implements AppJudgementService {

	@Autowired
	private AppDao appDao;

	@Autowired
	private AppJudgementDao appJudgementDao;

	@Autowired
	private AppJudgementVoteDao appJudgementVoteDao;

	@Override
	public AppJudgement getAppJudgementById(String id) {
		return appJudgementDao.findById(id);

	}

	@Override
	public List<AppJudgement> getAppJudgementHistoryList() {
		return appJudgementDao.findHistory();
	}

	@Override
	public List<AppJudgement> getAppJudgementOngoingList() {
		return appJudgementDao.findOngoing();
	}

	@Override
	public List<Candidate> getCandidates(String appJudgementId) {

		AppJudgement appJudgement = appJudgementDao.findById(appJudgementId);
		List<Candidate> list = appJudgement.getCandidateList();
		List<Candidate> candidateList = new ArrayList<Candidate>();
		for (Candidate candidate : list) {
			App app = appDao.findByAppId(candidate.getAppId());
			candidate.setName(app.getDetail().getName());
			candidate.setLogo(app.getDetail().getLogo());
			candidateList.add(candidate);
		}

		return candidateList;
	}

	public void setAppJudgementDao(AppJudgementDao appJudgementDao) {
		this.appJudgementDao = appJudgementDao;
	}

	public void setAppJudgementVoteDao(AppJudgementVoteDao appJudgementVoteDao) {
		this.appJudgementVoteDao = appJudgementVoteDao;
	}

	@Override
	public void voteAppJudgement(String authToken, String appId,
			String appJudgementId) {
		if (appJudgementVoteDao.voted(authToken, appJudgementId)) {
			return;
		} else {
			AppJudgementVote appJudgementVote = new AppJudgementVote();
			appJudgementVote.setAuthToken(authToken);
			appJudgementVote.setAppJudgementId(appJudgementId);
			appJudgementVote.setValue(appId);
			appJudgementVote.setDate(new Date());
			appJudgementVote.setId(new ObjectId().toString());
			appJudgementVoteDao.save(appJudgementVote);

			AppJudgement appJudgement = appJudgementDao
					.findById(appJudgementId);
			List<com.babeeta.appstore.entity.AppJudgement.Candidate> list = appJudgement
					.getCandidateList();
			for (Candidate candidate : list) {
				if (candidate.getAppId().equals(appId)) {
					candidate.setVote(candidate.getVote() + 1);
					break;
				}
			}

			appJudgement.setLastModify(new Date());
			appJudgementDao.save(appJudgement);
		}
	}

}
