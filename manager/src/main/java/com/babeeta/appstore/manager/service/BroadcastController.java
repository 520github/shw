package com.babeeta.appstore.manager.service;

import java.util.Set;

import com.babeeta.appstore.entity.Broadcast;

/**
 * 广播的控制器
 * 
 * @author leon
 * 
 */
public interface BroadcastController {

	/**
	 * 获取当前可用的命令
	 * 
	 * @return
	 */
	public Set<String> getAvailableCommands();

	/**
	 * 获取控制器对应的Broadcast对象
	 * 
	 * @return
	 */
	public Broadcast getBroadcast();

	/**
	 * 执行命令
	 * 
	 * @param command
	 */
	public void run(String command);
}