package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaPersonCategoryMapper;
import com.shop.model.dto.PersonCategory;
import com.shop.model.po.IdeaGoods;
import com.shop.model.po.IdeaPersonCategory;
import com.shop.service.common.BaseService;

@Service
public class IdeaPersonCategoryService extends BaseService<IdeaPersonCategory, IdeaPersonCategoryMapper> {

	@Override
	protected String getTableName() {
		return "idea_person_category";
	}

	
	/**
	 * 获取人群分类信息
	 * @param page
	 * @param size
	 * @return
	 */
	public PageInfo<PersonCategory> listInfo(Integer page, Integer size, Long id){
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<PersonCategory> list = mapper.listInfo(id);
		PageInfo<PersonCategory> pageInfo = new PageInfo<PersonCategory>(list);
		return pageInfo;
	}
	
	/**
	 * 获取所有的人群分类信息
	 * @param page
	 * @param size
	 * @return
	 */
	public PageInfo<PersonCategory> listAllInfo(){
		PageHelper.startPage(1, 1000);
		List<PersonCategory> list = mapper.listAllInfo();
		PageInfo<PersonCategory> pageInfo = new PageInfo<PersonCategory>(list);
		return pageInfo;
	}
	
	/**
	 * 通过id 获得商品信息
	 * @param page
	 * @param size
	 * @param id
	 * @return
	 */
	public PageInfo<IdeaGoods> getGoodsById(Integer page, Integer size, Long id) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<IdeaGoods> list = mapper.getGoodsById(id);
		PageInfo<IdeaGoods> pageInfo = new PageInfo<IdeaGoods>(list);
		return pageInfo;
	}
	
	/**
	 * 模糊查询
	 * @param page
	 * @param size
	 * @param keyword
	 * @return
	 */
	public PageInfo<IdeaGoods> getGoodsByKeys(Integer page, Integer size, String keyword) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<IdeaGoods> list = mapper.getGoodsByKeys(keyword);
		PageInfo<IdeaGoods> pageInfo = new PageInfo<IdeaGoods>(list);
		return pageInfo;
	}
}
