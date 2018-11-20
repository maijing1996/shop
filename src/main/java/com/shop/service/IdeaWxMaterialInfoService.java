package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaWxMaterialInfoMapper;
import com.shop.model.po.IdeaWxMaterialInfo;
import com.shop.service.common.BaseService;

@Service
public class IdeaWxMaterialInfoService extends BaseService<IdeaWxMaterialInfo, IdeaWxMaterialInfoMapper> {

	@Override
	protected String getTableName() {
		return "idea_wx_material_info";
	}

}
