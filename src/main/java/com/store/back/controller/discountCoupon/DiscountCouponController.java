package com.store.back.controller.discountCoupon;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.store.model.DiscountCoupon;
import com.store.model.StaticParam;
import com.store.service.DiscountCouponService;
import com.store.service.StaticParamService;

@Controller
@RequestMapping("/back/discountManage")
public class DiscountCouponController {

	@Autowired
	private DiscountCouponService discountCouponService;
	
	@Autowired
	private StaticParamService staticParamService;

	@RequiresPermissions("back:discountManage:list")
	@RequestMapping("/list")
	public String list() {
		return "discountManage/list";
	}

	@RequiresPermissions("back:discountManage:toAdd")
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "discountManage/add";
	}
	
	@RequiresPermissions("back:discountManage:toConfig")
	@RequestMapping("/toConfig")
	public ModelAndView toConfig() {
		ModelAndView mv = new ModelAndView();
		List<StaticParam> spList = staticParamService.getListByCondition("SOURCE");
		mv.addObject("spList", spList);
		List<DiscountCoupon> couponTypeList = discountCouponService.getAllCouponType();
		mv.addObject("couponTypeList", couponTypeList);
		mv.setViewName("discountManage/couponConfig");
		return mv;
	}

	@RequestMapping("/toEdit")
	public ModelAndView toEdit(Long couponId) {
		ModelAndView mv = new ModelAndView();
		List<DiscountCoupon> couponTypeList = discountCouponService.getAllCouponType();
		mv.addObject("couponTypeList", couponTypeList);
		List<StaticParam> switList = staticParamService.getListByCondition("SWIT");
		List<StaticParam> sourceList = staticParamService.getListByCondition("SOURCE");
		mv.addObject("switList", switList);
		mv.addObject("sourceList", sourceList);
		DiscountCoupon discountCoupon = discountCouponService.selectByPrimaryKey(couponId);
		mv.addObject("discountCoupon", discountCoupon);
		mv.setViewName("discountManage/edit");
		return mv;
	}
}
