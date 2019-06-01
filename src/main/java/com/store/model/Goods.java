package com.store.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.store.utils.Utils;



public class Goods {

	private String goodsId;
	private String goodsTitle;
	private String goodsLabel;
	private String goodsDescribe;
	private String goodsImage;
	private String goodsPrice;
	private String goodsType;
	private Integer goodsInventory;
	private Integer goodsSales;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private String goodsLibrary;
	private String goodsOriginal;
	private String goodsSecond;
	private String goodsGroup;
	private String goodsSpec;


	public Integer getGoodsSales() {
		return goodsSales;
	}

	public void setGoodsSales(Integer goodsSales) {
		this.goodsSales = goodsSales;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId == null ? null : goodsId.trim();
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle == null ? null : goodsTitle.trim();
	}

	public String getGoodsLabel() {
		return goodsLabel;
	}

	public void setGoodsLabel(String goodsLabel) {
		this.goodsLabel = goodsLabel;
	}

	public String getGoodsDescribe() {
		return goodsDescribe;
	}

	public void setGoodsDescribe(String goodsDescribe) {
		this.goodsDescribe = goodsDescribe;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage == null ? null : goodsImage.trim();
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice == null ? null : goodsPrice.trim();
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType == null ? null : goodsType.trim();
	}

	public Integer getGoodsInventory() {
		return goodsInventory;
	}

	public void setGoodsInventory(Integer goodsInventory) {
		this.goodsInventory = goodsInventory;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGoodsLibrary() {
		return goodsLibrary;
	}

	public void setGoodsLibrary(String goodsLibrary) {
		this.goodsLibrary = goodsLibrary;
	}

	public String getGoodsOriginal() {
		return goodsOriginal;
	}

	public void setGoodsOriginal(String goodsOriginal) {
		this.goodsOriginal = goodsOriginal;
	}

	public String getGoodsSecond() {
		return goodsSecond;
	}

	public void setGoodsSecond(String goodsSecond) {
		this.goodsSecond = goodsSecond;
	}

	public String getGoodsGroup() {
		return goodsGroup;
	}

	public void setGoodsGroup(String goodsGroup) {
		this.goodsGroup = goodsGroup;
	}

	// Non-database fields
	private List<String> gimgs;
	private String formatDate;
	private String startDate;
	private String endDate;
	private String num;
	private List<Gimg> imgList;
	
	private String parentName;
	private String goodsLabelt;
	
	private String name;
	private Integer lowerLimit;


	public List<String> getGimgs() {
		return gimgs;
	}

	public void setGimgs(List<String> gimgs) {
		this.gimgs = gimgs;
	}

	public String getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(String formatDate) {
		this.formatDate = formatDate;
	}

	public String getStartDate() {
		if (Utils.isNullStr(startDate)) {
			return startDate;
		}
		return startDate + " 00:00:00";
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		if (Utils.isNullStr(endDate)) {
			return endDate;
		}
		return endDate + " 23:59:59";
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getGoodsSpec() {
		return goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public List<Gimg> getImgList() {
		return imgList;
	}

	public void setImgList(List<Gimg> imgList) {
		this.imgList = imgList;
	}

	public String getName() {
		return name;
	}

	public String getParentName() {
		return parentName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getGoodsLabelt() {
		return goodsLabelt;
	}

	public void setGoodsLabelt(String goodsLabelt) {
		this.goodsLabelt = goodsLabelt;
	}

	public Integer getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(Integer lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
    
	
}