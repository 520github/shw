/**
 * 
 */
package com.babeeta.appstore.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.WeiBoDao;
import com.babeeta.appstore.entity.WeiBo;
import com.babeeta.appstore.service.WeiBoService;

/**
 * @author xuehui.miao
 *
 */
@Service("weiBoService")
public class WeiBoServiceImpl implements WeiBoService {
	private WeiBoDao weiBoDao;

	/* (non-Javadoc)
	 * @see com.babeeta.appstore.manager.service.WeiBoService#saveWeiBo(com.babeeta.appstore.entity.WeiBo)
	 */
	@Override
	public void saveWeiBo(WeiBo weiBo) {
		if(weiBo.getDate() == null) {
			weiBo.setDate(new Date());
		}
		weiBoDao.save(weiBo);
	}
	
	@Autowired
	@Required
	public void setWeiBoDao(WeiBoDao weiBoDao) {
		this.weiBoDao = weiBoDao;
	}

}
