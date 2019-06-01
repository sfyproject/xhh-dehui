package com.store.back.apicontroller.operationRecord;

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
import com.store.back.apicontroller.receiving.BackReceivingController;
import com.store.model.LowerLimit;
import com.store.model.OperationRecord;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.LowerLimitService;
import com.store.service.OperationRecordService;
import com.store.utils.PageUtils;

@RestController
@RequestMapping("/back/api/operationRecord")
public class BackOperationRecordController {
	Logger log = LoggerFactory.getLogger(BackReceivingController.class);
	
	@Autowired
	private OperationRecordService operationRecordService;
	@Autowired
	LowerLimitService lowerLimitService;
	
	
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.info("/back/api/operationRecord/page.json : \n");
		log.info("params = " + params + "\n");
		// 查询列表数据
		OperationRecord operationRecord= JSON.parseObject(JSON.toJSONString(params), OperationRecord.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<OperationRecord> pageInfo = operationRecordService.page(query, operationRecord);
		List<OperationRecord> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(list, total);
		return pageUtil;
	}
	
	
	@RequiresPermissions("back:api:operationRecord:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject del(String operationRecordId, HttpServletResponse resp) {
		try {
			 int row = operationRecordService.deleteByPrimaryKey(operationRecordId);
			if (row == 0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}
	/**
	 * 查
	 * 
	 * @param pageReq
	 * @param recharge
	 * @return
	 */
	@GetMapping("/lowerLimitpage")
	@ResponseBody
	PageUtils lowerLimit(@RequestParam Map<String, Object> params) {
		log.info("/back/api/operationRecord/lowerLimitpage.json : \n");
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

}
