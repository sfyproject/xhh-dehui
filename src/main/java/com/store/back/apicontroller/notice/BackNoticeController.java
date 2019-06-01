package com.store.back.apicontroller.notice;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.store.back.apicontroller.admin.BackAdminController;
import com.store.model.Notice;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.NoticeService;
import com.store.utils.PageUtils;
import com.store.utils.Utils;

@RestController
@RequestMapping("/back/api/notice")
public class BackNoticeController {
	Logger log = LoggerFactory.getLogger(BackAdminController.class);
	
	@Autowired
	private NoticeService noticeService;
	
	@Value("${img.path.prefix}")
	private String imgPathPrefix;

	@Value("${url.img.path.prefix}")
	private String urlImgPathPrefix;
	
	/**
	 * 查询所有公告
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.info("/back/api/notice/page.json : \n");
		log.info("params = " + params + "\n");
		// 查询列表数据
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<Notice> page = noticeService.page(query);
		List<Notice> noticeList = page.getList();
		long total = page.getTotal();
		PageUtils pageUtil = new PageUtils(noticeList, total);
		return pageUtil;
	}
	
	/**
	 * 添加一条公告
	 * @param notice
	 * @param resp
	 * @return
	 */
	@RequiresPermissions("back:api:notice:add")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject add(Notice notice, HttpServletResponse resp) {
		try {
			notice.setNoticeId(Utils.UUID());
			notice.setCreateTime(new Date());
			int row = noticeService.insertSelective(notice);
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
	 * 删除一条公告
	 * @param noticeId
	 * @param resp
	 * @return
	 */
	@RequiresPermissions("back:api:notice:del")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JSONObject del(String noticeId, HttpServletResponse resp) {
		try {
			 int row = noticeService.deleteByPrimaryKey(noticeId);
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
	 * 修改一条公告
	 * @param notice
	 * @param resp
	 * @return
	 */
	@RequiresPermissions("back:api:notice:edit")
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JSONObject edit(Notice notice, HttpServletResponse resp) {
		try {
			notice.setCreateTime(new Date());
			int row = noticeService.updateByPrimaryKeySelective(notice);
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
	 * 上传图片
	 * @param myfile
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	 @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	    public Map<String, Object> uploadFile(MultipartFile myfile) 
	    							throws IllegalStateException, IOException {
		 	Map<String, Object> map = new HashMap<String, Object>();
	    	try {
		    	String fullImageFileName = "";
				String currentDate = Utils.getYYYYMMDDCurrentDate();
				String path = imgPathPrefix;
				String relativePath = "";
				path = path + "/" + currentDate + "/";
				relativePath = "/" + currentDate + "/";
				fullImageFileName = Utils.storeImage(path, relativePath, myfile);
				String imgUrl = urlImgPathPrefix + fullImageFileName;
				System.out.println("url:"+ imgUrl);
            	map.put("success", "成功啦");
                map.put("url", imgUrl);
	            return map;
	    	} catch (Exception e) {
	            map.put("error", "图片不合法");
	            return map;
			}
	    }
	/**
	 * 图片删除
	 * @param name
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
	public JSONObject deleteImage(@RequestParam("name") String name ,HttpServletRequest request) {
		try {
			String path = imgPathPrefix;
			String PathPrefix=urlImgPathPrefix;
			String str=path+name.split(PathPrefix)[1];
			Utils.DeleteFolder(str);
			Map<String, String> map = new HashMap<String, String>();
			return GatewayProtocol.protocolBody(map);//删除成功回调函数返回json
		} catch (Exception e) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("error", "删除失败");
	        return GatewayProtocol.protocolBody(map);
		}
	} 
	
	/**
	 * 图片回显
	 * @param noticeId
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/showImage", method = RequestMethod.POST)
	public JSONObject showImage(@RequestParam("noticeId") String noticeId, HttpServletResponse resp ) {
		log.error("/back/api/notice/noticeId.json : \n");
		try {
			Notice notice = noticeService.selectByNoticeId(noticeId);
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, notice, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}

}
