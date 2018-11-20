package com.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaGoodsSpecPriceMapper;
import com.shop.model.po.IdeaGoodsSpecPrice;
import com.shop.service.common.BaseService;

@Service
public class IdeaGoodsSpecPriceService extends BaseService<IdeaGoodsSpecPrice, IdeaGoodsSpecPriceMapper> {

	@Override
	protected String getTableName() {
		return "idea_goods_spec_price";
	}

	/**
	 * 获得规格信息
	 * @param goodsId
	 * @return
	 */
	public List<IdeaGoodsSpecPrice> getAllInfo(Long goodsId) {
		return mapper.getAllInfo(goodsId);
	}
	
	/**
	 * 获得规格信息
	 * @param goodsId
	 * @return
	 */
	public List<Map<String, Object>> getInfo(Long goodsId) {
		return mapper.getInfo(goodsId);
	}
	
	/**
	 * 获得信息
	 * @param goodsId
	 * @return
	 */
	public IdeaGoodsSpecPrice getInfoById(Long goodsId, String keys) {
		return mapper.getInfoById(goodsId, keys);
	}
	
	/**
	 * 通过Id 所有信息
	 */
	public IdeaGoodsSpecPrice getAllById(Long id) {
		return mapper.getAllById(id);
	}
	
	/**
	 * 获得所有的规格信息
	 * @return
	 */
	public PageInfo<IdeaGoodsSpecPrice> listInfo(Integer page, Integer size, String keys_name) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<IdeaGoodsSpecPrice> list = mapper.listInfo(keys_name);
		PageInfo<IdeaGoodsSpecPrice> pageInfo = new PageInfo<IdeaGoodsSpecPrice>(list);
		return pageInfo;
	}
	
	/**
	 * 通过商品id删除所有的规格信息
	 * @param goodsId
	 */
	public void deleteByGoodsId(Long goodsId) {
		mapper.deleteByGoodsId(goodsId);
	}
}
