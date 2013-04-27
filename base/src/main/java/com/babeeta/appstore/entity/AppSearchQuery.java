package com.babeeta.appstore.entity;

import com.google.code.morphia.query.Query;
import com.google.common.base.Strings;

public class AppSearchQuery {

	public void setDataStoreFiled(Query<App> query, String field, String value) {
		if (!Strings.isNullOrEmpty(value)) {
			query.and(query.criteria(field).containsIgnoreCase(value));
		}

	}

}
