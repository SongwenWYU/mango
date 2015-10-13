//package test;
//
//import java.io.File;
//import java.io.IOException;
//
//import mango.config.PropertyConfiguration;
//
//import org.apache.lucene.analysis.cjk.CJKAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.queryparser.classic.ParseException;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.FuzzyQuery;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TermQuery;
//import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//
//import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
//
//public class SearchIndex {
//
//	
//	public static void main(String[] args) throws IOException, ParseException {
//		// TODO Auto-generated method stub
//
//		String queryStr = "全国妇联维权工作";  
//        // This is the directory that hosts the Lucene index  
////        File indexDir = new File(PropertyConfiguration.getIndexStorePath());  
////        Directory directory = FSDirectory.open(indexDir);  
//        // FSDirectory fsDirectory = FSDirectory.open(indexDir);  
////        IndexReader indexReader = IndexReader.open(directory);  
//        // FSDirectory directory = FSDirectory.getDirectory(indexDir,false);  
//        // IndexReader indexReader = IndexReader.open(fsDirectory);  
////        IndexSearcher indexSearcher = new IndexSearcher(indexReader);  
//        // IndexSearcher searcher = new IndexSearcher(indexReader);  
////        if (!indexDir.exists()) {  
////            System.out.println("The Lucene index is not exist");  
////            return;  
////        }  
////        Term term = new Term("all", queryStr);  
////        TermQuery luceneQuery = new TermQuery(term);  
////        FuzzyQuery fuzzyQuery = new FuzzyQuery(term);
//        QueryParser queryParser = new QueryParser("all",new MMSegAnalyzer());
//        queryParser.setFuzzyMinSim(10f);
//        Query query = queryParser.parse(queryStr);
//        // Hits hits = searcher.search(luceneQuery);  
//        TopDocs topDocs = indexSearcher.search(query, 1000);  
//        ScoreDoc[] scoreDocs = topDocs.scoreDocs;  
//        if (scoreDocs == null || scoreDocs.length == 0) {  
//            System.out.println("The  Lucene index is not exist");  
//        }  
//        for (int i = 0; i < scoreDocs.length; i++) {  
//            Document document = indexSearcher.doc(scoreDocs[i].doc);  
//            System.out.println("File: " +document.get("title")+"**"+ document.get("newsurl"));  
//        }  
//        indexReader.close();  
//    }  
//}
