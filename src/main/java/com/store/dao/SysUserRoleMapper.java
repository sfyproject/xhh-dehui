package com.store.dao;

import java.util.List;

import com.store.model.SysUserRole;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
    
    int batchSave(List<SysUserRole> urs);
    
    List<Long> selectByAdminId(String adminId);

	int deleteByAdminId(String adminId);
}