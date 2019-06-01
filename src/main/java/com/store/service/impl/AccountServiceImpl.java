package com.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.UserMapper;
import com.store.model.User;
import com.store.model.req.PageQuery;
import com.store.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public PageInfo<User> page(PageQuery pageQuery, User user) {
		PageHelper.startPage(pageQuery.getPage(), pageQuery.getLimit());
		return new PageInfo<>(userMapper.selectAll(user), 10);
	}

	@Override
	public int updateByPrimaryKeySelective(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}


	@Override
	public User selectByPrimaryKey(String userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

}