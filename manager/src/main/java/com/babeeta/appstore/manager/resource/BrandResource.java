package com.babeeta.appstore.manager.resource;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.dao.EntityNotFoundException;
import com.babeeta.appstore.entity.Brand;
import com.babeeta.appstore.manager.service.BrandService;
import com.babeeta.appstore.manager.servlet.FileUploadServlet;
import com.google.common.io.Files;

@Path("/api/brand")
@Controller("brandResource")
@Scope("prototype")
public class BrandResource {

	private static final File DIR_BRAND = new File(
			"/var/lib/android-coral-bay/web-static/client/brand");

	private BrandService brandService;

	private AuthenticationProvider authenticationProvider = null;

	public BrandResource() {
		super();
		DIR_BRAND.mkdirs();
	}

	private String unzip(String id) throws IOException {
		File source = new File(new File(FileUploadServlet.DIR_UPLOAD, "brand"),
				authenticationProvider.getUsername());
		if (source.exists()) {
			File zipFilePath = new File(DIR_BRAND, id.substring(
					id.length() - 6, id.length()));
			if (zipFilePath.createNewFile()) {
				Files.move(source, zipFilePath);
				File targetDir = new File(DIR_BRAND + File.separator + id);
				if (targetDir.exists()) {
					FileUtils.cleanDirectory(targetDir);
				} else {
					targetDir.mkdir();
				}
				unZipToFolder(zipFilePath, DIR_BRAND + File.separator + id);
				return ("http://m.shanhubay.com/android/client/brand/"
				+ id);
			}
		}
		return "";
	}

	private void unZipToFolder(File zipfile, String outputdir)
			throws IOException {
		if (zipfile.exists()) {
			outputdir = outputdir + File.separator;
			FileUtils.forceMkdir(new File(outputdir));
			ZipFile zf = new ZipFile(zipfile, "UTF-8");
			Enumeration zipArchiveEntrys = zf.getEntries();
			OutputStream out = null;
			while (zipArchiveEntrys.hasMoreElements()) {
				ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys
						.nextElement();
				if (zipArchiveEntry.isDirectory()) {
					FileUtils.forceMkdir(new File(outputdir
							+ zipArchiveEntry.getName() + File.separator));
				} else {
					out = FileUtils.openOutputStream(new File(outputdir
							+ zipArchiveEntry.getName()));
					IOUtils.copy(zf.getInputStream(zipArchiveEntry), out);
					out.close();
				}
			}
			zf.close();
			if (!zipfile.delete()) {
				System.out.println("删除失败" + zipfile.getAbsolutePath());
			}
		} else {
			throw new IOException("指定的解压文件不存在：");
		}
	}

	@Path("/")
	@GET
	@Produces("application/json;charset=utf-8")
	public Response findAll() {
		return Response.ok(brandService.findAll()).build();
	}

	public AuthenticationProvider getAuthenticationProvider() {
		return authenticationProvider;
	}

	@Path("/{id}")
	@GET
	@Produces("application/json;charset=utf-8")
	public Response getBrandById(@PathParam("id") String id) {
		return Response.ok(brandService.findById(id)).build();
	}

	@Path("/notice")
	@POST
	public Response notice() throws IOException {
		brandService.notice();
		return Response.ok().build();
	}

	@Path("/{id}")
	@DELETE
	public void removeBrand(@PathParam("id") String id) {
		brandService.remove(id);
	}

	@Path("/")
	@POST
	@Consumes("application/json")
	public Response saveBrand(Brand brand) throws IOException {
		Brand org = brandService.findByName(brand.getName());

		if (brand.getId() == null || brand.getId().length() == 0) {
			if (org != null) {
				return Response.status(Status.CONFLICT).build();
			}
			brand.setId(UUID.randomUUID().toString().replaceAll("-", ""));

			brand.setLastModified(new Date());// 新建时修改更新，修改时不通知更新
			// 处理zip包
			if ("fileupload/brand".equals(brand.getIcon())) {
				brand.setIcon(unzip(brand.getId()));
			}
			brandService.save(brand);
		} else {
			org.setWeight(brand.getWeight());

			if ("fileupload/brand".equals(brand.getIcon())) {
				brand.setIcon(unzip(org.getId()));
			}
			brandService.save(org);
		}

		return Response.ok().build();
	}

	@Autowired
	@Required
	public void setAuthenticationProvider(
			AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	@Autowired
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	@Path("/updateNew/{id}")
	@PUT
	@Produces("application/json;charset=utf-8")
	public Response updateBrand(@PathParam("id") String id) {
		Brand brand = brandService.findById(id);
		brand.setLastModified(new Date());
		brandService.save(brand);
		return Response.ok(brand).build();
	}

	@Path("/{id}-{state}")
	@PUT
	public void updateBrandState(@PathParam("id") String id,
			@PathParam("state") boolean state) {
		Brand brand = brandService.findById(id);
		if (brand == null) {
			throw new EntityNotFoundException(Brand.class, id);
		}
		brandService.updateBrandState(id, state);
	}
}
