package com.store.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.NoticeMapper;
import com.store.model.Notice;
import com.store.model.req.PageQuery;
import com.store.service.NoticeService;
import com.store.utils.Utils;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;
	
	@Value("${img.path.prefix}")
	private String imgPathPrefix;

	@Value("${url.img.path.prefix}")
	private String urlImgPathPrefix;

	@Override
	public Notice selectByPrimaryKey(String noticeId) {
		return noticeMapper.selectByPrimaryKey(noticeId);
	}

	/**
	 * 查询所有的公告
	 */
	@Override
	public PageInfo<Notice> page(PageQuery query) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		List<Notice> list = noticeMapper.selectAll();
		return new PageInfo<>(list, query.getLimit());
	}
	
	/**
	 * 添加一条公告
	 */
	@Override
	public int insertSelective(Notice notice) {
		return noticeMapper.insertSelective(notice);
	}
	
	/**
	 * 删除一条公告
	 */
	@Override
	@Transactional
	public int deleteByPrimaryKey(String noticeId) {
		Notice notice = noticeMapper.selectByNoticeId(noticeId);
		if(notice.getNoticeImage()!=null && notice.getNoticeImage()!="" ) {
			String name = notice.getNoticeImage();
			String path = imgPathPrefix;
			String PathPrefix=urlImgPathPrefix;
			String str=path+name.split(PathPrefix)[1];
			Utils.DeleteFolder(str);
		}
		return noticeMapper.deleteByPrimaryKey(noticeId);
	}

	/**
	 * 修改一条公告
	 */
	@Override
	public int updateByPrimaryKeySelective(Notice notice) {
		return noticeMapper.updateByPrimaryKeySelective(notice);
	}

	/**
	 * 根据noticeId查询一条公告内容
	 */
	@Override
	public Notice selectByNoticeId(String noticeId) {
		return noticeMapper.selectByNoticeId(noticeId);
	}
}