package com.store.service;

import java.util.List;

import com.store.model.Refund;

public interface RefundService {

	int insertSelective(Refund refund);

	List<Refund> selectByUid(String uid);

}