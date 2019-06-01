package com.store.model.resp;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class GatewayProtocol {

	public static final String RET_SUCCESS = "0";
	public static final String RET_FAIL = "1";

	public static JSONObject protocolBody(String ret, Object data, String msg) {
		JSONObject json = new JSONObject();
		json.put("ret", ret);
		json.put("data", data);
		json.put("msg", msg == null ? "" : msg);
		return json;
	}

	public static JSONObject protocolBody(Map<String, String> map) {
		JSONObject json = new JSONObject();
		for (String s : map.keySet()) {
			json.put(s, map.get(s));
		}
		return json;
	}

}