package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaGoodsCategoryMapper;
import com.shop.model.dto.Category;
import com.shop.model.po.IdeaGoods;
import com.shop.model.po.IdeaGoodsCategory;
import com.shop.service.common.BaseService;

@Service
public class IdeaGoodsCategoryService extends BaseService<IdeaGoodsCategory, IdeaGoodsCategoryMapper> {

	@Override
	protected String getTableName() {
		return "idea_goods_category";
	}

	/**
	 * 获得商品类型信息
	 * @param page
	 * @param size
	 * @return
	 */
	public PageInfo<Category> listInfo(Integer page, Integer size, Long parent_id) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 30);
		}
		
		List<Category> list = mapper.listInfo(parent_id);
		PageInfo<Category> pageInfo = new PageInfo<Category>(list);
		return pageInfo;
	}
	
	/**
	 * 获得所有商品类型信息
	 * @return
	 */
	public PageInfo<Category> listAllInfo() {
		PageHelper.startPage(1, 1000);
		
		List<Category> list = mapper.listAllInfo();
		PageInfo<Category> pageInfo = new PageInfo<Category>(list);
		return pageInfo;
	}
	
	/**
	 * 排序的数值是否存在
	 * 
	 * @param sequence
	 * @return
	 */
	public boolean isExists(Integer sequence) {
		int ic = mapper.isExists(sequence);
		if(ic > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 通过id 获得数据
	 * @param page
	 * @param size
	 * @param parent_id
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
}
