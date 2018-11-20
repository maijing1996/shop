package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaTopicCategoryMapper;
import com.shop.model.dto.TopicCategory;
import com.shop.model.po.IdeaTopicCategory;
import com.shop.service.common.BaseService;

@Service
public class IdeaTopicCategoryService extends BaseService<IdeaTopicCategory, IdeaTopicCategoryMapper> {

	@Override
	protected String getTableName() {
		return "idea_topic_category";
	}
	
	/**
	 * 获得专题分类信息
	 * @param page
	 * @param size
	 * @param parent_id
	 * @return
	 */
	public PageInfo<TopicCategory> listInfo(Integer page, Integer size, Long parentId) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<TopicCategory> list = mapper.listInfo(parentId);
		PageInfo<TopicCategory> pageInfo = new PageInfo<TopicCategory>(list);
		return pageInfo;
	}
	
	/**
	 * 获得专题分类信息
	 * @param page
	 * @param size
	 * @param parent_id
	 * @return
	 */
	public PageInfo<TopicCategory> listAllInfo() {
		List<TopicCategory> list = mapper.listAllInfo();
		PageInfo<TopicCategory> pageInfo = new PageInfo<TopicCategory>(list);
		return pageInfo;
	}
}
