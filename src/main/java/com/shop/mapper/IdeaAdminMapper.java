package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.Manager;
import com.shop.model.dto.RedisUser;
import com.shop.model.po.IdeaAdmin;
import com.shop.util.MyMapper;

public interface IdeaAdminMapper extends MyMapper<IdeaAdmin>{

	RedisUser login(@Param("account") String account, @Param("passwd") String passwd);
	
	List<Manager> listInfo();
}