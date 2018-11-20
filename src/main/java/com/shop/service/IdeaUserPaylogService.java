package com.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaUserPaylogMapper;
import com.shop.model.dto.Paylog;
import com.shop.model.dto.TradeDetails;
import com.shop.model.po.IdeaUserPaylog;
import com.shop.service.common.BaseService;

@Service
public class IdeaUserPaylogService extends BaseService<IdeaUserPaylog, IdeaUserPaylogMapper> {

	@Override
	protected String getTableName() {
		return "idea_user_paylog";
	}
	
	/**
	 * 获取资金明细信息和积分明细信息的接口
	 * @param page
	 * @param size
	 * @param nickName
	 * @return
	 */
	public PageInfo<TradeDetails> listInfo(Integer page, Integer size, int type1, int type2, String nickName) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<TradeDetails> list = mapper.listInfo(type1, type2, nickName);
		PageInfo<TradeDetails> pageInfo = new PageInfo<TradeDetails>(list);
		return pageInfo;
	}
	
	/**
	 * 获得用户提现信息
	 * @param page
	 * @param size
	 * @param nickName
	 * @return
	 */
	public PageInfo<Paylog> withdrawal(Integer page, Integer size, String nickName) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<Paylog> list = mapper.withdrawal(nickName);
		PageInfo<Paylog> pageInfo = new PageInfo<Paylog>(list);
		return pageInfo;
	}
	
	/**
	 * 获取我的积分列表
	 * @param userId
	 * @return
	 */
	public List<IdeaUserPaylog> getInfoByOpenId(Long userId) {
		return mapper.getInfoByOpenId(userId, 3, 4);
	}
	
	/**
	 * 获得我的支付记录信息
	 * @param userId
	 * @return
	 */
	public List<IdeaUserPaylog> getWallectByOpenId(Long userId) {
		return mapper.getInfoByOpenId(userId, 1, 2);
	}
	
	/**
	 * 获得分销总订单
	 * @param userId
	 * @return
	 */
	public Integer getCount(Long userId) {
		return mapper.getCount(userId);
	}
	
	/**
	 * 获得支付记录信息
	 * @param page
	 * @param size
	 * @param userId
	 * @return
	 */
	public PageInfo<Map<String, Object>> getPayInfo(Integer page, Integer size, String type, Long userId) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<Map<String, Object>> list = mapper.getPayInfo(type, userId);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		return pageInfo;
	}
	
	/**
	 * 获得指定类型的总金额
	 * @return
	 */
	public Integer getAllByUserId(long userId, int payType, int type) {
		return mapper.getAllByUserId(userId, payType, type);
	}
	/**
	 * 扣除积分记录改为拒绝
	 * @param userId
	 * @param orderId
	 */
	/*public void updateAll(Long userId, Long orderId) {
		mapper.updateAll(userId, orderId);
	}*/
}
