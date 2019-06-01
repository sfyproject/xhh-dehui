package com.store.back.apicontroller.recharge;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.store.model.Recharge;
import com.store.model.req.PageQuery;
import com.store.service.RechargeService;
import com.store.utils.PageUtils;

@RestController
@RequestMapping("/back/api/recharge")
public class BackRechargeController {

	Logger log = LoggerFactory.getLogger(BackRechargeController.class);
	@Autowired
	private RechargeService rechargeService;

	/**
	 * 查
	 * 
	 * @param pageReq
	 * @param recharge
	 * @return
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.error("/back/api/recharge/page.json : \n");
		log.error("params = " + params + "\n");
		// 查询列表数据
		Recharge recharge= JSON.parseObject(JSON.toJSONString(params), Recharge.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<Recharge> pageInfo = rechargeService.page(query, recharge);
		List<Recharge> rechargeList = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(rechargeList, total);
		return pageUtil;

}
	
}