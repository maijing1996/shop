package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaShopSitesMapper;
import com.shop.model.po.IdeaShopSites;
import com.shop.service.common.BaseService;

@Service
public class IdeaShopSitesService extends BaseService<IdeaShopSites, IdeaShopSitesMapper> {

	@Override
	protected String getTableName() {
		return "idea_shop_sites";
	}

	/**
	 * 获得多对象信息
	 * @return
	 */
	public IdeaShopSites getInfo() {
		return mapper.getInfo();
	}
}
