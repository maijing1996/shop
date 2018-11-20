package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.Ads;
import com.shop.model.po.IdeaAds;
import com.shop.util.MyMapper;

public interface IdeaAdsMapper extends MyMapper<IdeaAds> {

	List<Ads> listInfo();
	
	List<IdeaAds> getShowAds(@Param("size") Integer size, @Param("type") Integer type);
}
