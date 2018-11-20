package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.Topic;
import com.shop.model.po.IdeaTopic;
import com.shop.util.MyMapper;

public interface IdeaTopicMapper extends MyMapper<IdeaTopic> {

	List<Topic> listInfo(@Param("title") String title);
	
	List<IdeaTopic> getShowTopic(@Param("size") Integer size);
	
	List<IdeaTopic> getTopicDetails(@Param("id") Long id);
	
	List<IdeaTopic> listInfoByCatId(@Param("catId") Long catId);
}
