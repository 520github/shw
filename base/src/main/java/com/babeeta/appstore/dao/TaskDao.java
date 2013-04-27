package com.babeeta.appstore.dao;

import java.util.List;

import com.babeeta.appstore.entity.Task;
import com.babeeta.appstore.entity.Task.TaskStatus;

public interface TaskDao {

	public void deleteStatusCreate(String id);

	public List<Task> findTaskByStausOrderByExecuteTime(TaskStatus status);

	public List<Task> pageTask(int limt, int offset);

	public void save(Task task);

	public void updateStatusDone(String id);

	public void updateStatusProcessing(String id);

}
