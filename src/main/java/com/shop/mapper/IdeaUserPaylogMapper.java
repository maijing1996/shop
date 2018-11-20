package com.shop.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.Paylog;
import com.shop.model.dto.TradeDetails;
import com.shop.model.po.IdeaUserPaylog;
import com.shop.util.MyMapper;

public interface IdeaUserPaylogMapper extends MyMapper<IdeaUserPaylog> {
	 
	List<Paylog> withdrawal(@Param("nickName") String nickName);
	
	List<TradeDetails> listInfo(@Param("type1") int type1, @Param("type2") int type2, @Param("nickName") String nickName);
	
	List<IdeaUserPaylog> getInfoByOpenId(@Param("userId") Long userId, @Param("type1") int type1, @Param("type2") int type2);
	
	Integer getCount(@Param("userId") Long userId);
	
	List<Map<String, Object>> getPayInfo(@Param("type") String type, @Param("userId") Long userId);
	
	Integer getAllByUserId(@Param("userId") long userId, @Param("payType") int payType, @Param("type") int type);
	
	//int updateAll(@Param("userId") Long userId, @Param("orderId") Long orderId);
}
