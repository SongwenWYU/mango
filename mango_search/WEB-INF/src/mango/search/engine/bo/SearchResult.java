package mango.search.engine.bo;

/**
 * SearchResult 一个查询结果的Bean
 * @author songwen
 *
 */
public class SearchResult {

	private int id;
	//摘要
	private String abstractConcent;
	//内容
	private String content;
	private String imageUrl;
	//原始url
	private String url;
	private String title;
	//index创建时间
	private String indexCreateTIme;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAbstractConcent() {
		return abstractConcent;
	}
	public void setAbstractConcent(String abstractConcent) {
		this.abstractConcent = abstractConcent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIndexCreateTIme() {
		return indexCreateTIme;
	}
	public void setIndexCreateTIme(String indexCreateTIme) {
		this.indexCreateTIme = indexCreateTIme;
	}
}
