package com.babeeta.appstore.manager.service.impl.broadcast;

/**
 * 广播的游乐场
 * 
 * @author leon
 * 
 */
public interface Playground<K> {

	/**
	 * 中断， 这时 #BroadcastJob# 会退出Playground
	 * 
	 * @param key
	 *            要中断Job的Key
	 */
	public void interrupt(K key);

	/**
	 * 启动, 加入一个已经在跑的job不会有效果
	 * 
	 * @param job
	 *            要运行的Job
	 * @param key
	 *            Job的唯一标识
	 */
	public void play(Job job, K key);
}