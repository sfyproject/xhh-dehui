package com.store.dao;

import java.util.List;

import com.store.model.Admin;

public interface AdminMapper {

	int deleteByPrimaryKey(String adminId);

	int insert(Admin record);

	int insertSelective(Admin record);

	Admin selectByPrimaryKey(String adminId);

	int updateByPrimaryKeySelective(Admin record);

	int updateByPrimaryKey(Admin record);

	List<Admin> selectAll(String adminUsername);

	Admin loadAdminByUsername(String adminUsername);

	List<Admin> exitEdit(Admin admin); 

}