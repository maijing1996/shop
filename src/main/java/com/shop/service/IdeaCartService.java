package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.mapper.IdeaCartMapper;
import com.shop.model.dto.wechat.Cart;
import com.shop.model.po.IdeaCart;
import com.shop.service.common.BaseService;

@Service
public class IdeaCartService extends BaseService<IdeaCart, IdeaCartMapper> {

	@Override
	protected String getTableName() {
		return "idea_cart";
	}
	
	/**
	 * 获取购物车列表
	 * @param openId
	 * @return
	 */
	public List<Cart> getCartDetails(String openId){
		return mapper.getCartDetails(openId);
	}

	public IdeaCart getCart(String openId, Long goodsId, String specKey, String keyName) {
		return mapper.getCart(openId, goodsId,specKey,keyName);
	}
}
