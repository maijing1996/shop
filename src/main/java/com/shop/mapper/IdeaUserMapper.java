package com.shop.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.DistributionUser;
import com.shop.model.dto.MemberRanking;
import com.shop.model.dto.User;
import com.shop.model.dto.UserStatis;
import com.shop.model.po.IdeaUser;
import com.shop.util.MyMapper;

public interface IdeaUserMapper extends MyMapper<IdeaUser> {

	List<User> listInfo(@Param("nickName") String nickName);
	
	List<DistributionUser> listDistributionUser(@Param("uid") String uid, @Param("id") Long id);

	List<MemberRanking> getRanking(@Param("nickname") String nickname);
	
	IdeaUser isExist(@Param("unionId") String unionId, @Param("openId") String openId);
	
	IdeaUser getUser(@Param("openId") String openId);
	
	List<UserStatis> statistic(@Param("startTime")Long start, @Param("endTime")Long end);
	
	int updateAllNote(@Param("dcId") Long dcId, @Param("name") String name);
	
	int cancelBind(@Param("id") Long id);
	
	List<IdeaUser> getUserByIds(@Param("ids") String ids);
	
	Map<String, Object> getMerchant(@Param("id") Long id);
}
