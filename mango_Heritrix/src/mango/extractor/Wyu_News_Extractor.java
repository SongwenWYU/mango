package mango.extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.httpclient.URIException;
import org.archive.crawler.datamodel.CrawlURI;
import org.archive.crawler.extractor.Extractor;
import org.archive.crawler.extractor.Link;
import org.archive.io.ReplayCharSequence;
import org.archive.util.HttpRecorder;

public class Wyu_News_Extractor extends Extractor {

	/**
	 * 扩展Extractor 类 获取五邑大学新闻入口地址
	 */
	private static final long serialVersionUID = 1L;
	protected boolean ignoreUnexpectedHTML = true;

	// 五邑大学官网新闻页面
	private static final String WYU_NEWS_CN = "http://www.wyu.cn/news";
	private static Logger logger = Logger.getLogger(Wyu_News_Extractor.class
			.getName());

	// 构造函数
	public Wyu_News_Extractor(String name, String description) {
		super(name, description);
	}

	public Wyu_News_Extractor(String name) {
		super(name, "WYU_NEWS extractor , Extractor links from HTML documents");
	}

	@Override
	protected void extract(CrawlURI curi) {
		// 获取URL的字符串
		String urlString = curi.toString();
		// 如果当前处理的URL是新闻页
		if (urlString.contains(WYU_NEWS_CN)) {
			ReplayCharSequence csReplayCharSequence = null;

			try {
				HttpRecorder httpRecorder = curi.getHttpRecorder();
				if (httpRecorder == null) {
					throw new IOException("Why is recorder null here?");
				}
				// 获取页面内容
				csReplayCharSequence = httpRecorder.getReplayCharSequence();
			} catch (IOException e) {
				curi.addLocalizedError(this.getName(), e,
						"Failed get of replay char sequence " + curi.toString()
								+ " " + e.getMessage());
				logger.log(Level.SEVERE,
						"Failed get of replay char sequence in "
								+ Thread.currentThread().getName());
			}
			// 如果页面内容无法获取
			if (csReplayCharSequence == null) {
				return;
			}
			// 将页面转换为字符串
			String content = csReplayCharSequence.toString();
			try {
				BufferedReader reader = new BufferedReader(new StringReader(
						content));
				// 一行行的读取
				String lineString = reader.readLine();
				while (lineString != null) {
					// 遇到新闻页面链接地址时
					if (lineString.contains("href=\"http://www.wyu.cn/news")) {
						String fulString = null;
						fulString = lineString.substring(
								lineString.indexOf("href=") + 6,
								lineString.lastIndexOf("\"></a>"));
						addLinkFromString(curi, fulString, "", Link.NAVLINK_HOP);
						//System.out.println(fulString);
					} else if (lineString.contains("href=\"..")) {
						String fulString = null;
						fulString = WYU_NEWS_CN
								+ lineString.substring(lineString.indexOf("\"..") + 3,lineString.indexOf("\"",lineString.indexOf("\"..") + 3));
						addLinkFromString(curi, fulString, "", Link.NAVLINK_HOP);
					//	System.out.println(fulString);
					} else if (lineString.contains("href=\"news")
							|| lineString.contains("href=\"default.asp")) {
						String fulString = null;
						fulString = WYU_NEWS_CN
								+ "/"
								+ lineString.substring(
										lineString.indexOf("href=") + 6,
										lineString.lastIndexOf("\"></a>"));
						addLinkFromString(curi, fulString, "", Link.NAVLINK_HOP);
					//	System.out.println(fulString);
					}
					lineString = reader.readLine();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 将解析出来的URL加入待处理列表中
	 * 
	 * @param curi
	 * @param uri
	 * @param context
	 * @param hopType
	 */
	private void addLinkFromString(CrawlURI curi, String uri,
			CharSequence context, char hopType) {
		try {
			// 加入URL
			curi.createAndAddLinkRelativeToBase(uri, context.toString(),
					hopType);
		} catch (URIException e) {
			if (getController() != null) {
				getController().logUriError(e, curi.getUURI(), uri);
			} else {
				logger.info("Failed createAndAddLinkRelativeToBase " + curi
						+ ", " + uri + ", " + context + ", " + hopType + ": "
						+ e);
			}
		}
	}

}