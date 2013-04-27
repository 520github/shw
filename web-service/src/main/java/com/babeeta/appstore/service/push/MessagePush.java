package com.babeeta.appstore.service.push;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessagePush {
	private final int timeoutConnection = 5000;
	private final int timeoutSocket = 30000;

	private final Logger LOG = LoggerFactory.getLogger(MessagePush.class);
	private String host;
	private String appId;
	private String appKey;

	private String postEntity(String clientId, HttpClient httpClient,
			String content) {
		String token = appId + ":" + appKey;
		String authorization = "Basic ";
		try {
			authorization = authorization
					+ new String(Base64.encodeBase64(token.getBytes()), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			LOG.error(e1.getMessage(), e1);
		}
		HttpPost httpPost = new HttpPost(host + "/service/client/" + clientId
				+ "/message");
		httpPost.setHeader("Authorization", authorization);
		httpPost.setHeader("Content-Type", "application/json");
		StringBuffer sb = new StringBuffer();
		try {
			HttpEntity reqEntity = new StringEntity(content, "utf-8");
			httpPost.setEntity(reqEntity);
			HttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity resEntity = response.getEntity();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(resEntity.getContent(), "UTF-8"));
				String str = "";
				while ((str = reader.readLine()) != null) {
					sb.append(str);
				}
				return sb.toString();
			}
			LOG.error(" call api error :"
					+ response.getStatusLine().getStatusCode());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			httpPost.abort();
		}
		return null;
	}

	public HttpClient getHttpConnection() {
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		return new DefaultHttpClient(httpParameters);
	}

	public String pushMessage(HttpClient httpClient, String clientId,
			String content) {
		return postEntity(clientId,
				httpClient, content);

	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
