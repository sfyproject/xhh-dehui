package com.store.back.apicontroller.img;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.store.model.resp.GatewayProtocol;
import com.store.utils.Utils;

@RestController
@RequestMapping("/back/api/img")
public class BackImgController {
	Logger log = LoggerFactory.getLogger(BackImgController.class);

	@Value("${img.path.prefix}")
	private String imgPathPrefix;

	@Value("${url.img.path.prefix}")
	private String urlImgPathPrefix;

	
	/**
	 * 上传图片
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	public JSONObject uploadImg(@RequestParam("file") MultipartFile file) {
		try {
			String fullImageFileName = "";
			String currentDate = Utils.getYYYYMMDDCurrentDate();
			String path = imgPathPrefix;
			String relativePath = "";
			path = path + "/" + currentDate + "/";
			relativePath = "/" + currentDate + "/";
			fullImageFileName = Utils.storeImage(path, relativePath, file);
			String imgUrl = urlImgPathPrefix + fullImageFileName;
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, imgUrl, "SUCCESS");
		} catch (Exception e) {
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
		}
	}
	
}