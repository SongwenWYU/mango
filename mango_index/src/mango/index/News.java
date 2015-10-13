package mango.index;

public class News {

	//新闻标题
	private String title = null;
	//新闻内容
	private String content = null;
	//显示在搜索结果列表中的摘要
	private String summary = null;
	//对应URL
	private String  origunalUrl = null;
	//对应image
	private String imageUri = null;
	//最后更新时间
	private String updatedtime = null;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getOrigunalUrl() {
		return origunalUrl;
	}
	public void setOrigunalUrl(String origunalUri) {
		this.origunalUrl = origunalUri;
	}
	public String getImageUri() {
		return imageUri;
	}
	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}
	public String getUpdatedtime() {
		return updatedtime;
	}
	public void setUpdatedtime(String updatedtime) {
		this.updatedtime = updatedtime;
	}
}
