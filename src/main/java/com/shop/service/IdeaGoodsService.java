package com.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.exception.BusinessException;
import com.shop.mapper.IdeaGoodsMapper;
import com.shop.model.dto.Goods;
import com.shop.model.dto.SellDetail;
import com.shop.model.po.IdeaGoods;
import com.shop.model.po.IdeaGoodsSpecItem;
import com.shop.model.po.IdeaGoodsSpecPrice;
import com.shop.service.common.BaseService;
import com.shop.util.StringUtil;

@Service
public class IdeaGoodsService extends BaseService<IdeaGoods, IdeaGoodsMapper> {

	@Autowired
	private IdeaGoodsSpecItemService ideaGoodsSpecItemService;
	@Autowired
	private IdeaGoodsSpecPriceService ideaGoodsSpecPriceService;
	
	@Override
	protected String getTableName() {
		return "idea_goods";
	}

	/**
	 * 获得商品信息
	 * @param page
	 * @param size
	 * @param category
	 * @param ddd
	 */
	public PageInfo<Goods> listInfo(Integer page, Integer size, String keys, Long cat_id, int type){
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<Goods> list = mapper.listInfo(keys, cat_id, type);
		PageInfo<Goods> pageInfo = new PageInfo<Goods>(list);
		return pageInfo;
	}
	
	/**
	 * 更新商品所有的信息
	 * @return
	 */
	public boolean updateAll(){
		return true;
	}
	
	/**
	 * 判断相应的排序数值是否存在
	 * @param sequence
	 * @return
	 */
	public boolean isExist(int sequence) {
		int ic = mapper.isExist(sequence);
		if(ic > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获得销售排行信息
	 * @param page
	 * @param size
	 * @return
	 */
	public PageInfo<SellDetail> getSellRanking(Integer page, Integer size) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<SellDetail> list = mapper.getSellRanking();
		PageInfo<SellDetail> pageInfo = new PageInfo<SellDetail>(list);
		if(pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
			for(int i=0; i < pageInfo.getList().size(); i++) {
				SellDetail sellDetail = pageInfo.getList().get(i);
				sellDetail.setRanking(i+1);
			}
		}
		return pageInfo;
	}
	
	/**
	 * 猜你喜欢商品接口
	 * @return
	 */
	public List<IdeaGoods> guessGoods() {
		List<IdeaGoods> list = mapper.guessGoods();
		List<IdeaGoods> list2= new ArrayList<IdeaGoods>();
		list2.add(list.get(StringUtil.getNum(list.size())));
		list2.add(list.get(StringUtil.getNum(list.size())));
		list2.add(list.get(StringUtil.getNum(list.size())));
		list2.add(list.get(StringUtil.getNum(list.size())));
		return list2;
	}
	
	/**
	 * 获得指定条数的新品或非新品信息信息
	 * @param size
	 * @param type
	 * @return
	 */
	public List<IdeaGoods> getNewGoods(Integer size, Integer type) {
		return mapper.getNewGoods(size, type);
	}
	
	/**
	 * 综合排行，按兑换次数排行
	 * @param size
	 * @param page
	 * @return
	 */
	public PageInfo<IdeaGoods> getPopularity(Integer page, Integer size) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<IdeaGoods> list = mapper.getPopularity();
		PageInfo<IdeaGoods> pageInfo = new PageInfo<IdeaGoods>(list);
		return pageInfo;
	}

	/**
	 * 查询首页商品
	 * @param personId
	 * @return
	 */
	public List<IdeaGoods> IndexGoods(Long personId) {
		return mapper.info(personId);
	}
	
	/**
	 * 通过订单Id 获得订单中所有的商品
	 * @param orderId
	 * @return
	 */
	public List<Map<String, Object>> getAllByOrderId(Long orderId) {
		return mapper.getAllByOrderId(orderId);
	}
	
	/**
	 * 获得限时抢购项目的商品剩余可购买的数量
	 * @param userId
	 * @param id
	 * @return
	 */
	public int buyGoodsAmount(Long userId, Long id) {
		return mapper.buyGoodsAmount(userId, id);
	}
	
