package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaShopPayMapper;
import com.shop.model.po.IdeaShopPay;
import com.shop.service.common.BaseService;

@Service
public class IdeaShopPayService extends BaseService<IdeaShopPay, IdeaShopPayMapper> {

	@Override
	protected String getTableName() {
		return "idea_shop_pay";
	}

	/**
	 * 获得交易设置
	 * @return
	 */
	public IdeaShopPay getInfo() {
		return mapper.getInfo();
	}
}
