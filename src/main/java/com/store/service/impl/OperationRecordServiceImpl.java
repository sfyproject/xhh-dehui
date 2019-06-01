package com.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.OperationRecordMapper;
import com.store.model.OperationRecord;
import com.store.model.req.PageQuery;
import com.store.service.OperationRecordService;

@Service
public class OperationRecordServiceImpl implements OperationRecordService {
	
	@Autowired
	private OperationRecordMapper operationRecordMapper;

	/**
	 * 插入一条出入库信息
	 */
	@Override
	public int insertSelective(OperationRecord operationRecord ) {
		
		return operationRecordMapper.insertSelective(operationRecord);
	}

	/**
	 * 查询所有的操作记录
	 */
	@Override
	public PageInfo<OperationRecord> page(PageQuery query ,OperationRecord operationRecord) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		return new PageInfo<>(operationRecordMapper.selectAll(operationRecord), 10);
	}

	/**
	 * 删除一条操作记录
	 */
	@Override
	public int deleteByPrimaryKey(String operationRecordId) {
		
		return operationRecordMapper.deleteByPrimaryKey(operationRecordId);
	}
}


