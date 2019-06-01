package com.store.service;

import com.github.pagehelper.PageInfo;
import com.store.model.OperationRecord;
import com.store.model.req.PageQuery;

public interface OperationRecordService {
	
	int insertSelective(OperationRecord operationRecord);
	
	public PageInfo<OperationRecord> page(PageQuery query,OperationRecord operationRecord);
	
	int deleteByPrimaryKey(String operationRecordId);
}
