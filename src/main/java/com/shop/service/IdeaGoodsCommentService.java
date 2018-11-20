package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaGoodsCommentMapper;
import com.shop.model.dto.Comment;
import com.shop.model.po.IdeaGoodsComment;
import com.shop.service.common.BaseService;

@Service
public class IdeaGoodsCommentService extends BaseService<IdeaGoodsComment, IdeaGoodsCommentMapper> {

	@Override
	protected String getTableName() {
		return "idea_goods_comment";
	}

	/**
	 * 查询商品评论的接口
	 * @param uid
	 * @return
	 */
	public PageInfo<Comment> listInfo(Integer page, Integer size, String uid){
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<Comment> list = mapper.listInfo(uid);
		PageInfo<Comment> pageInfo = new PageInfo<Comment>(list);
		return pageInfo;
		
	}
}
