package com.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.CardMapper;
import com.store.model.Card;
import com.store.service.CardService;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private CardMapper cardMapper;

	@Override
	public int insertSelective(Card card) {
		return cardMapper.insertSelective(card);
	}

	@Override
	public Card selectByPrimaryKey(String cardId) {
		return cardMapper.selectByPrimaryKey(cardId);
	}

	@Override
	public int updateByPrimaryKeySelective(Card card) {
		return cardMapper.updateByPrimaryKeySelective(card);
	}

}