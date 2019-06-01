package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.CartMapper;
import com.store.model.Cart;
import com.store.model.resp.CartResp;
import com.store.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;

	@Override
	public int insertSelective(Cart cart) {
		return cartMapper.insertSelective(cart);
	}

	@Override
	public Cart selectByPrimaryKey(String cartId) {
		return cartMapper.selectByPrimaryKey(cartId);
	}

	@Override
	public int deleteBatch(List<String> ids) {
		return cartMapper.deleteBatch(ids);
	}

	@Override
	public int deleteByUid(String uid) {
		return cartMapper.deleteByUid(uid);
	}

	@Override
	public int selectGoodsCount(String uid) {
		return cartMapper.selectGoodsCount(uid);
	}

	@Override
	public List<CartResp> selectByUidAndGid(String uid, List<String> gid) {
		return cartMapper.selectByUidAndGid(uid, gid);
	}

	@Override
	public int updateByPrimaryKeySelective(Cart cartGood) {
		return cartMapper.updateByPrimaryKeySelective(cartGood);
	}

}