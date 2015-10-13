package mango.extractor.htmlparser;

import java.io.File;

import mango.util.Config;
/**
 * 提取网页内容main函数，必须在运行index之前运行这个。
 * @author songwen
 *
 */
public class HtmlparserMain {

	public static void main(String[] args) {
		String input = null;
		Extractor extractor = new RMW_Htmlparser();
		Config.configExtractor(extractor);
		input = extractor.getInputFilePathString();
		try {
			Extractor.tranverse(extractor, new File(input));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
