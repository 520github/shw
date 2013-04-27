package com.babeeta.appstore.manager.service;

/**
 * 锁冲突
 * 
 * @author leon
 * 
 */
public class LockConflictException extends RuntimeException {

	private static final long serialVersionUID = 161879993422110553L;

	private final String lockOwner;

	public LockConflictException(String lockOwner) {
		super();
		this.lockOwner = lockOwner;
	}

	@Override
	public String getMessage() {
		return "Owner: " + lockOwner;
	}

	public String getLockOwner() {
		return lockOwner;
	}
}
