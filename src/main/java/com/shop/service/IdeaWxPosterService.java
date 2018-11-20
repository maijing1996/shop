package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaWxPosterMapper;
import com.shop.model.po.IdeaWxPoster;
import com.shop.service.common.BaseService;

@Service
public class IdeaWxPosterService extends BaseService<IdeaWxPoster, IdeaWxPosterMapper> {

	@Override
	protected String getTableName() {
		return "idea_wx_poster";
	}

}
