package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaWxReplyMapper;
import com.shop.model.dto.WxReply;
import com.shop.model.po.IdeaWxReply;
import com.shop.service.common.BaseService;

@Service
public class IdeaWxReplyService extends BaseService<IdeaWxReply, IdeaWxReplyMapper> {

	@Override
	protected String getTableName() {
		return "idea_wx_reply";
	}

	/**
	 * 获得微信关键字回复列表信息
	 * @param page
	 * @param size
	 * @return
	 */
	public PageInfo<WxReply> listInfo(Integer page, Integer size) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<WxReply> list = mapper.listInfo();
		PageInfo<WxReply> pageInfo = new PageInfo<WxReply>(list);
		return pageInfo;
	}
}
