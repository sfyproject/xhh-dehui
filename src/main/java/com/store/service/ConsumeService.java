package com.store.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.store.model.Consume;
import com.store.model.req.PageQuery;

public interface ConsumeService {

	List<Consume> selectByUid(String uid);

	int insertSelective(Consume consume);

	PageInfo<Consume> page(PageQuery pageQuery,Consume consume);

}