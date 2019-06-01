package com.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.model.SysRoleMenu;

public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleMenu record);

    int updateByPrimaryKey(SysRoleMenu record);
    
    int deleteByMenuId(Long id);
    
    List<Long> listMenuIdByRoleId(@Param(value="id") Long id);

	int batchSave(List<SysRoleMenu> rms);

	int deleteByRoleId(Long roleId);

}