package com.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.model.User;

public interface UserMapper {

	int deleteByPrimaryKey(String userId);

	int insert(User user);

	int insertSelective(User user);

	User selectByPrimaryKey(String userId);

	int updateByPrimaryKeySelective(User user);

	int updateByPrimaryKey(User user);

	User loadUserByUsername(@Param(value = "username") String username);

	User loadUserByWxOpenid(@Param(value = "wxOpenid") String wxOpenid);

	int updateWxOpenidAndwxSessionKey(String userId, String userOpenid, String userSessionkey);

	List<User> selectAll(User user);

	//批量删除
	Integer batchRemove(@Param(value = "list") List<String> list);

}