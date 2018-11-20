package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.po.IdeaAdminLog;
import com.shop.util.MyMapper;

public interface IdeaAdminLogMapper extends MyMapper<IdeaAdminLog> {
	
	List<IdeaAdminLog> listInfo(@Param("state") Integer state);
}
