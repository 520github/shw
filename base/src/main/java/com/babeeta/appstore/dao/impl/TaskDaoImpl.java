package com.babeeta.appstore.dao.impl;

import java.util.Calendar;
import java.util.List;

import com.babeeta.appstore.dao.TaskDao;
import com.babeeta.appstore.entity.Task;
import com.babeeta.appstore.entity.Task.TaskStatus;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class TaskDaoImpl extends BasicDaoImpl implements TaskDao {
	@Override
	public void deleteStatusCreate(String id) {
		datastore.delete(datastore.createQuery(Task.class).filter("_id", id)
				.filter("status", TaskStatus.create));
	}

	@Override
	public List<Task> findTaskByStausOrderByExecuteTime(TaskStatus status) {
		return datastore.createQuery(Task.class).filter("status", status)
				.order("-executeTime")
				.asList();
	}

	@Override
	public List<Task> pageTask(int limt, int offset) {
		return datastore.createQuery(Task.class).limit(limt).offset(offset)
				.asList();
	}

	@Override
	public void save(Task task) {
		datastore.save(task);
	}

	@Override
	public void updateStatusDone(String id) {
		Query<Task> q = datastore.createQuery(Task.class).filter("_id", id)
				.filter("status", TaskStatus.processing);
		UpdateOperations<Task> u = datastore.createUpdateOperations(Task.class)
				.set("status", TaskStatus.done)
				.set("endTime", Calendar.getInstance().getTime());
		datastore.findAndModify(q, u);

	}

	@Override
	public void updateStatusProcessing(String id) {
		Query<Task> q = datastore.createQuery(Task.class).filter("_id", id)
				.filter("status", TaskStatus.create);
		UpdateOperations<Task> u = datastore.createUpdateOperations(Task.class)
				.set("status", TaskStatus.processing)
				.set("beginTime", Calendar.getInstance().getTime());
		datastore.findAndModify(q, u);

	}

}
