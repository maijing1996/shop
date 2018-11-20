package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaWxMaterialMapper;
import com.shop.model.po.IdeaWxMaterial;
import com.shop.service.common.BaseService;

@Service
public class IdeaWxMaterialService extends BaseService<IdeaWxMaterial, IdeaWxMaterialMapper> {

	@Override
	protected String getTableName() {
		return "idea_wx_material";
	}

}
