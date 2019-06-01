package com.store.module.wechat;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.JSONObject;
import com.store.model.req.VipPaymentReq;
import com.store.model.req.WeChatPaymentReq;
import com.store.model.req.WxRefundReq;

public interface WxSnsService {

	String getToke() throws IOException;

	WxLoginResp wxLogin(String code) throws IOException;

	boolean callBack(HttpServletRequest request, HttpServletResponse response) throws Exception;

	boolean rechargeCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception;

	JSONObject refund(WxRefundReq wxRefund) throws Exception;

	JSONObject payment(HttpServletRequest request, WeChatPaymentReq weChatPayment) throws Exception;

	boolean callBackVip(HttpServletRequest request, HttpServletResponse response) throws Exception;

	JSONObject paymentVip(HttpServletRequest req, VipPaymentReq vipPayment) throws Exception;

	JSONObject paymentRecharge(HttpServletRequest request, WeChatPaymentReq weChatPayment) throws Exception;

}