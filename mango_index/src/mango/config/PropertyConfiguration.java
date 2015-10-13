package mango.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertyConfiguration {

	/* property file identifier */
	private static String CONFIG_FILE = "./app.properties";

	private static Properties prop;

	static {
		try {
			prop = new Properties();// 属性集合对象
			FileInputStream fis = new FileInputStream(CONFIG_FILE);// 属性文件输入流
			prop.load(fis);// 将属性文件流装载到Properties对象中
			fis.close();// 关闭
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String getValue(String key) {
		return prop.getProperty(key);
	}

	public static String getDBUrl() {
		String val = getValue("jdbc.url");
		return val;
	}

	public static String getDBUsr() {
		String val = getValue("jdbc.username");
		return val;
	}

	public static String getDBPwd() {
		String val = getValue("jdbc.password");
		return val;
	}

	public static String getNewsDir() {
		String val = getValue("news.index.inputdicpath");
		return val;
	}

	public static String getIndexStorePath(int i) {
		String val = getValue("news.index.outputdicpath");
		File file = new File(val + i);
		if  (!file .exists()  && !file .isDirectory())      
		{       
		    file .mkdir();    
		} else   
		{  
			
		}  
		return val + i + "/";
	}

	public static String getNewsImageDir() {
		String val = getValue("news.image.directory");
		return val;
	}

	public static String getWordDictionary() {
		String val = getValue("word.dictionary.file");
		return val;
	}

	public static String getHtmlInputpath() {
		String val = getValue("htmlparser.inputdicpath");
		return val;
	}

	public static String getHtmlOutputPath() {
		String val = getValue("htmlparser.outputdicpath");
		return val;
	}

	public static String getHtmlMirrirPath() {
		String val = getValue("htmlparser.mirrirdicpath");
		return val;
	}

	public static String getHtmlImagePath() {
		String val = getValue("htmlparser.imgdicpath");
		return val;
	}
}
