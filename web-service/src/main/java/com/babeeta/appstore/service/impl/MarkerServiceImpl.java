package com.babeeta.appstore.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.MarkerCounterDao;
import com.babeeta.appstore.dao.MarkerDao;
import com.babeeta.appstore.entity.Marker;
import com.babeeta.appstore.entity.MarkerCounter;
import com.babeeta.appstore.entity.MarkerDomain;
import com.babeeta.appstore.service.MarkerService;

@Service("markerService")
public class MarkerServiceImpl implements MarkerService {
	private MarkerCounterDao markerCounterDao;
	private MarkerDao markerDao;

	@Override
	public long countDeviceMarker(String deviceId, String domain, String value) {
		return markerDao.countDeviceMarker(deviceId, domain, value);
	}

	@Override
	public long countDomainMarker(String markerDomain, String entityId,
			String value) {
		MarkerCounter.MarkerCounterId id = new MarkerCounter.MarkerCounterId();
		id.setDomain(markerDomain);
		id.setEntityId(entityId);
		return markerCounterDao.countById(id, value);
	}

	@Override
	public List<Marker> findMarker(String device, String markerDomain,
			String value, int offset, int limit) {
		return markerDao.findMarker(device, markerDomain, value, offset, limit);
	}

	@Override
	public Marker getMark(String deviceId, String domain, String entityId) {
		Marker.MarkerId mid = new Marker.MarkerId();
		mid.setDomain(domain);
		mid.setDeviceId(deviceId);
		mid.setEntityId(entityId);
		return markerDao.findById(mid);
	}

	@Override
	public MarkerCounter getMarkerCounter(String domain, String entityId) {
		MarkerCounter.MarkerCounterId mcId = new MarkerCounter.MarkerCounterId();
		mcId.setDomain(domain);
		mcId.setEntityId(entityId);
		return markerCounterDao.findById(mcId);
	}

	@Override
	public List<MarkerCounter> getMarkerCounters(String entityId) {
		return markerCounterDao.findByEntity(entityId);
	}

	@Override
	public void mark(String device, String markDomain, String entityId,
			String value, MarkerDomain.CounterMethod counterBy) {
		/*
		 * MarkerDomain domain = new MarkerDomain(); domain.setId(markDomain);
		 * domain.setCounterBy(counterBy); markerDomainDao.save(domain);
		 */
		Marker.MarkerId mid = new Marker.MarkerId();
		mid.setDomain(markDomain);
		mid.setDeviceId(device);
		mid.setEntityId(entityId);
		Marker marker = markerDao.findById(mid);
		String orgValue = value;
		if (marker == null) {
			marker = new Marker();
			marker.setId(mid);
			orgValue = null;
		} else {
			orgValue = marker.getValue();
		}
		marker.setDate(new Date());
		marker.setValue(value);
		markerDao.save(marker);
		MarkerCounter.MarkerCounterId mcid = new MarkerCounter.MarkerCounterId();
		mcid.setDomain(markDomain);
		mcid.setEntityId(entityId);
		if (counterBy.equals(MarkerDomain.CounterMethod.click)) {
			markerCounterDao.increaseCounter(mcid, value);
		} else {
			// negative++ positive-- 反之亦然
			if (!value.equals(orgValue)) {// 标记值有改变
				// value的计数器加一
				markerCounterDao.increaseCounter(mcid, value);
				// 把之前标记值的计数器减一
				if (orgValue != null) {
					markerCounterDao.decreaseCounter(mcid, orgValue);
				}
			}
		}
	}

	@Override
	public void removeMarker(String device, String markDomain, String entityId,
			MarkerDomain.CounterMethod counterBy) {
		Marker.MarkerId vid = new Marker.MarkerId();
		vid.setDomain(markDomain);
		vid.setDeviceId(device);
		vid.setEntityId(entityId);
		Marker marker = markerDao.findById(vid);
		if (marker != null) {
			if (counterBy.equals(MarkerDomain.CounterMethod.device)) {
				// 原来的value的值--
				MarkerCounter.MarkerCounterId mcid = new MarkerCounter.MarkerCounterId();
				mcid.setDomain(markDomain);
				mcid.setEntityId(entityId);
				markerCounterDao.decreaseCounter(mcid, marker.getValue());
			}
			markerDao.removeById(vid);
		}
	}

	@Autowired
	@Required
	public void setMarkerCounterDao(MarkerCounterDao markerCounterDao) {
		this.markerCounterDao = markerCounterDao;
	}

	@Autowired
	@Required
	public void setMarkerDao(MarkerDao markerDao) {
		this.markerDao = markerDao;
	}
}
