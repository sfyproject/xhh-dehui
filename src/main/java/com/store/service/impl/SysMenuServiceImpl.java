package com.store.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.SysMenuMapper;
import com.store.dao.SysRoleMenuMapper;
import com.store.model.SysMenu;
import com.store.model.Tree;
import com.store.service.SysMenuService;
import com.store.utils.BuildTree;



@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	SysMenuMapper sysMenuMapper;
	@Autowired
	SysRoleMenuMapper sysroleMenuMapper;
	@Override
	public Set<String> getPermsListByAdminId(String adminId) {
		List<String> perms  = sysMenuMapper.getPermsListByAdminId(adminId);
		Set<String> permsSet  = new HashSet<>();
		for (String  perm : perms  ) {
			if (StringUtils.isNotBlank(perm)) {
				permsSet .addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permsSet ;
	}
	
	@Override
	public List<Tree<SysMenu>> listMenuTree(String adminId) {
		List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
		List<SysMenu> menuDOs = sysMenuMapper.getMenuListByAdminId(adminId);
		if(menuDOs.size()>0) {
			for (SysMenu sysMenuDO : menuDOs) {
				Tree<SysMenu> tree = new Tree<SysMenu>();
				tree.setId(sysMenuDO.getMenuId().toString());
				tree.setParentId(sysMenuDO.getParentId().toString());
				tree.setText(sysMenuDO.getName());
				Map<String, Object> attributes = new HashMap<>();
				attributes.put("url", sysMenuDO.getUrl());
				attributes.put("icon", sysMenuDO.getIcon());
				tree.setAttributes(attributes);
				trees.add(tree);
			}
			// 默认顶级菜单为０，根据数据库实际情况调整
			List<Tree<SysMenu>> list = BuildTree.buildList(trees,"0");
			return list;
		}
		return null;
		
	}
	@Override
	public List<SysMenu> page() {
		return sysMenuMapper.getMenuList();
	}

	@Override
	public boolean delete(Long id) {
		int status= sysMenuMapper.deleteByPrimaryKey(id);
		if(status==0) {
			
			return false;
		}
		sysroleMenuMapper.deleteByMenuId(id);
		return true;
	}
	@Override
	public boolean childNode(SysMenu sysMenu) {
		 List<SysMenu> menuListby = sysMenuMapper.getMenuListbyEntity(sysMenu);
		if(menuListby.size()>0) {
			
			return false;
		}
		return true;
	}

	@Override
	public boolean save(SysMenu sysMenu) {
		int status = sysMenuMapper.insertSelective(sysMenu);
		if(status>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean update(SysMenu sysMenu) {
		int status = sysMenuMapper.updateByPrimaryKey(sysMenu);
		if(status>0) {
			return true;
		}
		return false;
	}
	public Tree<SysMenu> getTree() {
		List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
		List<SysMenu> sysMenus = sysMenuMapper.getMenuList();
		if(sysMenus!=null){
			for (SysMenu sysMenu : sysMenus) {
				Tree<SysMenu> tree = new Tree<SysMenu>();
				tree.setId(sysMenu.getMenuId().toString());
				tree.setParentId(sysMenu.getParentId().toString());
				tree.setText(sysMenu.getName());
				trees.add(tree);
			}
		}
		
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<SysMenu> t = BuildTree.build(trees);
		return t;
	}


	@Override
	public Tree<SysMenu> getTree(Long id) {
		// 根据roleId查询权限
		List<Long> menuIds = sysroleMenuMapper.listMenuIdByRoleId(id);
		List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
		if(menuIds.size()>0) {
			List<SysMenu> menus = sysMenuMapper.getMenuList();
			if(menus!=null) {
				for (SysMenu sysMenu : menus) {
					Tree<SysMenu> tree = new Tree<SysMenu>();
					tree.setId(sysMenu.getMenuId().toString());
					tree.setParentId(sysMenu.getParentId().toString());
					tree.setText(sysMenu.getName());
					Map<String, Object> state = new HashMap<>();
					Long menuId = sysMenu.getMenuId();
					if (menuIds.contains(menuId)) {
						state.put("selected", true);
					} else {
						state.put("selected", false);
					}
					tree.setState(state);
					trees.add(tree);
				}
			}
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<SysMenu> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public SysMenu exitPerms(String perms) {
		// TODO Auto-generated method stub
		return sysMenuMapper.exitPerms(perms);
	}

	@Override
	public List<SysMenu> exitEditPerms(SysMenu sysMenu) {
		// TODO Auto-generated method stub
		return sysMenuMapper.exitEditPerms(sysMenu);
	}

}
