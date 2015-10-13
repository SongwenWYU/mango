package mango.search.engine.bo;

/**
 * 
 * @author songwen
 *
 */
public class SearchRequest {

	// 新页面的起始索引号
	private int startindex;
	// 查询关键字;
	private String query;
	public int getStartindex() {
		return startindex;
	}
	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}

}
