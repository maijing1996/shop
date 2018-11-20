package com.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaUserVoucherMapper;
import com.shop.model.po.IdeaUserVoucher;
import com.shop.service.common.BaseService;

@Service
public class IdeaUserVoucherService extends BaseService<IdeaUserVoucher, IdeaUserVoucherMapper> {

	@Override
	protected String getTableName() {
		return "idea_user_voucher";
	}

	/**
	 * 将未使用的代金券改为已使用
	 * @param voucherId
	 * @param userId
	 * @return
	 */
	public int useVoucher(Long voucherId, Long userId) {
		return 0;
	}
	
	/**
	 * 获取已领取未使用的代金券列表
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getVoucher(Long userId) {
		return mapper.getVoucher(userId);
	}
	
	/**
	 * 获取代金券名称，已领取未使用的代金券列表
	 * @param userId
	 * @return
	 */
	public List<Map<String, String>> getVoucherName(Long userId) {
		return mapper.getVoucherName(userId);
	}
	
	/**
	 * 获得我的代金券
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> listVoucher(Long userId, Integer type) {
		return mapper.listVoucher(userId, type);
	}
}
