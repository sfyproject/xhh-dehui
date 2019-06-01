package com.store.service;

import com.github.pagehelper.PageInfo;
import com.store.model.Express;
import com.store.model.req.PageReq;

public interface ExpressService {

	int insert(Express express);

	Express selectByPrimaryKey(String expressId);

	int deleteByPrimaryKey(String expressId);

	PageInfo<Express> page(PageReq pageReq, Express express);

	int updateByPrimaryKeySelective(Express express);

}