package com.store.back.apicontroller.express;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.store.model.Express;
import com.store.model.Order;
import com.store.model.req.PageReq;
import com.store.model.resp.GatewayProtocol;
import com.store.service.ExpressService;
import com.store.service.OrderService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/back/api/express")
public class BackExpressController {

	@Autowired
	private ExpressService expressService;

	@Autowired
	private OrderService orderService;

	/**
	 * 增
	 * 
	 * @param req
	 * @param express
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject add(HttpServletRequest req, @RequestBody Express express) {
		try {
			express.setExpressId(Utils.UUID());
			String orderNo = express.getExpressOno();
			Order order = orderService.selectByPrimaryKey(orderNo);
			if (order == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "THE ORDER COULD NOT BE FOUND");
			}
			order.setUpdateTime(new Date());
			order.setOrderStatus(Order.STATUS_SHIPPED);
			int i = orderService.updateByPrimaryKeySelective(order);
			if (i != 1) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null,
						"ORDER NUMBER: " + orderNo + " FAILURE OF STATE CHANGE");
			}
			i = expressService.insert(express);
			if (i == 1) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 删
	 * 
	 * @param expressId
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JSONObject del(String expressId, HttpServletResponse resp) {
		try {
			int row = expressService.deleteByPrimaryKey(expressId);
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
	 * 改
	 * 
	 * @param express
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JSONObject edit(Express express, HttpServletResponse resp) {
		try {
			int row = expressService.updateByPrimaryKeySelective(express);
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
	 * @param express
	 * @return
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public JSONObject page(PageReq pageReq, Express express) {
		try {
			PageInfo<Express> pageInfo = expressService.page(pageReq, express);
			if (null == pageInfo) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, pageInfo, "SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}