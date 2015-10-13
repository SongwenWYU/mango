package mango.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mango.index.News;

public class NewsJDBC {

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private PreparedStatement pstmt = null;
	private boolean autoCommit = true;

	public NewsJDBC(String url, String usr, String pwd) {		
		try {
			// 标准jdbc连接方式
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, usr, pwd);
			// 设置自动提交为true
			con.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int addNews(News news){
		int nextId;
		nextId = getNextId();
		if (nextId < 0) {
			try {
				throw new Exception("Can't get next id!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String content = news.getContent();
		String summary = news.getSummary();
		String title = news.getTitle();
		String originalUrl = news.getOrigunalUrl();
		String imageURI = news.getImageUri();
		String updatedtime = news.getUpdatedtime();
		String expr = "insert into news (title,content, abstractcontent, url,  imageurl, updatedtime) values (?,?,?,?,?,?)";
		try {
			pstmt = con.prepareStatement(expr);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, summary);
			pstmt.setString(4, originalUrl);
			pstmt.setString(5, imageURI);
			pstmt.setString(6, updatedtime);
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nextId;

	}

	/**
	 * 返回当前id
	 * 
	 * @return
	 * @throws SQLException
	 */
	private int getNextId() {
		int result = -1;
		String sql = "select max(id)+1 from news";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void close() {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				con = null;
			}
		}
	}
}
