package com.shop.controller.wechat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.shop.exception.BusinessException;
import com.shop.model.dto.wechat.Cart;
import com.shop.model.dto.wechat.PageResponse;
import com.shop.model.po.IdeaAds;
import com.shop.model.po.IdeaCart;
import com.shop.model.po.IdeaCashGoods;
import com.shop.model.po.IdeaGoods;
import com.shop.model.po.IdeaGoodsCategory;
import com.shop.model.po.IdeaGoodsSpecItem;
import com.shop.model.po.IdeaGoodsSpecPrice;
import com.shop.model.po.IdeaPersonCategory;
import com.shop.model.po.IdeaUser;
import com.shop.service.IdeaAdsService;
import com.shop.service.IdeaCartService;
import com.shop.service.IdeaCashGoodsService;
import com.shop.service.IdeaGoodsCategoryService;
import com.shop.service.IdeaGoodsService;
import com.shop.service.IdeaGoodsSpecItemService;
import com.shop.service.IdeaGoodsSpecPriceService;
import com.shop.service.IdeaPersonCategoryService;
import com.shop.service.IdeaUserService;
import com.shop.util.DateUtil;
import com.shop.util.RedisUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/")
public class IdeaGoodsController {

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private IdeaGoodsService ideaGoodsService;
	@Autowired
	private IdeaCartService ideaCartService;
	@Autowired
	private IdeaUserService ideaUserService;
	@Autowired
	private IdeaAdsService ideaAdsService;
	@Autowired 
	private IdeaGoodsCategoryService ideaGoodsCategoryService;
	@Autowired 
	private IdeaPersonCategoryService ideaPersonCategoryService;
	@Autowired
	private IdeaGoodsSpecItemService ideaGoodsSpecItemService;
	@Autowired
	private IdeaGoodsSpecPriceService ideaGoodsSpecPriceService;
	@Autowired
	private IdeaCashGoodsService ideaCashGoodsService;
	
