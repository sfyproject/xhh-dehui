package com.store.back.apicontroller.sysRole;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.SysMenu;
import com.store.model.SysRole;
import com.store.model.Tree;
import com.store.model.resp.GatewayProtocol;
import com.store.service.SysMenuService;
import com.store.service.SysRoleService;


@RestController
@RequestMapping("/back/api/sysRole")
public class BackSysRoleController {
	Logger log = LoggerFactory.getLogger(BackSysRoleController.class);
	
	@Autowired
	SysRoleService sysRoleService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping("/page")
	List<SysRole> list(Integer limit) {
		log.error("/back/api/sysRole/page.json : \n");
		List<SysRole> roles = sysRoleService.page();
		return roles;
	}
	
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public JSONObject tree(Long roleId) {
		log.error("/back/api/sysRole/tree.json : \n");
		log.error("roleId = " + roleId + "\n");
		try {
			Tree<SysMenu> tree = new Tree<SysMenu>();
			if(roleId!=null) {
				tree = sysMenuService.getTree(roleId);
			}else {
				tree = sysMenuService.getTree();
			}
			if (tree!=null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, tree, "");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	
	@RequiresPermissions("back:api:sysRole:save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JSONObject save(SysRole sysRole) {
		log.error("/back/api/sysRole/save.json : \n");
		log.error("SysRole = " + sysRole + "\n");
		try {
			boolean status=sysRoleService.save(sysRole); 
			if (status) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "添加成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	
	@RequiresPermissions("back:api:sysRole:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject delete(Long id) {
		log.error("/back/api/sysRole/delete.json : \n");
		log.error("id = " + id + "\n");
		try {
			boolean status=sysRoleService.delete(id); 
			if (status) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "删除成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	
	@RequestMapping(value = "/existRoleName", method = RequestMethod.POST)
	public String existRoleName(String roleName) {
		log.error("/back/api/sysRole/existRoleName.json : \n");
		log.error("roleName = " + roleName + "\n");
		try {
			boolean status=sysRoleService.existRoleName(roleName); 
			if (status) {
				return "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			return "false";
		}
		
	}
	
	@RequestMapping(value = "/existEditRoleName", method = RequestMethod.POST)
	public String existEditRoleName(SysRole sysRole) {
		log.error("/back/api/sysRole/existEditRoleName.json : \n");
		log.error("sysRole = " + sysRole + "\n");
		try {
		List<SysRole> list=sysRoleService.existEditRoleName(sysRole); 
			if (list.size()==0) {
				return "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			return "false";
		}
		
	}
	
	@RequiresPermissions("back:api:sysRole:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JSONObject update(SysRole sysRole) {
		log.error("/back/api/sysRole/update.json : \n");
		log.error("SysRole = " + sysRole + "\n");
		try {
			boolean status=sysRoleService.update(sysRole);
			if (status) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "修改成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}

}
