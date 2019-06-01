package com.store.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.AdminMapper;
import com.store.dao.SysUserRoleMapper;
import com.store.model.Admin;
import com.store.model.SysUserRole;
import com.store.model.req.PageQuery;
import com.store.service.AdminService;
import com.store.utils.Utils;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	public PageInfo<Admin> page(PageQuery query, String storeAdminName) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		return new PageInfo<>(adminMapper.selectAll(storeAdminName), 10);
	}

	@Override
	public Admin selectByPrimaryKey(String adminId) {
		return adminMapper.selectByPrimaryKey(adminId);
	}

	@Override
	public int insert(Admin admin) {
		return adminMapper.insert(admin);
	}

	@Override
	public int deleteByPrimaryKey(String storeAdminName) {
		return adminMapper.deleteByPrimaryKey(storeAdminName);
	}

	@Override
	public int updateByPrimaryKeySelective(Admin admin) {
		return adminMapper.updateByPrimaryKeySelective(admin);
	}

	@Override
	public boolean existUserName(String adminUsername) {
		if (Utils.isNullStr(adminUsername)) {
			return false;
		}
		Admin admin = adminMapper.loadAdminByUsername(adminUsername);
		return admin == null ? false : true;
	}

	@Override
	public Admin loadAdminByUsername(String adminUsername) {
		
		return adminMapper.loadAdminByUsername(adminUsername);
	}
	@Transactional
	@Override
	public boolean save(Admin admin) {
		int insertSelectivestatus = adminMapper.insertSelective(admin);
		List<Long> roleIds = admin.getRoleIds();
		List<SysUserRole> list=new ArrayList<SysUserRole>();
		if(roleIds.size()>0) {
			for (Long roleId : roleIds) {
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setRoleId(roleId);
				sysUserRole.setUserId(admin.getAdminId());
				list.add(sysUserRole);
			}
		}
		int batchSaveStatus = sysUserRoleMapper.batchSave(list);
		if(insertSelectivestatus > 0 && batchSaveStatus > 0) {
			return true;
		}
		return false;
	}
    @Transactional
	@Override
	public boolean update(Admin admin) {
    	int insertSelectivestatus = adminMapper.updateByPrimaryKeySelective(admin);
    	sysUserRoleMapper.deleteByAdminId(admin.getAdminId());
    	List<Long> roleIds = admin.getRoleIds();
    	List<SysUserRole> list=new ArrayList<SysUserRole>();
    	for (Long roleId : roleIds) {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setRoleId(roleId);
			sysUserRole.setUserId(admin.getAdminId());
			list.add(sysUserRole);
		}
    	int batchSaveStatus = sysUserRoleMapper.batchSave(list);
		if(insertSelectivestatus > 0 && batchSaveStatus > 0) {
			return true;
		}
    	
    	return false;
	}
    @Transactional
	@Override
	public boolean delete(String id) {
		int deleteByAdminIdStatus = sysUserRoleMapper.deleteByAdminId(id);
		int deleteByPrimaryKeyStatus = adminMapper.deleteByPrimaryKey(id);
		if(deleteByPrimaryKeyStatus>0&&deleteByAdminIdStatus>0) {
			return true;
		}
		
		return false;
	}
	
    public boolean resetPwd(Admin admin) {
    	int insertSelectivestatus = adminMapper.updateByPrimaryKeySelective(admin);
		if(insertSelectivestatus > 0 ) {
			return true;
		}
    	
    	return false;
	}

	@Override
	public List<Admin> exitEdit(Admin admin) {
		// TODO Auto-generated method stub
		return adminMapper.exitEdit(admin);
	}


}