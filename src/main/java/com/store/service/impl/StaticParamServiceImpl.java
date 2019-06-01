package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.StaticParamMapper;
import com.store.model.StaticParam;
import com.store.service.StaticParamService;

@Service
public class StaticParamServiceImpl implements StaticParamService {

	@Autowired StaticParamMapper staticParamMapper;
	
	@Override
	public List<StaticParam> getListByCondition(String staticParamName) {
		
		return staticParamMapper.getListByCondition(staticParamName);
	}
	
}
