package com.shop.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.exception.BusinessException;
import com.shop.mapper.IdeaOrderMapper;
import com.shop.model.dto.Order;
import com.shop.model.dto.OrderInfo;
import com.shop.model.dto.OrderStatic;
import com.shop.model.dto.SellDetail;
import com.shop.model.dto.wechat.WechatReturnPayOrder;
import com.shop.model.po.IdeaCart;
import com.shop.model.po.IdeaCoupon;
import com.shop.model.po.IdeaDistributionCategroy;
import com.shop.model.po.IdeaDistributionCommission;
import com.shop.model.po.IdeaGoods;
import com.shop.model.po.IdeaGoodsSpecPrice;
import com.shop.model.po.IdeaOrder;
import com.shop.model.po.IdeaOrderGoods;
import com.shop.model.po.IdeaShopDispatching;
import com.shop.model.po.IdeaShopTransaction;
import com.shop.model.po.IdeaUser;
import com.shop.model.po.IdeaUserPaylog;
import com.shop.service.common.BaseService;
import com.shop.util.DateUtil;
import com.shop.util.StringUtil;

@Service
public class IdeaOrderService extends BaseService<IdeaOrder, IdeaOrderMapper> {

	private Logger logger = LoggerFactory.getLogger(IdeaOrderService.class);
	
	@Autowired
	private IdeaOrderGoodsService ideaOrderGoodsService;
	@Autowired
	private IdeaCartService ideaCartService;
	@Autowired
	private IdeaGoodsService ideaGoodsService;
	@Autowired
	private IdeaUserService ideaUserService;
	@Autowired
	private IdeaCouponService ideaCouponService;
	@Autowired
	private IdeaUserLevelService ideaUserLevelService;
	@Autowired
	private IdeaDistributionCategroyService ideaDistributionCategroyService;
	@Autowired
	private IdeaShopTransactionService ideaShopTransactionService;
	@Autowired
	private IdeaGoodsSpecPriceService ideaGoodsSpecPriceService;
	@Autowired
	private IdeaUserPaylogService ideaUserPaylogService;
	@Autowired
	private IdeaShopDispatchingService ideaShopDispatchingService;
	@Autowired
	private IdeaDistributionCommissionService ideaDistributionCommissionService;
	
	@Override
	protected String getTableName() {
		return "idea_order";
	}

