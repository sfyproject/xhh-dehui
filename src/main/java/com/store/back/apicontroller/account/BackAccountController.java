package com.store.back.apicontroller.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.store.model.Recharge;
import com.store.model.User;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.AccountService;
import com.store.service.RechargeService;
import com.store.utils.PageUtils;
import com.store.utils.Utils;

@RestController
@RequestMapping("/back/api/account")
public class BackAccountController {
	Logger log = LoggerFactory.getLogger(BackAccountController.class);

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RechargeService rechargeService;
	

	
	/**
	 * 查
	 * 
	 * @param PageQuery
	 * @param goods
	 * @return
	 */
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.error("/back/api/account/page.json : \n");
		log.error("params = " + params + "\n");
		// 查询列表数据
		User user= JSON.parseObject(JSON.toJSONString(params), User.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<User> pageInfo = accountService.page(query, user);
		List<User> userList = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(userList, total);
		return pageUtil;
	}
	
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED)
	public JSONObject recharge(User user) {

		log.info("/back/api/account/recharge.json : \n");
		log.info("user = " + user + "\n");

	//	try {
			if (user.getUserId() == null || user.getRechargeMoney() == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "");
			}
			//更新账户余额
			String walletBefore = user.getUserWallet();
			String rechargeMoney = user.getRechargeMoney();
			BigDecimal d1 = new BigDecimal(walletBefore);
			BigDecimal d2 = new BigDecimal(rechargeMoney);
			String walletAfter = d1.add(d2).toString();
			user.setUserWallet(walletAfter);
			//新插入充值记录
			Recharge recharge = new Recharge();
			recharge.setRechargeId(Utils.UUID());
			recharge.setRechargeCoin(rechargeMoney.toString());
			recharge.setRechargeStatus("1");
			recharge.setRechargeType("0");
			recharge.setRechargeUid(user.getUserId());
			recharge.setCreateTime(new Date());
			/**
			 * 解决一个controller调用多个service，事物控制问题
			 */
			try {
				wraper(user,recharge);
			} catch (Exception e) {
				throw new RuntimeException("事物异常");
			}
			
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, recharge, "充值成功");
	
	}
	
	
	public void wraper(User user,Recharge recharge) {
		
		accountService.updateByPrimaryKeySelective(user);
		rechargeService.insertSelective(recharge);
	}
	
}