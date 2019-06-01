package com.store.back.apicontroller.lowerLimit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.store.model.Admin;
import com.store.model.GoodsTree;
import com.store.model.LowerLimit;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.LowerLimitService;
import com.store.utils.PageUtils;
import com.store.utils.Utils;

@RequestMapping("/back/api/lowerLimit")
@RestController
public class BackLowerLimitController {
	Logger log = LoggerFactory.getLogger(BackLowerLimitController.class);
	@Autowired
	LowerLimitService lowerLimitService;
	
	/**
	 * 查
	 * 
	 * @param pageReq
	 * @param recharge
	 * @return
	 */
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.info("/back/api/lowerLimit/page.json : \n");
		log.info("params = " + params + "\n");
		// 查询列表数据
		LowerLimit lowerLimit= JSON.parseObject(JSON.toJSONString(params), LowerLimit.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<LowerLimit> pageInfo = lowerLimitService.page(query, lowerLimit);
		List<LowerLimit> adminList = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(adminList, total);
		return pageUtil;
	}

	@RequiresPermissions("back:api:lowerLimit:save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JSONObject add(HttpServletRequest request,Integer number) {

		log.error("/back/api/lowerLimit/save.json : \n");
		String partTInfArr = request.getParameter("goodsTreeArr");
		List<GoodsTree> list = JSONArray.parseArray(partTInfArr, GoodsTree.class);
		HttpSession session = request.getSession();
		Admin admin = (Admin)session.getAttribute("login");
		String adminId = admin.getAdminId();
		ArrayList<LowerLimit> arrayList = new ArrayList<>();
		for (GoodsTree goodsTree : list) {
			LowerLimit lowerLimit = new LowerLimit();
			lowerLimit.setId(Utils.UUID());
			lowerLimit.setAdminId(adminId);
			lowerLimit.setGoodsId(goodsTree.getId());
			lowerLimit.setCreateTime(new Date());
			lowerLimit.setLowerLimit(number);
			lowerLimit.setGoodsTitle(goodsTree.getName());
			lowerLimit.setAdminUsername(admin.getAdminUsername());
			arrayList.add(lowerLimit);
		}
		try {
			int status = lowerLimitService.batchInsert(arrayList);
			if (status>0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "");
	}
}
