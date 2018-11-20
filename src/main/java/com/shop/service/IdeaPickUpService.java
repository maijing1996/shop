package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaPickUpMapper;
import com.shop.model.po.IdeaPickUp;
import com.shop.service.common.BaseService;

@Service
public class IdeaPickUpService extends BaseService<IdeaPickUp, IdeaPickUpMapper> {

	@Override
	protected String getTableName() {
		return "idea_pick_up";
	}

	/**
	 * 获取自提点信息
	 * @return
	 */
	public IdeaPickUp getPicUp(){
		return mapper.getPicUp();
	}
}
