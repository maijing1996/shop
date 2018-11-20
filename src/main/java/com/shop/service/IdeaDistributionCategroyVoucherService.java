package com.shop.service;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaDistributionCategroyVoucherMapper;
import com.shop.model.po.IdeaDistributionCategroyVoucher;
import com.shop.service.common.BaseService;

@Service
public class IdeaDistributionCategroyVoucherService extends BaseService<IdeaDistributionCategroyVoucher, IdeaDistributionCategroyVoucherMapper> {

	@Override
	protected String getTableName() {
		return "idea_distribution_categroy_voucher";
	}

	/**
	 * 删除
	 * @param distributionCategroyVoucher
	 * @return
	 */
	public void deleteByParentId(long parentId) {
		mapper.deleteByParentId(parentId);
	}
}
