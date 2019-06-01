package com.store.dao;

import java.util.List;

import com.store.model.OperationRecord;

public interface OperationRecordMapper {
    int deleteByPrimaryKey(String operationRecordId);

    int insert(OperationRecord record);

    int insertSelective(OperationRecord record);

    OperationRecord selectByPrimaryKey(String operationRecordId);

    int updateByPrimaryKeySelective(OperationRecord record);

    int updateByPrimaryKey(OperationRecord record);
    
    List<OperationRecord>selectAll(OperationRecord record);
}