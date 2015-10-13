package mango.index.lucene;

import mango.index.News;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

/**
 * lucene索引结构
 * 
 * @author songwen
 *
 */
public class NewsDocument {

	// 建立field
	// id
	private static final String NEWS_ID = "newsid";
	// 建立索引时间
	private static final String INDEX_TIME = "indextime";
	// 原始URL
	private static final String NEWS_URL = "newsurl";

	private static final String NEWS_TITLE = "title";
	private static final String NEWS_CONTENT = "content";

	public static Document buildNewsDocument(News news, int id) {
		Document doc = new Document();
		
		/* 创建Field */
		Field identifier = new StringField(NEWS_ID, id + "", Store.YES);
		String mills = Long.toString(System.currentTimeMillis());
		Field indextime = new StringField(INDEX_TIME, mills + "", Store.YES);
		Field producturl = new StringField(NEWS_URL, news.getOrigunalUrl(),
				Store.YES);
		Field title = new TextField(NEWS_TITLE, news.getTitle(), Store.YES);
		Field content = new TextField(NEWS_CONTENT, news.getContent(),Store.YES);
		Field all = new TextField("all", news.getTitle()+ " " + news.getContent(),Store.YES);
		
		// add all field
		doc.add(identifier);
		doc.add(title);
		doc.add(content);
		doc.add(all);
		doc.add(producturl);
		doc.add(indextime);
		return doc;
	}

}
