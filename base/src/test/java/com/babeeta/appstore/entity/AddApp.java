package com.babeeta.appstore.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.babeeta.appstore.dao.impl.AppDaoImpl;

public class AddApp {
	public static void main(String[] args) {
		AppDaoImpl impl = new AppDaoImpl();
		App app = new App();
		app.setStatus(App.AppStatus.unprocessed);
		app.setId("com.babeeta.android");
		Map<String, ApplicationMarketDetail> details = new TreeMap<String, ApplicationMarketDetail>();

		ApplicationMarketDetail detail = new ApplicationMarketDetail();
		detail.setId("00000001");
		app.setDetail(detail);
		detail.setApkId("01102154992");
		detail.setChange("增加了视频通话功能");
		detail.setGenre("财务");
		detail.setLanguage("英语");
		detail.setLastUpdate("2012-02-14");
		detail.setLogo("http://www.niigoo.com/wp-content/uploads/2011/12/iphoneicon0.jpg");
		detail.setName("么么");
		detail.setOriginalDescription("这是一款很变态的应用，所有的夸词都可以毫不过分的用在它身上！");
		detail.setPrice("￥12");
		detail.setPublisher("chongf");
		detail.setRequirement("android2.0+");
		List<String> screenshotsForPhone = new ArrayList<String>();
		screenshotsForPhone
				.add("http://www.niigoo.com/wp-content/uploads/2011/12/iphoneicon0.jpg");
		detail.setScreenshotsForPhone(screenshotsForPhone);
		detail.setSize("3.5M");
		detail.setUrl("https://play.google.com");
		detail.setVersion("1.0.0");
		ApplicationMarketDetail.Score score = new ApplicationMarketDetail.Score();
		score.setStar(4.5f);
		score.setVote(3);
		detail.setWholeVersionScore(score);
		detail.setMarket("Google Play");
		ApplicationMarketDetail.Developer developer = new ApplicationMarketDetail.Developer();
		developer.setEmail("chong.fan@babeeta.com");
		developer.setName("chongf");
		developer.setWebsite("http://www.chong.com");
		detail.setDeveloper(developer);
		List<ApplicationMarketDetail.Permission> permissions = new ArrayList<ApplicationMarketDetail.Permission>();
		ApplicationMarketDetail.Permission p = new ApplicationMarketDetail.Permission();
		p.setId("android.permission.FLASHLIGHT");
		p.setText("使用闪光灯");
		permissions.add(p);
		detail.setPermissions(permissions);
		details.put(detail.getMarket(), detail);

		app.setDetail(detail);

		detail = new ApplicationMarketDetail();
		detail.setApkId("3233232323");
		detail.setChange("增加了视频通话功能");
		detail.setGenre("财务");
		detail.setId("Fsssdf00098");
		detail.setLanguage("英语");
		detail.setLastUpdate("2012-02-14");
		detail.setLogo("http://www.niigoo.com/wp-content/uploads/2011/12/iphoneicon0.jpg");
		detail.setName("么么");
		detail.setOriginalDescription("这是一款很变态的应用，所有的夸词都可以毫不过分的用在它身上！");
		detail.setPrice("￥12");
		detail.setPublisher("chongf");
		detail.setRequirement("android2.0+");
		screenshotsForPhone = new ArrayList<String>();
		screenshotsForPhone
				.add("http://www.niigoo.com/wp-content/uploads/2011/12/iphoneicon0.jpg");
		detail.setScreenshotsForPhone(screenshotsForPhone);
		detail.setSize("3.5M");
		detail.setUrl("http://apk.gfan.com/");
		detail.setVersion("1.0.0");
		score = new ApplicationMarketDetail.Score();
		score.setStar(3.5f);
		score.setVote(5);
		detail.setWholeVersionScore(score);
		detail.setMarket("Gfan");
		detail.setDeveloper(developer);
		detail.setPermissions(permissions);
		details.put(detail.getMarket(), detail);

		detail = new ApplicationMarketDetail();
		detail.setApkId("444444444");
		detail.setChange("增加了视频通话功能");
		detail.setGenre("财务");
		detail.setId("asdfsadkjfk");
		detail.setLanguage("英语");
		detail.setLastUpdate("2012-3-14");
		detail.setLogo("http://www.niigoo.com/wp-content/uploads/2011/12/iphoneicon0.jpg");
		detail.setName("么么");
		detail.setOriginalDescription("这是一款很变态的应用，所有的夸词都可以毫不过分的用在它身上！");
		detail.setPrice("￥12");
		detail.setPublisher("chongf");
		detail.setRequirement("android2.0+");
		screenshotsForPhone = new ArrayList<String>();
		screenshotsForPhone
				.add("http://www.niigoo.com/wp-content/uploads/2011/12/iphoneicon0.jpg");
		detail.setScreenshotsForPhone(screenshotsForPhone);
		detail.setSize("3.5M");
		detail.setUrl("http://www.eoemarket.com/");
		detail.setVersion("1.0.0");
		score = new ApplicationMarketDetail.Score();
		score.setStar(3.5f);
		score.setVote(5);
		detail.setWholeVersionScore(score);
		detail.setMarket("EoeMarket");
		detail.setDeveloper(developer);
		detail.setPermissions(permissions);
		details.put(detail.getMarket(), detail);

		app.setApplicationMarketDetails(details);
		impl.save(app);

		App ret = impl.findByAppId(app.getId());
		System.out.println(ret.getDetail().getName());
	}
}
