package mango.search.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.collections4.map.StaticBucketMap;

public class PropertyConfig {

	private static String CONFIG_FILE = "app";
	private static ResourceBundle bundle;
	static{
		try {
			bundle = ResourceBundle.getBundle(CONFIG_FILE);
		} catch (MissingResourceException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static String getValue(String key){
		return bundle.getString(key);
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
	
	public static String getIndexStorePath() {
		String val = getValue("news.index.outputdicpath");
		return val;
	}
	
	public static String getImagePath() {
		String val = getValue("news.image.directory");
		return val;
	}
	
}
