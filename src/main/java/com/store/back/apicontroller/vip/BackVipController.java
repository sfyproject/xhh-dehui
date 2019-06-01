package com.store.back.apicontroller.vip;

import java.util.ArrayList;
import java.util.Date;
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
import com.store.model.Vip;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.VipService;
import com.store.utils.PageUtils;
import com.store.utils.Utils;
@RestController
@RequestMapping("/back/api/vip")
public class BackVipController {
	
	Logger log = LoggerFactory.getLogger(BackVipController.class);
	
	@Autowired
	private VipService vipService;

	/**
	 * 查
	 * 
	 * @param PageQuery
	 * @param Vip
	 * @return
	 */
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.error("/back/api/vip/page.json : \n");
		log.error("params = " + params + "\n");
		// 查询列表数据
		Vip vip= JSON.parseObject(JSON.toJSONString(params), Vip.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<Vip> pageInfo = vipService.page(query, vip);
		List<Vip> userList = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(userList, total);
		return pageUtil;
	}
	/**
	 * 添加
	 * @param vip
	 * @return
	 */
	@RequiresPermissions("back:api:vip:save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JSONObject save(Vip vip) {
		log.error("/back/api/vip/save.json : \n");
		log.error("vip = " + vip + "\n");
		try {
			vip.setCreateTime(new Date());
			vip.setVipId(Utils.UUID());
			int status = vipService.insertSelective(vip);
			if (status>0) {
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
	 * 改
	 * 
	 * @param vip
	 * @param resp
	 * @return
	 */
	@RequiresPermissions("back:api:vip:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JSONObject update(Vip vip, HttpServletResponse resp) {
		log.error("/back/api/vip/update.json : \n");
		log.error("vip = " + vip + "\n");
		try {
			int row = vipService.updateByPrimaryKeySelective(vip);
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
	/**
	 * 删
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("back:api:vip:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject delete(String id) {
		log.error("/back/api/vip/delete.json : \n");
		log.error("id = " + id + "\n");
		try {
			int status=vipService.deleteByPrimaryKey(id);
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
	@RequiresPermissions("back:api:vip:batchRemove")
	@RequestMapping(value = "/batchRemove", method = RequestMethod.POST)
	public JSONObject batchRemove(@RequestParam("ids[]")String[] ids) {
		log.error("/back/api/vip/batchRemove.json : \n");
		log.error("ids = " + ids + "\n");
		try {
			List<String> list= new ArrayList<>();
			for (String id : ids) {
				list.add(id);
			}
			int status=vipService.batchRemove(list);
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
}
