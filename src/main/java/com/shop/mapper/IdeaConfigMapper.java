package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.po.IdeaConfig;
import com.shop.util.MyMapper;

public interface IdeaConfigMapper extends MyMapper<IdeaConfig> {

	List<IdeaConfig> getAllInfo();
	
	IdeaConfig getInfoById(@Param("id") Long id);
}
