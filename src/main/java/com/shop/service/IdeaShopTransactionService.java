package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaShopTransactionMapper;
import com.shop.model.po.IdeaShopTransaction;
import com.shop.service.common.BaseService;

@Service
public class IdeaShopTransactionService extends BaseService<IdeaShopTransaction, IdeaShopTransactionMapper> {

	@Override
	protected String getTableName() {
		return "idea_shop_transaction";
	}

	/**
	 * 获得交易设置
	 * @return
	 */
	public IdeaShopTransaction getInfo() {
		return mapper.getInfo();
	}
}
