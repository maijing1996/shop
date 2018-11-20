package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaShopDispatchingMapper;
import com.shop.model.po.IdeaShopDispatching;
import com.shop.service.common.BaseService;

@Service
public class IdeaShopDispatchingService extends BaseService<IdeaShopDispatching, IdeaShopDispatchingMapper> {

	@Override
	protected String getTableName() {
		return "idea_shop_dispatching";
	}

	/**
	 * 获得配送信息
	 * @return
	 */
	public IdeaShopDispatching getInfo() {
		return mapper.getInfo();
	}
}
