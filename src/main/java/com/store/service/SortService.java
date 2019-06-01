package com.store.service;

import java.util.List;

import com.store.model.Sort;

public interface SortService {

	List<Sort> page(Sort sort);

	Sort selectByPrimaryKey(String sortId);

	int save(Sort sort);

	int update(Sort sort);

	int delete(String sortId);



	List<Sort> existEditSortName(Sort sort);


	List<Sort> selectSortName();

	List<Sort> selectSortName(String grade);


	String selectBySortNameAndParentId(Sort sort);


}
