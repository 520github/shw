package com.babeeta.appstore.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.ApplicationMarketDetail;
import com.babeeta.appstore.entity.ApplicationMarketDetail.Score;
import com.babeeta.appstore.resource.JsonObject;

class YyyyMmDd extends JsonSerializer<Date>
{

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		jsonGenerator.writeString(sdf.format(date));
	}
}

public class BestAppReduceServiceImpl {

	private static Object reduceSimpleDetail(final App app) {
		@SuppressWarnings("unused")
		Object o = new JsonObject() {
			public ApplicationMarketDetail.Developer getDeveloper() {
				return app.getDetail().getDeveloper();
			}

			public String getLastUpdate() {
				return app.getDetail().getLastUpdate();
			}

			public String getLogo() {
				return app.getDetail().getLogo();
			}

			public String getMarket() {
				return app.getDetail().getMarket();
			}

			public String getName() {
				return app.getDetail().getName();
			}

			public Score getScore() {
				Score score = app.getDetail()
						.getWholeVersionScore();
				if (score == null) {
					score = new Score().setStar(0).setVote(0);
				}
				return score;
			}

			public String getSize() {
				return app.getDetail().getSize();
			}

			public String getVersion() {
				return app.getDetail().getVersion();
			}

			public String getVersionCode() {
				return app.getDetail().getVersionCode();
			}
		};
		return o;
	}

	public static Object getAppPageList(final List<App> apps, final long total,
			final boolean lastPage) {
		@SuppressWarnings("unused")
		Object o = new JsonObject() {
			public boolean getLastPage() {
				return lastPage;
			}

			public List<Object> getResult() {
				List<Object> list = new ArrayList<Object>();
				for (final App app : apps) {
					list.add(reduceApp(app));
				}
				return list;
			}

			public long getTotal() {
				return total;
			}

		};
		return o;
	}

	public static Object reduceApp(final App app) {
		@SuppressWarnings("unused")
		Object o = new JsonObject() {
			@JsonSerialize(using = YyyyMmDd.class)
			public Date getBestLastModified() {
				return app.getBestLastModified();
			}

			public List<String> getCatalog() {
				return app.getCatalog();
			}

			public String getDescription() {
				return app.getDescription();
			}

			public Object getDetail() {
				return reduceSimpleDetail(app);
			}

			public String getId() {
				return app.getId();
			}

			public String getIntroduction() {
				return app.getIntroduction();
			}

			public String getReview() {
				return app.getReview();
			}
		};
		return o;
	}

}