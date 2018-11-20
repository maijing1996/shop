package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaGoodsSpecItemMapper;
import com.shop.model.po.IdeaGoodsSpecItem;
import com.shop.service.common.BaseService;

@Service
public class IdeaGoodsSpecItemService extends BaseService<IdeaGoodsSpecItem, IdeaGoodsSpecItemMapper> {

	@Override
	protected String getTableName() {
		return "idea_goods_spec_item";
	}

	public List<IdeaGoodsSpecItem> getInfo(Integer type, String key, String key2) {
		return mapper.getInfo(type, key, key2);
	}
	
	public IdeaGoodsSpecItem getAllInfo(Long id) {
		return mapper.getAllInfo(id);
	}
}
