package com.store.model;

import java.util.Date;

import com.store.utils.Utils;

public class LowerLimit {
    /** */
    private String id;

    /** */
    private String adminId;

    /** */
    private String goodsId;

    /** */
    private Integer lowerLimit;

    /** */
    private Date createTime;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId == null ? null : adminId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /** */
    private String goodsTitle;

    /** */
    private String adminUsername;
    
    private String startDate;
    
	private String endDate;

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
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
    

}