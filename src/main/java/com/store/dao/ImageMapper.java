package com.store.dao;

import java.util.List;

import com.store.model.Image;

public interface ImageMapper {

	int deleteByPrimaryKey(String imageId);

	int insert(Image record);

	int insertSelective(Image record);

	Image selectByPrimaryKey(String imageId);

	int updateByPrimaryKeySelective(Image record);

	int updateByPrimaryKey(Image record);

	List<String> selectImageLink();
	
	List<Image> selectAll();

}