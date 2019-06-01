package com.store.service;

import java.util.List;

import com.store.model.SysRole;

public interface SysRoleService {

	List<SysRole> page();

	boolean save(SysRole sysRole);

	boolean delete(Long id);

	boolean existRoleName(String roleName);
	
	SysRole selectByPrimaryKey(Long roleId);

	boolean update(SysRole sysRole);

	List<SysRole> getsysRoleServiceByAdminId(String id);

	List<SysRole> existEditRoleName(SysRole sysRole);


}
