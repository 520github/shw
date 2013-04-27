package com.babeeta.appstore.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.ApplicationMarketDetail.Score;
import com.babeeta.appstore.entity.Brand;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.entity.SubscriptionRelation;
import com.babeeta.appstore.service.BrandService;
import com.babeeta.appstore.service.DeviceRegistService;
import com.babeeta.appstore.service.SubscriptionRelationService;

/**
 * 品牌接口
 * 
 * @author chongf
 * 
 */
@Path("/brand/")
@Scope("prototype")
@Controller("brandResource")
public class BrandResource {
	private static final Pattern USER_AGENT_PATTERN = Pattern
			.compile("CoralBay\\/(\\d+\\.\\d+\\.(\\d+)).*");

	private static String getToken(String para) {
		return para.substring(5).trim();
	}

	/**
	 * 判断token格式是否合法
	 * 
	 * @param token
	 * @throws WebApplicationException
	 */
	public static void validToken(String token) throws WebApplicationException {
		if (token == null) {
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}

		if (!token.startsWith("Token")) {
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}

	}

	private BrandService brandService;

	private DeviceRegistService deviceRegistService;

	private SubscriptionRelationService subscriptionRelationService;

	private Object chaos(final List<Object> list, final int offset) {
		@SuppressWarnings("unused")
		Object o = new JsonObject() {

			public int getOffset() {
				return offset;
			}

			public List<Object> getResults()
			{
				return list;
			}

		};
		return o;

	}

	private List<Object> reduceApps(List<App> apps) {
		List<Object> list = new ArrayList<Object>();
		for (final App app : apps) {
			@SuppressWarnings("unused")
			Object o = new JsonObject() {

				public String getId() {
					return app.getId();
				}

				public String getIntroduction() {
					return app.getIntroduction();
				}

				public String getLogo() {
					return app.getDetail().getLogo();
				}

				public String getName() {
					return app.getDetail().getName();
				}

				public String getPrice() {
					return app.getDetail().getPrice();
				}

				public Score getScore() {
					Score score = app.getDetail()
							.getWholeVersionScore();
					if (score == null) {
						score = new Score().setStar(0).setVote(0);
					}
					return score;
				}
			};
			list.add(o);
		}
		return list;
	}

	private List<Object> transformBrands(List<Brand> brands,
			List<SubscriptionRelation> relations) {
		final Map<String, SubscriptionRelation> map = new HashMap<String, SubscriptionRelation>();
		for (final SubscriptionRelation relation : relations) {
			map.put(relation.getBrandId(), relation);
		}

		List<Object> list = new ArrayList<Object>();

		for (final Brand brand : brands) {
			if (map.containsKey(brand.getId())) {
				@SuppressWarnings("unused")
				Object o = new JsonObject() {
					public Map<String, Long> getCounter() {
						return brand.getCounter();
					}

					public boolean getHasNewUpdate()
					{
						SubscriptionRelation subRel = map.get(brand.getId());
						if (subRel.getLastVisitDate().after(
								brand.getLastModified()))
						{
							return false;
						}
						else
						{
							return true;
						}
					}

					public String getId() {
						return brand.getId();
					}

					public String getLogo() {
						return brand.getIcon();
					}

					public String getName() {
						return brand.getName();
					}
				};
				list.add(o);
			} else {
				@SuppressWarnings("unused")
				Object o = new JsonObject() {
					public Map<String, Long> getCounter() {
						return brand.getCounter();
					}

					public String getId() {
						return brand.getId();
					}

					public String getLogo() {
						return brand.getIcon();
					}

					public String getName() {
						return brand.getName();
					}
				};
				list.add(o);
			}
		}
		return list;
	}

	/**
	 * 品牌下的App列表
	 * 
	 * @param brand
	 *            品牌名称
	 * @return
	 */
	@Path("/{brandName}/")
	@GET
	@Produces("application/json")
	public Response getAppListByCatalog(
			@PathParam("brandName") String brandName) {
		return Response.ok(reduceApps(brandService.getAppsByBrand(brandName)))
				.build();
	}

	/**
	 * 新版品牌列表
	 */
	@Path("/")
	@GET
	@Produces("application/json")
	public Response getNewBrands(@HeaderParam("Authorization") String token,
			@QueryParam("offset") int offset) {
		validToken(token);
		if (offset < 0) {
			return Response.ok().build();
		}
		long total = brandService.countEnabled();
		List<Brand> list = brandService.getEnabledBrands(offset, 9);
		if (offset + list.size() >= total) {
			offset = -1;
		} else {
			offset = offset + 9;
		}
		List<SubscriptionRelation> relations = subscriptionRelationService
				.getSubscriptionRelationListByToken(getToken(token));

		return Response.ok(chaos(transformBrands(list, relations), offset))
				.build();
	}

