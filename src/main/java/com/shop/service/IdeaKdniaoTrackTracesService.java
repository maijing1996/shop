package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaKdniaoTrackTracesMapper;
import com.shop.model.po.IdeaKdniaoTrackTraces;
import com.shop.service.common.BaseService;

@Service
public class IdeaKdniaoTrackTracesService extends BaseService<IdeaKdniaoTrackTraces, IdeaKdniaoTrackTracesMapper> {

	@Override
	protected String getTableName() {
		return null;
	}

}
