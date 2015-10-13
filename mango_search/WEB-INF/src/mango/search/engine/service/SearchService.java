package mango.search.engine.service;

import mango.search.engine.bo.SearchRequest;
import mango.search.engine.bo.SearchResult;
import mango.search.engine.bo.SearchResults;

/**
 * 索引检索类接口
 * @author songwen
 *
 */
public interface SearchService {

	//返回
	public abstract SearchResults getSearchResults(SearchRequest request) ;
	//返回单个
	public abstract SearchResult getSearchResultById(int id);
}
