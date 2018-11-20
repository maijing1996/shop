package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaVoucherMapper;
import com.shop.model.po.IdeaVoucher;
import com.shop.service.common.BaseService;

@Service
public class IdeaVoucherService extends BaseService<IdeaVoucher, IdeaVoucherMapper> {

	@Override
	protected String getTableName() {
		return "idea_voucher";
	}

}
