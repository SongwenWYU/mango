//package test;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.Reader;
//import java.util.Date;
//
//import mango.config.PropertyConfiguration;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field.Store;
//import org.apache.lucene.document.TextField;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.util.Version;
//
//import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
//
//public class TestIndex {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//		try {
//			myIndex();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private static void myIndex() throws IOException {
//		String input = PropertyConfiguration.getNewsDir();
////		String output = PropertyConfiguration.getIndexStorePath();
//
////		File indexDIrFile = new File(output);
//		File dataDirFIle = new File(input);
//
//		Analyzer luceneAnalyzer = new MMSegAnalyzer();
//		File[] dataFiles = dataDirFIle.listFiles();
//		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
//				Version.LUCENE_4_10_4, luceneAnalyzer);
//		// 创建索引
//		IndexWriter indexWriter = new IndexWriter(
//				FSDirectory.open(indexDIrFile), indexWriterConfig);
//
//		long startTime = new Date().getTime();
//		for (int i = 0; i < dataFiles.length; i++) {
//			if (dataFiles[i].isFile()
//					&& dataFiles[i].getName().endsWith(".txt")) {
//				System.out.println("Indexing file "
//						+ dataFiles[i].getCanonicalPath());
//				// 封装document对象
//				Document document = new Document();
//				Reader txtReader = new FileReader(dataFiles[i]);
//				document.add(new TextField("path", dataFiles[i]
//						.getCanonicalPath(), Store.YES));
//				// document.add(Field.Text("contents", txtReader));
//				document.add(new TextField("contents", txtReader));
//				indexWriter.addDocument(document);
//			}
//		}
//		indexWriter.commit();
//		// indexWriter.optimize();
//		indexWriter.close();
//		long endTime = new Date().getTime();
//		System.out.println("It takes " + (endTime - startTime)
//				+ " milliseconds to create index for the files in directory "
//				+ dataDirFIle.getPath());
//	}
//
//}
