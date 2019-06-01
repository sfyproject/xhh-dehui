package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.UserMapper;
import com.store.model.User;
import com.store.model.req.PageQuery;
import com.store.model.req.PageReq;
import com.store.service.UserService;
import com.store.utils.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User loadUserByUsername(String username) {
		if (Utils.isNullStr(username)) {
			return null;
		}
		return userMapper.loadUserByUsername(username);
	}

	@Override
	public User loadUserByWxOpenid(String wxOpenid) {
		if (Utils.isNullStr(wxOpenid)) {
			return null;
		}
		return userMapper.loadUserByWxOpenid(wxOpenid);
	}

	@Override
	public int updateWxOpenidAndwxSessionKey(String storeUserId, String wxOpenid, String wxSessionKey) {
		return userMapper.updateWxOpenidAndwxSessionKey(storeUserId, wxOpenid, wxSessionKey);
	}

	@Override
	public int insertSelective(User user) {
		return userMapper.insertSelective(user);
	}

	@Override
	public User selectByPrimaryKey(String userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public int updateByPrimaryKeySelective(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public PageInfo<User> page(PageReq pageReq, User user) {
		PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
		return new PageInfo<>(userMapper.selectAll(user), 10);
	}

	@Override
	public int deleteByPrimaryKey(String storeUserId) {
		return userMapper.deleteByPrimaryKey(storeUserId);
	}

	//后台查询展示
	public PageInfo<User> page(PageQuery query, User user) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		return new PageInfo<>(userMapper.selectAll(user), 10);
	}

	@Override
	public int batchRemove(List<String> list) {
		// TODO Auto-generated method stub
		return userMapper.batchRemove(list);
	}

	@Override
	public List<User> selectAll(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectAll(user);
	}

}