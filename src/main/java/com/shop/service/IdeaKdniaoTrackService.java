package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaKdniaoTrackMapper;
import com.shop.model.po.IdeaKdniaoTrack;
import com.shop.service.common.BaseService;

@Service
public class IdeaKdniaoTrackService extends BaseService<IdeaKdniaoTrack, IdeaKdniaoTrackMapper> {

	@Override
	protected String getTableName() {
		return null;
	}

}
