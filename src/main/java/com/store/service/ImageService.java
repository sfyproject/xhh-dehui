package com.store.service;

import java.util.List;

import com.store.model.Image;

public interface ImageService {

	List<String> selectImageLink();
	
	int insertSelective(Image record);
	
	int deleteByPrimaryKey(String imageId);
	
	List<Image> selectAll();

}