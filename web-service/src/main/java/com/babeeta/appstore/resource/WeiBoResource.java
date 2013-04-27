/**
 * 
 */
package com.babeeta.appstore.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.WeiBo;
import com.babeeta.appstore.service.WeiBoService;


/**
 * @author xuehui.miao
 *
 */
@Path("/")
@Controller
public class WeiBoResource extends BaseResource {
	private WeiBoService weiBoService;
	
	@Path("/device/log")
	@POST
	@Consumes({"application/json"})
	public Response saveWeiBoLog(@HeaderParam("Authorization") String token, WeiBo weiBo) {
		if((token =extractToken(token)).equals("")) {//非法身份
			return Response.status(Status.UNAUTHORIZED).build();
		}
		else {
			weiBo.setAuthToken(token);
			//weiBo.setSource(WeiBo.enumSource.sina);//来源新浪
			weiBoService.saveWeiBo(weiBo);
		}
		return Response.ok().build();
	}
	
	@Autowired
	@Required
	public void setWeiBoService(WeiBoService weiBoService) {
		this.weiBoService = weiBoService;
	}
	
	
	
}
