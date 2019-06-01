package com.store.service;

import java.util.List;

import com.store.model.StaticParam;

public interface StaticParamService  {
	
	List<StaticParam> getListByCondition(String paramName);
}
