package mango.extractor.htmlparser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.logging.Logger;

import mango.util.StringUtils;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HtmlPage;

public class RMW_Htmlparser extends Extractor {

	private static Logger logger = Logger.getLogger(RMW_Htmlparser.class
			.getName());

	@Override
	public void extract() {
		NodeFilter imageFilter = new AndFilter(new TagNameFilter("img"),
				new HasParentFilter(new TagNameFilter("p")));
		// BufferedWriter bufferedWriter = null;
		FileOutputStream fos = null;
		OutputStreamWriter writer =null;
		String errString = null;
		try {
			String path = this.getOutputPathString(count/10000);
			String url = this.getInputFilePathString();
			GetContent gc = new GetContent();
			String jieguo = gc.getContentUsingStringBean(url);
			HtmlPage htmlpage = new HtmlPage(this.getParser());
			this.getParser().visitAllNodesWith(htmlpage);
			String title = htmlpage.getTitle();
			title = title.replaceAll("\\s*|\t|\r|\n|&nbsp", "");
			if (title.equals("")|| title.equals( "302Found")
					|| title.equals("Redirecting...")
					|| title.equals("${NEWTITLE}")
					||title.equals("404NotFound")) {
				return;
			}
//			byte bb[] = (title + "\n" + jieguo).getBytes();
			String bb = title + "\n" + jieguo;
			// 获取当前提取网页的完整URL地址
			int startPos = getInputFilePathString().indexOf("mirror") + 6;
			String url_segString = getInputFilePathString().substring(startPos);
			url_segString = url_segString.replace("\\\\", "/");
			String outputUrlString = "http:/" + url_segString;
			errString = outputUrlString;
			String newtitle = new String(( title.replace("/", "")+ "-").getBytes(), "UTF-8")  ;
			fos = new FileOutputStream(new File(path + newtitle
					+ new Date().getTime() + ".txt"));
			writer = new OutputStreamWriter(fos, "UTF-8");
			writer.append(outputUrlString + NEWLINE);
			writer.append(bb.toString());
			
//			fos.write((outputUrlString + NEWLINE).getBytes());
//			fos.write(bb);
		} catch (Exception e) {
			System.out.println(errString + "出错了");
			e.printStackTrace();
		}
		this.getParser().reset();
		// 获取图片
//		try {
//			boolean imgbool = true;
//			NodeList imageNodeList = null;
//			imageNodeList = this.getParser().parse(imageFilter);
//			for (int i = 0; i < imageNodeList.size(); i++) {
//				ImageTag nodeImageTag = (ImageTag) imageNodeList.elementAt(i);
//				String imageUrlString = nodeImageTag.getAttribute("src");
//				String fileTypeString = imageUrlString.substring(imageUrlString
//						.lastIndexOf(".") + 1);
//				String newNameString = StringUtils.encodePassword(
//						imageUrlString, HASH_ALGORITHM) + "." + fileTypeString;
//				imageUrlString = StringUtils.replace(imageUrlString, "+", " ");
//				imgbool = copyImage(imageUrlString, newNameString);
//				writer.append(imageUrlString + NEWLINE);
//				writer.append(SEPARATOR + NEWLINE);
////				fos.write((imageUrlString + NEWLINE).getBytes());
////				fos.write((SEPARATOR + NEWLINE).getBytes());
//				if (imgbool) {
////					fos.write((newNameString + NEWLINE).getBytes());
//					writer.append(newNameString + NEWLINE);
//				}else {
////					fos.write(("./noimage/unable.jpeg" + NEWLINE).getBytes());
//					writer.append("./noimage/unable.jpeg" + NEWLINE);
//				}
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("Failed  " + e.getMessage());
//		}
		try {
			if (writer != null) {
				writer.close();
			}
			if (fos != null) {
				fos.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}