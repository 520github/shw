package com.babeeta.appstore.dao;

public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 8269606227641145923L;

	private final Class<?> entityClass;
	private final Object entityId;

	public EntityNotFoundException(Class<?> entityClass, Object entityId) {
		super();
		this.entityClass = entityClass;
		this.entityId = entityId;
	}

	@Override
	public String getMessage() {
		return "Entity ["
				+ (entityClass == null ? "Null" : entityClass.getName())
				+ "] identified by ["
				+ (entityId == null ? "Null" : entityId.toString())
				+ "] not found.";
	}
}
