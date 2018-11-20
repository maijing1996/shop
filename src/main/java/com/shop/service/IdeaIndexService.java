package com.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaIndexMapper;
import com.shop.model.po.IdeaIndex;
import com.shop.service.common.BaseService;

@Service
public class IdeaIndexService extends BaseService<IdeaIndex, IdeaIndexMapper> {

	@Override
	protected String getTableName() {
		return "idea_index";
	}
	
	/**
	 * 获得模块信息
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> listInfoByType(Integer type) {
		return mapper.listInfoByType(type);
	}
}
