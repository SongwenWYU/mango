package mango.extractor.htmlparser;



import org.htmlparser.beans.StringBean;


public class GetContent {
	public String getContentUsingStringBean(String url) {
		StringBean sb = new StringBean();
		sb.setLinks(false);
		sb.setCollapse(true); 
		sb.setReplaceNonBreakingSpaces(true);
		sb.setURL(url);
		
		return sb.getStrings();
	}

}