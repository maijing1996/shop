package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.po.IdeaDcenter;
import com.shop.util.MyMapper;

public interface IdeaDcenterMapper extends MyMapper<IdeaDcenter> {
	
	List<IdeaDcenter> listInfo();
	
	int isEmpty(@Param("name") String name);
	
	void deleteByUserId(@Param("userId") Long userId);
}
