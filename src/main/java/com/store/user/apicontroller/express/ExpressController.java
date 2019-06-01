package com.store.user.apicontroller.express;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.Express;
import com.store.model.Freight;
import com.store.model.Order;
import com.store.model.resp.GatewayProtocol;
import com.store.service.FreightService;
import com.store.service.OrderService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/express")
public class ExpressController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private FreightService freightService;

	/**
	 * 快递查询
	 * 
	 * @param req
	 * @param express
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public JSONObject query(HttpServletRequest req, @RequestBody Express express) {
		try {
			String no = express.getExpressNo();
			String type = express.getExpressType();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Authorization", "APPCODE " + Express.APPCODE);
			Map<String, String> querys = new HashMap<String, String>();
			querys.put("no", no);
			querys.put("type", type);
			HttpResponse response = Utils.doGet(Express.HOST, Express.PATH, "GET", headers, querys);
			HttpEntity entity = response.getEntity();
			return JSONObject.parseObject(EntityUtils.toString(entity));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 查询运费
	 * 
	 * @param req
	 * @param freight
	 * @return
	 */
	@RequestMapping(value = "/freight", method = RequestMethod.POST)
	public JSONObject freight(HttpServletRequest req, @RequestBody Freight freight) {
		try {
			if (freight.getFreightType() == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "请选择快递公司");
			}
			if (freight.getFreightCity() == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "请选择起发城市");
			}
			String end = freight.getEnd();
			if (end == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "请选择收货地址");
			}
			if (!Freight.FREIGHT_SAME.equals(end) && !Freight.FREIGHT_GENERAL.equals(end)
					&& !Freight.FREIGHT_REMOTE.equals(end)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "收货地区类型错误");
			}
			Freight freightTemplate = freightService.selectByTypeAndCity(freight.getFreightType(),
					freight.getFreightCity());
			if (freightTemplate == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "没有该运费模板");
			}
			switch (end) {
			case Freight.FREIGHT_SAME:
				String same = freightTemplate.getFreightSame();
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, same, "SUCCESS");
			case Freight.FREIGHT_GENERAL:
				String general = freightTemplate.getFreightGeneral();
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, general, "SUCCESS");
			case Freight.FREIGHT_REMOTE:
				String remote = freightTemplate.getFreightRemote();
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, remote, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 确认收货
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/confirmReceipt", method = RequestMethod.GET)
	public JSONObject confirmReceipt(HttpServletRequest req, @RequestBody Express express) {
		try {
			List<String> orderNoList = express.getOrderNoList();
			if (Utils.isNullList(orderNoList)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "REQUEST PARAMETERS IS EMPTY");
			}
			for (String orderNo : orderNoList) {
				Order order = new Order();
				order.setOrderNo(orderNo);
				order.setUpdateTime(new Date());
				order.setOrderStatus(Order.STATUS_COMPLETED);
				int edit = orderService.updateByPrimaryKeySelective(order);
				if (edit == 0) {
					return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "ORDER STATUS CHANGE FAILED");
				}
			}
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 商家合作的快递公司列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/courierServicesCompany", method = RequestMethod.GET)
	public JSONObject courierServicesCompany(HttpServletRequest req) {
		try {
			List<String> list = freightService.selectAll();
			if (list == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, list, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}