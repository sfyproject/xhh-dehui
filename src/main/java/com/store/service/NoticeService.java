package com.store.service;


import com.github.pagehelper.PageInfo;
import com.store.model.Notice;
import com.store.model.req.PageQuery;

public interface NoticeService {
	Notice selectByPrimaryKey(String string);
	
	PageInfo<Notice> page(PageQuery query);
	
	int insertSelective (Notice notice);
	
	int deleteByPrimaryKey(String noticeId);
	
	int updateByPrimaryKeySelective(Notice notice);
	
	Notice selectByNoticeId(String noticeId);
}