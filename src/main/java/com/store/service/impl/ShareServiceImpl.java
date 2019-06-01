package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.ShareMapper;
import com.store.model.Share;
import com.store.service.ShareService;

@Service
public class ShareServiceImpl implements ShareService {

	@Autowired
	private ShareMapper shareMapper;

	@Override
	public int insertSelective(Share share) {
		return shareMapper.insertSelective(share);
	}

	@Override
	public int deleteAll() {
		return shareMapper.deleteAll();
	}

	@Override
	public List<Share> selectByUid(String authUserId) {
		return shareMapper.selectByUid(authUserId);
	}

}