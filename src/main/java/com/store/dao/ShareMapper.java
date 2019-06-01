package com.store.dao;

import java.util.List;

import com.store.model.Share;

public interface ShareMapper {

	int deleteByPrimaryKey(String shareId);

	int insert(Share record);

	int insertSelective(Share record);

	Share selectByPrimaryKey(String shareId);

	int updateByPrimaryKeySelective(Share record);

	int updateByPrimaryKey(Share record);

	int deleteAll();

	List<Share> selectByUid(String shareUid);

}