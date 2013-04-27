package com.babeeta.appstore.manager.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.Task;
import com.babeeta.appstore.entity.Task.TaskStatus;
import com.babeeta.appstore.entity.Task.TaskType;
import com.babeeta.appstore.manager.service.TaskService;

@Path("/api/task")
@Controller("taskResource")
@Scope("prototype")
public class TaskResource {

	private TaskService taskService;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskResource.class);

	@Path("/")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response addTask(@FormParam("executeTime") String executeTime,
			@FormParam("type") TaskType type) {
		try {
			Task task = new Task();
			task.setId(new ObjectId().toString());
			task.setCreateTime(Calendar.getInstance().getTime());
			task.setType(type);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			task.setExecuteTime(sdf.parse(executeTime));
			task.setStatus(TaskStatus.create);
			taskService.save(task);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		return Response.ok().build();
	}

	@Path("/")
	@DELETE
	@Consumes("text/plain")
	public Response deleteTask(String id) {
		taskService.deleteStatusCreate(id);
		return Response.ok().build();
	}

	@Path("/")
	@GET
	@Produces("application/json")
	public Response getTask(@QueryParam("offset") int offset,
			@QueryParam("limit") int limit) {

		return Response.ok(taskService.pageTask(offset, limit)).build();
	}

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

}
