package com.shop.controller.wechat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.shop.controller.express.KdniaoTrackQueryAPI;
import com.shop.exception.BusinessException;
import com.shop.model.dto.express.KdniaoTrackQueryResp;
import com.shop.model.dto.express.Traces;
import com.shop.model.dto.wechat.ObjectResponse;
import com.shop.model.po.IdeaDistributionCategroy;
import com.shop.model.po.IdeaGoods;
import com.shop.model.po.IdeaGoodsComment;
import com.shop.model.po.IdeaGoodsSpecPrice;
import com.shop.model.po.IdeaKdniaoTrack;
import com.shop.model.po.IdeaKdniaoTrackTraces;
import com.shop.model.po.IdeaOrder;
import com.shop.model.po.IdeaPickUp;
import com.shop.model.po.IdeaShopDispatching;
import com.shop.model.po.IdeaShopSites;
import com.shop.model.po.IdeaShopTransaction;
import com.shop.model.po.IdeaUser;
import com.shop.model.po.IdeaUserAddress;
import com.shop.service.IdeaCartService;
import com.shop.service.IdeaCouponService;
import com.shop.service.IdeaDistributionCategroyService;
import com.shop.service.IdeaExpressCompanyService;
import com.shop.service.IdeaGoodsCommentService;
import com.shop.service.IdeaGoodsService;
import com.shop.service.IdeaGoodsSpecPriceService;
import com.shop.service.IdeaKdniaoTrackService;
import com.shop.service.IdeaKdniaoTrackTracesService;
import com.shop.service.IdeaOrderService;
import com.shop.service.IdeaPickUpService;
import com.shop.service.IdeaShopDispatchingService;
import com.shop.service.IdeaShopSitesService;
import com.shop.service.IdeaShopTransactionService;
import com.shop.service.IdeaUserAddressService;
import com.shop.service.IdeaUserLevelService;
import com.shop.service.IdeaUserService;
import com.shop.service.common.WechatPayService;
import com.shop.util.DateUtil;
import com.shop.util.HttpUtil;
import com.shop.util.JsonUtil;
import com.shop.util.RedisUtil;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/")
public class IdeaOrderController {

	private Logger logegr = LoggerFactory.getLogger(IdeaOrderController.class);
	
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private IdeaCartService ideaCartService;
	@Autowired
	private IdeaPickUpService ideaPickUpService;
	@Autowired
	private IdeaUserAddressService ideaUserAddressService;
	@Autowired
	private IdeaOrderService ideaOrderService;
	@Autowired
	private IdeaGoodsService ideaGoodsService;
	@Autowired
	private IdeaUserLevelService ideaUserLevelService;
	@Autowired
	private IdeaGoodsSpecPriceService ideaGoodsSpecPriceService;
	@Autowired
	private IdeaUserService ideaUserService;
	@Autowired
	private IdeaCouponService ideaCouponService;
	@Autowired
	private IdeaGoodsCommentService ideaGoodsCommentService;
	@Autowired
	private IdeaShopDispatchingService ideaShopDispatchingService;
	@Autowired
	private IdeaShopTransactionService ideaShopTransactionService;
	@Autowired
	private IdeaDistributionCategroyService ideaDistributionCategroyService;
	@Autowired
	private WechatPayService wechatPayService;
	@Autowired
	private IdeaKdniaoTrackService ideaKdniaoTrackService;
	@Autowired
	private IdeaKdniaoTrackTracesService ideaKdniaoTrackTracesService;
	@Autowired
	private KdniaoTrackQueryAPI kdniaoTrackQueryAPI;
	@Autowired
	private IdeaShopSitesService ideaShopSitesService;
	@Autowired
	private IdeaExpressCompanyService ideaExpressCompanyService;
	
