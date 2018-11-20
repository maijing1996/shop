package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.po.IdeaUserAddress;
import com.shop.util.MyMapper;

public interface IdeaUserAddressMapper extends MyMapper<IdeaUserAddress> {

	List<IdeaUserAddress> getDefaultAddress(@Param("openId") String openId, @Param("type") Integer type);
}
