package com.babeeta.appstore.dao.impl;

import java.util.Iterator;

public class EstimatableIterator<E> implements Iterator<E> {

	private final Iterator<E> iter;
	private final long total;

	public EstimatableIterator(Iterator<E> iter, long total) {
		super();
		this.iter = iter;
		this.total = total;
	}

	public long getTotal() {
		return total;
	}

	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}

	@Override
	public E next() {
		return iter.next();
	}

	@Override
	public void remove() {
		iter.remove();
	}

}
