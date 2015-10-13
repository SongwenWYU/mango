package test;

import mango.extractor.htmlparser.Extractor;
import mango.extractor.htmlparser.RMW_Htmlparser;
import mango.util.Config;

public class TextConfig {

	public static void main(String[] args) {
		Extractor extractor = new RMW_Htmlparser();
		Config.configExtractor(extractor);
		System.out.println(extractor.getImageDirString());
		System.out.println(extractor.getInputFilePathString());
		System.out.println(extractor.getMirrorDirString());
//		System.out.println(extractor.getOutputPathString());

	}

}
