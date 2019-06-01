package com.store.service;

import com.store.model.Card;

public interface CardService {

	int insertSelective(Card card);

	Card selectByPrimaryKey(String cardId);

	int updateByPrimaryKeySelective(Card card);

}