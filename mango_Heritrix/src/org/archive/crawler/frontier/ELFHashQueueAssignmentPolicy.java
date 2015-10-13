package org.archive.crawler.frontier;

import java.util.logging.Logger;

import org.archive.crawler.datamodel.CandidateURI;
import org.archive.crawler.framework.CrawlController;

/**
 * 定制Queue-assignment-policy继承QueueAssignmentPolicy 使用ELFHsah算法，改变uri 的Key值生成方式
 * 
 * @author songwen
 *
 */
public class ELFHashQueueAssignmentPolicy extends QueueAssignmentPolicy {

	private static final Logger logger = Logger
			.getLogger(ELFHashQueueAssignmentPolicy.class.getName());

	@Override
	public String getClassKey(CrawlController controller, CandidateURI cauri) {
		String uri = cauri.getUURI().toString();
		long hash = ELFHash(uri);
		String a = Long.toString(hash % 100);
		return a;
	}

	/**
	 * ELFHash算法
	 * 
	 * @param uri
	 * @return
	 */
	private long ELFHash(String uri) {

		long hash = 0;
		long x = 0;
		for (int i = 0; i < uri.length(); i++) {
			hash = (hash << 4) + uri.charAt(i);
			if ((x = hash & 0xF0000000L) != 0) {
				hash ^= (x >> 24);
				hash &= ~x;
			}
		}
		return (hash & 0x7FFFFFFF);
	}

}
