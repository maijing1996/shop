package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaDistributionCommissionMapper;
import com.shop.model.po.IdeaDistributionCommission;
import com.shop.service.common.BaseService;

@Service
public class IdeaDistributionCommissionService extends BaseService<IdeaDistributionCommission, IdeaDistributionCommissionMapper> {

	@Override
	protected String getTableName() {
		return null;
	}

}
