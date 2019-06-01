package com.store.service;

import com.github.pagehelper.PageInfo;
import com.store.model.User;
import com.store.model.req.PageQuery;

public interface AccountService {

	/**
	 * 分页查询
	 * 
	 * @param pageReq
	 * @param account
	 * @return
	 */
	PageInfo<User> page(PageQuery pageQuery, User User);

	/**
	 * 修改账户信息
	 * 
	 * @param acco
	 * @return
	 */
	int updateByPrimaryKeySelective(User user);



	/**
	 * 通过主键查询账户
	 * 
	 * @param userId
	 * @return
	 */
	User selectByPrimaryKey(String userId);
	

}