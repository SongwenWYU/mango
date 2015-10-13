package mango.search.engine.bo;

import java.util.ArrayList;

/**
 * SearchResults 所有查询结果的Bean
 * @author songwen
 *
 */
public class SearchResults {

	//id集合
	private ArrayList results = new ArrayList();
	//起始索引
	private int startIndex;
	//所要显示的最小页
	private int minpage;
	//所要显示大最大也
	private int maxpage;
	//是否含有比maxpage更大的页数
	private int hasnext;
	public ArrayList getResults() {
		return results;
	}
	public void setResults(ArrayList results) {
		this.results = results;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getMinpage() {
		return minpage;
	}
	public void setMinpage(int minpage) {
		this.minpage = minpage;
	}
	public int getMaxpage() {
		return maxpage;
	}
	public void setMaxpage(int maxpage) {
		this.maxpage = maxpage;
	}
	public int getHasnext() {
		return hasnext;
	}
	public void setHasnext(int hasnext) {
		this.hasnext = hasnext;
	}
	
}
