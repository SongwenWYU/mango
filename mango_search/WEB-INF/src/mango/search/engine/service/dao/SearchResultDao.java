package mango.search.engine.service.dao;

import mango.search.engine.bo.SearchResult;

/**
 * 数据库访问Dao接口
 * @author songwen
 *
 */
public interface SearchResultDao {
	public abstract SearchResult getSearchResultById(int id);
}
