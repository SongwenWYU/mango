package mango.extractor.htmlparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.NodeList;
/**
 * 网页处理基类，httpparser提取网页内容
 * @author songwen
 *
 */
public abstract class Extractor {

	protected static final String NEWLINE = "\r\n";
	// 正在处理文件
	private String inputFilePathString;
	// 输出路径
	private String outputPathString = "";
	// 镜像根目录
	private String mirrorDirString = "";
	// 存放图片路径
	private String imageDirString = "";
	private Parser parser;
	// 图片采用MD5哈希算法
	protected static final String HASH_ALGORITHM = "md5";
	public static final String SEPARATOR = "========================";

	/**
	 * 装载网页文件
	 * 
	 * @param path
	 */
	public void loadFile(String path) {
		try {
			
			parser = new Parser(path);
			parser.setEncoding(parseByContent(path));
			inputFilePathString = path;
			
//			Lexer mLexer = new Lexer(new Page(path));
////			parser = new Parser(path);
//			parser = new Parser(mLexer,new DefaultParserFeedback(DefaultParserFeedback.QUIET));
//			parser.setEncoding("utf-8");
//			inputFilePathString = path;
//			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getInputFilePathString() {
		return inputFilePathString;
	}

	public void setInputFilePathString(String inputFilePathString) {
		this.inputFilePathString = inputFilePathString;
	}

	public String getOutputPathString(int i) {
		File file =new File(outputPathString+i);    
		//如果文件夹不存在则创建    
		if  (!file .exists()  && !file .isDirectory())      
		{       
		    file .mkdir();    
		} else   
		{  
			
		}  
		return outputPathString+i+"/";
	}

	public void setOutputPathString(String outputPathString) {
		this.outputPathString = outputPathString;
	}

	public String getMirrorDirString() {
		return mirrorDirString;
	}

	public void setMirrorDirString(String mirrorDirString) {
		this.mirrorDirString = mirrorDirString;
	}

	public String getImageDirString() {
		return imageDirString;
	}

	public void setImageDirString(String imageDirString) {
		this.imageDirString = imageDirString;
	}

	public Parser getParser() {
		return parser;
	}

	/**
	 * 使用正则匹配并获得网页在的字符串
	 * 
	 * @param pattern
	 * @param match
	 * @param index
	 * @return
	 */
	protected String getProp(String pattern, String match, int index) {
		Pattern sp = Pattern.compile(pattern);
		Matcher matcher = sp.matcher(match);
		while (matcher.find()) {
			return matcher.group(index);
		}
		return null;
	}

	/**
	 * 抽象方法
	 */
	public abstract void extract();

	/**
	 * copy图片到指定目录
	 * 
	 * @param image_url
	 * @param new_image_file
	 * @return
	 */
	protected boolean copyImage(String image_url, String new_image_file) {
		String dirs = image_url.substring(7);
		try {
			File inFile = new File(new File(mirrorDirString), dirs);
			if (inFile == null || !inFile.exists()) {
				// 如果图片不存在
//				inFile = new File("./noimage/unable.jpeg");
				return false;
			}else {
				File outFile = new File(new File(imageDirString), new_image_file);
				FileInputStream inputStream = new FileInputStream(inFile);
				FileOutputStream outputStream = new FileOutputStream(outFile);
				byte[] bytes = new byte[1024];
				int c;
				while ((c = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, c);
				}
				inputStream.close();
				outputStream.close();
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 计数器，显示当前处理了多少网页
	public static int count = 1;

	/**
	 * 遍历目录下的所有网页
	 * 
	 * @param extractor
	 * @param path
	 */
	public static void tranverse(Extractor extractor, File path)throws Exception {
		if (path == null) {
			return;
		}
		if (path.isDirectory()) {
			String[] fileStrings = path.list();
			for (int i = 0; i < fileStrings.length; i++) {
				tranverse(extractor, new File(path, fileStrings[i]));
			}
		} else {
			if (path.getAbsolutePath().endsWith(".html")
					|| path.getAbsolutePath().endsWith(".htmlN")
					|| path.getAbsolutePath().endsWith(".htm")) {
				System.out.println(path);
				
				count++;
				extractor.loadFile(path.getAbsolutePath());
				extractor.extract();
			}
		}
	}
	
	/**
	 * 根据HTML<meta></meta>标签,对http-equive值为Content-Type的行解析网页编码
	 * 
	 * @param urlStr
	 *            网页的URL
	 * @return 网页使用的字符编码 如utf-8 gbk等
	 */
	private static String parseByContent(String path) {
		String temp;
		String charset = null;
		String regex_charset = "<meta http-equiv=\"content-type\" content=\"text/html;[ ]{0,1}charset=(.*?)\"[ ]{0,1}[/]{0,1}>";
		String regex_charset_spc = "<meta content=\"text/html;[ ]{0,1}charset=(.*)\" http-equiv=\"content-type\"[ ]{0,1}[/]{0,1}>";
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					new File(path)));
			
			while ((temp = br.readLine()) != null) {
				temp = temp.toLowerCase();
				Pattern p_charset = Pattern.compile(regex_charset);
				Matcher m_charset = p_charset.matcher(temp);
				if (m_charset.find()) {
					System.out.println("content-type" + temp);
					charset = m_charset.group(1);
					break;
				}

				Pattern p_charset_spc = Pattern.compile(regex_charset_spc);
				Matcher m_charset_spc = p_charset_spc.matcher(temp);
				if (m_charset_spc.find()) {
					System.out.println(2);
					charset = m_charset_spc.group(1);
					break;
				}
				if (charset == null) {
					charset = "GB2312";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return charset;
	}
		

}