	/**
	 * 获得订单信息
	 * @param page
	 * @param size
	 * @param startTime
	 * @param endTime
	 * @param nickName
	 * @param status
	 * @return
	 */
	public PageInfo<Order> listInfo(Integer page, Integer size, Long startTime, Long endTime, String nickName, Integer status) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<Order> list = mapper.listInfo(startTime, endTime, nickName, status);
		PageInfo<Order> pageInfo = new PageInfo<Order>(list);
		return pageInfo;
	}
	
	/**
	 * 获得销售详情信息
	 * @param page
	 * @param size
	 * @param startTime
	 * @param endTime
	 * @param nickName
	 * @param status
	 * @return
	 */
	public PageInfo<SellDetail> sellDetail(Integer page, Integer size, Long startTime, Long endTime, String title) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<SellDetail> list = mapper.sellDetail(startTime, endTime, title);
		PageInfo<SellDetail> pageInfo = new PageInfo<SellDetail>(list);
		return pageInfo;
	}
	
	/**
	 * 获得订单详情信息
	 * @param id
	 * @return
	 */
	public Order details(Long id) {
		return mapper.details(id);
	}
	
	/**
	 * 下订单的所有处理方式
	 * @param userId
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = BusinessException.class)
	public Long saveAll(Long userId, Map<String, String> map) throws BusinessException {
		
		IdeaUser ideaUser = ideaUserService.getById(userId);
		
		List<IdeaOrderGoods> list = new ArrayList<IdeaOrderGoods>();
		Long now = DateUtil.getTimestamp();
		String amount=map.get("amount"), goodsIds = map.get("goods_id"), spec=map.get("spec_item"), m_coupon=map.get("m_coupon"),
				openId=map.get("m_openid"), pic=null;
		Double m_total=0.0, m_pay=0.0, coupon_price = 0.0, send_price=0.0,
				jf_price=0.0, discount_price=0.0, discount = 0.0, weight=0.0;
		Integer m_jf=0, useJf = StringUtil.strToInt(map.get("exchange_jf"));

		IdeaOrder ideaOrder = new IdeaOrder();
		ideaOrder.setUser_id(ideaUser.getId());
		ideaOrder.setInfo(map.get("m_info"));
		ideaOrder.setAddress(map.get("m_addr"));
		ideaOrder.setSend_type(StringUtil.strToInt(map.get("send_type")));
		ideaOrder.setName(map.get("m_name"));
		ideaOrder.setTel(map.get("m_tel"));
		ideaOrder.setAdd_date(DateUtil.getTimestamp());
		ideaOrder.setOrder_state(0);
		ideaOrder.setPay_type(3);
		ideaOrder.setExpress_price(0D);
		
		//获取折扣率
		if(ideaUser.getIs_fx() == 1) {
			IdeaDistributionCategroy ideaDistributionCategroy = ideaDistributionCategroyService.getById(ideaUser.getDistribution_lev());
			Integer ic = 100 - ideaDistributionCategroy.getBuy_discount();
			discount = ic * 1.0 / 100;
		} else {
			Map<String, Object> map3 = ideaUserLevelService.getLevelRebate(map.get("m_openid"));
			Integer ic = 100 - StringUtil.strToInt(map3.get("rebate").toString());
			discount = ic * 1.0 / 100;
		}
		
		//价钱计算
		if(goodsIds != null && amount != null) {
			String[] ids = goodsIds.split(",");
			String[] amountArr = amount.split(",");
			
			String[] specArr = null;
			if(spec != null ) {
				specArr = spec.split(",");
			}
			
			for(int i=0; i < ids.length; i++) {
				IdeaGoods ideaGoods = ideaGoodsService.getById(StringUtil.strToLong(ids[i]));
				weight = weight + ideaGoods.getWeight() * StringUtil.strToInt(amountArr[i]);
				if(pic == null) {
					pic = ideaGoods.getPic();
				}
				Double goodsPrice = 0.0;
				Double goods_Total_price = 0.0;
				
				IdeaOrderGoods ideaOrderGoods = new IdeaOrderGoods();
				if(!"-1".equals(specArr[i])) {
					ideaOrderGoods.setSpec_item(specArr[i]);
					IdeaGoodsSpecPrice ideaGoodsSpecPrice = ideaGoodsSpecPriceService.getInfoById(ideaGoods.getId(), specArr[i]);
					
					if(ideaGoodsSpecPrice != null && ideaGoodsSpecPrice.getStock() > 0) {
						//库存处理
						int stock = ideaGoodsSpecPrice.getStock()-StringUtil.strToInt(amountArr[i]);
						if(stock >= 0) {
							ideaGoodsSpecPrice.setStock(stock);
							ideaGoodsSpecPriceService.update(ideaGoodsSpecPrice);
						} else {
							return 0L;
						}
					}
					
					goodsPrice = ideaGoodsSpecPrice.getPrice();
					goods_Total_price = goodsPrice*StringUtil.strToInt(amountArr[i]);
					m_pay=m_pay+goods_Total_price;
				} else {
					return 0L;
				}
				
				Double rebate = goodsPrice * discount;
				BigDecimal bg = new BigDecimal(rebate).setScale(2, RoundingMode.HALF_UP);
				Double rebatePrice = bg.doubleValue();
				
				ideaOrderGoods.setAdd_date(now);
				ideaOrderGoods.setGoods_id(StringUtil.strToLong(ids[i]));
				ideaOrderGoods.setPrice(goodsPrice);
				ideaOrderGoods.setAmount(StringUtil.strToInt(amountArr[i]));
				ideaOrderGoods.setTotal_price(goods_Total_price);
				ideaOrderGoods.setRebate_price(rebatePrice);
				ideaOrderGoods.setIs_comment(0);
				
				m_jf=m_jf+ideaGoods.getJf();
				list.add(ideaOrderGoods);
			}
			
			m_total=m_pay;
		}
		
		//积分抵扣
		if(useJf != null && useJf != 0) {
			if(ideaUser.getIntegral() < useJf) {
				useJf = ideaUser.getIntegral();
			}
			IdeaShopTransaction ideaShopTransaction = ideaShopTransactionService.getById(1L);
			Double val = useJf/ideaShopTransaction.getUse_ratio_integral();
			BigDecimal bg = new BigDecimal(val).setScale(2, RoundingMode.HALF_UP);
			jf_price = bg.doubleValue();
		}
		
		//优惠券
		if(m_coupon != null && !"".equals(m_coupon)) {
			ideaOrder.setCoupon_id(m_coupon);
			
			List<String> listCoupon = Arrays.asList(m_coupon.split(","));
			List<IdeaCoupon> list2 = ideaCouponService.getInfoByIds(listCoupon, ideaUser.getId());
			for(IdeaCoupon ideaCoupon : list2) {
				coupon_price = coupon_price + ideaCoupon.getYh_price();
			}
			//将优惠券设置为已使用
			ideaCouponService.updateUseCoupon(listCoupon, ideaUser.getId());
			
			//
			Double doub = m_total * 2/5;
			BigDecimal bg = new BigDecimal(doub).setScale(2, RoundingMode.HALF_UP);
			doub = bg.doubleValue();
			if(doub > coupon_price) {
				discount_price = coupon_price + jf_price;
				ideaOrder.setCoupon_price(coupon_price);
				ideaOrder.setRebate_price(0D);
				ideaOrder.setDiscount_price(discount_price);
				ideaOrder.setCoupon_id(m_coupon);
				m_pay = m_pay - discount_price;
			} else {
				discount_price = doub + jf_price;
				ideaOrder.setCoupon_price(doub);
				ideaOrder.setRebate_price(0D);
				ideaOrder.setDiscount_price(discount_price);
				ideaOrder.setCoupon_id(m_coupon);
				m_pay = m_pay - discount_price;
			}
		} else {
			Double val = m_pay * discount;
			discount_price = val + jf_price;
			ideaOrder.setDiscount_price(discount_price);
			ideaOrder.setCoupon_price(0D);
			ideaOrder.setRebate_price(val);
			m_pay = m_pay - discount_price;
		}
		
		//提成计算
		/*Long fid = ideaUser.getDistribution_recommend_uid();
		for(int i=0; i < 20; i++) {
			if(ideaUser.getDistribution_recommend_uid() != 0) {
				IdeaUser ideaUser2 = ideaUserService.getById(fid);
				if(ideaUser2.getDistribution_lev() != 0) {
					IdeaDistributionCategroy ideaDistributionCategroy = ideaDistributionCategroyService.getById(ideaUser2.getDistribution_lev());
					Double tc3 = ideaDistributionCategroy.getCommission() * m_pay /100;
					BigDecimal bg = new BigDecimal(tc3).setScale(2, RoundingMode.UP);
					tc = bg.doubleValue();
				} else {
					break;
				}
			}
		}*/
		
		//邮费
		IdeaShopTransaction ideaShopTransaction = ideaShopTransactionService.getById(1L);
		if(ideaShopTransaction.getFull_cut() > weight) {
			IdeaShopDispatching ideaShopDispatching = ideaShopDispatchingService.getById(1L);
			if(ideaShopDispatching.getExpress_switch() == 1) {
				if(ideaShopDispatching.getFirst_weight() < weight) {
					Double dou = ideaShopDispatching.getSecond_price() * (weight - ideaShopDispatching.getFirst_weight());
					send_price = ideaShopDispatching.getFirst_price() + dou;
				} else {
					send_price = ideaShopDispatching.getFirst_price();
				}
			}
		}
		
		/*Double tc3 = m_pay * 9/25;
		BigDecimal bg = new BigDecimal(tc3).setScale(2, RoundingMode.HALF_UP);
		tc = bg.doubleValue();*/

		ideaOrder.setUse_jf(useJf);
		ideaOrder.setJf_price(jf_price);
		ideaOrder.setPay_price(m_pay);
		ideaOrder.setPrice(m_total);
		ideaOrder.setTrim_price(0D);
		ideaOrder.setTc(0D);
		ideaOrder.setPay_terminal(2);
		ideaOrder.setJf(m_jf);
		ideaOrder.setOrder_sn(StringUtil.getOrderSn(1));
		ideaOrder.setExpress_price(send_price);
		ideaOrder.setPic(pic);
		ideaOrder.setIs_tc(1);
		this.save(ideaOrder);
		
		//新增订单商品
		for(IdeaOrderGoods ideaOrderGoods : list) {
			ideaOrderGoods.setOrder_id(ideaOrder.getId());
		}
		ideaOrderGoodsService.insertList(list);
		
		//删除购物车信息
		IdeaCart ideaCart = new IdeaCart();
		ideaCart.setSession_id(openId);
		List<IdeaCart> lli = ideaCartService.getAll(ideaCart, null);
		if(lli != null && !lli.isEmpty()) {
			ideaCartService.deleteByIds(lli);
		}
		
		//积分冻结
		if(useJf != null && useJf > 0) {
			int integral = ideaUser.getIntegral() - useJf;
			ideaUser.setIntegral(integral);
			ideaUserService.update(ideaUser);
		}
		
		return ideaOrder.getId();
	}
	
	/**
	 * 限时购买
	 * @param userId
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@Transactional(rollbackFor = BusinessException.class)
	public Long flashSale(Long userId, Map<String, String> map, int goodsAmo) throws BusinessException {
		IdeaUser ideaUser = ideaUserService.getById(userId);
		
		IdeaOrderGoods ideaOrderGoods = new IdeaOrderGoods();
		Long now = DateUtil.getTimestamp();
		String amount=map.get("amount"), goodsIds = map.get("goods_id"), spec=map.get("spec_item"), pic=null;
		Double m_pay=0.0, send_price=0.0, weight=0.0, goodsPrice=0.0, jf_price=0.0, price=0.0;
		Integer useJf = StringUtil.strToInt(map.get("exchange_jf"));
		
		//价钱计算
		if(goodsIds != null && amount != null) {
			Long goodsId = StringUtil.strToLong(goodsIds);
			Integer num = StringUtil.strToInt(amount);
			if(goodsAmo <= num) {
				num = goodsAmo;
			}
			
			IdeaGoods ideaGoods = ideaGoodsService.getById(goodsId);
			weight = ideaGoods.getWeight() * num + 0.0;
			pic = ideaGoods.getPic();
			
			if(spec != null) {
				ideaOrderGoods.setSpec_item(spec);
				IdeaGoodsSpecPrice ideaGoodsSpecPrice = ideaGoodsSpecPriceService.getInfoById(ideaGoods.getId(), spec);
				
				if(ideaGoodsSpecPrice != null && ideaGoodsSpecPrice.getStock() > 0) {
					//库存处理
					int stock = ideaGoodsSpecPrice.getStock()-num;
					if(stock >= 0) {
						ideaGoodsSpecPrice.setStock(stock);
						ideaGoodsSpecPriceService.update(ideaGoodsSpecPrice);
					} else {
						return 0L;
					}
				}
				goodsPrice = ideaGoodsSpecPrice.getPrice();
				price=goodsPrice*num;
			} else {
				return 0L;
			}
			
			ideaOrderGoods.setAdd_date(now);
			ideaOrderGoods.setGoods_id(goodsId);
			ideaOrderGoods.setPrice(goodsPrice);
			ideaOrderGoods.setAmount(num);
			ideaOrderGoods.setIs_comment(0);
			ideaOrderGoods.setTotal_price(price);
			ideaOrderGoods.setRebate_price(goodsPrice);
		}
		
		//积分抵扣
		if(useJf != null && useJf != 0) {
			if(ideaUser.getIntegral() < useJf) {
				useJf = ideaUser.getIntegral();
			}
			IdeaShopTransaction ideaShopTransaction = ideaShopTransactionService.getById(1L);
			Double val = useJf/ideaShopTransaction.getUse_ratio_integral();
			BigDecimal bg = new BigDecimal(val).setScale(2, RoundingMode.HALF_UP);
			jf_price = bg.doubleValue();
		}
		m_pay = price - jf_price;
		
		//邮费
		IdeaShopTransaction ideaShopTransaction = ideaShopTransactionService.getById(1L);
		if(ideaShopTransaction.getFull_cut() > weight) {
			IdeaShopDispatching ideaShopDispatching = ideaShopDispatchingService.getById(1L);
			if(ideaShopDispatching.getExpress_switch() == 1) {
				if(ideaShopDispatching.getFirst_weight() < weight) {
					Double dou = ideaShopDispatching.getSecond_price() * (weight - ideaShopDispatching.getFirst_weight());
					send_price = ideaShopDispatching.getFirst_price() + dou;
				} else {
					send_price = ideaShopDispatching.getFirst_price();
				}
			}
		}

		IdeaOrder ideaOrder = new IdeaOrder();
		ideaOrder.setUser_id(ideaUser.getId());
		ideaOrder.setInfo(map.get("m_info"));
		ideaOrder.setAddress(map.get("m_addr"));
		ideaOrder.setSend_type(StringUtil.strToInt(map.get("send_type")));
		ideaOrder.setName(map.get("m_name"));
		ideaOrder.setTel(map.get("m_tel"));
		ideaOrder.setUse_jf(0);
		ideaOrder.setAdd_date(DateUtil.getTimestamp());
		ideaOrder.setOrder_state(0);
		ideaOrder.setPay_type(3);
		ideaOrder.setJf_price(jf_price);
		ideaOrder.setPay_price(m_pay);
		ideaOrder.setPrice(price);
		ideaOrder.setTrim_price(0D);
		ideaOrder.setTc(0D);
		ideaOrder.setPay_terminal(2);
		ideaOrder.setJf(0);
		ideaOrder.setUse_jf(useJf);
		ideaOrder.setOrder_sn(StringUtil.getOrderSn(1));
		ideaOrder.setExpress_price(send_price);
		ideaOrder.setPic(pic);
		ideaOrder.setIs_tc(0);
		this.save(ideaOrder);
		
		ideaOrderGoods.setOrder_id(ideaOrder.getId());
		ideaOrderGoodsService.save(ideaOrderGoods);
		
		return ideaOrder.getId();
	}
	
	/**
	 * 通过订单编号获得所有信息
	 * @param orderNo
	 * @return
	 */
	public IdeaOrder getOrderBySn(String orderNo) {
		return mapper.getOrderBySn(orderNo);
	}
	
	/**
	 * 获得订单信息列表
	 * @param userId
	 * @param state
	 * @return
	 */
	public List<IdeaOrder> getInfoByOpenId(Long userId, Integer state) {
		return mapper.getInfoByOpenId(userId, state);
	}
	
	/**
	 * 获得用户订单总数量
	 * @param userId
	 * @return
	 */
	public int getCount(Long userId) {
		return mapper.getCount(userId);
	}
	
	/**
	 * 我的分销订单
	 * @param page
	 * @param size
	 * @param userId
	 * @return
	 */
	public PageInfo<OrderInfo> getDistributionOrder(Integer page, Integer size, Long userId) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<OrderInfo> list = mapper.getDistributionOrder(userId);
		PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(list);
		return pageInfo;
	}
	
	/**
	 * 我的团队订单
	 * @param page
	 * @param size
	 * @param userId
	 * @return
	 */
	public List<OrderInfo> listMyGroupOrder(Long userId) {
		return  mapper.listMyGroupOrder(userId);
	}
	
	/**
	 * 获得订单的统计数量
	 * @param userId
	 * @return
	 */
	public List<Map<String, Integer>> getOrderCount(Long userId) {
		return mapper.getOrderCount(userId);
	}
	
	/**
	 * 会员账户余额支付
	 * @param map2
	 * @param userId
	 * @return
	 * @throws BusinessException 
	 */
	@Transactional(rollbackFor = BusinessException.class)
	public Map<String, String> balancePay(Map<String, String> map2, Long userId) throws BusinessException {
		Map<String, String> map = new HashMap<String, String>();
		IdeaUser ideaUser = ideaUserService.getById(userId);
		IdeaOrder ideaOrder = this.getOrderBySn(map2.get("sn"));
		Double dou = ideaUser.getUser_money() - ideaOrder.getPay_price();
		Double pay_price = dou -ideaOrder.getExpress_price();
		if(pay_price > 0) {
			//积分扣除记录
			if(ideaOrder.getUse_jf() > 0) {
				int integral = ideaUser.getIntegral() - ideaOrder.getUse_jf();
				ideaUser.setIntegral(integral);
				
				IdeaUserPaylog ideaUserPaylog = new IdeaUserPaylog();
				ideaUserPaylog.setAccount_fee(0.0);
				ideaUserPaylog.setFee(integral+0.0);
				ideaUserPaylog.setAdd_date(DateUtil.getTimestamp());
				ideaUserPaylog.setFee(ideaOrder.getUse_jf()+0.0);
				ideaUserPaylog.setInfo("积分抵扣，订单：" + ideaOrder.getId());
				ideaUserPaylog.setUser_id(ideaUser.getId());
				ideaUserPaylog.setType(4);
				ideaUserPaylog.setPay_state(1);
				ideaUserPaylog.setPay_type(3);
				ideaUserPaylog.setOrder_id(ideaOrder.getId());
				ideaUserPaylogService.save(ideaUserPaylog);
			}
			//用户信息更新
			ideaUser.setUser_money(pay_price);
			ideaUserService.update(ideaUser);
			
			//用户支付记录
			IdeaUserPaylog ideaUserPaylog = new IdeaUserPaylog();
			ideaUserPaylog.setAccount_fee(dou);
			ideaUserPaylog.setFee(ideaOrder.getPay_price());
			ideaUserPaylog.setAdd_date(DateUtil.getTimestamp());
			ideaUserPaylog.setInfo("订单：" + ideaOrder.getId());
			ideaUserPaylog.setUser_id(ideaUser.getId());
			ideaUserPaylog.setType(2);
			ideaUserPaylog.setPay_state(1);
			ideaUserPaylog.setPay_type(3);
			ideaUserPaylog.setOrder_id(ideaOrder.getId());
			ideaUserPaylogService.save(ideaUserPaylog);
			
			//订单处理
			ideaOrder.setPay_type(3);
			ideaOrder.setOrder_state(1);
			ideaOrder.setPay_sn(StringUtil.getOrderSn(5));
			ideaOrder.setPay_date(DateUtil.format(new Date(), DateUtil.FORMAT_YYYY_MM_DD));
			ideaOrder.setPay_terminal(2);
			this.update(ideaOrder);
			
			//库存处理
			/*List<Map<String, Object>> list = ideaGoodsService.getAllByOrderId(ideaOrder.getId());
			if(list != null && !list.isEmpty()) {
				for(Map<String, Object> map3 : list) {
					int ic = StringUtil.strToInt(map3.get("stock").toString()) - StringUtil.strToInt(map3.get("amount").toString());
					IdeaGoodsSpecPrice ideaGoodsSpecPrice = new IdeaGoodsSpecPrice();
					ideaGoodsSpecPrice.setId(StringUtil.strToLong(map3.get("id").toString()));
					ideaGoodsSpecPrice.setStock(ic);
					
					ideaGoodsSpecPriceService.update(ideaGoodsSpecPrice);
				}
			}*/
			
			//推荐人处理
			if(ideaOrder.getIs_tc() == 1 && ideaOrder.getPay_price() > 0) {
				//判断并计算是否给予佣金
				Double allPrice = ideaOrder.getPay_price();
				Long uid = ideaUser.getDistribution_recommend_uid();//后一个分销人的用户id
				Long dis_lev = 0L;//前一个分销人的分销等级
				for(int i=0; i < 10; i++) {
					if(uid != 0) {
						IdeaUser ideaUser2 = ideaUserService.getById(uid);
						if(ideaUser2 != null && ideaUser2.getIs_fx() == 1 && ideaUser2.getDistribution_lev() != 0) {
							if(dis_lev == 0L) {
								IdeaDistributionCategroy ideaDistributionCategroy = ideaDistributionCategroyService.getById(ideaUser2.getDistribution_lev());
								
								Double reTc = allPrice * ideaDistributionCategroy.getCommission() / 100;
								BigDecimal bg = new BigDecimal(reTc).setScale(2, RoundingMode.HALF_UP);
								Double tc = bg.doubleValue();
								
								if(tc >= 0) {
									//推荐人佣金记录
									IdeaUserPaylog ideaUserPaylog2 = new IdeaUserPaylog();
									ideaUserPaylog2.setAccount_fee(0.0);
									ideaUserPaylog2.setAdd_date(DateUtil.getTimestamp());
									ideaUserPaylog2.setFee(tc);
									ideaUserPaylog2.setInfo("分销佣金，用户：" + ideaUser.getId());
									ideaUserPaylog2.setUser_id(uid);
									ideaUserPaylog2.setType(5);
									ideaUserPaylog.setPay_state(0);
									ideaUserPaylog2.setPay_type(3);
									ideaUserPaylog2.setState(0);
									ideaUserPaylog2.setOrder_id(ideaOrder.getId());
									ideaUserPaylogService.save(ideaUserPaylog2);
									
									//是否需要结束循环
									if(ideaUser2.getDistribution_recommend_uid() == 0) {
										break;
									} else {
										uid = ideaUser2.getDistribution_recommend_uid();
										dis_lev = ideaUser2.getDistribution_lev();
									}
								} else {
									break;
								}
							} else {
								IdeaDistributionCommission ideaDistributionCommission = new IdeaDistributionCommission();
								ideaDistributionCommission.setCid(dis_lev);
								ideaDistributionCommission.setParent_id(ideaUser2.getDistribution_lev());
								List<IdeaDistributionCommission> list = ideaDistributionCommissionService.getAll(ideaDistributionCommission, null);
								if(list != null && !list.isEmpty()) {
									IdeaDistributionCommission ideaDistributionCommission2 = list.get(0);
									
									Double reTc = allPrice * ideaDistributionCommission2.getCommission() / 100;
									BigDecimal bg = new BigDecimal(reTc).setScale(2, RoundingMode.HALF_UP);
									Double tc = bg.doubleValue();
									
									if(tc >= 0) {
										//推荐人佣金记录
										IdeaUserPaylog ideaUserPaylog2 = new IdeaUserPaylog();
										ideaUserPaylog2.setAccount_fee(0.0);
										ideaUserPaylog2.setAdd_date(DateUtil.getTimestamp());
										ideaUserPaylog2.setFee(tc);
										ideaUserPaylog2.setInfo("分销佣金，用户：" + ideaUser.getId());
										ideaUserPaylog2.setUser_id(uid);
										ideaUserPaylog2.setType(5);
										ideaUserPaylog.setPay_state(0);
										ideaUserPaylog2.setPay_type(3);
										ideaUserPaylog2.setState(0);
										ideaUserPaylog2.setOrder_id(ideaOrder.getId());
										ideaUserPaylogService.save(ideaUserPaylog2);
										
										//是否需要结束循环
										if(ideaUser2.getDistribution_recommend_uid() == 0) {
											break;
										} else {
											uid = ideaUser2.getDistribution_recommend_uid();
											dis_lev = ideaUser2.getDistribution_lev();
										}
									} else {
										break;
									}
								} else {
									break;
								}
							}
						} else {
							break;
						}
					} else {
						break;
					}
				}
				
				/*Double discount = 0.0;//优惠比例
				Double allTc = ideaOrder.getPay_price();
				Long uid = ideaUser.getDistribution_recommend_uid();
				for(int i=0; i < 15; i++) {
					if(uid != 0) {
						IdeaUser ideaUser2 = ideaUserService.getById(uid);
						if(ideaUser2 != null && ideaUser2.getIs_fx() == 1 && ideaUser2.getDistribution_lev() != 0) {
							IdeaDistributionCategroy ideaDistributionCategroy = ideaDistributionCategroyService.getById(ideaUser2.getDistribution_lev());
							if(ideaDistributionCategroy != null) {
								Double reTc = allTc * (ideaDistributionCategroy.getCommission() - discount) / 100;
								BigDecimal bg = new BigDecimal(reTc).setScale(2, RoundingMode.HALF_UP);
								Double tc = bg.doubleValue();
								
								if(tc > 0) {
									//推荐人佣金记录
									IdeaUserPaylog ideaUserPaylog2 = new IdeaUserPaylog();
									ideaUserPaylog2.setAccount_fee(0.0);
									ideaUserPaylog2.setAdd_date(DateUtil.getTimestamp());
									ideaUserPaylog2.setFee(tc);
									ideaUserPaylog2.setInfo("分销佣金，用户：" + ideaUser.getId());
									ideaUserPaylog2.setUser_id(uid);
									ideaUserPaylog2.setType(5);
									ideaUserPaylog.setPay_state(0);
									ideaUserPaylog2.setPay_type(3);
									ideaUserPaylog2.setState(0);
									ideaUserPaylog2.setOrder_id(ideaOrder.getId());
									ideaUserPaylogService.save(ideaUserPaylog2);
									
									//是否需要结束循环
									if(ideaUser2.getDistribution_recommend_uid() == 0) {
										break;
									} else {
										uid = ideaUser2.getDistribution_recommend_uid();
										discount = ideaDistributionCategroy.getCommission();
									}
								}
							} else {
								break;
							}
						} else {
							break;
						}
					} else {
						break;
					}
				}*/
			}
			
			/*if(ideaUser.getDistribution_recommend_uid() != 0) {
				IdeaUser ideaUser2 = ideaUserService.getById(ideaUser.getDistribution_recommend_uid());
				if(ideaUser2.getIs_fx() == 1 && ideaUser2.getDistribution_lev() != 0) {
					Long fid = ideaUser2.getDistribution_lev();
					Long recommendId=ideaUser.getDistribution_recommend_uid();
					Double ic = 0.0;
					for(int i=0; i < 10; i++) {
						IdeaDistributionCategroy ideaDistributionCategroy = ideaDistributionCategroyService.getById(fid);
						Double discount = (ideaDistributionCategroy.getCommission()-ic)/100;
						Double tc1 = ideaOrder.getPay_price() * discount;
						BigDecimal bg = new BigDecimal(tc1).setScale(2, RoundingMode.UP);
						Double tc = bg.doubleValue();
						
						IdeaUser ideaUser3 = ideaUserService.getById(fid);
						Double money = ideaUser2.getUser_money() + ideaOrder.getTc();
						ideaUser3.setUser_money(money);
						ideaUserService.update(ideaUser3);
						
						//推荐人佣金记录
						IdeaUserPaylog ideaUserPaylog2 = new IdeaUserPaylog();
						ideaUserPaylog2.setAccount_fee(0.0);
						ideaUserPaylog2.setAdd_date(DateUtil.getTimestamp());
						ideaUserPaylog2.setFee(tc);
						ideaUserPaylog2.setInfo("分销佣金，用户：" + ideaUser.getId());
						ideaUserPaylog2.setUser_id(fid);
						ideaUserPaylog2.setType(5);
						ideaUserPaylog.setPay_state(0);
						ideaUserPaylog2.setPay_type(3);
						ideaUserPaylog2.setOrder_id(ideaOrder.getId());
						ideaUserPaylogService.save(ideaUserPaylog2);
						
						//是否需要结束循环
						if(ideaDistributionCategroy.getFid() == 0) {
							break;
						} else {
							fid = ideaDistributionCategroy.getFid();
							ic = ideaDistributionCategroy.getCommission();
						}
					}
				}
			}*/
			
			map.put("code", "1001");
			map.put("msg", "操作成功");
		} else {
			map.put("code", "1002");
			map.put("msg", "余额不足");
		}
		return map;
	}
	
	/**
	 * 判断时候已经存在相应的订单
	 * @param wechatOrderId
	 * @return
	 */
	public boolean verifyInfoIsNull(String wechatOrderId) {
		int ic = mapper.verifyInfoIsNull(wechatOrderId);
		if(ic > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 微信订单成功下单后处理
	 * @param notifyReq
	 * @throws BusinessException 
	 */
	public void saveOrder(WechatReturnPayOrder notifyReq) throws BusinessException {
		
		logger.info("接收到的数据："+ JSONObject.toJSONString(notifyReq));
		JSONObject jsonObject = JSONObject.parseObject("{"+notifyReq.getAttach()+"}");
        Long userId = StringUtil.strToLong(jsonObject.get("userId").toString());
        logger.info("获得商家数据包信息：用户Id：" + userId);
        
		IdeaUser ideaUser = ideaUserService.getById(userId);
		IdeaOrder ideaOrder = mapper.getByOrderSn(notifyReq.getOut_trade_no());
		
		if(ideaOrder.getUse_jf() > 0) {
			//用户信息更新
			int integral = ideaUser.getIntegral() - ideaOrder.getUse_jf();
			ideaUser.setIntegral(integral);
			ideaUserService.update(ideaUser);
			
			//积分扣除记录
			IdeaUserPaylog ideaUserPaylog = new IdeaUserPaylog();
			ideaUserPaylog.setAccount_fee(0.0);
			ideaUserPaylog.setFee(integral+0.0);
			ideaUserPaylog.setAdd_date(DateUtil.getTimestamp());
			ideaUserPaylog.setFee(ideaOrder.getUse_jf()+0.0);
			ideaUserPaylog.setInfo("积分抵扣，订单：" + ideaOrder.getId());
			ideaUserPaylog.setUser_id(ideaUser.getId());
			ideaUserPaylog.setType(4);
			ideaUserPaylog.setPay_state(1);
			ideaUserPaylog.setPay_type(3);
			ideaUserPaylog.setOrder_id(ideaOrder.getId());
			ideaUserPaylogService.save(ideaUserPaylog);
		}
			
		//用户支付记录
		IdeaUserPaylog ideaUserPaylog = new IdeaUserPaylog();
		ideaUserPaylog.setAccount_fee(0.0);
		ideaUserPaylog.setAdd_date(DateUtil.getTimestamp());
		ideaUserPaylog.setFee(ideaOrder.getPay_price()+ideaOrder.getExpress_price());
		ideaUserPaylog.setInfo("订单：" + ideaOrder.getId());
		ideaUserPaylog.setUser_id(ideaUser.getId());
		ideaUserPaylog.setType(2);
		ideaUserPaylog.setPay_state(1);
		ideaUserPaylog.setPay_type(3);
		ideaUserPaylog.setOrder_id(ideaOrder.getId());
		ideaUserPaylogService.update(ideaUserPaylog);
		logger.info("获得商家数据包信息：用户Id：" + userId);
		
		//订单处理
		ideaOrder.setPay_type(1);
		ideaOrder.setOrder_state(1);
		ideaOrder.setPay_sn(StringUtil.getOrderSn(5));
		ideaOrder.setPay_date(DateUtil.format(new Date(), DateUtil.FORMAT_YYYY_MM_DD));
		ideaOrder.setPay_terminal(2);
		this.update(ideaOrder);
		logger.info("订单处理");
		
		//推荐人处理
		if(ideaOrder.getIs_tc() == 1 && ideaOrder.getPay_price() > 0) {
			//判断并计算是否给予佣金
			Double allPrice = ideaOrder.getPay_price();
			Long uid = ideaUser.getDistribution_recommend_uid();//后一个分销人的用户id
			Long dis_lev = 0L;//前一个分销人的分销等级
			for(int i=0; i < 10; i++) {
				if(uid != 0) {
					IdeaUser ideaUser2 = ideaUserService.getById(uid);
					if(ideaUser2 != null && ideaUser2.getIs_fx() == 1 && ideaUser2.getDistribution_lev() != 0) {
						if(dis_lev == 0L) {
							IdeaDistributionCategroy ideaDistributionCategroy = ideaDistributionCategroyService.getById(ideaUser2.getDistribution_lev());
							
							Double reTc = allPrice * ideaDistributionCategroy.getCommission() / 100;
							BigDecimal bg = new BigDecimal(reTc).setScale(2, RoundingMode.HALF_UP);
							Double tc = bg.doubleValue();
							
							if(tc >= 0) {
								//推荐人佣金记录
								IdeaUserPaylog ideaUserPaylog2 = new IdeaUserPaylog();
								ideaUserPaylog2.setAccount_fee(0.0);
								ideaUserPaylog2.setAdd_date(DateUtil.getTimestamp());
								ideaUserPaylog2.setFee(tc);
								ideaUserPaylog2.setInfo("分销佣金，用户：" + ideaUser.getId());
								ideaUserPaylog2.setUser_id(uid);
								ideaUserPaylog2.setType(5);
								ideaUserPaylog.setPay_state(0);
								ideaUserPaylog2.setPay_type(3);
								ideaUserPaylog2.setState(0);
								ideaUserPaylog2.setOrder_id(ideaOrder.getId());
								ideaUserPaylogService.save(ideaUserPaylog2);
								
								//是否需要结束循环
								if(ideaUser2.getDistribution_recommend_uid() == 0) {
									break;
								} else {
									uid = ideaUser2.getDistribution_recommend_uid();
									dis_lev = ideaUser2.getDistribution_lev();
								}
							} else {
								break;
							}
						} else {
							IdeaDistributionCommission ideaDistributionCommission = new IdeaDistributionCommission();
							ideaDistributionCommission.setCid(dis_lev);
							ideaDistributionCommission.setParent_id(ideaUser2.getDistribution_lev());
							List<IdeaDistributionCommission> list = ideaDistributionCommissionService.getAll(ideaDistributionCommission, null);
							if(list != null && !list.isEmpty()) {
								IdeaDistributionCommission ideaDistributionCommission2 = list.get(0);
								
								Double reTc = allPrice * ideaDistributionCommission2.getCommission() / 100;
								BigDecimal bg = new BigDecimal(reTc).setScale(2, RoundingMode.HALF_UP);
								Double tc = bg.doubleValue();
								
								if(tc >= 0) {
									//推荐人佣金记录
									IdeaUserPaylog ideaUserPaylog2 = new IdeaUserPaylog();
									ideaUserPaylog2.setAccount_fee(0.0);
									ideaUserPaylog2.setAdd_date(DateUtil.getTimestamp());
									ideaUserPaylog2.setFee(tc);
									ideaUserPaylog2.setInfo("分销佣金，用户：" + ideaUser.getId());
									ideaUserPaylog2.setUser_id(uid);
									ideaUserPaylog2.setType(5);
									ideaUserPaylog.setPay_state(0);
									ideaUserPaylog2.setPay_type(3);
									ideaUserPaylog2.setState(0);
									ideaUserPaylog2.setOrder_id(ideaOrder.getId());
									ideaUserPaylogService.save(ideaUserPaylog2);
									
									//是否需要结束循环
									if(ideaUser2.getDistribution_recommend_uid() == 0) {
										break;
									} else {
										uid = ideaUser2.getDistribution_recommend_uid();
										dis_lev = ideaUser2.getDistribution_lev();
									}
								} else {
									break;
								}
							} else {
								break;
							}
						}
					} else {
						break;
					}
				} else {
					break;
				}
			}
			
			/*Double discount = 0.0;//优惠比例
			Double allTc = ideaOrder.getPay_price();
			Long uid = ideaUser.getDistribution_recommend_uid();
			for(int i=0; i < 15; i++) {
				if(uid != 0) {
					IdeaUser ideaUser2 = ideaUserService.getById(uid);
					if(ideaUser2 != null && ideaUser2.getIs_fx() == 1 && ideaUser2.getDistribution_lev() != 0) {
						IdeaDistributionCategroy ideaDistributionCategroy = ideaDistributionCategroyService.getById(ideaUser2.getDistribution_lev());
						if(ideaDistributionCategroy != null) {
							Double reTc = allTc * (ideaDistributionCategroy.getCommission() - discount) / 100;
							BigDecimal bg = new BigDecimal(reTc).setScale(2, RoundingMode.HALF_UP);
							Double tc = bg.doubleValue();
							
							if(tc > 0) {
								//推荐人佣金记录
								IdeaUserPaylog ideaUserPaylog2 = new IdeaUserPaylog();
								ideaUserPaylog2.setAccount_fee(0.0);
								ideaUserPaylog2.setAdd_date(DateUtil.getTimestamp());
								ideaUserPaylog2.setFee(tc);
								ideaUserPaylog2.setInfo("分销佣金，用户：" + ideaUser.getId());
								ideaUserPaylog2.setUser_id(uid);
								ideaUserPaylog2.setType(5);
								ideaUserPaylog.setPay_state(0);
								ideaUserPaylog2.setPay_type(3);
								ideaUserPaylog2.setState(0);
								ideaUserPaylog2.setOrder_id(ideaOrder.getId());
								ideaUserPaylogService.save(ideaUserPaylog2);
								
								//是否需要结束循环
								if(ideaUser2.getDistribution_recommend_uid() == 0) {
									break;
								} else {
									uid = ideaUser2.getDistribution_recommend_uid();
									discount = ideaDistributionCategroy.getCommission();
								}
							}
						} else {
							break;
						}
					} else {
						break;
					}
				} else {
					break;
				}
			}*/
		}
	}
	
	/**
	 * 获得订单信息
	 * @param orderSn
	 * @return
	 */
	public IdeaOrder getByOrderSn(String orderSn) {
		return mapper.getByOrderSn(orderSn);
	}
	
	/**
	 * 确认收货
	 * @param userId
	 * @param orderId
	 * @throws BusinessException 
	 */
	public void gainGoods(Long userId, Long orderId) throws BusinessException {
		//获取信息
		IdeaUser ideaUser = ideaUserService.getById(userId);
		IdeaOrder ideaOrder = this.getById(orderId);
		
		//累计消费额
		Double total_money = ideaUser.getTotal_money() + ideaOrder.getPay_price();
		ideaUser.setTotal_money(total_money);
		
		//是否是余额支付
		if(ideaOrder.getPay_type() == 3) {
			Double dou = ideaUser.getUser_money() - ideaOrder.getPay_price();
			ideaUser.setUser_money(dou);
		}
		
		//修改用户信息
		int integral = ideaUser.getIntegral() + ideaOrder.getJf();
		ideaUser.setIntegral(integral);
		ideaUserService.update(ideaUser);
		
		//修改用户状态
		ideaOrder.setOrder_state(3);
		this.update(ideaOrder);
		
		//分销商获得分销佣金记录处理及给予资金
		IdeaUserPaylog ideaUserPaylog = new IdeaUserPaylog();
		ideaUserPaylog.setOrder_id(orderId);
		ideaUserPaylog.setPay_state(0);
		List<IdeaUserPaylog> list = ideaUserPaylogService.getAll(ideaUserPaylog, null);
		for(IdeaUserPaylog ideaUserPaylog2 : list) {
			if(ideaUserPaylog2.getType() != 5) {
				IdeaUser ideaUser2 = ideaUserService.getById(ideaUserPaylog2.getUser_id());
				Double dou = ideaUser2.getUser_money() + ideaUserPaylog2.getFee();
				ideaUser2.setUser_money(dou);
				ideaUserService.update(ideaUser2);
			}
				
			ideaUserPaylog2.setPay_state(1);
			ideaUserPaylogService.update(ideaUserPaylog2);
		}
	}
	
	/**
	 * 获得没有支付的订单
	 * @return
	 */
	public List<IdeaOrder> getNotPayOrder(int unpaid) {
		return mapper.getNotPayOrder(unpaid);
	}
	
	/**
	 * 获得一张商品图片
	 * @param orderId
	 * @return
	 */
	public String getOneGoodsPic(Long orderId) {
		return mapper.getOneGoodsPic(orderId);
	}
	
	/**
	 * 获得订单统计
	 * @return
	 */
	public List<OrderStatic> statistics(Long start) {
		Long now = DateUtil.getTimestamp();
		if(start == null) {
			Long end = now;
			start = now - 2592000;
			return mapper.statistics(start, end);
		} else {
			Long end = start + 2592000;
			if(now < end) {
				end = now;
			}
			return mapper.statistics(start, end);
		}
	}
	
	/**
	 * 获得可以自动收获的订单
	 * @param shipments
	 * @return
	 */
	public List<IdeaOrder> getGainGoods(long shipments) {
		return mapper.getGainGoods(shipments);
	}
}
