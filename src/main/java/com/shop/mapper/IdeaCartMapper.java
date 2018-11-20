package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.wechat.Cart;
import com.shop.model.po.IdeaCart;
import com.shop.util.MyMapper;

public interface IdeaCartMapper extends MyMapper<IdeaCart> {

	List<Cart> getCartDetails(@Param("openId") String openId);

	IdeaCart getCart(@Param("openId") String openId, @Param("goodsId") Long goodsId, @Param("specKey") String specKey, @Param("keyName") String keyName);
}
