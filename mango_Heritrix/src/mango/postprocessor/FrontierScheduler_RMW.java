package mango.postprocessor;

import org.archive.crawler.datamodel.CandidateURI;
import org.archive.crawler.postprocessor.FrontierScheduler;

/**
 * 针对人民网定制frontierScheduler，不抓取某些文件
 * @author songwen
 *
 */
public class FrontierScheduler_RMW extends FrontierScheduler {


	private static final long serialVersionUID = -6570595407661584431L;

	public FrontierScheduler_RMW(String name) {
		super(name);
	}

	@Override
	protected void schedule(CandidateURI caUri) {
		// 获取等待队列URL字符串
				String urlString = caUri.toString();
				try {
					//
					if (urlString.indexOf("people.com.cn") != -1
							||urlString.indexOf("robot.txt") != -1
							||urlString.indexOf("dns:") != -1
							||urlString.endsWith(".jpg")
							|| urlString.endsWith(".gif")
							|| urlString.endsWith(".jpeg")){
						if (urlString.endsWith(".css")
							||urlString.endsWith(".js")
							|| urlString.indexOf("#") != -1){
							return;
						}
						getController().getFrontier().schedule(caUri);
					}else {
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	

}