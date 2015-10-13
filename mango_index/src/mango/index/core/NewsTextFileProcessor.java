package mango.index.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.apache.lucene.index.IndexWriter;

import mango.config.PropertyConfiguration;
import mango.database.NewsJDBC;
import mango.extractor.htmlparser.Extractor;
import mango.index.News;
import mango.index.lucene.NewsIndexer;

/**
 * 数据库和索引集中处理类 运行main
 * 
 * @author songwen
 *
 */
public class NewsTextFileProcessor {
	// txt文件目录
	private File[] directories;

	private static final String DBURL = PropertyConfiguration.getDBUrl();
	private static final String DBUSR = PropertyConfiguration.getDBUsr();
	private static final String DBPWD = PropertyConfiguration.getDBPwd();
	private NewsJDBC newsJDBC = null;

	private NewsIndexer newsIndexer = null;

	private final static int SUMMARY_LENGTH = 80;

	private String INDEXPATH = PropertyConfiguration
			.getIndexStorePath(0);

	public NewsTextFileProcessor(String path) {
		setINDEXPATH(path);
		initialize();
	}

	/**
	 * 初始化方法
	 */
	private void initialize() {
		try {
			newsJDBC = new NewsJDBC(DBURL, DBUSR, DBPWD);
			newsIndexer = new NewsIndexer(INDEXPATH);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理文档目录
	 * 
	 * @param directories
	 */
	public void setDirectories(File[] directories) {
		this.directories = directories;
	}

	/**
	 * 处理逻辑
	 * 
	 * @throws Exception
	 */
	protected void process() throws Exception {
		if (newsJDBC == null) {
			throw new Exception("Database connection failed, please retry!!!");
		}
		if (newsIndexer == null) {
			throw new Exception("Lucene index failed, please retry!!!");
		}
		if (directories == null || directories.length == 0) {
			return;
		}
		try {
			// dirTraverse(directories);

			traverse(directories);

			closeIndex();
			closeDB();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void closeIndex() {
		newsIndexer.close();
	}

	private void closeDB() {
		// TODO Auto-generated method stub
		newsJDBC.close();
	}

	/**
	 * 处理每一个目录
	 * 
	 * @param file
	 * @throws Exception
	 */
	private void traverse(File[] file) throws Exception {
		for (int j = 0; j < file.length; j++) {
			if (file[j].isFile() && file[j].getName().endsWith(".txt")) {
				// 取得所需要的各类信息
				String fname = new String((file[j].getName()).getBytes(),"UTF-8");
				System.out.println(j + ":"+fname);
				InputStreamReader reader = null;
				reader = new InputStreamReader(new FileInputStream(file[j]),
						"UTF-8");
				BufferedReader bufferedReader = new BufferedReader(reader);
				String url = bufferedReader.readLine();
				String title = bufferedReader.readLine();
				String imageUrl = "";
				String updatedtime = fname.substring(
						fname.lastIndexOf("-") + 1, fname.lastIndexOf("."));
				StringBuffer content = new StringBuffer();
				String line = bufferedReader.readLine();
				while (line != null && !line.equals(Extractor.SEPARATOR)) {
					content.append(line).append("\r\n");
					line = bufferedReader.readLine();
				}
//				imageUrl = bufferedReader.readLine();

				// 生成news对象
				News n = new News();
				n.setTitle(title);
//				n.setImageUri(imageUrl);
				n.setOrigunalUrl(url);

				// 制作摘要
				String contentstr = content.toString();
				n.setContent(contentstr);
				if (contentstr.length() > SUMMARY_LENGTH) {
					n.setSummary(contentstr.substring(0, SUMMARY_LENGTH));
				} else {
					n.setSummary(contentstr);
				}
				n.setUpdatedtime(updatedtime);

				// 存入数据库，获得返回id
				int nextid = insert2DB(n);
				buildindex(n, nextid);
				bufferedReader.close();
				reader.close();
			}
		}
		optimizeIndex();
	}

	/**
	 * 优化索引
	 * 
	 * @throws Exception
	 */
	private void optimizeIndex() throws Exception {
		newsIndexer.optimizeIndex();
	}

	/**
	 * 创建索引
	 * 
	 * @param n
	 * @param nextid
	 * @throws Exception
	 */
	private void buildindex(News n, int nextid) throws Exception {
		newsIndexer.addNews(n, nextid);
	}

	/**
	 * 插入数据库
	 * 
	 * @param n
	 * @return
	 * @throws Exception
	 */
	private int insert2DB(News n) throws Exception {
		return newsJDBC.addNews(n);
	}

	public static String getDburl() {
		return DBURL;
	}

	public static String getDbusr() {
		return DBUSR;
	}

	public static String getDbpwd() {
		return DBPWD;
	}

	public  String getIndexpath() {
		return INDEXPATH;
	}

	public void setINDEXPATH(String iNDEXPATH) {
		INDEXPATH = iNDEXPATH;
	}

//	public static void main(String[] args) throws Exception {
//		NewsTextFileProcessor newsTextFileProcessor = new NewsTextFileProcessor();
//		String pathString = PropertyConfiguration.getNewsDir();
//		File newsDir = new File(pathString);
//		System.out.println(pathString);
//		newsTextFileProcessor.setDirectories(newsDir.listFiles());
//		// newsTextFileProcessor.initialize();
//		newsTextFileProcessor.process();
//	}

}
