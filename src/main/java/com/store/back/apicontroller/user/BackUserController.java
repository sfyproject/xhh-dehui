package com.store.back.apicontroller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.store.model.User;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.UserService;
import com.store.utils.PageUtils;

@RestController
@RequestMapping("/back/api/user")
public class BackUserController {
	Logger log = LoggerFactory.getLogger(BackUserController.class);
	@Autowired
	private UserService userService;


	/**
	 * 查
	 * 
	 * @param PageQuery
	 * @param User
	 * @return
	 */
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.error("/back/api/user/page.json : \n");
		log.error("params = " + params + "\n");
		// 查询列表数据
		User user= JSON.parseObject(JSON.toJSONString(params), User.class);
		// PageQuery 查询参数
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		//
		PageInfo<User> pageInfo = userService.page(query, user);
		List<User> userList = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(userList, total);
		return pageUtil;
	}
	/**
	 * 删
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("back:api:user:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject delete(String id) {
		log.error("/back/api/user/delete.json : \n");
		log.error("id = " + id + "\n");
		try {
			int status=userService.deleteByPrimaryKey(id);
			if (status>0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "删除成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	/**
	 * 批量删
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("back:api:user:batchRemove")
	@RequestMapping(value = "/batchRemove", method = RequestMethod.POST)
	public JSONObject batchRemove(@RequestParam("ids[]")String[] ids) {
		log.error("/back/api/user/batchRemove.json : \n");
		log.error("ids = " + ids + "\n");
		try {
			List<String> list= new ArrayList<>();
			for (String id : ids) {
				list.add(id);
			}
			int status=userService.batchRemove(list);
			if (status>0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "删除成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	/**
	 * 改
	 * 
	 * @param goods
	 * @param resp
	 * @return
	 */
	@RequiresPermissions("back:api:user:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JSONObject update(User user, HttpServletResponse resp) {
		log.error("/back/api/user/update.json : \n");
		log.error("User = " + user + "\n");
		try {
			int row = userService.updateByPrimaryKeySelective(user);
			if (row == 0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "修改失败");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	
}