package com.store.dao;

import java.util.List;

import com.store.model.Express;

public interface ExpressMapper {

	int insert(Express record);

	int insertSelective(Express record);

	List<Express> selectAll(Express express);

	Express selectByPrimaryKey(String expressId);

	int deleteByPrimaryKey(String expressId);

	int updateByPrimaryKeySelective(Express express);

}