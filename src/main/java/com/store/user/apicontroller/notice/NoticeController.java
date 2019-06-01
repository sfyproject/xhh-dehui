package com.store.user.apicontroller.notice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.Notice;
import com.store.model.resp.GatewayProtocol;
import com.store.service.NoticeService;

@RestController
@RequestMapping("/user/api/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	/**
	 * 系统公告
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/select", method = RequestMethod.POST)
	public JSONObject select(HttpServletRequest req, @RequestBody Notice notice) {
		try {
			if (notice.getNoticeId() == null) {
				notice.setNoticeId("1");
			}
			notice = noticeService.selectByPrimaryKey(notice.getNoticeId());
			if (notice == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, notice, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}