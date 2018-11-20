package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaShopMessageMapper;
import com.shop.model.po.IdeaShopMessage;
import com.shop.service.common.BaseService;

@Service
public class IdeaShopMessageService extends BaseService<IdeaShopMessage, IdeaShopMessageMapper> {

	@Override
	protected String getTableName() {
		return null;
	}

}
