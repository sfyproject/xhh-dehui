package com.store.service;

import java.util.List;

import com.store.model.Cart;
import com.store.model.resp.CartResp;

public interface CartService {

	int insertSelective(Cart cart);

	Cart selectByPrimaryKey(String cartId);

	int deleteBatch(List<String> ids);

	int deleteByUid(String uid);

	int selectGoodsCount(String uid);

	List<CartResp> selectByUidAndGid(String uid, List<String> gid);

	int updateByPrimaryKeySelective(Cart cartGood);

}