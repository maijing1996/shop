package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaUserAddressMapper;
import com.shop.model.po.IdeaUserAddress;
import com.shop.service.common.BaseService;

@Service
public class IdeaUserAddressService extends BaseService<IdeaUserAddress, IdeaUserAddressMapper> {

	@Override
	protected String getTableName() {
		return "idea_user_address";
	}
	
	/**
	 * 获取默认地址
	 * @param openId
	 * @param index
	 * @return
	 */
	public List<IdeaUserAddress> getDefaultAddress(String openId, Integer type) {
		return mapper.getDefaultAddress(openId, type);
	}
}
