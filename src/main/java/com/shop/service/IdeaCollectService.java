package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaCollectMapper;
import com.shop.model.po.IdeaCollect;
import com.shop.service.common.BaseService;

@Service
public class IdeaCollectService extends BaseService<IdeaCollect, IdeaCollectMapper> {

	@Override
	protected String getTableName() {
		return "idea_collect";
	}

}
