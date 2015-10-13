package mango.search.engine.service.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;

import mango.search.engine.bo.SearchResult;
import mango.search.engine.service.dao.SearchResultDao;

/**
 * SearchResultDao实现类，数据库访问
 * 
 * @author songwen
 *
 */
public class SearchResultDaoImpl implements SearchResultDao {

	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public SearchResult getSearchResultById(int id) {
		final int id_db = id;
		final SearchResult sr = new SearchResult();
		JdbcTemplate template = new JdbcTemplate(dataSource);

		template.query("select * from news where id=?",
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, id_db);

					}
				}, new RowCallbackHandler() {

					public void processRow(ResultSet rs) throws SQLException {
						try {
							sr.setAbstractConcent(rs.getString("abstractcontent"));
							sr.setContent(rs.getString("content"));
							sr.setImageUrl(rs.getString("imageurl"));
							sr.setUrl(rs.getString("url"));
							sr.setTitle(rs.getString("title"));		
							sr.setId(rs.getInt("id"));

							SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							String time = rs.getString("updatedtime");

							if (time != null && !time.trim().equals("")) {
								Date d = new Date(Long.parseLong(time));
								String timestr = sf.format(d);
								sr.setIndexCreateTIme(timestr);
							} else {
								sr.setIndexCreateTIme("undefined time");
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

		return sr;
	}

}
