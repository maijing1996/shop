package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.po.IdeaCashGoods;
import com.shop.util.MyMapper;

public interface IdeaCashGoodsMapper extends MyMapper<IdeaCashGoods> {

	List<IdeaCashGoods> getShowInfo(@Param("size")Integer size);
}
