package com.babeeta.appstore.analyze.extractor;

public interface Extractor<T> {
	T extract(String source);
}
