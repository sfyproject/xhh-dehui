package com.store.back.apicontroller.admin;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.store.model.Admin;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.AdminService;
import com.store.utils.PageUtils;
import com.store.utils.Utils;


@RestController
@RequestMapping("/back/api/admin")
public class BackAdminController {
	Logger log = LoggerFactory.getLogger(BackAdminController.class);
	
	@Autowired
	AdminService  adminService;
	
	/**
	 * 查询
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.info("/back/api/admin/page.json : \n");
		log.info("params = " + params + "\n");
		// 查询列表数据
		Admin admin= JSON.parseObject(JSON.toJSONString(params), Admin.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<Admin> pageInfo = adminService.page(query, admin.getAdminUsername());
		List<Admin> adminList = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(adminList, total);
		return pageUtil;
	}
	/**
	 * 添加
	 * @param admin
	 * @return
	 */
	@RequiresPermissions("back:api:admin:save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JSONObject save(Admin admin) {
		log.info("/back/api/admin/save.json : \n");
		log.info("Admin = " + admin + "\n");
		try {
			admin.setAdminId(Utils.UUID());
			admin.setAdminPassword(Utils.encryptByBase64(admin.getAdminPassword(),"utf-8"));
			boolean status=adminService.save(admin); 
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
	/**
	 * 校验用户名
	 * @param adminUsername
	 * @return
	 */
	@RequestMapping(value = "/exit", method = RequestMethod.POST)
	public String exit(String adminUsername) {
		log.info("/back/api/admin/exit.json : \n");
		log.info("adminUsername = " + adminUsername + "\n");
		try {
			boolean status = adminService.existUserName(adminUsername);
			if (!status) {
				return  "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			return  "false";
		} 
		
	}
	/**
	 * 编辑页面校验用户名
	 * @param adminUsername
	 * @return
	 */
	@RequestMapping(value = "/exitEdit", method = RequestMethod.POST)
	public String exitEdit(Admin admin) {
		log.info("/back/api/admin/exitEdit.json : \n");
		log.info("admin = " + admin + "\n");
		try {
			List<Admin> list = adminService.exitEdit(admin);
			if (list.size()==0) {
				return  "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			return  "false";
		} 
		
	}
	/**
	 * 修改
	 * @param admin
	 * @return
	 */
	@RequiresPermissions("back:api:admin:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JSONObject update(Admin admin) {
		log.info("/back/api/admin/update.json : \n");
		log.info("Admin = " + admin + "\n");
		try {
			boolean status = adminService.update(admin);
			if (status) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "修改成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "");
			}
		} catch (Exception e) {
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
		} 
	
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequiresPermissions("back:api:admin:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject delete(String id) {
		log.info("/back/api/admin/delete.json : \n");
		log.info("id = " + id + "\n");
		try {
			boolean status=adminService.delete(id); 
			if (status) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "删除成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "删除失败");
			}
		} catch (Exception e) {
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
		}
		
	}
	/**
	 * 密码修改
	 * @param admin
	 * @return
	 */
	@RequiresPermissions("back:api:admin:resetPwd")
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	public JSONObject resetPwd(Admin admin) {
		log.info("/back/api/admin/resetPwd.json : \n");
		log.info("Admin = " + admin + "\n");
		try {
			admin.setAdminPassword(Utils.encryptByBase64(admin.getAdminPassword(),"utf-8"));
			boolean status = adminService.resetPwd(admin);
			if (status) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "修改成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "");
			}
		} catch (Exception e) {
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
		} 
		
	}
}
