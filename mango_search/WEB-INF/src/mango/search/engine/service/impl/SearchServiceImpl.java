package mango.search.engine.service.impl;

import java.io.File;
import java.util.ArrayList;

import mango.search.config.PropertyConfig;
import mango.search.engine.bo.SearchRequest;
import mango.search.engine.bo.SearchResult;
import mango.search.engine.bo.SearchResults;
import mango.search.engine.service.SearchService;
import mango.search.engine.service.dao.SearchResultDao;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

/**
 * 索引实现类，查询翻页
 * 
 * @author songwen
 *
 */
public class SearchServiceImpl implements SearchService{

	private static final String NEWS_ID = "newsid";
	private static final String INDEX_TIME = "indextime";
	private static final String NEWS_URL = "newsurl";
	private static final String TITLE = "title";
	private static final String SUMMARY = "summary";

	private static final String NEWS_CONTENT = "content";
	
	private static final String INDEX_STORE_PATH = PropertyConfig
			.getIndexStorePath();

	// DAO 对象
	private SearchResultDao searchResultDao;

	public SearchResults getSearchResults(SearchRequest request) {

		SearchResults results = new SearchResults();

		ArrayList list = new ArrayList();

		try {
			File indexDir = new File(INDEX_STORE_PATH);
			Directory directory = FSDirectory.open(indexDir);
			IndexReader indexReader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(indexReader);

			// 从SearchResquest中取出检索关键字，通过makeQuery生成lucene的query实例
			Query query = makeQuery(request.getQuery());
//			Sort sort = new Sort(new SortField("indextime", Type.STRING,true));
			TopDocs topDocs = searcher.search(query, 1000);
			
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			// 取得长度
			int length = scoreDocs.length;
			// 取得记录起始位置
			int startIndex = request.getStartindex();
			// 当前页最后一条记录
			int endIndex;
			if (startIndex > length) {
				//should be impossible
			} else {
				// 每页显示10条记录
				endIndex = startIndex + 9;

				if (endIndex >= length) {
					endIndex = length;
				}
				if (scoreDocs == null || scoreDocs.length == 0) {
					System.out.println("The  Lucene index is not exist");
				}
				for (int i = startIndex; i < endIndex; i++) {
					Document document = searcher.doc(scoreDocs[i - 1].doc);
					String id = document.get("newsid");
					list.add(id);
					// System.out.println("File: " + document.get("path"));
				}
			}
			// 加入到SearchResults中
			results.setResults(list);

			int startpage;
			int endpage;
			if (startIndex % 100 == 0) {
				startpage = (startIndex / 100 - 1) * 10 + 1;
			}else {
				startpage = (startIndex/100)*10+1;
			}
			int span;
			int hasnext;
			float temp = ((float) (length - (startpage - 1) * 10)) / 10;
			if (temp > 10) {
				span = 9;
				hasnext = 1;
			} else if (temp == 10) {
				span = 9;
				hasnext = 0;
			} else {
				hasnext = 0;
				if ((int) temp < temp) {
					span = (int) temp;
				} else {
					span = (int) temp - 1;
				}
			}

			// 计算终止页
			endpage = startpage + span;

			results.setMinpage(startpage);
			results.setMaxpage(endpage);
			results.setHasnext(hasnext);
			results.setStartIndex(startIndex);
			indexReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * 可修改成QueryParset来进行用户关键字查询
	 * @param query
	 * @return
	 * @throws ParseException 
	 */
	private Query makeQuery(String query) throws ParseException {
		QueryParser queryParser = new QueryParser("all",new MMSegAnalyzer());
		return queryParser.parse(query);
//		TermQuery qu = new TermQuery(new Term("content", query.toLowerCase()));
//		return qu;
	}

	@Override
	public SearchResult getSearchResultById(int id) {
		return searchResultDao.getSearchResultById(id);
	}
	
	/**
	 * 
	 * @return
	 */
	public SearchResultDao getSearchResultDao() {
		return searchResultDao;
	}

	/**
	 * 
	 * @param searchResultDao
	 */
	public void setSearchResultDao(SearchResultDao searchResultDao) {
		this.searchResultDao = searchResultDao;
	}
}
