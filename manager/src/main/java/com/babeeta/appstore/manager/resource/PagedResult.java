package com.babeeta.appstore.manager.resource;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 分页下发的结果
 * 
 * @author leon
 * 
 * @param <E>
 */
public class PagedResult<E> {
	@JsonSerialize(as = ArrayList.class)
	private List<E> result;
	private long total;

	public List<E> getResult() {
		return result;
	}

	public long getTotal() {
		return total;
	}

	public PagedResult<E> setResult(List<E> result) {
		this.result = result;
		return this;
	}

	public PagedResult<E> setTotal(long total) {
		this.total = total;
		return this;
	}

}
