package com.babeeta.sheepcounter.dao;

import java.util.List;

import com.babeeta.sheepcounter.entity.Url;

public interface UrlDao {

	public List<Url> find(String keywords, int offset, int limit);

	public Url save(Url url);

}
