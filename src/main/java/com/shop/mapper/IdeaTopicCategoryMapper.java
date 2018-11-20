package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.TopicCategory;
import com.shop.model.po.IdeaTopicCategory;
import com.shop.util.MyMapper;

public interface IdeaTopicCategoryMapper extends MyMapper<IdeaTopicCategory> {

	List<TopicCategory> listInfo(@Param("parentId") Long parentId);
	
	List<TopicCategory> listAllInfo();
}
