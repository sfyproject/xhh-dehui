package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.RefundMapper;
import com.store.model.Refund;
import com.store.service.RefundService;

@Service
public class RefundServiceImpl implements RefundService {

	@Autowired
	private RefundMapper refundMapper;

	@Override
	public int insertSelective(Refund refund) {
		return refundMapper.insertSelective(refund);
	}

	@Override
	public List<Refund> selectByUid(String uid) {
		return refundMapper.selectByUid(uid);
	}

}