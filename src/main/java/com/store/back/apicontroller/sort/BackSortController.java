package com.store.back.apicontroller.sort;

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
import com.store.model.Sort;
import com.store.model.resp.GatewayProtocol;
import com.store.service.SortService;
import com.store.utils.Utils;

@RequestMapping("/back/api/sort")
@RestController
public class BackSortController {
	Logger log = LoggerFactory.getLogger(BackSortController.class);
	@Autowired
	SortService sortService;
	
	/**
	 * 查
	 * 
	 * @param pageReq
	 * @param recharge
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	List<Sort> list() {
		log.info("/back/api/sort/page.json : \n");
		List<Sort> list = sortService.page(null);
		return list;
	}
	@RequiresPermissions("back:api:sort:save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JSONObject save(Sort sort) {
		log.info("/back/api/sort/save.json : \n");
		log.info("sort = " + sort + "\n");
		try {
			if("0".equals(sort.getParentId())) {
				sort.setGrade("1");
			}else {
				sort.setGrade("2");
			}
			sort.setSortId(Utils.UUID());
			int status=sortService.save(sort); 
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
	@RequiresPermissions("back:api:sort:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JSONObject update(Sort sort) {
		log.info("/back/api/sort/update.json : \n");
		log.info("sort = " + sort + "\n");
		try {
			int status=sortService.update(sort);
			if (status>0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "修改成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	@RequiresPermissions("back:api:sort:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject delete(String sortId) {
		log.info("/back/api/sort/delete.json : \n");
		log.info("sortId = " + sortId + "\n");
		try {
			int status=sortService.delete(sortId); 
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
	@RequestMapping(value = "/childNode", method = RequestMethod.POST)
	public JSONObject childNode(Sort sort) {
		log.info("/back/api/sort/childNode.json : \n");
		log.info("sort = " + sort + "\n");
		try {
			List<Sort> list = sortService.page(sort);
			if (list.size()==0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "请先删除子节点");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	
	@RequestMapping("/sortlist")
	@ResponseBody
	public JSONObject Sortlist(Sort sort) {
		log.info("/back/api/sort/sortlist.json : \n");
		log.info("sort = " + sort + "\n");
		try {
			List<Sort> list = sortService.page(sort);
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, list, "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
		
	}
	@RequestMapping(value = "/existSortName", method = RequestMethod.POST)
	public String existRoleName(Sort sort) {
		log.info("/back/api/sysRole/existRoleName.json : \n");
		log.info("sort = " + sort + "\n");
		try {
			List<Sort> list = sortService.page(sort);
			if (list.size()==0) {
				return "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			return "false";
		}
		
	}
	@RequestMapping(value = "/existEditSortName", method = RequestMethod.POST)
	public String existEditSortName(Sort sort) {
		log.error("/back/api/sysRole/existRoleName.json : \n");
		log.error("sort = " + sort + "\n");
		try {
			List<Sort> list = sortService.existEditSortName(sort);
			if (list.size()==0) {
				return "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			return "false";
		}
		
	}
}
