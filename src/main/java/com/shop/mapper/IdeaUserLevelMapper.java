package com.shop.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shop.model.po.IdeaUserLevel;
import com.shop.util.MyMapper;

public interface IdeaUserLevelMapper extends MyMapper<IdeaUserLevel> {

	List<IdeaUserLevel> listInfo(@Param("nickName") String nickName);
	
	Long getLevelId(@Param("score") int score);
	
	Map<String, Object> getLevelRebate(@Param("openId") String openId);
}
