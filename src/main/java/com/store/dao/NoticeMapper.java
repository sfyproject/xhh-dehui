package com.store.dao;

import java.util.List;

import com.store.model.Notice;

public interface NoticeMapper {

	int insert(Notice record);

	Notice selectByPrimaryKey(String noticeId);

	int updateByPrimaryKeyWithBLOBs(Notice record);

	int updateByPrimaryKey(Notice record);
	
	/**
	 * 查询所有的公告
	 * @return
	 */
	List<Notice> selectAll();
	
	/**
	 * 删除一条公告
	 * @param noticeId
	 * @return
	 */
	int deleteByPrimaryKey(String noticeId);
	
	/**
	 * 添加一条公告
	 * @param record
	 * @return
	 */
	int insertSelective(Notice record);
	
	/**
	 * 修改一条公告
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(Notice record);
	
	/**
	 * 根据noticeId查询一条公告
	 * @param noticeId
	 * @return
	 */
	Notice selectByNoticeId(String noticeId);
	
}