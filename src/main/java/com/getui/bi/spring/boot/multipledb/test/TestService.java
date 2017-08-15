package com.getui.bi.spring.boot.multipledb.test;

import org.springframework.stereotype.Service;

import com.getui.bi.spring.boot.multipledb.TargetDataSource;

import java.util.List;

import javax.annotation.Resource;

@Service
public class TestService {

	@Resource
	private TestDao testDao;

	/**
	 * 不指定数据源使用默认数据源
	 * 
	 * @return
	 */
	public List<Demo> getList() {
		return testDao.getList();
	}

	/**
	 * 指定数据源
	 * 
	 * @return
	 */
	@TargetDataSource("ds1")
	public List<Demo> getListByDs1() {
		return testDao.getListByDs1();
	}
}
