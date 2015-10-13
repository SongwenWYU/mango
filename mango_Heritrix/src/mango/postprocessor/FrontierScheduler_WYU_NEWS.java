package mango.postprocessor;

import java.util.logging.Logger;

import org.archive.crawler.datamodel.CandidateURI;
import org.archive.crawler.postprocessor.FrontierScheduler;

public class FrontierScheduler_WYU_NEWS extends FrontierScheduler {

	private static final long serialVersionUID = -1969654020268617114L;
	private static final Logger LOGGER = Logger
			.getLogger(FrontierScheduler_WYU_NEWS.class.getName());

	public FrontierScheduler_WYU_NEWS(String name) {
		super(name);
	}

	@Override
	protected void schedule(CandidateURI caUri) {

		// 获取等待队列URL字符串
		String urlString = caUri.toString();
		try {
			// 绕过某些文件
			if (urlString.endsWith(".zip") || urlString.endsWith(".rar")
					|| urlString.endsWith(".exe")
					|| urlString.indexOf("robots.txt") != -1
					|| urlString.indexOf("dns:") != -1) {
				return;
			}
			// 检索Url是否带有 “#”号，这些一般带有特殊链接
			if (urlString.indexOf("#") == -1) {
				getController().getFrontier().schedule(caUri);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}