package com.babeeta.appstore.manager.resource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.manager.service.ConfigService;

@Path("/api/config")
@Controller("configResource")
@Scope("prototype")
public class ConfigResource {
	public static final String DIR_INDEX = "/var/lib/android-coral-bay/web-static/client/home/index.json";
	public static final String DIR_INDEX_TEMP = "/var/lib/android-coral-bay/web-static/client/home/index_temp.json";

	private ConfigService configService;

	@Path("/json")
	@GET
	@Produces("text/plain")
	public Response getIndexJson() {
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		String temp = "temp";
		try {
			File f = new File(DIR_INDEX_TEMP);
			if (!f.exists()) {
				f = new File(DIR_INDEX);
				temp = "";
			}
			in = new BufferedReader(new InputStreamReader(
					new FileInputStream(f), "UTF-8"));
			String str = "";
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return Response.ok(temp + sb.toString()).build();
	}

	@Path("/json")
	@PUT
	@Produces("text/plain")
	public Response saveJson() {
		configService.moveTempToJson(DIR_INDEX_TEMP, DIR_INDEX);
		return Response.ok("true").build();
	}

	@Path("/tempjson")
	@PUT
	@Consumes("text/plain")
	@Produces("text/plain")
	public Response saveTempJson(String json) {
		json = json.replaceAll("\\n", "").replaceAll("\\t", "");
		try {
			new JSONObject(json);
		} catch (JSONException e1) {
			StringWriter sw = new StringWriter();
			e1.printStackTrace(new PrintWriter(sw));
			return Response.ok(sw.toString()).build();
		}

		BufferedWriter out = null;
		try {
			File f = new File(DIR_INDEX_TEMP);
			if (!f.exists()) {
				f.createNewFile();
			}
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f), "UTF-8"));
			out.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return Response.ok("true").build();
	}

	@Autowired
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

}
