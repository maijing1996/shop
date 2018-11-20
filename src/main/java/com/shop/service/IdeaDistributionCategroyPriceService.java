package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaDistributionCategroyPriceMapper;
import com.shop.model.po.IdeaDistributionCategroyPrice;
import com.shop.service.common.BaseService;

@Service
public class IdeaDistributionCategroyPriceService extends BaseService<IdeaDistributionCategroyPrice, IdeaDistributionCategroyPriceMapper> {

	@Override
	protected String getTableName() {
		return null;
	}

}
