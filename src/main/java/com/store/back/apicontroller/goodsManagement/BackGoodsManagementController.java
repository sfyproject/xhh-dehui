package com.store.back.apicontroller.goodsManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.store.back.apicontroller.recommend.BackRecommendGoodsController;
import com.store.model.Admin;
import com.store.model.Goods;
import com.store.model.GoodsTree;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.GoodsService;
import com.store.utils.PageUtils;

@RestController
@RequestMapping("/back/api/goodsManagement")
public class BackGoodsManagementController {

	Logger log = LoggerFactory.getLogger(BackRecommendGoodsController.class);

	@Autowired
	private GoodsService goodsService;
	
	@Value("${img.path.prefix}")
	private String imgPathPrefix;

	@Value("${url.img.path.prefix}")
	private String urlImgPathPrefix;
	
	/**
	 * 查询所有商品信息
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.info("/back/api/goodsManagement/page.json : \n");
		log.info("params = " + params + "\n");
		// 查询列表数据
		Goods goods= JSON.parseObject(JSON.toJSONString(params), Goods.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<Goods> pageInfo = goodsService.page(query, goods);
		List<Goods> adminList = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(adminList, total);
		return pageUtil;
	}
	
	/**
	 * 商品入库
	 * @param req
	 * @param goods
	 * @return
	 */
	@RequiresPermissions("back:api:goodsManagement:save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JSONObject save(HttpServletRequest request) {
		log.info("/back/api/goodsManagement/save.json : \n");
		log.info("HttpServletRequest = " + request + "\n");
		String partTInfArr = request.getParameter("goodsArr");
		List<Goods> list = JSONArray.parseArray(partTInfArr, Goods.class);
		try {
			HttpSession session = request.getSession();
			Admin admin = (Admin) session.getAttribute("login");
			boolean flag = goodsService.save(list,admin);
			if (flag) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "商品添加成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "商品添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	
	/**
	 * 商品出库 
	 * @param request
	 * @return
	 */
	@RequiresPermissions("back:api:goodsManagement:del")
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JSONObject del(HttpServletRequest request) {
		log.info("/back/api/goodsManagement/del.json : \n");
		log.info("HttpServletRequest = " + request + "\n");
		String partTInfArr = request.getParameter("goodsArr");
		List<Goods> list = JSONArray.parseArray(partTInfArr, Goods.class);
		try {
			HttpSession session = request.getSession();
			Admin admin = (Admin) session.getAttribute("login");
			boolean flag = goodsService.del(list,admin);
			if (flag) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "商品出库成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "商品出库失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	
	
	/**
	 * 删除一种商品
	 * @param id
	 * @return
	 */
	@RequiresPermissions("back:api:goodsManagement:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject delete(String id) {
		log.info("/back/api/goodsManagement/delete.json : \n");
		log.info("id = " + id + "\n");
		try {
			int status=goodsService.deleteByGoodsId(id);
			if (status>0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "删除成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	
	@RequiresPermissions("back:api:goodsManagement:batchRemove")
	@RequestMapping(value = "/batchRemove", method = RequestMethod.POST)
	public JSONObject batchRemove(@RequestParam("ids[]")String[] ids) {
		log.info("/back/api/goodsManagement/batchRemove.json : \n");
		log.info("ids = " + ids + "\n");
		try {
			List<String> list= new ArrayList<>();
			for (String id : ids) {
				list.add(id);
			}
			int status=goodsService.batchRemove(list);
			if (status>0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "删除成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	
	/**
	 * 修改一种商品的信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("back:api:goodsManagement:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JSONObject update(Goods goods, HttpServletResponse resp) {
		log.info("/back/api/goodsManagement/update.json : \n");
		log.error("Goods = " + goods + "\n");
		try {
			int row = goodsService.updateByPrimaryKeySelective(goods);
			if (row == 0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "修改失败");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	
	@RequestMapping(value = "/selectByGoodsId")
	public JSONObject selectByGoodsId(String  goodsId) {
		log.info("/back/api/goodsManagement/selectByGoodsId.json : \n");
		log.info("String = " + goodsId + "\n");
		try {
			 Goods goods = new Goods();
			 goods.setGoodsId(goodsId);
			
			 Goods goods1 = goodsService.selectOneGoods(goods);
			if (goods1 == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, goods1, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "");
	}
	
	@RequestMapping("/selectAllGoods")
	@ResponseBody
	public JSONObject selectAllGoods(Goods goods) {
		log.info("/back/api/goodsManagement/selectAllGoods.json : \n");
		log.info("Goods = " + goods + "\n");
		try {
			List<Goods> list = goodsService.selectAllGoods(goods);
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, list, "");
			
		} catch (Exception e) {
			
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
		
	}
	@RequestMapping(value = "/goodsTree", method = RequestMethod.POST)
	public JSONObject goodsTree() {
		log.info("/back/api/goodsManagement/goodsTree.json : \n");
		try {
			 List<GoodsTree> selectTree = goodsService.selectTree();
			 return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, selectTree, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	
}