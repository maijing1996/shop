package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaCashGoodsMapper;
import com.shop.model.po.IdeaCashGoods;
import com.shop.service.common.BaseService;

@Service
public class IdeaCashGoodsService extends BaseService<IdeaCashGoods, IdeaCashGoodsMapper> {

	@Override
	protected String getTableName() {
		return null;
	}

	/**
	 * 获得最新的兑换信息，在前端展示作用
	 * @param page
	 * @param size
	 * @return
	 */
	public List<IdeaCashGoods> getShowInfo(Integer size) {
		return mapper.getShowInfo(size);
	}
}
