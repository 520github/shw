package com.babeeta.appstore.manager.service.impl.apns;

import com.notnoop.apns.ApnsService;

/**
 * ApnsService对象的创建工厂
 * 
 * @author leon
 * 
 */
public interface ApnsServiceFactory {
	ApnsService getInstance();
}
