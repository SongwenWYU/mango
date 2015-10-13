package mango.extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.URIException;
import org.archive.crawler.datamodel.CrawlURI;
import org.archive.crawler.extractor.Extractor;
import org.archive.crawler.extractor.Link;
import org.archive.io.ReplayCharSequence;
import org.archive.util.HttpRecorder;

/**
 * 针对人民网制定的Extractor，抓取人民网新闻
 * 
 * @author songwen
 *
 */
public class RMW_Extractor extends Extractor {

	private static final long serialVersionUID = 1L;

	private static String RMW = "people.com.cn";
	// private static final String PATTERN_A_HREF =
	// "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";
	private static final String PATTERN_A_HREF = "href=\"(?<url>[\\s\\S]+?)\"";
	private static final String PATTERN_A_HREF2 = "href=\'(?<url>[\\s\\S]+?)\'";
	private static Logger logger = Logger.getLogger(RMW_Extractor.class
			.getName());

	public RMW_Extractor(String name, String description) {
		super(name, description);
	}

	public RMW_Extractor(String name) {
		super(name, "people extractor , Extractor links from HTML documents");
	}

	@Override
	protected void extract(CrawlURI curi) {
		// 获取URL的字符串
		String urlString = curi.toString();
		// 如果当前处理的URL是新闻页
		if (urlString.contains(RMW)) {
			ReplayCharSequence csReplayCharSequence = null;

			try {
				HttpRecorder httpRecorder = curi.getHttpRecorder();
				if (httpRecorder == null) {
					throw new IOException("Why is recorder null here?");
				}
				// 获取页面内容
				httpRecorder.setCharacterEncoding("GBK");
				csReplayCharSequence = httpRecorder.getReplayCharSequence();
				

			} catch (IOException e) {
				curi.addLocalizedError(this.getName(), e,
						"Failed get of replay char sequence " + curi.toString()
								+ " " + e.getMessage());
				logger.log(Level.SEVERE,
						"Failed get of replay char sequence in "
								+ Thread.currentThread().getName(), e);
			}
			// 如果页面内容无法获取
			if (csReplayCharSequence == null) {
				return;
			}
			// 将页面转换为字符串
			String content = csReplayCharSequence.toString();
			String errString = null;
			try {
				Pattern pattern = Pattern.compile(PATTERN_A_HREF,
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(content);
				Pattern pattern2 = Pattern.compile(PATTERN_A_HREF2,
						Pattern.CASE_INSENSITIVE);
				Matcher matcher2 = pattern2.matcher(content);

				while (matcher.find()) {
					String matcherString = matcher.group();
					if (matcherString != null) {
						Pattern p = Pattern.compile("\\s*|\t|\r|\n");
						Matcher m = p.matcher(matcherString);
						String lineString = m.replaceAll("");
						errString = lineString;
						lineString = lineString.toLowerCase();
						if (lineString.indexOf("javascript:void") == -1) {
							String fulString = null;
							fulString = lineString.substring(
									lineString.indexOf("href") + 6,
									lineString.indexOf("\"",
											lineString.indexOf("href") + 6));
							if (fulString.indexOf(RMW) != -1) {
								addLinkFromString(curi, fulString, "",
										Link.NAVLINK_HOP);
							} else if (fulString.indexOf("http") == -1) {
								String newUrl = urlString.substring(0,
										urlString.indexOf(RMW) + 13)
										+ fulString;
								addLinkFromString(curi, newUrl, "",
										Link.NAVLINK_HOP);
							}
						}
					}
				}
				while (matcher2.find()) {
					String matcherString = matcher2.group();
					if (matcherString != null) {
						Pattern p = Pattern.compile("\\s*|\t|\r|\n");
						Matcher m = p.matcher(matcherString);
						String lineString = m.replaceAll("");
						errString = lineString;
						lineString = lineString.toLowerCase();
						if (lineString.indexOf("javascript:void") == -1) {
							String fulString = null;
							fulString = lineString.substring(
									lineString.indexOf("href") + 6,
									lineString.indexOf("\'",
											lineString.indexOf("href") + 6));
							if (fulString.indexOf(RMW) != -1) {
								addLinkFromString(curi, fulString, "",
										Link.NAVLINK_HOP);
							} else if (fulString.indexOf("http") == -1) {
								String newUrl = urlString.substring(0,
										urlString.indexOf(RMW) + 13)
										+ fulString;
								addLinkFromString(curi, newUrl, "",
										Link.NAVLINK_HOP);
							}
						}
					}
				}

				// BufferedReader bufferedReader = new BufferedReader(
				// new StringReader(content.replace(" ", "")));
				//
				// // 单行读取
				// String lineString = bufferedReader.readLine();
				//
				// while (lineString != null) {
				// lineString = lineString.replace(" ", "");
				// while (lineString.indexOf("href=") != -1) {
				// errString = lineString;
				// if (lineString.substring(
				// lineString.indexOf("href") + 5,
				// lineString.indexOf("href") + 6).equals("\"")) {
				// String fulString = null;
				// fulString = lineString.substring(
				// lineString.indexOf("href") + 6,
				// lineString.indexOf("\"",
				// lineString.indexOf("href") + 6));
				// if (fulString.indexOf(RMW) != -1) {
				// addLinkFromString(curi, fulString, "",
				// Link.NAVLINK_HOP);
				// } else if (fulString.indexOf("http") == -1) {
				// String newUrl = urlString.substring(0,
				// urlString.indexOf(RMW) + 13)
				// + fulString;
				// addLinkFromString(curi, newUrl, "",
				// Link.NAVLINK_HOP);
				// }
				// } else if (lineString.substring(
				// lineString.indexOf("href") + 5,
				// lineString.indexOf("href") + 6).equals("\'")) {
				// String fulString = null;
				// fulString = lineString.substring(
				// lineString.indexOf("href") + 6,
				// lineString.indexOf("\'",
				// lineString.indexOf("href") + 6));
				// if (fulString.indexOf(RMW) != -1) {
				// addLinkFromString(curi, fulString, "",
				// Link.NAVLINK_HOP);
				// } else if (fulString.indexOf("http") == -1) {
				// String newUrl = urlString.substring(0,
				// urlString.indexOf(RMW) + 13)
				// + fulString;
				// addLinkFromString(curi, newUrl, "",
				// Link.NAVLINK_HOP);
				// }
				// }
				// lineString = lineString.substring(lineString
				// .indexOf("href") + 6);
				// }
				// lineString = bufferedReader.readLine();
				// }

				// // 将字符串进行正则匹配
				// // 去除链接信息
				// Pattern pattern = Pattern.compile(PATTERN_A_HREF,
				// Pattern.CASE_INSENSITIVE);
				// Matcher matcher = pattern.matcher(content);
				// while (matcher.find()) {
				// String newUrl = matcher.group();
				// if (newUrl.indexOf(RMW) != -1) {
				// addLinkFromString(curi, newUrl, "", Link.NAVLINK_HOP);
				// }
				// }

			} catch (Exception e) {
				System.out.println(urlString + "---" + errString + "errir");
				// logger.info("出错啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦 "+ urlString );
				e.printStackTrace();
			} finally {

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