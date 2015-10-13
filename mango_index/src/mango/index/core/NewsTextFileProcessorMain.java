package mango.index.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

import mango.config.PropertyConfiguration;
import mango.database.NewsJDBC;
import mango.extractor.htmlparser.Extractor;
import mango.index.News;
import mango.index.lucene.NewsIndexer;

/**
 * 运行main
 * 
 * @author songwen
 *
 */
public class NewsTextFileProcessorMain {

	/**
	 * 合并索引
	 * @param from
	 * @param to
	 * @param sa
	 */
	 private static void mergeIndex(String  from, String to) {  
		 IndexWriter indexWriter = null;
		 File fromFile = new File(from);
		 File toFile = new File(to);
//		 IndexWriter indexWriterTo = null;
	        try {  
	        	IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_4, new MMSegAnalyzer());
	        	
	            System.out.println("正在合并索引文件!\t ");  
	            FSDirectory ts = FSDirectory.open(toFile);
	            indexWriter = new IndexWriter(ts, indexWriterConfig);
	           for (int i = 0; i < fromFile.list().length; i++) {
//	        	   FSDirectory fs =FSDirectory.open(from);
	        	  
	        	   System.out.println( (fromFile.listFiles())[i].getAbsoluteFile());
	        	   indexWriter.addIndexes(FSDirectory.open((fromFile.listFiles())[i]));  
			}
	           
	            
	           
	            indexWriter.commit();
	            indexWriter.close();  
	            System.out.println("已完成合并!\t ");  
	        } catch (Exception e) {  
	            System.out.println("合并索引出错！");  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                if (indexWriter != null)  
	                    indexWriter.close();  
	            } catch (Exception e) {  
	            	e.printStackTrace();
	            }  
	  
	        }  
	  
	    }   

	public static void main(String[] args) throws Exception {
		String pathString = PropertyConfiguration.getNewsDir();
		File newsDir = new File(pathString);
		if (newsDir.list().length <= 0) {
			System.out.println("not hava file !!!");
			return;
		}
		
		for (int i = 0; i < newsDir.list().length; i++) {
			NewsTextFileProcessor newsTextFileProcessor  = new NewsTextFileProcessor(PropertyConfiguration.getIndexStorePath(i));
			newsTextFileProcessor.setDirectories((newsDir.listFiles())[i].listFiles());
			newsTextFileProcessor.process();
		}
		
		
		mergeIndex("./indexAll/", "./index/");
	}

}
