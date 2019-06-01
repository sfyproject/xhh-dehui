package com.store.dao;

import java.util.List;

import com.store.model.StaticParam;

public interface StaticParamMapper {

	List<StaticParam> getListByCondition(String staticParamName);

}