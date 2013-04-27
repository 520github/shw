package com.babeeta.appstore.manager.resource;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.common.io.Files;

@Path("/api/fileupload")
@Controller("fileUploadResource")
@Scope("prototype")
public class FileUploadResource {
	private AuthenticationProvider authenticationProvider;
	private final File DIR_UPLOAD;

	public FileUploadResource() {
		super();
		DIR_UPLOAD = new File("/var/lib/android-coral-bay/web-static/upload");
		DIR_UPLOAD.mkdirs();
	}

	@Path("/{dir}")
	@POST
	@Consumes("application/binary")
	public void uploadPNG(byte[] fileData)
			throws IOException {
		File target = new File(DIR_UPLOAD, authenticationProvider.getUsername()
				+ ".png");
		Files.write(fileData, target);
	}
}
