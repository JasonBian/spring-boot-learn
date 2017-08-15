package com.getui.bi.spring.boot.multipledb.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class TestDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	     * 不指定数据源使用默认数据源
	     * @return
	     */
	    public List<Demo> getList(){
	       String sql = "select *from Demo";
	        return (List<Demo>) jdbcTemplate.query(sql, new RowMapper<Demo>(){
	            @Override
	            public Demo mapRow(ResultSet rs, int rowNum) throws SQLException {
	                Demo demo = new Demo();
	                demo.setId(rs.getLong("id"));
	                demo.setName(rs.getString("name"));
	                return demo;
	            }
	        });
	    }

	/**
	     * 指定数据源
	     * 在对应的service进行指定;
	     * @return
	     */
	    public List<Demo> getListByDs1(){
	        /*
	         * 这张表示复制的主库到ds1的，在ds中并没有此表.
	         * 需要自己自己进行复制，不然会报错：Table 'test1.demo1' doesn't exist
	         */
	       String sql = "select * from Demo1";
	        return (List<Demo>) jdbcTemplate.query(sql, new RowMapper<Demo>(){
	 
	            @Override
	            public Demo mapRow(ResultSet rs, int rowNum) throws SQLException {
	                Demo demo = new Demo();
	                demo.setId(rs.getLong("id"));
	                demo.setName(rs.getString("name"));;
	                return demo;
	            }
	 
	        });
	    }
}
