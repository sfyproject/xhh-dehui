package com.store.service;

import java.util.List;
import java.util.Set;


import com.store.model.SysMenu;
import com.store.model.Tree;



/**
 * @author 1992lcg@163.com
 * @Time 2017年8月21日
 * @description 系统菜单业务代码
 * 
 */

public interface SysMenuService {

	Set<String> getPermsListByAdminId(String adminId);

	List<Tree<SysMenu>> listMenuTree(String adminId);

	List<SysMenu> page();

	boolean delete(Long id);
	
	boolean childNode(SysMenu sysMenu);

	boolean save(SysMenu sysMenu);
	
	boolean update(SysMenu sysMenu);

	Tree<SysMenu> getTree(Long roleId);
	
	Tree<SysMenu> getTree();

	SysMenu exitPerms(String perms);

	List<SysMenu> exitEditPerms(SysMenu sysMenu); 
	
}
