package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.ImageMapper;
import com.store.model.Image;
import com.store.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageMapper imageMapper;

	@Override
	public List<String> selectImageLink() {
		return imageMapper.selectImageLink();
	}

	@Override
	public int insertSelective(Image record) {
		// TODO Auto-generated method stub
		return imageMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String imageId) {
		// TODO Auto-generated method stub
		return imageMapper.deleteByPrimaryKey(imageId);
	}

	@Override
	public List<Image> selectAll() {
		// TODO Auto-generated method stub
		return imageMapper.selectAll();
	}

}