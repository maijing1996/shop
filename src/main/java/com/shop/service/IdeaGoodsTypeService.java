package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaGoodsTypeMapper;
import com.shop.model.dto.OptionBox;
import com.shop.model.po.IdeaGoodsType;
import com.shop.service.common.BaseService;

@Service
public class IdeaGoodsTypeService extends BaseService<IdeaGoodsType, IdeaGoodsTypeMapper> {

	@Override
	protected String getTableName() {
		return "idea_goods_type";
	}

	/**
	 * 获取商品模型信息
	 * @param page
	 * @param size
	 * @param title
	 * @return
	 */
	public PageInfo<IdeaGoodsType> listInfo(Integer page, Integer size, String title) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<IdeaGoodsType> list = mapper.listInfo(title);
		PageInfo<IdeaGoodsType> pageInfo = new PageInfo<IdeaGoodsType>(list);
		return pageInfo;
	}
	
	/**
	 * 获得选项框数值
	 * @return
	 */
	public List<OptionBox> listOptionBox() {
		return mapper.listOptionBox();
	}
}
