package com.store.back.apicontroller.discountCoupon;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.store.model.Admin;
import com.store.model.DiscountCoupon;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.DiscountCouponService;
import com.store.utils.PageUtils;

@RestController
@RequestMapping("/back/api/discountManage")
public class BackDiscountCouponController {

	Logger log = LoggerFactory.getLogger(BackDiscountCouponController.class);

	@Autowired
	private DiscountCouponService discountCouponService;

	/**
	 * 查询所有的商品信息
	 * @return
	 */
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.info("/back/api/discountCoupon/page.json : \n");
		log.info("params = " + params + "\n");
		// 查询列表数据
		DiscountCoupon discountCoupon= JSON.parseObject(JSON.toJSONString(params), DiscountCoupon.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<DiscountCoupon> pageInfo = discountCouponService.page(query, discountCoupon);
		List<DiscountCoupon> discountList = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(discountList, total);
		return pageUtil;
	}

	/**
	 * 添加商品
	 * @param req
	 * @param goods
	 * @return
	 */
	@RequiresPermissions("back:api:discountManage:save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JSONObject save(HttpServletRequest request,DiscountCoupon dc) {
		log.info("/back/api/goodsManagement/save.json : \n");
		log.info("HttpServletRequest = " + request + "\n");
		try {
			HttpSession session = request.getSession();
			Admin admin = (Admin) session.getAttribute("login");
			dc.setUserId(admin.getAdminId());
			dc.setCreateTime(new Date());
			boolean flag = discountCouponService.saveCouponType(dc);
			if (flag) {
				
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "优惠券添加成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "优惠券添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "系统异常");
	}
	
	/**
	 * 添加商品
	 * @param req
	 * @param goods
	 * @return
	 */
	@RequiresPermissions("back:api:discountManage:couponConfigSave")
	@RequestMapping(value = "/couponConfigSave", method = RequestMethod.POST)
	public JSONObject couponConfig(HttpServletRequest request,DiscountCoupon dc) {
		log.info("/back/api/discountManage/couponConfigSave.json : \n");
		try {
			dc.setFacePrice(dc.getFacePrice()*100);
			dc.setPayPrice(dc.getPayPrice()*100);
			dc.setAddtime(new Date());
			dc.setCouponStatus((byte) 0);
			dc.setCouponType((byte) 0);
			dc.setSwit("off");
			int row = discountCouponService.save(dc);
			if (row==1) {
				
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "优惠券配置成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "优惠券配置失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "系统异常");
	}
	/**
	 * 删除一种商品
	 * @param id
	 * @return
	 */
	@RequiresPermissions("back:api:discountManage:remove")
	@RequestMapping(value = "/toDelete", method = RequestMethod.POST)
	public JSONObject delete(Long id) {
		log.info("/back/api/discountManage/delete.json : \n");
		log.info("id = " + id + "\n");
		try {
			int row =discountCouponService.deleteByCouponId(id);
			if (row>0) {
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
	@RequiresPermissions("back:api:discountManage:edit")
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JSONObject update(DiscountCoupon dc, HttpServletResponse resp) {
		log.info("/back/api/discountManage/edit.json : \n");
		log.info("dc = " + dc + "\n");
		try {
			dc.setFacePrice(dc.getFacePrice()*100);
			dc.setPayPrice(dc.getPayPrice()*100);
			int row = discountCouponService.updateByPrimaryKeySelective(dc);
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
}