package com.store.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.store.model.User;
import com.store.model.req.PageQuery;
import com.store.model.req.PageReq;

/**
 * 用户接口
 */
public interface UserService {

	User loadUserByUsername(String username);

	User loadUserByWxOpenid(String wxOpenid);

	int updateWxOpenidAndwxSessionKey(String storeUserId, String wxOpenid, String wxSessionKey);

	int insertSelective(User user);

	User selectByPrimaryKey(String userId);

	int updateByPrimaryKeySelective(User user);

	PageInfo<User> page(PageReq pageReq, User user);

	int deleteByPrimaryKey(String storeUserId);

	PageInfo<User> page(PageQuery query, User user);

	int batchRemove(List<String> list);
	
	List<User> selectAll(User user);

}