	/**
	 * 删除商品
	 * @param open_id
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_cart/{id}", method=RequestMethod.DELETE, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String delTopic(@PathVariable("id") Long id) throws BusinessException {
		ideaCartService.deleteById(id);
		return "true";
	}
	
	/**
	 * 获取自提点信息
	 * @return
	 */
	@RequestMapping(value="/api/com_get/getPickUp", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String getPickUp() {
		Map<String, Object> map = new HashMap<String, Object>();
		IdeaPickUp ideaPickUp = ideaPickUpService.getPicUp();
		map.put("data", ideaPickUp);
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 直接购买获取单个商品信息(商品ID,规格key,数量)
	 * @param goods_id
	 * @param spec_key
	 * @param amount
	 * @return
	 */
	@RequestMapping(value="/api/com_get/getOrderGoods", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String getOrderGoods(@RequestParam(name="goods_id") Long goodsId, @RequestParam(name="spec_key") String specKey,
			@RequestParam(name="amount") Integer amount, @RequestParam(name="open_id", required=false) String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		IdeaGoods ideaGoods = ideaGoodsService.getById(goodsId);
		if(specKey != null) {
			IdeaGoodsSpecPrice ideaGoodsSpecPrice = ideaGoodsSpecPriceService.getInfoById(goodsId, specKey);
			map.put("spec_key_name", ideaGoodsSpecPrice.getKey_name());
			map.put("yprice", ideaGoodsSpecPrice.getPrice());
		} else {
			map.put("yprice", ideaGoods.getPrice());
			map.put("spec_key_name", "");
		}
		
		map.put("goods_id", ideaGoods.getId());
		map.put("amount", amount);
		map.put("spec_key", specKey);
		map.put("pic", ideaGoods.getPic());
		map.put("title", ideaGoods.getTitle());
		map.put("tc", ideaGoods.getTc());//获得的提出
		map.put("jf", ideaGoods.getJf());//获得的积分
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 获取订单商品订单价，运费，优惠，实付价等信息
	 * @param goods_price
	 * @param goods_id
	 * @param open_id
	 * @param goods_amount
	 * @return
	 */
	@RequestMapping(value="/api/com_get/getBuyInfo", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String getBuyInfo(@RequestParam(name="goods_price", required=false) String specIds, @RequestParam(name="goods_id") String goodsId,
			@RequestParam(name="open_id", required=false) String openId, @RequestParam(name="goods_amount", required=false) String goodsAmount) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map1 = (Map<String, Object>) redisUtil.get(openId);
		if(map1 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map1 = userMap;
			} else {
				List<Object> list = new ArrayList<Object>();
				map.put("send_price", 0);
				map.put("integral_exchange", 0);//积分兑换(1:开启 0:关闭)
				map.put("use_ratio_integral", 0);//积分使用比例
				map.put("order_price", 0);
				map.put("voucher_list", list);
				map.put("is_jf", 0);
				map.put("use_jf", 0);
				map.put("all_jf", 0);
				map.put("is_enough", 0);
				map.put("rebate", 0);
				map.put("rebate_price", 0);
				map.put("max_discount", 0);
				map.put("level", 0);
				map.put("code", 500);
				map.put("msg", "登录异常，该用户未授权登录");
				return JSONObject.toJSONString(map);
			}
		}
		
		IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map1.get("userId").toString()));
		if(goodsId != null && RegexUtil.matches(RegexUtil.ID_REGEX, goodsId)) {
			//积分购买及现金购买
			IdeaGoods ideaGoods = ideaGoodsService.getById(StringUtil.strToLong(goodsId));
			if(ideaGoods.getIs_restrict() == 1) {
				//价钱
				Double orderPrice = 0.0;
				if(specIds != null && !"".equals(specIds)) {
					IdeaGoodsSpecPrice ideaGoodsSpecPrice = ideaGoodsSpecPriceService.getInfoById(StringUtil.strToLong(goodsId), specIds);
					orderPrice = orderPrice + ideaGoodsSpecPrice.getPrice() * StringUtil.strToInt(goodsAmount);
				} else {
					orderPrice = ideaGoods.getPrice() * StringUtil.strToInt(goodsAmount);
				}
				
				//积分抵扣
				int integral_exchange = 0;
				IdeaShopTransaction ideaShopTransaction = ideaShopTransactionService.getById(1L);
				if(ideaShopTransaction.getIntegral_exchange() == 1) {
					integral_exchange = ideaShopTransaction.getMini_integral();
				}
				
				//邮费
				Double send_price = 0.0;
				if(ideaShopTransaction.getFull_cut() > ideaGoods.getPrice()) {
					IdeaShopDispatching ideaShopDispatching = ideaShopDispatchingService.getById(1L);
					if(ideaShopDispatching.getExpress_switch() == 1) {
						if(ideaShopDispatching.getFirst_price()*1000 < ideaGoods.getWeight() * StringUtil.strToInt(goodsAmount)) {
							Double dou = ideaShopDispatching.getSecond_price() * (ideaGoods.getWeight() * StringUtil.strToInt(goodsAmount) - ideaShopDispatching.getFirst_price()*1000)/1000;
							send_price = ideaShopDispatching.getFirst_price() + dou;
						} else {
							send_price = ideaShopDispatching.getFirst_price();
						}
					}
				}
				
				List<String> list = new ArrayList<String>();
				map.put("max_discount", 0);
				map.put("level", 0);
				map.put("send_price", send_price);//邮费
				map.put("integral_exchange", integral_exchange);//最高可使用比例
				map.put("use_ratio_integral", ideaShopTransaction.getUse_ratio_integral());//积分使用比例
				map.put("order_price", orderPrice);
				map.put("voucher_list", list);
				map.put("is_jf", 0);
				map.put("use_jf", 0);
				map.put("all_jf", ideaUser.getIntegral());
				map.put("is_enough", 0);
				map.put("discount", 0);
				map.put("rebate_price", 0);
				
				return JSONObject.toJSONString(map);
			}
			if(ideaGoods.getIs_jf() == 0) {
				//折扣
				int discount = 0;
				int level =0;
				Double rebate_price = 0.0, order_price=0.0;
				
				//价钱
				if(specIds != null && !"".equals(specIds)) {
					IdeaGoodsSpecPrice ideaGoodsSpecPrice = ideaGoodsSpecPriceService.getInfoById(StringUtil.strToLong(goodsId), specIds);
					order_price = order_price + ideaGoodsSpecPrice.getPrice() * StringUtil.strToInt(goodsAmount);
				} else {
					order_price = ideaGoods.getPrice() * StringUtil.strToInt(goodsAmount);
				}
				
				//获得折扣
				if(ideaUser.getIs_fx() == 1) {
					level = 1;
					IdeaDistributionCategroy ideaDistributionCategroy = ideaDistributionCategroyService.getById(ideaUser.getDistribution_lev());
					discount = 100 - ideaDistributionCategroy.getBuy_discount();
					rebate_price = order_price * discount / 100;
				} else {
					Map<String, Object> map2 = ideaUserLevelService.getLevelRebate(openId);
					discount = 100 - StringUtil.strToInt(map2.get("rebate").toString());
					rebate_price = order_price * discount / 100;
				}
				
				//优惠
				List<Map<String, Object>> list = ideaCouponService.getUseVoucher(StringUtil.strToLong(map1.get("userId").toString()), order_price);
				if(list == null) {
					list = new ArrayList<Map<String, Object>>();
				}
				
				//其他内容
				int integral_exchange = 0;
				IdeaShopTransaction ideaShopTransaction = ideaShopTransactionService.getById(1L);
				if(ideaShopTransaction.getIntegral_exchange() == 1) {
					integral_exchange = ideaShopTransaction.getMini_integral();
				}
				
				//邮费
				Double send_price = 0.0;
				if(ideaShopTransaction.getFull_cut() > ideaGoods.getPrice()) {
					IdeaShopDispatching ideaShopDispatching = ideaShopDispatchingService.getById(1L);
					if(ideaShopDispatching.getExpress_switch() == 1) {
						if(ideaShopDispatching.getFirst_price()*1000 < ideaGoods.getWeight()) {
							Double dou = ideaShopDispatching.getSecond_price() * (ideaGoods.getWeight() - ideaShopDispatching.getFirst_price()*1000)/1000;
							send_price = ideaShopDispatching.getFirst_price() + dou;
						} else {
							send_price = ideaShopDispatching.getFirst_price();
						}
					}
				}
				
				map.put("max_discount", 40);
				map.put("level", level);
				map.put("send_price", send_price);//邮费
				map.put("integral_exchange", integral_exchange);//最高可使用比例
				map.put("use_ratio_integral", ideaShopTransaction.getUse_ratio_integral());//积分使用比例
				map.put("order_price", order_price);
				map.put("voucher_list", list);
				map.put("is_jf", 0);
				map.put("use_jf", 0);
				map.put("all_jf", ideaUser.getIntegral());
				map.put("is_enough", 0);
				map.put("discount", discount);
				map.put("rebate_price", rebate_price);
			} else {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
				Double send_price = 0.0;
				int use_jf=0, all_jf=0, is_enough=0;
				use_jf = StringUtil.strToInt(goodsAmount) * ideaGoods.getUse_jf();
				all_jf = use_jf;
				
				//邮费
				IdeaShopDispatching ideaShopDispatching = ideaShopDispatchingService.getById(1L);
				if(ideaShopDispatching.getExpress_switch() == 1) {
					if(ideaShopDispatching.getFirst_price()*1000 < ideaGoods.getWeight()) {
						Double dou = ideaShopDispatching.getSecond_price() * (ideaGoods.getWeight() - ideaShopDispatching.getFirst_price()*1000)/1000;
						send_price = ideaShopDispatching.getFirst_price() + dou;
					} else {
						send_price = ideaShopDispatching.getFirst_price();
					}
				}
				
				//是否够积分
				if(ideaUser.getIntegral() > use_jf) {
					is_enough = 1;
				}
				
				map.put("max_discount", 0);
				map.put("level", 0);
				map.put("send_price", send_price);//邮费
				map.put("integral_exchange", 0);//最高可使用比例
				map.put("use_ratio_integral", 0);//积分使用比例
				map.put("order_price", 0);
				map.put("voucher_list", list);
				map.put("is_jf", 1);
				map.put("use_jf", use_jf);
				map.put("all_jf", all_jf);
				map.put("is_enough", is_enough);
				map.put("discount", 0);
				map.put("rebate_price", 0);
			}
		} else {
			//现金购买
			Double weight = 0.0;
			Double orderPrice = 0.0;
			String[] amountArr = goodsAmount.split(",");
			String[] ids = goodsId.split(",");
			String[] spec = specIds.split(",");
			List<IdeaGoods> list2 = ideaGoodsService.getByIds(goodsId);
			for(int i=0; i < list2.size(); i++) {
				IdeaGoods ideaGoods = list2.get(i);
				for(int j=0; j < list2.size(); j++) {
					if(ideaGoods.getId() == StringUtil.strToLong(ids[j])) {
						if("-1".equals(spec[j])) {
							//拿商品均市场价
							orderPrice = orderPrice + ideaGoods.getPrice() * StringUtil.strToInt(amountArr[j]);
							weight = weight + ideaGoods.getWeight() * StringUtil.strToInt(amountArr[j]);
						} else {
							//拿去规格价钱
							IdeaGoodsSpecPrice ideaGoodsSpecPrice = ideaGoodsSpecPriceService.getInfoById(ideaGoods.getId(), spec[j]);
							orderPrice = orderPrice + ideaGoodsSpecPrice.getPrice() * StringUtil.strToInt(amountArr[j]);
							weight = weight + ideaGoods.getWeight() * StringUtil.strToInt(amountArr[j]);
						}
					}
				}
			}
			
			//优惠券
			List<Map<String, Object>> list = ideaCouponService.getUseVoucher(StringUtil.strToLong(map1.get("userId").toString()), orderPrice);
			if(list == null) {
				list = new ArrayList<Map<String, Object>>();
			}
			
			//折扣
			int level =0;//是否是分销商
			int discount = 0;
			Double rebate_price = 0.0;
			if(ideaUser.getIs_fx() == 1) {
				IdeaDistributionCategroy ideaDistributionCategroy = ideaDistributionCategroyService.getById(ideaUser.getDistribution_lev());
				discount = 100 - ideaDistributionCategroy.getBuy_discount();
				rebate_price = orderPrice * discount / 100;
				level = 1;
			} else {
				Map<String, Object> map2 = ideaUserLevelService.getLevelRebate(openId);
				discount = 100 - StringUtil.strToInt(map2.get("rebate").toString());
				rebate_price = orderPrice * discount / 100;
			}
			
			//其他内容
			int integral_exchange = 0;
			IdeaShopTransaction ideaShopTransaction = ideaShopTransactionService.getById(1L);
			if(ideaShopTransaction.getIntegral_exchange() == 1) {
				integral_exchange = ideaShopTransaction.getMini_integral();
			}
			
			//邮费
			Double send_price = 0.0;
			if(ideaShopTransaction.getFull_cut() > orderPrice) {
				IdeaShopDispatching ideaShopDispatching = ideaShopDispatchingService.getById(1L);
				if(ideaShopDispatching.getExpress_switch() == 1) {
					if(ideaShopDispatching.getFirst_price()*1000 < weight) {
						Double dou = ideaShopDispatching.getSecond_price() * (weight - ideaShopDispatching.getFirst_price()*1000)/1000;
						send_price = ideaShopDispatching.getFirst_price() + dou;
					} else {
						send_price = ideaShopDispatching.getFirst_price();
					}
				}
			}
			
			map.put("max_discount", 40);
			map.put("level", level);
			map.put("send_price", send_price);
			map.put("integral_exchange", integral_exchange);
			map.put("use_ratio_integral", ideaShopTransaction.getUse_ratio_integral());//积分使用比例
			map.put("order_price", orderPrice);
			map.put("voucher_list", list);
			map.put("is_jf", 0);
			map.put("use_jf", 0);
			map.put("all_jf", ideaUser.getIntegral());
			map.put("is_enough", 0);
			map.put("discount", discount);
			map.put("rebate_price", rebate_price);
		}

		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 获取代金券列表
	 * 获取我的代金券及优惠券列表
	 * @param request
	 * @param openId
	 * @param type
	 * @param state
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/api_voucher", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String listVoucher(HttpServletRequest request, @RequestParam(name="open_id") String openId,
			@RequestParam(name="type", required=false) Integer type, @RequestParam(name="state", required=false) Integer state,
			@RequestParam(name="page", required=false) Integer page, @RequestParam(name="size", required=false) Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map1 = (Map<String, Object>) redisUtil.get(openId);
		if(map1 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map1 = userMap;
			} else {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				map.put("id", 0);
				map.put("amount", "不使用代金券");
				map.put("data", list);
				map.put("code", 204);
				map.put("msg", "登录异常，该用户未授权登录");
				return JSONObject.toJSONString(map);
			}
		}
		
		Long userId = (Long) map1.get("userId");
		if(type == null) {
			//获取代金券列表
			map.put("msg", "接口删除");
		} else {
			//获取我的代金券列表
			PageInfo<Map<String, Object>> pageInfo = ideaCouponService.listVoucher(page, size, userId, state);
			if(pageInfo != null && pageInfo.getTotal() > 0) {
				map.put("data", pageInfo.getList());
				map.put("code", 200);
				map.put("total", pageInfo.getTotal());
				map.put("per_page", pageInfo.getFirstPage());
				map.put("current_page", pageInfo.getPageNum());
				map.put("last_page", pageInfo.getLastPage());
			} else {
				map.put("data", pageInfo.getList());
				map.put("code", 204);
				map.put("total", pageInfo.getTotal());
				map.put("per_page", pageInfo.getFirstPage());
				map.put("current_page", pageInfo.getPageNum());
				map.put("last_page", pageInfo.getLastPage());
			}
		}
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 获取默认地址
	 * 获得地址列表
	 * @param open_id
	 * @param index
	 * @return
	 */
	@RequestMapping(value="/api_address", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String getAddress(@RequestParam(name="open_id") String openId, @RequestParam(name="index", required=false) Integer index) {
		if(index == null) {
			//获得地址列表
			List<IdeaUserAddress> list = ideaUserAddressService.getDefaultAddress(openId, null);
			return JSONObject.toJSONString(list);
		} else {
			//获取默认地址
			List<IdeaUserAddress> list = ideaUserAddressService.getDefaultAddress(openId, 1);
			return JSONObject.toJSONString(list);
		}
	}
	
	/**
	 * 提交订单
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_order", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String saveOrder(HttpServletRequest request, @RequestBody Map<String, String> map) throws BusinessException {
		Map<String, Object> map2 = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map1 = (Map<String, Object>) redisUtil.get(map.get("m_openid"));
		if(map1 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, map.get("m_openid"));
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map1 = userMap;
			} else {
				map2.put("code", "1000");
				map2.put("msg", "登录异常，该用户未授权登录");
				return JSONObject.toJSONString(map);
			}
		}
		
		Long id = 0L;
		if("1".equals(map.get("is_restrict"))){
			//限时购买
			int ic = ideaGoodsService.buyGoodsAmount(StringUtil.strToLong(map1.get("userId").toString()), StringUtil.strToLong(map.get("goods_id")));
			if(ic < 1) {
				map2.put("code", "1002");
				map2.put("data", "");
				map2.put("msg", "您已经参与了活动");
			} else {
				id = ideaOrderService.flashSale(StringUtil.strToLong(map1.get("userId").toString()), map, ic);
			}
		} else {
			//正常购买
			//资金购买及积分购买
			id = ideaOrderService.saveAll(StringUtil.strToLong(map1.get("userId").toString()), map);
		}
		
		if(id == 0) {
			Map<String, String> mm = new HashMap<String, String>();
			mm.put("sn", id.toString());
			map2.put("code", "1002");
			map2.put("data", "");
			map2.put("msg", "库存不足");
		} else {
			Map<String, String> mm = new HashMap<String, String>();
			mm.put("sn", id.toString());
			map2.put("code", "1001");
			map2.put("data", mm);
		}
		return JSONObject.toJSONString(map2);
	}
	
	/**
	 * 获得未支付的指定订单
	 * @param sn
	 * @param open_id
	 * @return
	 */
	@RequestMapping(value="/api/com_get/getOrderPrice", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String getOrderPrice(@RequestParam(name="sn") Long sn, @RequestParam(name="open_id") String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map1 = (Map<String, Object>) redisUtil.get(openId);
		if(map1 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map1 = userMap;
			} else {
				map.put("code", "1002");
				map.put("msg", "登录异常，该用户未授权登录");
				return JSONObject.toJSONString(map);
			}
		}
		
		IdeaOrder ideaOrder = ideaOrderService.getById(sn);
		if(ideaOrder != null) {
			ideaOrder.setPay_price(ideaOrder.getPay_price() + ideaOrder.getExpress_price());
			List<IdeaOrder> list2 = new ArrayList<IdeaOrder>();
			list2.add(ideaOrder);
			map.put("code", "1001");
			map.put("msg", "订单已返回");
			map.put("data", list2);
		} else {
			List<String> list2 = new ArrayList<String>();
			map.put("code", "1002");
			map.put("msg", "没有该订单");
			map.put("data", list2);
		}
		
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 微信立即支付
	 * @param open_id
	 * @param index
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/api/miniapp_pay/wx_pay", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String wxPay(HttpServletRequest request, @RequestParam(name="order_no") Long order_no, @RequestParam(name="open_id") String openId) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> map1 = (Map<String, Object>) redisUtil.get(openId);
		if(map1 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map1 = userMap;
			} else {
				Map<String, String> map = new HashMap<String, String>();
				map.put("code", "204");
				map.put("msg", "登录异常，该用户未授权登录");
				return JSONObject.toJSONString(map);
			}
		}
		
		String ip = HttpUtil.getClientIP(request);
		IdeaOrder ideaOrder = ideaOrderService.getById(order_no);
		JSONObject jsonObject = wechatPayService.setWechatVal(ip, openId, ideaOrder.getPay_price(), ideaOrder.getOrder_sn(), map1.get("userId").toString());
		return jsonObject.toString();
	}
	
	/*if(map1 == null) {
		IdeaUser ideaUser = ideaUserService.isExist(null, openId);
		if(ideaUser != null) {
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("unionId", ideaUser.getSession_key());
			userMap.put("userId", ideaUser.getId());
			redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
			
			map1 = userMap;
		} else {
			map.put("code", "1002");
			map.put("msg", "登录异常，该用户未授权登录");
			return JSONObject.toJSONString(map);
		}
	}*/
	
	/**
	 * 会员账户余额支付
	 * @param order_no
	 * @param open_id
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api/miniapp_pay/balance_pay", method=RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String balancePay(@RequestBody Map<String, String> map2) throws BusinessException {
		Map<String, String> map = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map1 = (Map<String, Object>) redisUtil.get(map2.get("openid"));
		if(map1 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, map2.get("openid"));
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map1 = userMap;
			} else {
				map.put("code", "1002");
				map.put("msg", "登录异常，该用户未授权登录");
				return JSONObject.toJSONString(map);
			}
		}
		
		map = ideaOrderService.balancePay(map2, StringUtil.strToLong(map1.get("userId").toString()));
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 商品评论接口
	 * @param order_no
	 * @param open_id
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_evaluate", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String apiEvaluate(@RequestBody Map<String, String> map) throws BusinessException {
		ObjectResponse objectResponse = new ObjectResponse();
		@SuppressWarnings("unchecked")
		Map<String, Object> map1 = (Map<String, Object>) redisUtil.get(map.get("open_id"));
		if(map1 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, map.get("open_id"));
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map1 = userMap;
			} else {
				objectResponse.setCode(204);
				objectResponse.setMsg("登录异常，该用户未授权登录");
				return JSONObject.toJSONString(objectResponse);
			}
		}
		
		IdeaOrder ideaOrder = ideaOrderService.getByOrderSn(map.get("order_no"));
		if(ideaOrder.getIs_comment() == 0) {
			ideaOrder.setIs_comment(1L);
			ideaOrderService.update(ideaOrder);
			
			IdeaGoodsComment ideaGoodsComment = new IdeaGoodsComment();
			ideaGoodsComment.setAdd_date(DateUtil.getTimestamp());
			ideaGoodsComment.setGoods_id(StringUtil.strToLong(map.get("goods_id")));
			ideaGoodsComment.setInfo(map.get("info"));
			ideaGoodsComment.setOrder_id(StringUtil.strToLong(map.get("order_id")));
			ideaGoodsComment.setRate(StringUtil.strToInt(map.get("rate")));
			ideaGoodsComment.setUser_id(StringUtil.strToLong(map.get("order_no")));
			//ideaGoodsComment.setIs_show(StringUtil.strToInt(map.get("is_show")));
			//ideaGoodsComment.setPic(map.get("pic"));
			//ideaGoodsComment.setSpec_item(map.get("spec_item"));

			ideaGoodsCommentService.save(ideaGoodsComment);
			objectResponse.setCode(200);
			objectResponse.setMsg("评价成功");
		} else {
			objectResponse.setCode(204);
			objectResponse.setMsg("订单已经评价");
		}
		return JSONObject.toJSONString(objectResponse);
	}
	
	/**
	 * 快递信息
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/api_express", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiExpress(@RequestParam(name="order_id") Long orderId, @RequestParam(name="open_id") String openId) throws BusinessException {
		ObjectResponse objectResponse = new ObjectResponse();
		@SuppressWarnings("unchecked")
		Map<String, Object> map1 = (Map<String, Object>) redisUtil.get(openId);
		if(map1 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map1 = userMap;
			} else {
				objectResponse.setCode(204);
				objectResponse.setMsg("登录异常，该用户未授权登录");
				return JSONObject.toJSONString(objectResponse);
			}
		}
		
		IdeaKdniaoTrack ideaKdniaoTrack = new IdeaKdniaoTrack();
		ideaKdniaoTrack.setOrder_no(orderId);
		List<IdeaKdniaoTrack> list = ideaKdniaoTrackService.getAll(ideaKdniaoTrack, null);
		if(list != null && !list.isEmpty()) {
			IdeaKdniaoTrack ideaKdniaoTrack2 = list.get(0);
			if("3".equals(ideaKdniaoTrack2.getState()) || "4".equals(ideaKdniaoTrack2.getState())) {
				IdeaKdniaoTrackTraces ideaKdniaoTrackTraces = new IdeaKdniaoTrackTraces();
				ideaKdniaoTrackTraces.setParent_id(ideaKdniaoTrack2.getId());
				List<IdeaKdniaoTrackTraces> list2 = ideaKdniaoTrackTracesService.getAll(ideaKdniaoTrackTraces, "num DESC");
				ideaKdniaoTrack2.setList(list2);
				
				objectResponse.setData(ideaKdniaoTrack2);
				objectResponse.setCode(200);
				objectResponse.setMsg("快递信息返回成功");
			} else {
				IdeaOrder ideaOrder = ideaOrderService.getById(orderId);
				if(ideaOrder != null) {
					if(ideaOrder.getExpress_title() != null && ideaOrder.getExpress_sn() != null) {
						try {
							String result = kdniaoTrackQueryAPI.getOrderTracesByJson(ideaOrder.getExpress_code(), ideaOrder.getExpress_sn());
							logegr.info("快递鸟返回来的数据：" + result);
							KdniaoTrackQueryResp kdniaoTrackQueryResp = JsonUtil.getObject(result, KdniaoTrackQueryResp.class);
							if(kdniaoTrackQueryResp != null && kdniaoTrackQueryResp.getSuccess()) {
								logegr.info("快递当前状态：" + kdniaoTrackQueryResp.getState());
								
								if(!ideaKdniaoTrack2.getState().equals(kdniaoTrackQueryResp.getState())) {
									ideaKdniaoTrack2.setState(kdniaoTrackQueryResp.getState());
									ideaKdniaoTrackService.update(ideaKdniaoTrack2);
								}
								
								IdeaKdniaoTrackTraces ideaKdniaoTrackTraces = new IdeaKdniaoTrackTraces();
								ideaKdniaoTrackTraces.setParent_id(ideaKdniaoTrack2.getId());
								List<IdeaKdniaoTrackTraces> list2 = ideaKdniaoTrackTracesService.getAll(ideaKdniaoTrackTraces, "num DESC");
								
								List<Traces> list3 = kdniaoTrackQueryResp.getTraces();
								List<IdeaKdniaoTrackTraces> list4 = new ArrayList<IdeaKdniaoTrackTraces>();
								if(list2.size() < list3.size()) {
									//int ic = list3.size() - list2.size();
									for(int i=list2.size(); i < list3.size(); i++) {
										Traces traces = list3.get(i);
										
										IdeaKdniaoTrackTraces ideaKdniaoTrackTraces2 = new IdeaKdniaoTrackTraces();
										ideaKdniaoTrackTraces2.setAccept_station(traces.getAcceptStation());
										ideaKdniaoTrackTraces2.setAccept_time(traces.getAcceptTime());
										ideaKdniaoTrackTraces2.setRemark(traces.getRemark());
										ideaKdniaoTrackTraces2.setParent_id(ideaKdniaoTrack2.getId());
										ideaKdniaoTrackTraces2.setNum(i+1);
										
										//状态设置
										String[] arr = traces.getAcceptStation().split("：|:");
										if("到达".equals(arr[0])) {
											ideaKdniaoTrackTraces2.setNumber(3);
										} else if("已签收 ".equals(arr[0])) {
											ideaKdniaoTrackTraces2.setNumber(2);
										} else {
											ideaKdniaoTrackTraces2.setNumber(1);
										}
										
										list4.add(ideaKdniaoTrackTraces2);
									}
									ideaKdniaoTrackTracesService.insertList(list4);
									
									IdeaKdniaoTrackTraces ideaKdniaoTrackTraces2 = new IdeaKdniaoTrackTraces();
									ideaKdniaoTrackTraces2.setParent_id(ideaKdniaoTrack2.getId());
									List<IdeaKdniaoTrackTraces> list5 =  ideaKdniaoTrackTracesService.getAll(ideaKdniaoTrackTraces2, "number DESC");
									ideaKdniaoTrack2.setList(list5);
									
									objectResponse.setData(ideaKdniaoTrack2);
									objectResponse.setCode(200);
									objectResponse.setMsg("快递信息返回成功");
								} else {
									ideaKdniaoTrack2.setList(list2);

									objectResponse.setData(ideaKdniaoTrack2);
									objectResponse.setCode(200);
									objectResponse.setMsg("快递信息返回成功");
								}
							} else {
								objectResponse.setCode(204);
								objectResponse.setMsg("没有快递信息");
							}
						} catch (Exception e) {
							logegr.error("快递鸟返回来的数据解析出错" + JSONObject.toJSONString(e));
						}
					} else {
						objectResponse.setCode(204);
						objectResponse.setMsg("没有快递信息");
					}
				} else {
					objectResponse.setCode(204);
					objectResponse.setMsg("订单不存在");
				}
			}
		} else {
			IdeaOrder ideaOrder = ideaOrderService.getById(orderId);
			if(ideaOrder != null) {
				if(ideaOrder.getExpress_title() != null && ideaOrder.getExpress_sn() != null) {
					try {
						//暂时使用的逻辑，添加快递公司编码
						String code = ideaExpressCompanyService.getCodeByName(ideaOrder.getExpress_title());
						if(code != null) {
							ideaOrder.setExpress_code(code);
							ideaOrderService.update(ideaOrder);
						}
						
						String result = kdniaoTrackQueryAPI.getOrderTracesByJson(ideaOrder.getExpress_code(), ideaOrder.getExpress_sn());
						logegr.info("快递鸟返回来的数据：" + result);
						KdniaoTrackQueryResp kdniaoTrackQueryResp = JsonUtil.getObject(result, KdniaoTrackQueryResp.class);
						if(kdniaoTrackQueryResp != null && kdniaoTrackQueryResp.getSuccess()) {
							logegr.info("快递当前状态：" + kdniaoTrackQueryResp.getState());
							
							//获取公司电话号码
							IdeaShopSites ideaShopSites = ideaShopSitesService.getById(1L);
							
							IdeaKdniaoTrack ideaKdniaoTrack2 = new IdeaKdniaoTrack();
							ideaKdniaoTrack2.setTel(ideaShopSites.getPhone());
							ideaKdniaoTrack2.setPic(ideaOrder.getPic());
							ideaKdniaoTrack2.setEbusiness_id(kdniaoTrackQueryResp.getEBusinessID());
							ideaKdniaoTrack2.setLogistic_code(kdniaoTrackQueryResp.getLogisticCode());
							ideaKdniaoTrack2.setOrder_no(orderId);
							ideaKdniaoTrack2.setReason(kdniaoTrackQueryResp.getReason());
							ideaKdniaoTrack2.setRes(kdniaoTrackQueryResp.getSuccess());
							ideaKdniaoTrack2.setShipper_code(kdniaoTrackQueryResp.getShipperCode());
							ideaKdniaoTrack2.setShipper_name(ideaOrder.getExpress_title());
							ideaKdniaoTrack2.setState(kdniaoTrackQueryResp.getState());
							ideaKdniaoTrackService.save(ideaKdniaoTrack2);
							
							List<IdeaKdniaoTrackTraces> list2 = new ArrayList<IdeaKdniaoTrackTraces>();
							for(int i=0; i < kdniaoTrackQueryResp.getTraces().size(); i++) {
								Traces traces = kdniaoTrackQueryResp.getTraces().get(i);
								IdeaKdniaoTrackTraces ideaKdniaoTrackTraces2 = new IdeaKdniaoTrackTraces();
								ideaKdniaoTrackTraces2.setAccept_station(traces.getAcceptStation());
								ideaKdniaoTrackTraces2.setAccept_time(traces.getAcceptTime());
								ideaKdniaoTrackTraces2.setParent_id(ideaKdniaoTrack2.getId());
								ideaKdniaoTrackTraces2.setRemark(traces.getRemark());
								ideaKdniaoTrackTraces2.setNum(i+1);
								
								//状态设置
								String[] arr = traces.getAcceptStation().split("：|:");
								if("到达".equals(arr[0])) {
									ideaKdniaoTrackTraces2.setNumber(3);
								} else if("已签收 ".equals(arr[0])) {
									ideaKdniaoTrackTraces2.setNumber(2);
								} else {
									ideaKdniaoTrackTraces2.setNumber(1);
								}
								
								list2.add(ideaKdniaoTrackTraces2);
							}
							ideaKdniaoTrackTracesService.insertList(list2);
							
							ideaKdniaoTrack2.setList(list2);
							objectResponse.setData(ideaKdniaoTrack2);
							objectResponse.setCode(200);
							objectResponse.setMsg("快递信息返回成功");
						} else {
							objectResponse.setCode(204);
							objectResponse.setMsg("没有快递信息");
						}
					} catch (Exception e) {
						logegr.error("快递鸟返回来的数据解析出错" + JSONObject.toJSONString(e));
					}
				} else {
					objectResponse.setCode(204);
					objectResponse.setMsg("该订单未发货");
				}
			} else {
				objectResponse.setCode(204);
				objectResponse.setMsg("该订单不存在");
			}
		}
		return JSONObject.toJSONString(objectResponse);
	}
}
