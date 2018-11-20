package com.shop.controller.wechat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.shop.model.po.IdeaAds;
import com.shop.model.po.IdeaConfig;
import com.shop.model.po.IdeaCouponPerson;
import com.shop.model.po.IdeaGoods;
import com.shop.model.po.IdeaGoodsCategory;
import com.shop.model.po.IdeaIndex;
import com.shop.model.po.IdeaPersonCategory;
import com.shop.service.IdeaAdsService;
import com.shop.service.IdeaConfigService;
import com.shop.service.IdeaCouponPersonService;
import com.shop.service.IdeaGoodsCategoryService;
import com.shop.service.IdeaGoodsService;
import com.shop.service.IdeaIndexService;
import com.shop.service.IdeaPersonCategoryService;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/")
public class IndexController {

	@Autowired
	private IdeaGoodsService ideaGoodsService;
	@Autowired
	private IdeaAdsService ideaAdsService;
	@Autowired
	private IdeaIndexService ideaIndexService;
	@Autowired
	private IdeaConfigService ideaConfigService;
	@Autowired
	private IdeaPersonCategoryService ideaPersonCategoryService;
	@Autowired
	private IdeaGoodsCategoryService ideaGoodsCategoryService;
	@Autowired
	private IdeaCouponPersonService ideaCouponPersonService;
	@Autowired
	private IdeaConfigService iConfigService;
	
