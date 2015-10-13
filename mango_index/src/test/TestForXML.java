package test;

import java.io.File;
import java.util.List;

import mango.extractor.htmlparser.Extractor;
import mango.extractor.htmlparser.RMW_Htmlparser;
import mango.util.Config;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class TestForXML {


	public static void main(String[] args) {
		String urlString = "./config.xml";
		SAXBuilder builder = new SAXBuilder();
		String input = null;
		try {
	        Document doc = builder.build(new File(urlString));
	        Element rootEl = doc.getRootElement();
	        //获得所有子元素
	        List<Element> list = rootEl.getChildren("htmlparser");
	        //List<Element> list = rootEl.getChildren("disk");
	        
	        for (Element el : list) {
	            String name = el.getAttributeValue("outputdicpath");
	            input = el.getAttributeValue("inputdicpath");
	            System.out.println(name);
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		Extractor extractor = new RMW_Htmlparser();
		Config.configExtractor(extractor);
		
		try {
			Extractor.tranverse(extractor, new File(input));
			System.out.println(Extractor.count);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
}
