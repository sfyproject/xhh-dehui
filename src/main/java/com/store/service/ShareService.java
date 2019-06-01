package com.store.service;

import java.util.List;

import com.store.model.Share;

public interface ShareService {

	int insertSelective(Share share);

	int deleteAll();

	List<Share> selectByUid(String authUserId);

}