package com.store.dao;

import java.util.List;

import com.store.model.SysMenu;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

	List<String> getPermsListByAdminId(String adminId);
	
	List<SysMenu> getMenuListByAdminId(String adminId);
	
	List<SysMenu> getMenuList();
	
	List<SysMenu> getMenuListbyEntity(SysMenu  sysMenu );

	SysMenu exitPerms(String perms);

	List<SysMenu> exitEditPerms(SysMenu sysMenu);

	
}