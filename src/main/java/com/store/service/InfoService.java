package com.store.service;

import com.github.pagehelper.PageInfo;
import com.store.model.Info;
import com.store.model.req.PageReq;

public interface InfoService {

	PageInfo<Info> page(PageReq pageReq);

	int insert(Info info);

	int updateByPrimaryKeySelective(Info info);

	int deleteByPrimaryKey(String storeInfoId);

	String selectCity(String storeInfoId);

}