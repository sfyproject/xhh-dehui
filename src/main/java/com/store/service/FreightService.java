package com.store.service;

import java.util.List;

import com.store.model.Freight;

public interface FreightService {

	Freight selectByTypeAndCity(String type, String city);

	List<String> selectAll();

}