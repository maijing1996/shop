package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaBrandMapper;
import com.shop.model.dto.Brand;
import com.shop.model.po.IdeaBrand;
import com.shop.service.common.BaseService;

@Service
public class IdeaBrandService extends BaseService<IdeaBrand, IdeaBrandMapper> {

	@Override
	protected String getTableName() {
		return "idea_brand";
	}

	/**
	 * 获取品牌信息
	 * @param page
	 * @param size
	 * @param title
	 * @return
	 */
	public PageInfo<Brand> listInfo(Integer page, Integer size, String title) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<Brand> list = mapper.listInfo(title);
		PageInfo<Brand> pageInfo = new PageInfo<Brand>(list);
		return pageInfo;
	}
	
	/**
	 * 查询排序数值是否存在
	 * @param number
	 * @return
	 */
	public boolean isExists (Long id, int number) {
		int ic = mapper.isExists(id, number);
		if(ic > 0) {
			return true;
		}
		return false;
	}
}
