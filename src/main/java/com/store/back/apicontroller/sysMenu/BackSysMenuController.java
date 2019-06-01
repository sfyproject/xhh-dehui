package com.store.back.apicontroller.sysMenu;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.SysMenu;
import com.store.model.resp.GatewayProtocol;
import com.store.service.SysMenuService;

@RestController
@RequestMapping("/back/api/sysMenu")
public class BackSysMenuController {
	Logger log = LoggerFactory.getLogger(BackSysMenuController.class);
	
	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 查
	 * 
	 * @param pageReq
	 * @param recharge
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	List<SysMenu> list() {
		log.error("/back/api/sysMenu/page.json : \n");
		List<SysMenu> sysMenuList = sysMenuService.page();
		return sysMenuList;
	}
	
	@RequiresPermissions("back:api:sysMenu:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject delete(Long id) {
		log.error("/back/api/sysMenu/delete.json : \n");
		log.error("id = " + id + "\n");
		try {
			boolean status=sysMenuService.delete(id); 
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
	
	@RequestMapping(value = "/childNode", method = RequestMethod.POST)
	public JSONObject childNode(SysMenu sysMenu) {
		log.error("/back/api/sysMenu/childNode.json : \n");
		log.error("SysMenu = " + sysMenu + "\n");
		try {
			boolean status=sysMenuService.childNode(sysMenu); 
			if (status) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "请先删除子节点");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	
	@RequiresPermissions("back:api:sysMenu:save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JSONObject save(SysMenu sysMenu) {
		log.error("/back/api/sysMenu/save.json : \n");
		log.error("SysMenu = " + sysMenu + "\n");
		try {
			boolean status=sysMenuService.save(sysMenu); 
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
	@RequiresPermissions("back:api:sysMenu:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JSONObject update(SysMenu sysMenu) {
		log.error("/back/api/sysMenu/update.json : \n");
		log.error("SysMenu = " + sysMenu + "\n");
		try {
			boolean status=sysMenuService.update(sysMenu);
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
	/**
	 * 判断权限标示是否存在
	 * @param perms
	 * @return String
	 */
	@RequestMapping(value = "/exitPerms", method = RequestMethod.POST)
	public String  exitPerms(String perms) {
		log.error("/back/api/sysMenu/exitPerms.json : \n");
		log.error("perms = " + perms + "\n");
		try {
			SysMenu sysMenu=sysMenuService.exitPerms(perms);
			if (sysMenu != null) {
				return "false";
			} else {
				return "true";
			}
		} catch (Exception e) {
			return "false";
		}
		
	}
	/**
	 * 判断权限标示是否存在
	 * @param perms
	 * @return String
	 */
	@RequestMapping(value = "/exitEditPerms", method = RequestMethod.POST)
	public String  exitEditPerms(SysMenu sysMenu) {
		log.error("/back/api/sysMenu/exitEditPerms.json : \n");
		log.error("SysMenu = " + sysMenu + "\n");
		try {
			List<SysMenu> list=sysMenuService.exitEditPerms(sysMenu);
			if (list.size()>0) {
				return "false";
			} else {
				return "true";
			}
		} catch (Exception e) {
			return "false";
		}
		
	}

}
