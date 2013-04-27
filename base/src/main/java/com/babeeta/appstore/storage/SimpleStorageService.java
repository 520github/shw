package com.babeeta.appstore.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 简单存储服务。 可以用来存储 APK 等等各种二进制数据、文件
 * 
 * @author chongf
 */
public interface SimpleStorageService {
	/**
	 * 删除一个文件
	 * 
	 * @param id
	 *            要删除文件的 ID
	 * @param volume
	 *            存储卷
	 * @throws FileNotFoundException
	 *             文件不存在
	 * @throws IOException
	 *             发生了未知的 IO 异常
	 */
	public void delete(String volume, String id) throws FileNotFoundException,
			IOException;

	/**
	 * 读取一个文件的内容
	 * 
	 * @param id
	 * @param volume
	 *            存储卷
	 * @return 用于读取文件内容的 InputStream
	 * @throws FileNotFoundException
	 *             文件不存在
	 * @throws IOException
	 *             发生了位置的 IO 异常
	 */

	public InputStream get(String volume, String id)
			throws FileNotFoundException, IOException;

	/**
	 * 保存一个新文件到存储中
	 * 
	 * @param inputStream
	 *            用于读取被保存文件内容的 InputStream
	 * @param volume
	 *            存储卷
	 * @return 文件成功保存后被分配的 ID 号。之后可以通过这个 ID 号操作这个文件
	 * @throws IOException
	 *             发生了异常,存储操作失败
	 */

	public String put(String volume, InputStream inputStream)
			throws IOException;

}
