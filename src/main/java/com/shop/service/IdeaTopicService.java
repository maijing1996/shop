package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaTopicMapper;
import com.shop.model.dto.Topic;
import com.shop.model.po.IdeaTopic;
import com.shop.service.common.BaseService;

@Service
public class IdeaTopicService extends BaseService<IdeaTopic, IdeaTopicMapper> {

	@Override
	protected String getTableName() {
		return "idea_topic";
	}

	/**
	 * 查询专题信息
	 * @param page
	 * @param size
	 * @param title
	 * @return
	 */
	public PageInfo<Topic> listInfo(Integer page, Integer size, String title) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<Topic> list = mapper.listInfo(title);
		PageInfo<Topic> pageInfo = new PageInfo<Topic>(list);
		return pageInfo;
	}
	
	/**
	 * 获得小程序的展示专题
	 * @param size
	 * @return
	 */
	public List<IdeaTopic> getShowTopic(Integer size) {
		return mapper.getShowTopic(size);
	}
	
	/**
	 * 获得专题详情信息
	 * @param id
	 * @return
	 */
	public List<IdeaTopic> getTopicDetails(Long id) {
		return mapper.getTopicDetails(id);
	}
	
	/**
	 * 获得所有的专题信息
	 * @param page
	 * @param size
	 * @param catId
	 * @return
	 */
	public PageInfo<IdeaTopic> listInfoByCatId(Integer page, Integer size, Long catId) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<IdeaTopic> list = mapper.listInfoByCatId(catId);
		PageInfo<IdeaTopic> pageInfo = new PageInfo<IdeaTopic>(list);
		return pageInfo;
	}
}
