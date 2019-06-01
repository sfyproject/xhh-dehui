package com.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.model.Cart;
import com.store.model.resp.CartResp;

public interface CartMapper {

	int deleteByPrimaryKey(String cartId);

	int insert(Cart record);

	int insertSelective(Cart record);

	Cart selectByPrimaryKey(String cartId);

	int updateByPrimaryKeySelective(Cart record);

	int updateByPrimaryKey(Cart record);

	int deleteBatch(List<String> ids);

	int deleteByUid(String uid);

	int selectGoodsCount(String uid);

	List<CartResp> selectByUidAndGid(@Param(value = "uid") String uid, @Param(value = "gid") List<String> gid);

}