	/**
	 * 初始化幻灯片
	 * @param size
	 * @param stype
	 * @return
	 */
	@RequestMapping(value="/api_ads", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String listAds(@RequestParam(name="size", required=false) Integer size, @RequestParam(name="stype", required=false) Integer stype) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(stype != null && size != null) {
			List<IdeaAds> list = ideaAdsService.getShowAds(size, stype);
			map.put("total", size);
			map.put("per_page", 1);
			map.put("current_page", 1);
			map.put("last_page", 1);
			map.put("data", list);
		} else {
			List<IdeaAds> list = ideaAdsService.getAll(null, null);
			map.put("total", size);
			map.put("per_page", 1);
			map.put("current_page", 1);
			map.put("last_page", 1);
			map.put("data", list);
		}
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 初始化新品
	 * 初始化猜你喜欢商品
	 * 专题商品
	 * @param size
	 * @param stype
	 * @param ids
	 * @param sort
	 * @param type
	 * @param page
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value="/api_goods", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String listGoods(@RequestParam(name="size", required=false) Integer size, @RequestParam(name="stype", required=false) String stype,
			@RequestParam(name="ids", required=false) String ids, @RequestParam(name="sort", required=false) Long sort,
			@RequestParam(name="type", required=false) Integer type, @RequestParam(name="page", required=false) Integer page,
			@RequestParam(name="keyword", required=false) String keyword) {
		//type 1商品分类，2 人群分类
		Map<String, Object> map = new HashMap<String, Object>();
		if(keyword != null) {
			PageInfo<IdeaGoods> pageInfo = ideaPersonCategoryService.getGoodsByKeys(page, size, keyword);
			if(pageInfo != null && pageInfo.getTotal() > 0) {
				map.put("total", pageInfo.getTotal());
				map.put("per_page", pageInfo.getFirstPage());
				map.put("current_page", pageInfo.getPageNum());
				map.put("last_page", pageInfo.getLastPage());
				map.put("data", pageInfo.getList());
			} else {
				map.put("total", 0);
				map.put("per_page", 0);
				map.put("current_page", 0);
				map.put("last_page", 0);
				map.put("data", "没有数据");
			}
			return JSONObject.toJSONString(map);
		} else if(stype != null && stype.equals("new") && size != null) {
			//初始化新品
			List<IdeaGoods> list = ideaGoodsService.getNewGoods(size, 1);
			if(list == null) {
				list = new ArrayList<IdeaGoods>();
			}
			map.put("total", list.size());
			map.put("per_page", 1);
			map.put("current_page", 1);
			map.put("last_page", 1);
			map.put("data", list);
			return JSONObject.toJSONString(map);
		} else if(ids != null && RegexUtil.matches(RegexUtil.IDS_REGEX, ids)){
			//专题商品
			List<IdeaGoods> list = ideaGoodsService.getByIds(ids);
			if(list == null) {
				list = new ArrayList<IdeaGoods>();
			}
			map.put("total", list.size());
			map.put("per_page", 1);
			map.put("current_page", 1);
			map.put("last_page", 1);
			map.put("data", list);
			return JSONObject.toJSONString(map);
		} else if("undefined".equals(ids)) {
			//没有专题商品
			map.put("total", size);
			map.put("per_page", 1);
			map.put("current_page", 1);
			map.put("last_page", 1);
			map.put("data", null);
			return JSONObject.toJSONString(map);
		} else if(sort != null){
			//分类商品
			if(type == 1) {
				//商品分类
				IdeaGoodsCategory ideaGoodsCategory = ideaGoodsCategoryService.getById(sort);
				PageInfo<IdeaGoods> pageInfo = null;
				if(ideaGoodsCategory.getParent_id() == sort || ideaGoodsCategory.getParent_id() ==0) {
					pageInfo = ideaGoodsCategoryService.getGoodsById(page, size, ideaGoodsCategory.getId());
				} else {
					IdeaGoods ideaGoods = new IdeaGoods();
					ideaGoods.setCat_id(sort);
					if(page != null && size != null) {
						ideaGoods.setRows(size);
						ideaGoods.setPage(page);
					}
					pageInfo = ideaGoodsService.getPage(ideaGoods, "sequence ASC, id DESC");
				}
				
				map.put("total", pageInfo.getTotal());
				map.put("per_page", pageInfo.getFirstPage());
				map.put("current_page", pageInfo.getPageNum());
				map.put("last_page", pageInfo.getLastPage());
				map.put("data", pageInfo.getList());
				return JSONObject.toJSONString(map);
			} else if(type == 2) {
				//人群分类
				IdeaPersonCategory ideaPersonCategory = ideaPersonCategoryService.getById(sort);
				PageInfo<IdeaGoods> pageInfo = null;
				if(ideaPersonCategory.getParent_id() == sort || ideaPersonCategory.getParent_id() == 0) {
					pageInfo = ideaPersonCategoryService.getGoodsById(page, size, ideaPersonCategory.getId());
				} else {
					IdeaGoods ideaGoods = new IdeaGoods();
					ideaGoods.setPerson_id(sort);
					if(page != null && size != null) {
						ideaGoods.setRows(size);
						ideaGoods.setPage(page);
					}
					pageInfo = ideaGoodsService.getPage(ideaGoods, "sequence ASC, id DESC");
				}
				
				map.put("total", pageInfo.getTotal());
				map.put("per_page", pageInfo.getFirstPage());
				map.put("current_page", pageInfo.getPageNum());
				map.put("last_page", pageInfo.getLastPage());
				map.put("data", pageInfo.getList());
				return JSONObject.toJSONString(map);
			} else {
				map.put("total", 0);
				map.put("per_page", 0);
				map.put("current_page", 0);
				map.put("last_page", 0);
				map.put("data", "参数有误");
				return JSONObject.toJSONString(map);
			}
		} else {
			//初始化猜你喜欢商品，随机取4个
			List<IdeaGoods> list = ideaGoodsService.guessGoods();
			map.put("total", 4);
			map.put("per_page", 1);
			map.put("current_page", 1);
			map.put("last_page", 1);
			map.put("data", list);
			return JSONObject.toJSONString(map);
		}
	}
	
	/**
	 * 初始化首页
	 * @param size
	 * @param stype
	 * @return
	 */
	@RequestMapping(value="/api_index", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String getindex(@RequestParam(name="open_id", required=false) String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mmm = new HashMap<String, Object>();
		
		List<Map<String, Object>> modList = new ArrayList<Map<String, Object>>();
		IdeaIndex ideaIndex12 = new IdeaIndex();
		ideaIndex12.setType(2);
		List<IdeaIndex> lli = ideaIndexService.getAll(ideaIndex12, null);
		if(lli != null && !lli.isEmpty()) {
			for(int i=0; i < lli.size(); i++) {
				IdeaIndex ideaIndex = lli.get(i);
				Map<String, Object> mm = new HashMap<String, Object>();
				mm.put("id", ideaIndex.getId());
				mm.put("modPic", ideaIndex.getPic());
				mm.put("enTitle", ideaIndex.getTitle());
				mm.put("b_type", ideaIndex.getB_type());
				mm.put("value", ideaIndex.getValue());
				
				IdeaPersonCategory ideaPersonCategory = ideaPersonCategoryService.getById(StringUtil.strToLong(ideaIndex.getValue()));
				mm.put("modName", ideaPersonCategory.getTitle());
				List<IdeaGoods> listG = ideaGoodsService.IndexGoods(StringUtil.strToLong(ideaIndex.getValue()));
				if(listG != null && !listG.isEmpty()) {
					List<Object> listOb = new ArrayList<Object>();
					for(int j=0; j < listG.size(); j++) {
						IdeaGoods ideaGoods2 = listG.get(j);
						Map<String, Object> mg = new HashMap<String, Object>();
						mg.put("goodTitle", ideaGoods2.getTitle());
						mg.put("mainImg", ideaGoods2.getPic());
						mg.put("goodPrice", ideaGoods2.getPrice());
						mg.put("goodId", ideaGoods2.getId());
						listOb.add(mg);
					}
					mm.put("productList", listOb);
				} else {
					List<Object> listOb = new ArrayList<Object>();
					mm.put("productList", listOb);
				}
				modList.add(mm);
			}
		}
		
		List<Object> banner = new ArrayList<Object>();
		IdeaAds ideaAds2 = new IdeaAds();
		ideaAds2.setType(1);
		List<IdeaAds> ban = ideaAdsService.getAll(ideaAds2, "sequence ASC");
		if(ban != null && !ban.isEmpty()) {
			for(int i=0; i < ban.size(); i++) {
				IdeaAds ideaAds = ban.get(i);
				Map<String, Object> mg = new HashMap<String, Object>();
				mg.put("jumpUrl", ideaAds.getUrl());
				mg.put("ImgUrl", ideaAds.getPic());
				banner.add(mg);
			}
		}
			
		List<Map<String, Object>> articleMod = ideaIndexService.listInfoByType(3);
		
		List<IdeaGoods> listGoods = ideaGoodsService.guessGoods();
		List<Map<String, Object>> likeMod = new ArrayList<Map<String, Object>>();
		for(int i=0; i < listGoods.size(); i++) {
			IdeaGoods ideaGoods = listGoods.get(i);
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("goodTitle", ideaGoods.getTitle());
			mm.put("mainImg", ideaGoods.getPic());
			mm.put("goodPrice", ideaGoods.getPrice());
			mm.put("goodId", ideaGoods.getId());
			likeMod.add(mm);
		}
		
		List<Map<String, Object>> mc = ideaIndexService.listInfoByType(5);
		Map<String, Object> hotMod = mc.get(0);
		if(hotMod != null && !hotMod.isEmpty()) {
			IdeaGoods ideaGoods = new IdeaGoods();
			ideaGoods.setIs_hot(1);
			List<IdeaGoods> list = ideaGoodsService.getAll(ideaGoods, null);
			if(list != null && !list.isEmpty()) {
				List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
				for(int i=0; i < list.size(); i++) {
					IdeaGoods ideaGoods2 = list.get(i);
					Map<String, Object> mm = new HashMap<String, Object>();
					mm.put("goodTitle", ideaGoods2.getTitle());
					mm.put("mainImg", ideaGoods2.getPic());
					mm.put("goodPrice", ideaGoods2.getPrice());
					mm.put("goodId", ideaGoods2.getId());
					list2.add(mm);
				}
				hotMod.put("productList", list2);
			} else {
				List<String> lll = new ArrayList<String>();
				hotMod.put("productList", lll);
			}
		}
		
		List<Map<String, Object>> xxMod = ideaIndexService.listInfoByType(4);
		
		Map<String, Object> specialMod = new HashMap<String, Object>();
		List<Map<String, Object>> specialList = ideaIndexService.listInfoByType(1);
		if(specialList != null && !specialList.isEmpty()) {
			List<Map<String, Object>> all = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> eq = new ArrayList<Map<String, Object>>();
			for(int i=0; i < specialList.size(); i++) {
				Map<String, Object> mp = specialList.get(i);
				if(mp.get("id") != null && (Integer)mp.get("id") == 1) {
					all.add(mp);
				} else {
					eq.add(mp);
				}
			}
			specialMod.put("all", all);
			specialMod.put("eq", eq);
		}
		List<IdeaConfig> listAdd = ideaConfigService.getAllInfo();
		Map<String, String> address = new HashMap<String, String>();
		if(listAdd != null && !listAdd.isEmpty()) {
			for(int i=0; i < 2; i++) {
				IdeaConfig ideaConfig = listAdd.get(i);
				address.put(ideaConfig.getMkey(), ideaConfig.getValue());
			}
		}
		
		Map<String, String> mg = new HashMap<>();
		if(openId != null) {
			IdeaCouponPerson ideaCouponPerson = new IdeaCouponPerson();
			ideaCouponPerson.setOpen_id(openId);
			List<IdeaCouponPerson> list = ideaCouponPersonService.getAll(ideaCouponPerson, null);
			if(list != null && !list.isEmpty() && list.get(0).getIs_gain() == 1) {
				mg.put("pic", "");
				mg.put("type", "2");//type:1弹框，2不弹框
			} else {
				IdeaConfig ideaConfig = iConfigService.getById(3L);
				mg.put("pic", ideaConfig.getValue());
				mg.put("type", "1");//type:1弹框，2不弹框
			}
		}
		
		mmm.put("modList", modList);
		mmm.put("banner", banner);
		mmm.put("articleMod", articleMod);
		mmm.put("likeMod", likeMod);
		mmm.put("hotMod", hotMod);
		mmm.put("xxMod", xxMod);
		mmm.put("specialMod", specialMod);
		mmm.put("address", address);
		mmm.put("newPerCou", mg);
		
		map.put("code", 200);
		map.put("msg", "获取数据成功");
		map.put("data", mmm);
		return JSONObject.toJSONString(map);
	}
}
