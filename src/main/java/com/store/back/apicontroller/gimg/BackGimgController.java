package com.store.back.apicontroller.gimg;

import java.io.IOException;
import java.util.Date;
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
import com.store.model.Gimg;
import com.store.model.resp.GatewayProtocol;
import com.store.service.GimgService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/back/api/gimg")
public class BackGimgController {
	Logger log = LoggerFactory.getLogger(BackImgController.class);

	@Value("${img.path.prefix}")
	private String imgPathPrefix;

	@Value("${url.img.path.prefix}")
	private String urlImgPathPrefix;

	@Autowired
	private GimgService gimgService;

	//图片上传
    @RequestMapping(value = "/uploadGimg", method = RequestMethod.POST)
    public Map<String, Object> uploadFile(MultipartFile myfile,@RequestParam("goodsId")String goodsId) throws IllegalStateException, IOException {
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
			
			Gimg gimg = new Gimg();
			gimg.setGimgGid(goodsId);
			gimg.setGimgUrl(imgUrl);
			gimg.setCreateTime(new Date());
			gimg.setGimgId(Utils.UUID());
			int status = gimgService.insertSelective(gimg);
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
    


    //图片删除
	@RequestMapping(value = "/deleteGimg", method = RequestMethod.POST)
	public JSONObject zjyjjDel( @RequestParam("id")String id,@RequestParam("name") String name ,HttpServletRequest request) {
		try {
			String path = imgPathPrefix;
			String PathPrefix=urlImgPathPrefix;
			String str=path+name.split(PathPrefix)[1];
			Utils.DeleteFolder(str);
			gimgService.deleteByPrimaryKey(id);
			Map<String, String> map = new HashMap<String, String>();
			return GatewayProtocol.protocolBody(map);//删除成功回调函数返回json
		} catch (Exception e) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("error", "删除失败");
	        return GatewayProtocol.protocolBody(map);
		}
	}
	//图片回显
	@RequestMapping(value = "/showGimg", method = RequestMethod.POST)
	public JSONObject getGimgs(String goodsId, HttpServletResponse resp) {
		log.error("/back/api/recommend/getGimgs.json : \n");
		log.error("goodsId = " + goodsId + "\n");
		try {
			List<Gimg> gimgList = gimgService.selectGimgListByGid(goodsId);
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, gimgList, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}

}
