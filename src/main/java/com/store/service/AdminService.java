package com.store.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.store.model.Admin;
import com.store.model.req.PageQuery;

public interface AdminService {

	Admin selectByPrimaryKey(String storeAdminName);

	PageInfo<Admin> page(PageQuery query, String storeAdminName);

	int insert(Admin admin);

	int deleteByPrimaryKey(String storeAdminName);

	int updateByPrimaryKeySelective(Admin admin);

	boolean existUserName(String storeAdminName);

	Admin loadAdminByUsername(String adminUsername);

	boolean save(Admin admin);

	boolean update(Admin admin);

	boolean delete(String id);

	boolean resetPwd(Admin admin);

	List<Admin> exitEdit(Admin admin);

}