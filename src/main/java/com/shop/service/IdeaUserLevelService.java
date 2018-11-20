package com.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaUserLevelMapper;
import com.shop.model.po.IdeaUserLevel;
import com.shop.service.common.BaseService;

@Service
public class IdeaUserLevelService extends BaseService<IdeaUserLevel, IdeaUserLevelMapper> {

	@Override
	protected String getTableName() {
		return "idea_user_level";
	}

	/**
	 * 获取会员等级信息
	 * @param nickName
	 * @return
	 */
	public List<IdeaUserLevel> listInfo(String nickName) {
		return mapper.listInfo(nickName);
	}
	
	/**
	 * 通过积分获得会员等级
	 * @param score
	 * @return
	 */
	public Long getLevelId(int score) {
		return mapper.getLevelId(score);
	}
	
	/**
	 * 获得会员等级的折扣价
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getLevelRebate(String openId) {
		return mapper.getLevelRebate(openId);
	}
}