	/**
	 * 初始化商品分类及子分类
	 * @param type
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/api_goods_category", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String goodsCategory(@RequestParam(name="type", required=false) Integer type, @RequestParam(name="pid", required=false) Long pid,
			@RequestParam(name="id", required=false) Long id) {
		if(type == 1) {
			//商品分类
			if(pid != null) {
				//父级查看
				IdeaGoodsCategory ideaGoodsCategory = new IdeaGoodsCategory();
				if(id != null) {
					ideaGoodsCategory.setId(id);
				}
				ideaGoodsCategory.setParent_id(pid);
				IdeaGoodsCategory ideaGoodsCategory2 = new IdeaGoodsCategory();
				ideaGoodsCategory2.setId(pid);
				List<IdeaGoodsCategory> list = ideaGoodsCategoryService.getAll(ideaGoodsCategory2, "sequence ASC");
				List<IdeaGoodsCategory> list2 = ideaGoodsCategoryService.getAll(ideaGoodsCategory, "sequence ASC");
				if(list == null) {
					list = new ArrayList<IdeaGoodsCategory>();
				}
				if(list2 != null && !list2.isEmpty()) {
					for(IdeaGoodsCategory goodsCategory : list2) {
						list.add(goodsCategory);
					}
				}
				
				return JSONObject.toJSONString(list);
			} else if(id != null) {
				//子类查看
				IdeaGoodsCategory ideaGoodsCategory = ideaGoodsCategoryService.getById(id);
				if(ideaGoodsCategory.getParent_id() != 0) {
					IdeaGoodsCategory ideaGoodsCategory2 = new IdeaGoodsCategory();
					ideaGoodsCategory2.setParent_id(ideaGoodsCategory.getParent_id());
					List<IdeaGoodsCategory> list = ideaGoodsCategoryService.getAll(ideaGoodsCategory2, "sequence ASC");
					if(list == null) {
						list = new ArrayList<IdeaGoodsCategory>();
					}
					return JSONObject.toJSONString(list);
				} else {
					IdeaGoodsCategory ideaGoodsCategory2 = new IdeaGoodsCategory();
					ideaGoodsCategory2.setParent_id(ideaGoodsCategory.getId());
					List<IdeaGoodsCategory> list = ideaGoodsCategoryService.getAll(ideaGoodsCategory2, "sequence ASC");
					if(list == null) {
						list = new ArrayList<IdeaGoodsCategory>();
					}
					return JSONObject.toJSONString(list);
				}
			} else {
				List<String> list = new ArrayList<String>();
				list.add("参数错误");
				return JSONObject.toJSONString(list);
			}
		} else if(type == 2) {
			//人群分类
			if(pid != null) {
				//父级查看
				IdeaPersonCategory ideaPersonCategory = new IdeaPersonCategory();
				if(id != null) {
					ideaPersonCategory.setId(id);
				}
				ideaPersonCategory.setParent_id(pid);
				IdeaPersonCategory ideaPersonCategory2 = new IdeaPersonCategory();
				ideaPersonCategory2.setId(pid);
				List<IdeaPersonCategory> list = ideaPersonCategoryService.getAll(ideaPersonCategory2, "sequence ASC");
				if(list == null) {
					list = new ArrayList<IdeaPersonCategory>();
				}
				List<IdeaPersonCategory> list2 = ideaPersonCategoryService.getAll(ideaPersonCategory, "sequence ASC");
				if(list2 != null && !list2.isEmpty()) {
					list.addAll(list2);
				}
				return JSONObject.toJSONString(list);
			} else if(id != null) {
				//子类查看
				IdeaPersonCategory personCategory = ideaPersonCategoryService.getById(id);
				if(personCategory.getParent_id() != 0) {
					IdeaPersonCategory ideaPersonCategory = new IdeaPersonCategory();
					ideaPersonCategory.setParent_id(personCategory.getParent_id());
					List<IdeaPersonCategory> list = ideaPersonCategoryService.getAll(ideaPersonCategory, "sequence ASC");
					if(list == null) {
						list = new ArrayList<IdeaPersonCategory>();
					}
					return JSONObject.toJSONString(list);
				} else {
					IdeaPersonCategory ideaPersonCategory = new IdeaPersonCategory();
					ideaPersonCategory.setParent_id(personCategory.getId());
					List<IdeaPersonCategory> list = ideaPersonCategoryService.getAll(ideaPersonCategory, "sequence ASC");
					if(list == null) {
						list = new ArrayList<IdeaPersonCategory>();
					}
					return JSONObject.toJSONString(list);
				}
			} else {
				List<String> list = new ArrayList<String>();
				list.add("参数错误");
				return JSONObject.toJSONString(list);
			}
		} else {
			List<String> list = new ArrayList<>();
			list.add("参数有误");
			return JSONObject.toJSONString(list);
		}
	}
	
	/**
	 * 初始积分商品列表
	 * 99编号就是积分规则的Id
	 * @param request
	 * @param openId
	 * @param page
	 * @param size
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/api_credits", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiCredits(HttpServletRequest request, @RequestParam(name="openId", required=false) String openId,
			@RequestParam(name="page", required=false) Integer page, @RequestParam(name="size", required=false) Integer size, 
			@RequestParam(name="type") Integer type) {
		PageResponse pageResponse = new PageResponse();
		Map<String, Object> map = new HashMap<String, Object>();
		
		IdeaGoods ideaGoods = new IdeaGoods();
		ideaGoods.setIs_delete(0);
		ideaGoods.setIs_sale(1);
		ideaGoods.setIs_jf(1);
		if(type == 1) {
			//综合排行，按兑换次数排行
			PageInfo<IdeaGoods> pageInfo = ideaGoodsService.getPopularity(page, size);
			pageResponse.setCurrent_page(pageInfo.getPageNum());
			pageResponse.setLast_page(pageInfo.getLastPage());
			pageResponse.setPer_page(pageInfo.getFirstPage());
			pageResponse.setTotal(pageInfo.getTotal());
			map.put("goods", pageInfo.getList());//商品信息
		} else if(type == 2) {
			//价钱升序
			if(page != null && size != null) {
				ideaGoods.setRows(size);
				ideaGoods.setPage(page);
			}
			String orderBy = "price ASC";
			PageInfo<IdeaGoods> pageInfo = ideaGoodsService.getPage(ideaGoods, orderBy);
			pageResponse.setCurrent_page(pageInfo.getPageNum());
			pageResponse.setLast_page(pageInfo.getLastPage());
			pageResponse.setPer_page(pageInfo.getFirstPage());
			pageResponse.setTotal(pageInfo.getTotal());
			map.put("goods", pageInfo.getList());//商品信息
		} else if(type == 3){
			//价钱降序
			if(page != null && size != null) {
				ideaGoods.setRows(size);
				ideaGoods.setPage(page);
			}
			String orderBy = "price DESC";
			PageInfo<IdeaGoods> pageInfo = ideaGoodsService.getPage(ideaGoods, orderBy);
			pageResponse.setCurrent_page(pageInfo.getPageNum());
			pageResponse.setLast_page(pageInfo.getLastPage());
			pageResponse.setPer_page(pageInfo.getFirstPage());
			pageResponse.setTotal(pageInfo.getTotal());
			map.put("goods", pageInfo.getList());//商品信息
		} else if(type == 4){
			//最新上架商品
			if(page != null && size != null) {
				ideaGoods.setRows(size);
				ideaGoods.setPage(page);
			}
			String orderBy = "id DESC";
			PageInfo<IdeaGoods> pageInfo = ideaGoodsService.getPage(ideaGoods, orderBy);
			pageResponse.setCurrent_page(pageInfo.getPageNum());
			pageResponse.setLast_page(pageInfo.getLastPage());
			pageResponse.setPer_page(pageInfo.getFirstPage());
			pageResponse.setTotal(pageInfo.getTotal());
			map.put("goods", pageInfo.getList());//商品信息
		}
		
		//用户信息
		if(openId != null) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map1 = (Map<String, Object>) redisUtil.get(openId);
			IdeaUser ideaUser = ideaUserService.getById((Long) map1.get("userId"));
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("nickname", ideaUser.getOauth_nickname());
			mm.put("integral", ideaUser.getIntegral());
			map.put("user", mm);//用户信息
		} else {
			map.put("user", "");//用户信息
		}
		
		IdeaAds ideaAds = new IdeaAds();
		ideaAds.setType(1);
		ideaAds.setIs_show(1);
		List<IdeaAds> list = ideaAdsService.getAll(ideaAds, "sequence ASC");
		if(list != null && !list.isEmpty()){
			map.put("ads", list);//广告
		} else {
			map.put("ads", null);//广告
		}
		
		//兑换信息
		List<IdeaCashGoods> list2 = ideaCashGoodsService.getShowInfo(10);
		if(!list2.isEmpty()) {
			List<Object> lis = new ArrayList<Object>();
			for(int i=0; i < list2.size(); i++) {
				IdeaCashGoods cashGoods = list2.get(i);
				Map<String, Object> ma = new HashMap<String, Object>();
				ma.put("user_name", cashGoods.getNickname());
				ma.put("goods_name", cashGoods.getGoods_name());
				ma.put("amount", cashGoods.getAmount());
				
				lis.add(ma);
			}
			map.put("cash", lis);
		} else {
			List<String> lis = new ArrayList<String>();
			map.put("cash", lis);
		}
		
		pageResponse.setData(map);
		pageResponse.setCode(200);
		return JSONObject.toJSONString(pageResponse);
	}
	
	/**
	 * 初始化详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/api_goods/{id}", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String getGoods(@PathVariable("id") Long id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		IdeaGoods ideaGoods = ideaGoodsService.getById(id);
		if(ideaGoods != null) {
			mp.put("id", ideaGoods.getId());
			mp.put("cat_id", ideaGoods.getCat_id());
			mp.put("shop_id", ideaGoods.getShop_id());
			mp.put("brand_id", ideaGoods.getBrand_id());
			mp.put("title", ideaGoods.getTitle());
			mp.put("subtitle", ideaGoods.getSubtitle());
			mp.put("sn", ideaGoods.getSn());
			mp.put("spu", ideaGoods.getSpu());
			mp.put("sku", ideaGoods.getSku());
			mp.put("pic", ideaGoods.getPic());
			mp.put("slide", ideaGoods.getSlide());
			mp.put("markey_price", ideaGoods.getMarkey_price());
			mp.put("price", ideaGoods.getPrice());
			mp.put("cost_price", ideaGoods.getCost_price());
			mp.put("stock", ideaGoods.getStock());
			mp.put("weight", ideaGoods.getWeight());
			mp.put("initial_sales", ideaGoods.getInitial_sales());
			mp.put("sales", ideaGoods.getSales());
			mp.put("info", ideaGoods.getInfo());
			mp.put("keywords", ideaGoods.getKeywords());
			mp.put("description", ideaGoods.getDescription());
			mp.put("tc", ideaGoods.getTc());
			mp.put("jf", ideaGoods.getJf());
			mp.put("use_jf", ideaGoods.getUse_jf());
			mp.put("other_info", ideaGoods.getOther_info());
			mp.put("spec_type", ideaGoods.getSpec_type());
			mp.put("prom_type", ideaGoods.getProm_type());
			mp.put("prom_id", ideaGoods.getProm_id());
			mp.put("hits", ideaGoods.getHits());
			mp.put("sequence", ideaGoods.getSequence());
			mp.put("is_top", ideaGoods.getIs_top());
			mp.put("is_free", ideaGoods.getIs_free());
			mp.put("is_new", ideaGoods.getIs_new());
			mp.put("is_hot", ideaGoods.getIs_hot());
			mp.put("is_sale", ideaGoods.getIs_sale());
			mp.put("is_delete", ideaGoods.getIs_delete());
			mp.put("add_date", DateUtil.format(ideaGoods.getAdd_date() * 1000, DateUtil.FORMAT_YYYY_MM_DD));
			mp.put("is_jf", ideaGoods.getIs_jf());
			mp.put("person_id", ideaGoods.getPerson_id());
			mp.put("is_index", ideaGoods.getIs_index());
			mp.put("person_id", ideaGoods.getPerson_id());
			mp.put("is_index", ideaGoods.getIs_index());
			mp.put("is_restrict", ideaGoods.getIs_restrict());
			mp.put("restrict_unit", ideaGoods.getRestrict_unit());

			if(ideaGoods.getGg_info() != null && !"".equals(ideaGoods.getGg_info().trim())) {
				String[] info = ideaGoods.getGg_info().split(",");
				List<Object> list = new ArrayList<Object>();
				for(int i=0; i < info.length; i++) {
					String[] val = info[i].split(":");
					String[] arr = new String[2];
					if(val.length < 2) {
						arr[0] = val[i];
						arr[1] = null;//为空
					} else {
						arr[0] = val[i];
						arr[0] = val[i+1];
					}
					list.add(arr);
				}
				mp.put("gg_info", list);
			}
			
			//规格信息
			List<IdeaGoodsSpecPrice> list2 = ideaGoodsSpecPriceService.getAllInfo(ideaGoods.getId());
			if(list2 != null && !list2.isEmpty()) {
				String val = null;
				List<Object> list3 = new ArrayList<Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				
				for(IdeaGoodsSpecPrice goodsSpecPrice : list2) {
					String[] keys = goodsSpecPrice.getKey_name().split(":");
					IdeaGoodsSpecItem ideaGoodsSpecItem = ideaGoodsSpecItemService.getById(StringUtil.strToLong(goodsSpecPrice.getKey()));
					
					Map<String, Object> mg = new HashMap<String, Object>();
					mg.put("item_id", goodsSpecPrice.getKey());
					mg.put("item", keys[1]);
					if(ideaGoodsSpecItem != null && ideaGoodsSpecItem.getSpec_id() != null) {
						mg.put("spec_id", ideaGoodsSpecItem.getSpec_id());
						list3.add(mg);
					} else {
						mg.put("spec_id", "");
					}
					
					if(val == null) {
						val = keys[0];
					} else if(!keys[0].equals(val)) {
						map.put(keys[0], list3);
						val = keys[0];
						list3 = new ArrayList<Object>();
					}
				}
				if(!list3.isEmpty()) {
					map.put(val, list3);
				}
				
				mp.put("getspec", list2);
				mp.put("spec_list", map);
				mp.put("spec_count", map.size());
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				List<IdeaGoodsSpecPrice> list3 = new ArrayList<IdeaGoodsSpecPrice>();
				mp.put("getspec", list3);
				mp.put("spec_list", map);
				mp.put("spec_count", 0);;
			}
		} else {
			mp.put("msg", "商品不存在");
		}
		return JSONObject.toJSON(mp).toString();
	}
	
	/**
	 * 加入购物车
	 * @param id
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_cart", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String apiCart(@RequestBody Map<String, String> map) throws BusinessException {
		Map<String, String> map2 = new HashMap<String, String>();
		
		Long goodsId = StringUtil.strToLong(map.get("goods_id"));
		String specKey = map.get("spec_key"); 
		String keyName = map.get("key_name");
		IdeaCart ideaCart = ideaCartService.getCart(map.get("open_id"), goodsId, specKey, keyName);
		
		if(ideaCart == null) {
			//加入购物车
			IdeaGoods ideaGoods = ideaGoodsService.getById(StringUtil.strToLong(map.get("goods_id")));
			@SuppressWarnings("unchecked")
			Map<String, Object> map3 = (Map<String, Object>) redisUtil.get(map.get("open_id"));
			
			ideaCart = new IdeaCart();
			ideaCart.setAdd_date(DateUtil.getTimestamp());
			ideaCart.setAmount(StringUtil.strToInt(map.get("amount")));
			ideaCart.setGoods_id(goodsId);
			ideaCart.setPrice(ideaGoods.getPrice());
			ideaCart.setSession_id(map.get("open_id"));
			ideaCart.setSpec_key(map.get("spec_key"));
			ideaCart.setSpec_key_name(map.get("key_name"));
			ideaCart.setUser_id((Long) map3.get("userId"));
			ideaCartService.save(ideaCart);
			
			map2.put("code", "1001");
		} else {
			int ic = StringUtil.strToInt(map.get("amount")) + ideaCart.getAmount();
			ideaCart.setAmount(ic);
			ideaCartService.update(ideaCart);
		}
		return JSONObject.toJSONString(map2);
	}
	
	/**
	 * 获取购物车列表
	 * @param open_id
	 * @return
	 */
	@RequestMapping(value="/api_cart", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiTopic(@RequestParam(name="open_id") String openId) {
		if(openId != null && !"".equals(openId) && !"undefined".equals(openId)) {
			List<Cart> list = ideaCartService.getCartDetails(openId);
			return JSONObject.toJSONString(list);
		} else {
			List<String> list = new ArrayList<String>();
			return JSONObject.toJSONString(list);
		}
	}
}
