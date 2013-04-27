package com.babeeta.appstore.job;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.Header;
import org.archive.io.arc.ARCReader;
import org.archive.io.arc.ARCReaderFactory;
import org.archive.io.arc.ARCRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.babeeta.appstore.android.crawler.AnalyzerConstant;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.DataAnalyzer;
import com.babeeta.appstore.android.crawler.integrateapp.PortalIntegrateApp;
//import com.babeeta.appstore.android.crawler.validate.PortalStyleValidate;
//import com.babeeta.appstore.android.crawler.validate.StyleValidate;
//import com.babeeta.appstore.android.file.BufferedReaderReadFile;

/**
 * arc文件解析的Job
 * @author xuehui.miao
 *
 */
public class Job implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(Job.class);
	private LinkedBlockingQueue<File> readingArcsQueue = new LinkedBlockingQueue<File>();//正在处理的arc
	private LinkedBlockingQueue<File> pendingArcsQueue = new LinkedBlockingQueue<File>();//等待处理的arc
	private static String locationName = "Location";
	private static String contentLength = "Content-Length";
	private String archiveDirectory;
	private static String arcDirectoryName = "arcs";
	private static String marksDirectoryName = "marks";
	private File marksDir;
	protected long sleepTime = 1000*60*60;
	protected DataAnalyzer dataAnalyzer;
	private PortalIntegrateApp portalIntegrateApp;

	
	@Override
	public void run() {
		logger.info("{}","start job......");
		try {
			//判断arcs的根目录是否存在
			if(!new File(archiveDirectory).exists()) {
				throw new Exception("archiveDirectory Home:" + archiveDirectory + " doesn't exist!");
			}
			//创建已读标记文件夹
			this.createMarkDir();
			
			while(true) {
				//循环arcs目录,把arc.gz文件添加到队列中
				this.loopArcFile();
				//循环处理队列文件
				this.loopPendingArc();
				//处理一轮之后,先休息一会
				try {
					logger.info("Ha,i have already took a loop for archiveDirectory:{},let's smoke a cigarette before next loop",archiveDirectory);
					if(sleepTime <= 0)sleepTime = 600000;
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			logger.error("{}", e.getMessage(), e);
		}
	}
	
	/**
	 * 循环arcs目录,把arc.gz文件添加到队列中
	 * @throws Exception
	 */
	private void loopArcFile() throws Exception {
		File arcFile = new File(archiveDirectory,arcDirectoryName);
		if(!arcFile.exists()) {
			throw new Exception("not exist path " + archiveDirectory + arcDirectoryName);
		}
		for (File gzFile:arcFile.listFiles()) {
			if(!this.isComplete(gzFile) || !this.unRead(gzFile)) {
				continue;
			}
			
			if(this.isPendingFile(gzFile)) {
				continue;
			}
			pendingArcsQueue.add(gzFile);
		}
	}
	
	/**
	 * 循环处理队列文件
	 */
	private void loopPendingArc() {
		logger.info("prepared analyzer [{}] arc file......",pendingArcsQueue.size());
		while(!pendingArcsQueue.isEmpty()) {
			File arcFile = this.nextArcFile();
			this.readArc(arcFile);
			this.completeArcFile(arcFile);
		}
		logger.info("{}","current loop is completed......");
	}
	
	/**
	 * 从等待队列中读取一个arc.gz文件进行处理
	 * @param arc
	 */
	private void readArc(File arc) {
		ARCReader reader = null;
		try {
			reader = ARCReaderFactory.get(arc, 0);
			for (Iterator i =reader.iterator(); i.hasNext();) {
				ARCRecord r = (ARCRecord) i.next();
				try {
					String url = r.getHeader().getUrl();
					long length = this.getContentLength(r);
					logger.debug("url:{},contentLength:{}",url,length);
//					if(url.startsWith("http://c11.eoemarket.com//upload/") && r.getStatusCode() != 304) {
//						//logger.info("yes:{}", r.getStatusCode());
//						//logger.info("headerStringLength:{}", r.getHeaderString().length());
//					}
//					if(url.equalsIgnoreCase("http://c11.eoemarket.com//upload/2012/0305/apps/79377//apks/164962/3005fb4d-4b45-4450-85e8-ab45f77bfa5e.apk")) {
//						logger.info("yes:{}", r.getStatusCode());
//						logger.info("available:{}", r.available());
//						logger.info("getBodyOffset:{}", r.getBodyOffset());
//					}
					if(r.getStatusCode() == 304) {//304内容没有发生改变
						continue;
					}
					if(r.available() < r.getBodyOffset() + length) {//内容下载不全,暂不处理
						logger.error("url[{}] content is not full download,now download length[{}],but original content length[{}].", new Object[]{url,r.available(),length});
						continue;
					}
					if(!AnalyzerConstant.checkUrl(url)) {//判断URL是否需要解析的url
						continue;
					}
					analyzerARCRecord(r);
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						r.close();
						r = null;
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 对每一个ARCRecord进行解析
	 * @param r
	 * @throws Exception
	 */
	private void analyzerARCRecord(ARCRecord r) throws Exception {
		String url = r.getHeader().getUrl();
		try {
			//校验HTML解析样式是否发生变更
//			ARCRecord cobj = (ARCRecord)cloneObject(r);
//			cobj.skipHttpHeader();
//			StyleValidate validate = PortalStyleValidate.getStyleValidate(url, cobj);
//			cobj = null;
//			if(validate !=null && !validate.validateStyle()) {
//				logger.error("url[{}]{}", url,validate.getValidateMessage());
//				return ;
//			}
			
			Context context = new Context();
			context.setUrl(url);
			context.setLocation(this.getLocation(r));
			logger.debug("解析URL:{}",url);
			r.skipHttpHeader();
			
			//分析数据
			dataAnalyzer.process(r, context);			
			
			//整合APP
			portalIntegrateApp.integrateOneAPP(url);
		} catch (Exception e) {
			logger.error("解析URL[{}]throws exception:\n",url, e);
		}
	}
	
	/**
	 * 获取下一个执行的arc文件
	 * @return
	 */
	public File nextArcFile(){
		File arc = null;
		try {
			arc = pendingArcsQueue.poll(5, TimeUnit.MINUTES);
			if(arc!=null)readingArcsQueue.add(arc);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return arc;
	}
	
	
	/**
	 * arc文件解析完毕
	 * @param arc
	 */
	public void completeArcFile(File arc){
		readingArcsQueue.remove(arc);
		File mark = new File(marksDir, arc.getName());
		try {
			mark.createNewFile();
		} catch (Exception e) {
			this.createMarkDir();
			try {
				mark.createNewFile();
			} catch (Exception e2) {
				logger.error("create mark file["+mark.getAbsolutePath()+"] throws exception:", e);
			}
		}
		logger.info("Marked arc:{}",arc.getName());
	}
	
	/**
	 * 创建已读标记文件夹
	 */
	private void createMarkDir() {
		marksDir = new File(archiveDirectory, marksDirectoryName);
		if(!marksDir.exists()) {
			marksDir.mkdir();
		}
	}
	
	/**
	 * 判断arc文件是否是完整的.gz文件
	 * @param arc
	 * @return
	 */
	private boolean isComplete(File arc){
		String suffix = arc.getAbsolutePath().substring(arc.getAbsolutePath().lastIndexOf('.'), arc.getAbsolutePath().length());
		if(suffix.equals(".gz")){
			//.gz结尾是完整的arc压缩文件。
			return true;
		}
		//其他一般都是.open,爬虫正在往压缩文件里写入
		return false;
	}
	
	/**
	 * 判断文件是否已读
	 * @param arc
	 * @return
	 */
	public boolean unRead(File arc) {
		File mark = new File(marksDir,arc.getName());
		return !mark.exists();
	}
	
	/**
	 * 判断文件是否在等待队列中已经存在
	 * @param gzFile
	 * @return
	 */
	private boolean isPendingFile(File gzFile)  {
		boolean isPending = false;
		for (File file:pendingArcsQueue) {
			if(file.getAbsolutePath().equalsIgnoreCase(gzFile.getAbsolutePath())) {
				isPending = true;
				break;
			}
		}
		return isPending;
	}
	
	/**
	 * 从httpHeader中获取Content-Length
	 * @param r
	 * @return
	 */
	private long getContentLength(ARCRecord r) {
		long length = 0;
		try {
			length = Integer.parseInt(this.getHttpHeader(r, contentLength));
		} catch (Exception e) {
			
		}
		return length;
	}
	
	/**
	 * 从httpHeader中获取location
	 * @param r
	 * @return
	 */
	private String getLocation(ARCRecord r) {
		return this.getHttpHeader(r, locationName);
	}
	
	/**
	 * 从httpHeader中获取location
	 * @param r
	 * @param headerName
	 * @return
	 */
	private String getHttpHeader(ARCRecord r,String headerName) {
		Header headers[] = r.getHttpHeaders();
		if(headers == null)return "";
		for (int i = 0; i < headers.length; i++) {
			Header header = headers[i];
			if(headerName.equalsIgnoreCase(header.getName())) {
				return header.getValue();
			}
		}
		return "";
	}

	public void setArchiveDirectory(String archiveDirectory) {
		this.archiveDirectory = archiveDirectory;
	}
	
	@Autowired
	@Required
	public void setDataAnalyzer(DataAnalyzer dataAnalyzer) {
		this.dataAnalyzer = dataAnalyzer;
	}
	
	@Autowired
	@Required
	public void setPortalIntegrateApp(PortalIntegrateApp portalIntegrateApp) {
		this.portalIntegrateApp = portalIntegrateApp;
	}

	public void start() {
		new Thread(this).start();
	}
}
