package com.store.back.apicontroller.info;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.store.model.Info;
import com.store.model.req.PageReq;
import com.store.model.resp.GatewayProtocol;
import com.store.service.InfoService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/back/api/info")
public class BackInfoController {

	@Autowired
	private InfoService infoService;

	/**
	 * 增
	 * 
	 * @param info
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject add(Info info, HttpServletResponse resp) {
		try {
			info.setInfoId(Utils.UUID());
			info.setCreateTime(new Date());
			int row = infoService.insert(info);
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
	 * 删
	 * 
	 * @param storeInfoId
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JSONObject del(String storeInfoId, HttpServletResponse resp) {
		try {
			int row = infoService.deleteByPrimaryKey(storeInfoId);
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
	 * @param info
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JSONObject edit(Info info, HttpServletResponse resp) {
		try {
			int row = infoService.updateByPrimaryKeySelective(info);
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
	 * @param info
	 * @param pageReq
	 * @return
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public JSONObject page(Info info, PageReq pageReq) {
		try {
			PageInfo<Info> pageInfo = infoService.page(pageReq);
			if (null == pageInfo) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, pageInfo, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}