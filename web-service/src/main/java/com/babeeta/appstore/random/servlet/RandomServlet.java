package com.babeeta.appstore.random.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.babeeta.appstore.dao.AppDao;
import com.babeeta.appstore.entity.App;

public class RandomServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory
			.getLogger(RandomServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -4659544331254780718L;
	private AppDao appDao;

	private App getRandomApp() {
		App app = null;
		for (int i = 0; i < 10 && app == null; i++) {
			double randomNum = Math.random();
			logger.debug("Seed: {}", randomNum);
			app = appDao.getAppByRandom(randomNum);
		}
		logger.debug("Random app: {} {}", app.getId(), app.getRandom());
		return app;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		App app = getRandomApp();
		if (app == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			req.setAttribute("app", app);
			RequestDispatcher dispatcher = req
					.getRequestDispatcher("random/app/app.jsp");
			dispatcher.forward(req, resp);
		}
	}

	@Autowired
	@Required
	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}
}
