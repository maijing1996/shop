package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaFlashSaleMapper;
import com.shop.model.dto.FlashSale;
import com.shop.model.po.IdeaFlashSale;
import com.shop.service.common.BaseService;

@Service
public class IdeaFlashSaleService extends BaseService<IdeaFlashSale, IdeaFlashSaleMapper> {

	@Override
	protected String getTableName() {
		return "idea_flash_sale";
	}

	/**
	 * 获取限时抢购的信息
	 * @param page
	 * @param size
	 * @param title
	 * @return
	 */
	public PageInfo<FlashSale> listInfo(Integer page, Integer size, String title) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<FlashSale> list = mapper.listInfo(title);
		PageInfo<FlashSale> pageInfo = new PageInfo<FlashSale>(list);
		return pageInfo;
	}
}