	/**
	 * 查询已订阅品牌列表
	 * 
	 * @param token
	 * @return
	 */
	@Path("/subscription")
	@GET
	@Produces("application/json")
	public Response getSubscriptionBands(
			@HeaderParam("Authorization") String token,
			@QueryParam("offset") int offset) {
		validToken(token);

		String[] ids = subscriptionRelationService
				.getBrandIdsByToken(getToken(token));
		long total = brandService.countBrandByBrands(ids);
		List<Brand> list = brandService.findBrandByBrands(ids, offset, 9);
		if (offset + list.size() >= total) {
			offset = -1;
		} else {
			offset = offset + 9;
		}
		List<SubscriptionRelation> relations = subscriptionRelationService
				.getSubscriptionRelationListByToken(getToken(token));

		return Response.ok(chaos(transformBrands(list, relations), offset))
				.build();
	}

	@Path("/subscription/{brandId}")
	@GET
	@Produces("text/plain")
	public Response getSubscriptionBrand(
			@PathParam("brandId") String brandId,
			@HeaderParam("Authorization") String token) {
		validToken(token);
		SubscriptionRelation sb = subscriptionRelationService
				.getSubscriptionRelation(getToken(token),
						brandId);
		if (sb == null) {
			return Response.ok("").build();
		}
		return Response.ok(sb.getBrandId()).build();
	}

	/**
	 * 
	 * 查询订阅更新状
	 * 
	 * @param token
	 * @return
	 */
	@Path("/has-new-subscription")
	@GET
	@Produces("application/json")
	public Response hasNewSubscription(
			@HeaderParam("Authorization") String token) throws JSONException {
		validToken(token);

		List<SubscriptionRelation> relations = subscriptionRelationService
				.getSubscriptionRelationListByToken(getToken(token));
		JSONObject json = new JSONObject();
		json.put("hasNew", false);

		for (SubscriptionRelation subscriptionRelation : relations) {
			Brand brand = brandService.findBrandById(subscriptionRelation
					.getBrandId());
			if (brand != null
					&& brand.getLastModified() != null
					&& brand.getLastModified().after(
							subscriptionRelation.getLastVisitDate())) {
				json.put("hasNew", true);
			}
		}

		return Response.ok(json.toString()).build();
	}

	@Autowired
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	@Autowired
	@Required
	public void setDeviceRegistService(DeviceRegistService deviceRegistService) {
		this.deviceRegistService = deviceRegistService;
	}

	@Autowired
	public void setSubscriptionRelationService(
			SubscriptionRelationService subscriptionRelationService) {
		this.subscriptionRelationService = subscriptionRelationService;
	}

	/**
	 * 品牌订阅
	 * 
	 * @param brandId
	 * @return
	 */
	@Path("/subscription/{brandId}")
	@PUT
	public Response subscriptionBrand(
			@PathParam("brandId") String brandId,
			@HeaderParam("Authorization") String token) {
		validToken(token);
		String authToken = getToken(token);
		Device device = deviceRegistService.findDeviceByToken(authToken);
		if (device == null) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		SubscriptionRelation subRel = new SubscriptionRelation();
		subRel.setBrandId(brandId);
		subRel.setToken(authToken);
		subscriptionRelationService.createSubscriptionRelation(subRel);
		return Response.ok().build();
	}

	/**
	 * 取消订阅
	 * 
	 * @param brandId
	 * @return
	 */
	@Path("/subscription/{brandId}")
	@DELETE
	public Response unSubscriptionBrand(
			@PathParam("brandId") String brandId,
			@HeaderParam("Authorization") String token) {
		validToken(token);
		subscriptionRelationService.deleteSubscriptionRelation(getToken(token),
				brandId);
		return Response.ok("").build();
	}

	/**
	 * 更新品牌订阅关系时间
	 * 
	 * @param brandId
	 * @param token
	 * @return
	 */
	@Path("/repository/{brandId}/hasNewUpdate")
	@PUT
	@Produces("application/json")
	public Response updateBrandDate(@PathParam("brandId") String brandId,
			@HeaderParam("Authorization") String token
			) {
		validToken(token);
		subscriptionRelationService.updateSubscriptionRelation(getToken(token),
				brandId);
		return Response.ok().build();
	}
}