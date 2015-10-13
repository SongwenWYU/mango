package mango.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import mango.config.PropertyConfiguration;
import mango.extractor.htmlparser.Extractor;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class Config {
	
	public static void configExtractor(Extractor extractor) {
	            extractor.setImageDirString(PropertyConfiguration.getHtmlImagePath());
	            extractor.setInputFilePathString(PropertyConfiguration.getHtmlInputpath());
	            extractor.setMirrorDirString(PropertyConfiguration.getHtmlMirrirPath());
	            extractor.setOutputPathString(PropertyConfiguration.getHtmlOutputPath());
	}
	public static void configIndex() {

	}
	
}
