package com.store.dao;

import java.util.List;

import com.store.model.SysRole;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

	List<SysRole> getSysRoleList();

	SysRole selectByRoleName(String roleName);

	List<SysRole> existEditRoleName(SysRole sysRole);



}