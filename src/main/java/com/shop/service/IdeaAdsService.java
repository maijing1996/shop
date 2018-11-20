package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaAdsMapper;
import com.shop.model.dto.Ads;
import com.shop.model.po.IdeaAds;
import com.shop.service.common.BaseService;

@Service
public class IdeaAdsService extends BaseService<IdeaAds, IdeaAdsMapper> {

	@Override
	protected String getTableName() {
		return "idea_ads";
	}

	/**
	 * 获得广告短片
	 * @param page
	 * @param size
	 * @return
	 */
	public PageInfo<Ads> listInfo(Integer page, Integer size) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<Ads> list = mapper.listInfo();
		PageInfo<Ads> pageInfo = new PageInfo<Ads>(list);
		return pageInfo;
	}
	
	/**
	 * 获取幻灯片信息
	 * @param size
	 * @param type
	 * @return
	 */
	public List<IdeaAds> getShowAds(Integer size, Integer type){
		return mapper.getShowAds(size, type);
	}
}
