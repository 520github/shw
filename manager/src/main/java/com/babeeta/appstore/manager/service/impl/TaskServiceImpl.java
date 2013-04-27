package com.babeeta.appstore.manager.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.TaskDao;
import com.babeeta.appstore.entity.Task;
import com.babeeta.appstore.entity.Task.TaskStatus;
import com.babeeta.appstore.entity.Task.TaskType;
import com.babeeta.appstore.manager.resource.ConfigResource;
import com.babeeta.appstore.manager.service.BestAppService;
import com.babeeta.appstore.manager.service.BrandService;
import com.babeeta.appstore.manager.service.ConfigService;
import com.babeeta.appstore.manager.service.TaskService;

@Service("taskService")
public class TaskServiceImpl implements TaskService {
	private TaskDao taskDao;

	private BrandService brandService;
	private ConfigService configService;
	private BestAppService bestAppService;

	@Override
	public void deleteStatusCreate(String id) {
		taskDao.deleteStatusCreate(id);
	}

	public TaskDao getTaskDao() {
		return taskDao;
	}

	@Override
	public List<Task> pageTask(int offset, int limit) {
		return taskDao.pageTask(limit, offset);
	}

	@Override
	public void save(Task task) {
		taskDao.save(task);

	}

	@Autowired
	public void setBestAppService(BestAppService bestAppService) {
		this.bestAppService = bestAppService;
	}

	@Autowired
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	@Autowired
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public void work() {
		Date now = Calendar.getInstance().getTime();
		List<Task> list = taskDao
				.findTaskByStausOrderByExecuteTime(TaskStatus.create);
		for (Task t : list) {
			if (t.getExecuteTime().before(now)) {
				taskDao.updateStatusProcessing(t.getId());
				if (t.getType().equals(TaskType.index_json)) {
					configService.moveTempToJson(ConfigResource.DIR_INDEX_TEMP,
							ConfigResource.DIR_INDEX);
				} else if (t.getType().equals(TaskType.brand_notice)) {
					brandService.notice();
				} else if (t.getType().equals(TaskType.best_app)) {
					bestAppService.saveAppBest();
				}
				taskDao.updateStatusDone(t.getId());
			}
		}
	}
}
