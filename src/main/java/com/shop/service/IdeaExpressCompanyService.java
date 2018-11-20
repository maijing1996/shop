package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaExpressCompanyMapper;
import com.shop.model.po.IdeaExpressCompany;
import com.shop.service.common.BaseService;

@Service
public class IdeaExpressCompanyService extends BaseService<IdeaExpressCompany, IdeaExpressCompanyMapper> {

	@Override
	protected String getTableName() {
		return null;
	}

	/**
	 * 通过快递名称获得快递公司的编码
	 * @param expressTitle
	 * @return
	 */
	public String getCodeByName(String expressTitle) {
		return mapper.getCodeByName(expressTitle);
	}
}
