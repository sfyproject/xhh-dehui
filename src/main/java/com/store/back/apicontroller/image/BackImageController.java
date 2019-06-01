package com.store.back.apicontroller.image;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.store.back.apicontroller.img.BackImgController;
import com.store.model.Image;
import com.store.model.resp.GatewayProtocol;
import com.store.service.ImageService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/back/api/image")
public class BackImageController {
	Logger log = LoggerFactory.getLogger(BackImgController.class);

	@Value("${img.path.prefix}")
	private String imgPathPrefix;

	@Value("${url.img.path.prefix}")
	private String urlImgPathPrefix;

	@Autowired
	private ImageService imageService;
	
	//图片上传
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public Map<String, Object> uploadFile(MultipartFile myfile) throws IllegalStateException, IOException {
    	try {
	    	String fullImageFileName = "";
			String currentDate = Utils.getYYYYMMDDCurrentDate();
			String path = imgPathPrefix;
			String relativePath = "";
			path = path + "/" + currentDate + "/";
			relativePath = "/" + currentDate + "/";
			fullImageFileName = Utils.storeImage(path, relativePath, myfile);
			String imgUrl = urlImgPathPrefix + fullImageFileName;
			System.out.println("url:"+imgUrl);
			Image image = new Image();
			image.setImageId(Utils.UUID());
			image.setImageLink(imgUrl);
			int status = imageService.insertSelective(image);
            Map<String, Object> map = new HashMap<String, Object>();
            if(status>0) {
            	map.put("success", "成功啦");
                map.put("url", imgUrl);
            }else {
            	map.put("error", "失败");
            }
            
            return map;
    	} catch (Exception e) {
    		Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", "图片不合法");
            return map;
		}
    }

    //图片删出
	@RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
	public JSONObject zjyjjDel( @RequestParam("id")String id,@RequestParam("name") String name ,HttpServletRequest request) {
		try {
			String path = imgPathPrefix;
			String PathPrefix=urlImgPathPrefix;
			int indexOf = name.indexOf(PathPrefix);
			if(indexOf!=-1) {
				String str=path+name.split(PathPrefix)[1];
				Utils.DeleteFolder(str);
			}
			imageService.deleteByPrimaryKey(id);
			Map<String, String> map = new HashMap<String, String>();
			return GatewayProtocol.protocolBody(map);//删除成功回调函数返回json
		} catch (Exception e) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("error", "删除失败");
	        return GatewayProtocol.protocolBody(map);
		}
	}
	//图片回显
	@RequestMapping(value = "/showImage", method = RequestMethod.POST)
	public JSONObject getGimgs( HttpServletResponse resp) {
		log.error("/back/api/recommend/getGimgs.json : \n");
		try {
			 List<Image> list = imageService.selectAll();
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, list, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}

}
