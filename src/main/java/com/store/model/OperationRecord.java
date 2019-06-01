package com.store.model;

import java.util.Date;

public class OperationRecord {
    private String operationRecordId;

    private String operationRecordAid;

    private String operationRecordGid;

    private String operationRecordType;

    private Integer operationRecordGoodsnum;

    private String operationRecordName;

    private Date createTime;
    
    private String goodsTitle;
    
    private String startDate;
    
    private String endDate;

    public String getOperationRecordId() {
        return operationRecordId;
    }

    public void setOperationRecordId(String operationRecordId) {
        this.operationRecordId = operationRecordId == null ? null : operationRecordId.trim();
    }

    public String getOperationRecordAid() {
        return operationRecordAid;
    }

    public void setOperationRecordAid(String operationRecordAid) {
        this.operationRecordAid = operationRecordAid == null ? null : operationRecordAid.trim();
    }

    public String getOperationRecordGid() {
        return operationRecordGid;
    }

    public void setOperationRecordGid(String operationRecordGid) {
        this.operationRecordGid = operationRecordGid == null ? null : operationRecordGid.trim();
    }

    public String getOperationRecordType() {
        return operationRecordType;
    }

    public void setOperationRecordType(String operationRecordType) {
        this.operationRecordType = operationRecordType == null ? null : operationRecordType.trim();
    }

    public Integer getOperationRecordGoodsnum() {
        return operationRecordGoodsnum;
    }

    public void setOperationRecordGoodsnum(Integer operationRecordGoodsnum) {
        this.operationRecordGoodsnum = operationRecordGoodsnum;
    }

    public String getOperationRecordName() {
        return operationRecordName;
    }

    public void setOperationRecordName(String operationRecordName) {
        this.operationRecordName = operationRecordName == null ? null : operationRecordName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}