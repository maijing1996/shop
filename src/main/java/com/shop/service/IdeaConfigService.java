package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaConfigMapper;
import com.shop.model.po.IdeaConfig;
import com.shop.service.common.BaseService;

@Service
public class IdeaConfigService extends BaseService<IdeaConfig, IdeaConfigMapper> {

	@Override
	protected String getTableName() {
		return "idea_config";
	}

	/**
	 * 获得所有地址信息
	 * 微信端授权登录有调用
	 * @return
	 */
	public List<IdeaConfig> getAllInfo() {
		return mapper.getAllInfo();
	}
}
