package com.store.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.store.dao.SysRoleMapper;
import com.store.dao.SysRoleMenuMapper;
import com.store.dao.SysUserRoleMapper;
import com.store.model.SysRole;
import com.store.model.SysRoleMenu;
import com.store.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	
	@Autowired
	SysRoleMapper sysRoleMapper;
	
	@Autowired
	SysRoleMenuMapper sysRoleMenuMapper;
	
	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	@Override
	public List<SysRole> page() {
		// TODO Auto-generated method stub
		return sysRoleMapper.getSysRoleList();
	}


	
	@Transactional
	@Override
	public boolean save(SysRole sysRole) {
		int count = sysRoleMapper.insertSelective(sysRole);
		if(count==0) {
			return false;
		}
		SysRole role = sysRoleMapper.selectByRoleName(sysRole.getRoleName());
		
		List<Long> menuIds = sysRole.getMenuIds();
		Long roleId = role.getRoleId();
		List<SysRoleMenu> rms = new ArrayList<>();
		for (Long menuId : menuIds) {
			SysRoleMenu rmDo = new SysRoleMenu();
			rmDo.setRoleId(roleId);
			rmDo.setMenuId(menuId);
			rms.add(rmDo);
		}
		sysRoleMenuMapper.deleteByRoleId(roleId);

		if (rms.size() > 0) {
			int batchSave = sysRoleMenuMapper.batchSave(rms);
			if(batchSave==0) {
				return false;
			}
		}
		return true;
	}


	@Transactional
	@Override
	public boolean delete(Long id) {
		int deleteByPrimaryKeyStatus = sysRoleMapper.deleteByPrimaryKey(id);
		sysRoleMenuMapper.deleteByRoleId(id);
		if( deleteByPrimaryKeyStatus>0) {
			return true;
		}
		return false;
	}



	@Override
	public boolean existRoleName(String roleName) {
		SysRole sysRole = sysRoleMapper.selectByRoleName(roleName);
		if(sysRole==null) {
			return true;
		}
		return false;
	}



	@Override
	public SysRole selectByPrimaryKey(Long roleId) {
		
		return sysRoleMapper.selectByPrimaryKey(roleId);
	}
	
	@Transactional
	@Override
	public boolean update(SysRole sysRole) {
		int r = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
		if(r==0) {
			return false;
		}
		List<Long> menuIds = sysRole.getMenuIds();
		
		Long roleId = sysRole.getRoleId();
		sysRoleMenuMapper.deleteByRoleId(roleId);
		List<SysRoleMenu> rms = new ArrayList<>();
		if(menuIds!=null) {
			for (Long menuId : menuIds) {
				SysRoleMenu sysRoleMenu = new SysRoleMenu();
				sysRoleMenu.setRoleId(roleId);
				sysRoleMenu.setMenuId(menuId);
				rms.add(sysRoleMenu);
			}
			
		}
		if (rms.size() > 0) {
			sysRoleMenuMapper.batchSave(rms);
		}
		return true;
	}



	@Override
	public List<SysRole> getsysRoleServiceByAdminId(String id) {
		List<SysRole> sysRoleList = sysRoleMapper.getSysRoleList();
		
		List<Long> sysRoleIds=sysUserRoleMapper.selectByAdminId(id);
		List<SysRole> list=new ArrayList<>();
		
			
		
		for (SysRole sysRole : sysRoleList) {
			for (Long roleId : sysRoleIds) {
				 long rId=sysRole.getRoleId();
				 if(roleId==rId) {
					 sysRole.setRoleSign("true");
				 }
			}
			 list.add(sysRole);
		}
		
		
		return list;
	}



	@Override
	public List<SysRole> existEditRoleName(SysRole sysRole) {
		// TODO Auto-generated method stub
		return sysRoleMapper.existEditRoleName(sysRole);
	}






}
