package com.shop.mapper;

import java.util.List;

import com.shop.model.dto.WxReply;
import com.shop.model.po.IdeaWxReply;
import com.shop.util.MyMapper;

public interface IdeaWxReplyMapper extends MyMapper<IdeaWxReply> {

	List<WxReply> listInfo();
}
