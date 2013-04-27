/**
 * 
 */
package com.babeeta.appstore.android.crawler.validate;

/**
 * 各个市场的样式格式校验
 * @author xuehui.miao
 *
 */
public interface StyleValidate {
	
	/**
	 * 校验
	 * @return
	 */
	public boolean validateStyle();
	
	/**
	 * 获取校验结果
	 * @return
	 */
	public String getValidateMessage();
}