	/**
	 * 保存用户信息
	 * @param ideaGoods
	 * @throws BusinessException 
	 */
	@Transactional(rollbackFor = BusinessException.class)
	public void saveAll(IdeaGoods ideaGoods) throws BusinessException {
		this.save(ideaGoods);
		
		String specId = ideaGoods.getSpec_key_ids();
		if(specId != null) {
			String[] specArr = specId.split(",");
			String[] stocks = ideaGoods.getStocks().split(",");
			String[] prices = ideaGoods.getPrices().split(",");
			if(specArr != null && specArr.length > 0) {
				List<IdeaGoodsSpecPrice> list = new ArrayList<IdeaGoodsSpecPrice>();
				for(int i=0; i < specArr.length; i++) {
					String str = specArr[i];
					IdeaGoodsSpecItem ideaGoodsSpecItem = ideaGoodsSpecItemService.getAllInfo(StringUtil.strToLong(str));
					
					IdeaGoodsSpecPrice ideaGoodsSpecPrice = new IdeaGoodsSpecPrice();
					ideaGoodsSpecPrice.setGoods_id(ideaGoods.getId());
					ideaGoodsSpecPrice.setKey_name(ideaGoodsSpecItem.getFtitle()+":"+ideaGoodsSpecItem.getTitle());
					ideaGoodsSpecPrice.setMkey(ideaGoodsSpecItem.getId().toString());
					ideaGoodsSpecPrice.setPrice(StringUtil.strToDouble(prices[i]));
					ideaGoodsSpecPrice.setSales(0);
					ideaGoodsSpecPrice.setSku(ideaGoods.getSn()+"#"+ideaGoodsSpecItem.getTitle());
					ideaGoodsSpecPrice.setStock(StringUtil.strToInt(stocks[i]));
					
					list.add(ideaGoodsSpecPrice);
				}
				
				ideaGoodsSpecPriceService.insertList(list);
			}
		}
	}
	
	/**
	 * 更新操作
	 * @param ideaGoods
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = BusinessException.class)
	public void updateAll(IdeaGoods ideaGoods) throws BusinessException {
		this.update(ideaGoods);
		
		String specId = ideaGoods.getSpec_key_ids();
		if(specId != null) {
			String[] specArr = specId.split(",");
			String[] stocks = ideaGoods.getStocks().split(",");
			String[] prices = ideaGoods.getPrices().split(",");
			if(specArr != null && specArr.length > 0 && !"".equals(specArr[0])) {
				ideaGoodsSpecPriceService.deleteByGoodsId(ideaGoods.getId());
				List<IdeaGoodsSpecPrice> list = new ArrayList<IdeaGoodsSpecPrice>();
				for(int i=0; i < specArr.length; i++) {
					String str = specArr[i];
					IdeaGoodsSpecItem ideaGoodsSpecItem = ideaGoodsSpecItemService.getAllInfo(StringUtil.strToLong(str));
					
					IdeaGoodsSpecPrice ideaGoodsSpecPrice = new IdeaGoodsSpecPrice();
					ideaGoodsSpecPrice.setGoods_id(ideaGoods.getId());
					ideaGoodsSpecPrice.setKey_name(ideaGoodsSpecItem.getFtitle()+":"+ideaGoodsSpecItem.getTitle());
					ideaGoodsSpecPrice.setMkey(ideaGoodsSpecItem.getId().toString());
					ideaGoodsSpecPrice.setPrice(StringUtil.strToDouble(prices[i]));
					ideaGoodsSpecPrice.setSales(0);
					ideaGoodsSpecPrice.setSku(ideaGoods.getSn()+"#"+ideaGoodsSpecItem.getTitle());
					ideaGoodsSpecPrice.setStock(StringUtil.strToInt(stocks[i]));
					
					list.add(ideaGoodsSpecPrice);
				}
				
				ideaGoodsSpecPriceService.insertList(list);
			}
		}
	}
	
	/**
	 * 获得分类信息
	 * @param catId
	 * @param personId
	 * @return
	 */
	public List<IdeaGoods> getCategroy(Long catId) {
		return mapper.getCategroy(catId);
	}
	
	/**
	 * 获得分类信息
	 * @param catId
	 * @param personId
	 * @return
	 */
	public List<IdeaGoods> getPerson(Long personId) {
		return mapper.getPerson(personId);
	}
}
