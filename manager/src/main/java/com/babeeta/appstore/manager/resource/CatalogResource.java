package com.babeeta.appstore.manager.resource;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.dao.EntityNotFoundException;
import com.babeeta.appstore.entity.Catalog;
import com.babeeta.appstore.manager.service.CatalogService;
import com.babeeta.appstore.manager.servlet.FileUploadServlet;
import com.google.common.base.Strings;
import com.google.common.io.Files;

@Path("/api/catalog")
@Controller("catalogResource")
@Scope("prototype")
public class CatalogResource {

	private static final File DIR_CATALOG = new File(
			"/var/lib/android-coral-bay/web-static/client/image/catalog");

	private static <T> List<T> subList(List<T> list, int offset, int limit) {
		if (list.isEmpty() || offset >= list.size()) {
			return Collections.emptyList();
		} else {
			int endIndex = offset + limit;
			if (endIndex >= list.size()) {
				endIndex = list.size();
			}
			return list.subList(offset, endIndex);
		}
	}

	private AuthenticationProvider authenticationProvider = null;

	private CatalogService catalogService;

	public CatalogResource() {
		super();
		DIR_CATALOG.mkdirs();
	}

	@Path("/")
	@GET
	@Produces("application/json")
	public PagedResult<Catalog> findAll(@QueryParam("search") String search,
			@QueryParam("parent") String parent,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		List<Catalog> data = null;
		if (Strings.isNullOrEmpty(parent)) {
			data = catalogService.findAll();
		} else if ("null".equals(parent)) {
			data = catalogService.findAll(true);
		} else {
			data = catalogService.findByParent(parent);
		}
		List<Catalog> result = null;
		if (Strings.isNullOrEmpty(search)) {
			result = data;
		} else {
			result = new LinkedList<Catalog>();
			for (Catalog catalog : data) {
				if (catalog.getName().indexOf(search) != -1) {
					result.add(catalog);
				}
			}
		}
		return new PagedResult<Catalog>().setResult(
				limit == 0 ? result : subList(result, offset, limit)).setTotal(
				result.size());
	}

	@Path("/{name}")
	@GET
	@Produces("application/json")
	public Catalog findByName(@PathParam("name") String name) {
		Catalog catalog = catalogService.findByName(name);
		if (catalog != null) {
			return catalog;
		} else {
			throw new EntityNotFoundException(Catalog.class, name);
		}
	}

	public AuthenticationProvider getAuthenticationProvider() {
		return authenticationProvider;
	}

	@Path("/{name}/editorOpinion")
	@GET
	@Produces("application/json")
	public List<String> getEditorOpinion(@PathParam("name") String name) {
		Catalog catalog = catalogService.findByName(name);
		if (catalog != null) {
			return catalog.getEditorOpinion();
		} else {
			throw new EntityNotFoundException(Catalog.class, name);
		}
	}

	@Path("/{name}")
	@DELETE
	public void remove(@PathParam("name") String name) {
		catalogService.remove(name);
	}

	@Path("/{name}")
	@PUT
	@Consumes("application/json")
	public void saveCatalog(Catalog catalog, @PathParam("name") String name)
			throws IOException {
		Catalog org = catalogService.findByName(name);
		catalog.setName(name);
		if (org != null) {
			catalog.setEditorOpinion(org.getEditorOpinion());
		}

		// 处理图标
		if ("fileupload/catalog".equals(catalog.getIcon())) {
			File target = new File(new File(FileUploadServlet.DIR_UPLOAD,
					"catalog"), authenticationProvider.getUsername());
			if (!target.exists()) {
				catalog.setIcon(null);
			} else {
				File staticFile = null;
				do {
					staticFile = new File(DIR_CATALOG, UUID.randomUUID()
							.toString());
				} while (!staticFile.createNewFile());

				Files.move(target, staticFile);
				catalog
						.setIcon("http://static.shanhubay.com/android/client/image/catalog/"
								+ staticFile.getName());
			}
		}
		catalog.setEnabled(false);
		catalogService.save(catalog);
	}

	@Path("/{name}/editorOpinion")
	@PUT
	@Consumes("application/json")
	public void saveEditorOpinion(@PathParam("name") String name,
			List<String> editorOpinion) {
		Catalog catalog = catalogService.findByName(name);
		if (catalog != null) {
			catalog.setEditorOpinion(editorOpinion);
			catalogService.save(catalog);
		}
	}

	@Autowired
	@Required
	public void setAuthenticationProvider(
			AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	@Autowired
	@Required
	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@Path("/{name}/enabled")
	@PUT
	@Consumes("application/json")
	public void setEnabled(@PathParam("name") String name, boolean enabled) {
		catalogService.setEnabled(name, enabled);
	}

}
