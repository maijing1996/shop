package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaWxConfigMapper;
import com.shop.model.po.IdeaWxConfig;
import com.shop.service.common.BaseService;

@Service
public class IdeaWxConfigService extends BaseService<IdeaWxConfig, IdeaWxConfigMapper> {

	@Override
	protected String getTableName() {
		return "idea_wx_config";
	}

	/**
	 * 获得微信配置信息
	 * @return
	 */
	public IdeaWxConfig getInfo(){
		return mapper.getInfo();
	}
}
