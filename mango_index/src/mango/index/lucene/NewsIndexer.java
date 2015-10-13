package mango.index.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import mango.config.PropertyConfiguration;
import mango.index.News;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

public class NewsIndexer {

	private String indexpath = "";
	private IndexWriter indexWriter = null;
	private Analyzer analyzer = null;
	// 词库文件
	private String dictionary_file = PropertyConfiguration.getWordDictionary();

	/**
	 * 构造函数，传入索引存放位置
	 * 
	 * @param indexpath
	 */
	public NewsIndexer(String indexpath) {

		this.indexpath = indexpath;
		initialize();
	}

	/**
	 * 初始化方法，实例化分词，可选择把词库加载到词库中
	 */
	private void initialize() {
		try {
			// analyzer = new MMSegAnalyzer(dictionary_file);
			analyzer = new MMSegAnalyzer();
//			analyzer = new CJKAnalyzer();
			File inputFile = new File(indexpath);
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
					Version.LUCENE_4_10_4, analyzer);
			indexWriterConfig.setRAMBufferSizeMB(128);
			indexWriter = new IndexWriter(FSDirectory.open(inputFile),
					indexWriterConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// close indexwriter
	public void close() {
		try {
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			indexWriter = null;
		}
	}

	/**
	 * 向索引中加入文档
	 * 
	 * @param news
	 * @param id
	 * 
	 */
	public void addNews(News news, int id)  {
		try {
			indexWriter.addDocument(NewsDocument.buildNewsDocument(news, id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * 优化索引
 * @throws IOException
 */
	public void optimizeIndex() {
		try {
			indexWriter.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
