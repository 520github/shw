package com.babeeta.appstore.dao.impl;

import java.util.List;

import com.babeeta.appstore.dao.BrandDao;
import com.babeeta.appstore.entity.Brand;
import com.google.code.morphia.query.Query;

public class BrandDaoImpl extends BasicDaoImpl implements BrandDao {

	@Override
	public long count() {
		Query<Brand> query = datastore.createQuery(Brand.class);
		return query.countAll();
	}

	@Override
	public long countBrandByBrands(String ids[]) {
		return datastore.createQuery(Brand.class)
				.field("enabled").equal(true)
				.filter("_id in", ids)
				.order("-lastModified").countAll();
	}

	@Override
	public long countEnabled() {
		return datastore.createQuery(Brand.class).field("enabled").equal(true)
				.countAll();
	}

	@Override
	public void decreaseBrandCounter(String id, String value) {
		datastore.findAndModify(
				datastore.createQuery(Brand.class)
						.field("_id").equal(id),
				datastore.createUpdateOperations(Brand.class)
						.dec("counter." + value), false, true);

	}

	@Override
	public List<Brand> findAll() {
		return datastore.find(Brand.class).order("-weight").asList();
	}

	@Override
	public List<Brand> findAll(int offset, int limit) {
		return datastore.createQuery(Brand.class).order("asciiName")
				.offset(offset)
				.limit(limit)
				.asList();
	}

	@Override
	public List<Brand> findBrandByBrands(String[] brands, int offset, int limit) {
		return datastore.createQuery(Brand.class)
				.field("enabled").equal(true)
				.filter("_id in", brands)
				.order("-lastModified").offset(offset).limit(limit)
				.asList();
	}

	@Override
	public Brand findById(String id) {
		return datastore.createQuery(Brand.class).field("_id").equal(id).get();

	}

	@Override
	public Brand findByName(String name) {
		List<Brand> result = datastore.find(Brand.class, "name", name).asList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

	@Override
	public List<Brand> findEnabledBrands() {
		return datastore.createQuery(Brand.class).field("enabled").equal(true)
				.asList();
	}

	@Override
	public List<Brand> findEnabledBrands(int offset, int limit) {
		return datastore.createQuery(Brand.class).field("enabled").equal(true)
				.order("-weight").limit(limit).offset(offset).asList();
	}

	@Override
	public void increaseBrandCounter(String id, String value) {
		datastore.findAndModify(
				datastore.createQuery(Brand.class)
						.field("_id").equal(id),
				datastore.createUpdateOperations(Brand.class)
						.inc("counter." + value), false, true);
	}

	@Override
	public void remove(String id) {
		datastore.delete(Brand.class, id);
	}

	@Override
	public Brand save(Brand brand) {
		datastore.save(brand);
		return brand;
	}

	@Override
	public void updateBrand(String id, Brand brand) {
		datastore.findAndModify(
				datastore.createQuery(Brand.class)
						.field("_id").equal(id),
				datastore
						.createUpdateOperations(Brand.class)
						.set("name", brand.getName())
						.set("icon", brand.getIcon())
						.set("asciiName",
								brand.getAsciiName()));
	}

	@Override
	public void updateBrandState(String id, boolean state) {
		datastore.findAndModify(
				datastore.createQuery(Brand.class)
						.field("_id").equal(id),
				datastore.createUpdateOperations(Brand.class)
						.set("enabled", state));
	}
}
