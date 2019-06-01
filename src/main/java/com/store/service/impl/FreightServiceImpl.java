package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.FreightMapper;
import com.store.model.Freight;
import com.store.service.FreightService;

@Service
public class FreightServiceImpl implements FreightService {

	@Autowired
	private FreightMapper freightMapper;

	@Override
	public Freight selectByTypeAndCity(String type, String city) {
		return freightMapper.selectByTypeAndCity(type, city);
	}

	@Override
	public List<String> selectAll() {
		return freightMapper.selectAll();
	}

}