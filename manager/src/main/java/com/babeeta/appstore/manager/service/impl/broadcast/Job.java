package com.babeeta.appstore.manager.service.impl.broadcast;

/**
 * 在后台执行的任务
 * 
 * @author leon
 * 
 */
public interface Job {

	/**
	 * 任务要中断了
	 */
	public void interrupt();

	/**
	 * 发生了错误, 任务失败
	 * 
	 * @param t
	 */
	public void onException(Throwable t);

	/**
	 * 广播逻辑
	 */
	public void run();
}