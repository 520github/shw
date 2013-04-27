package com.babeeta.appstore.manager.service.impl.broadcast;

import java.util.Set;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.entity.Broadcast.Status;

/**
 * 广播任务的状态机
 * 
 * @author leon
 * 
 */
public interface BroadcastState {

	/**
	 * 当前状态可以接受的命令
	 * 
	 * @return
	 */
	public Set<String> getAvailableCommands();

	/**
	 * 获取当前的状态
	 * 
	 * @return
	 */
	public Status getStatus();

	/**
	 * 执行命令
	 * 
	 * @param broadcast
	 *            广播对象
	 * @param command
	 *            要执行的命令
	 * @return 下一个状态
	 */
	public BroadcastState run(Broadcast boradcast, String command);
}