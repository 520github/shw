package com.babeeta.appstore.manager.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.io.Files;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = -4008822373717029652L;
	private static final Pattern urlPattern = Pattern
			.compile("fileupload/([^/\\\\]+)$");

	public static final File DIR_UPLOAD = new File(
			"/var/lib/android-coral-bay/web-static/fileupload");
	static {
		DIR_UPLOAD.mkdirs();
	}

	private File getFile(HttpServletRequest req) {
		Matcher matcher = urlPattern.matcher(req.getRequestURI());
		if (!matcher.find()) {
			return null;
		} else {
			File folder = new File(DIR_UPLOAD, matcher.group(1));
			folder.mkdirs();
			return new File(folder,
					req.getUserPrincipal().getName());
		}
	}

	private void sendFile(File target, HttpServletResponse resp)
			throws IOException {
		Files.copy(target, resp.getOutputStream());
	}

	private void write(ServletInputStream inputStream, File target)
			throws IOException {
		byte[] buf = new byte[10240];
		int len = -1;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(target);
			while ((len = inputStream.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception ignore) {
				}
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Matcher matcher = urlPattern.matcher(req.getRequestURI());
		if (matcher.find()) {
			File target = new File(DIR_UPLOAD, matcher.group(1) + "/"
					+ req.getUserPrincipal().getName());
			if (target.exists()) {
				resp.setContentType("image/png");
				resp.setDateHeader("Last-Modify", target.lastModified());
				resp.setContentLength((int) target.length());
				sendFile(target, resp);

			}
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		File target = getFile(req);
		write(req.getInputStream(), target);
		resp.setStatus(HttpServletResponse.SC_CREATED);
		resp.setHeader("Content-Type", "application/json");
		PrintWriter writer = resp.getWriter();
		writer.print("{\"filename\":\"");
		writer.print(req.getUserPrincipal().getName());
		writer.print("\"}");
	}

}
