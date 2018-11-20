package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.Comment;
import com.shop.model.po.IdeaGoodsComment;
import com.shop.util.MyMapper;

public interface IdeaGoodsCommentMapper extends MyMapper<IdeaGoodsComment> {
	
	List<Comment> listInfo(@Param("uid") String uid);
}
