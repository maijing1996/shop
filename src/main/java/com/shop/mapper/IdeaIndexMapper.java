package com.shop.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shop.model.po.IdeaIndex;
import com.shop.util.MyMapper;

public interface IdeaIndexMapper extends MyMapper<IdeaIndex> {

	List<Map<String, Object>> listInfoByType(@Param("type") Integer type);
	
	List<IdeaIndex> getInfo(@Param("type") Integer type);
}
