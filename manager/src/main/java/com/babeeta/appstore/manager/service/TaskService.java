package com.babeeta.appstore.manager.service;

import java.util.List;

import com.babeeta.appstore.entity.Task;

public interface TaskService {

	public void deleteStatusCreate(String id);

	public List<Task> pageTask(int offset, int limit);

	public void save(Task task);
}
