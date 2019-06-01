package com.store.dao;

import java.util.List;

import com.store.model.Sort;

public interface SortMapper {
    int deleteByPrimaryKey(String sortId);

    int insert(Sort record);

    int insertSelective(Sort record);

    Sort selectByPrimaryKey(String sortId);

    int updateByPrimaryKeySelective(Sort record);

    int updateByPrimaryKey(Sort record);

	List<Sort> selectAll(Sort sort);

	
	List<Sort> existEditSortName(Sort sort);

	List<Sort> selectSortName();

	List<Sort> selectSortName(String grade);



	String selectBySortNameAndParentId(Sort sort);

}