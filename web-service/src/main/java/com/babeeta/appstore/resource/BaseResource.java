/**
 * 
 */
package com.babeeta.appstore.resource;

/**
 * 资源基础类
 * @author xuehui.miao
 *
 */
public abstract class BaseResource {
	/**
	 * 判断token格式是否合法
	 * 
	 * @param token
	 */
	protected static String extractToken(String token) {
		if (token == null || !token.startsWith("Token")) {
			return "";
		}
		return token.replace("Token ", "");
	}
}
