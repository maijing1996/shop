package com.shop.controller.wechat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.shop.model.dto.wechat.PageResponse;
import com.shop.model.po.IdeaTopic;
import com.shop.model.po.IdeaTopicCategory;
import com.shop.service.IdeaTopicCategoryService;
import com.shop.service.IdeaTopicService;
import com.shop.util.DateUtil;

@Controller
@ResponseBody
@RequestMapping("/")
public class TopicController {

	@Autowired
	private IdeaTopicService ideaTopicService;
	@Autowired
	private IdeaTopicCategoryService ideaTopicCategoryService;
	
	/**
	 * 初始化详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/api_topic/{id}", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiTopic(@PathVariable Long id) {
		IdeaTopic ideaTopic = ideaTopicService.getById(id);
		return JSONObject.toJSONString(ideaTopic);
	}
	
	/**
	 * 初始化专题
	 * 
	 * @param size
	 * @param stype
	 * @return
	 */
	@RequestMapping(value="/api_topic", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String listTopic(@RequestParam(name="cat_id") Long catId, @RequestParam(name="page", required=false) Integer page,
			@RequestParam(name="size", required=false) Integer size) {
		PageResponse pageResponse = new PageResponse();
		
		IdeaTopicCategory ideaTopicCategory = ideaTopicCategoryService.getById(catId);
		PageInfo<IdeaTopic> pageInfo = null;
		if(ideaTopicCategory != null && ideaTopicCategory.getParent_id() == 0) {
			pageInfo = ideaTopicService.listInfoByCatId(page, size, catId);
		} else {
			IdeaTopic ideaTopic = new IdeaTopic();
			if(page != null && size != null) {
				ideaTopic.setRows(size);
				ideaTopic.setPage(page);
			}
			ideaTopic.setCat_id(catId);
			pageInfo = ideaTopicService.getPage(ideaTopic, "id desc");
			
		}
		List<IdeaTopic> list = pageInfo.getList();
		if(list != null && !list.isEmpty()) {
			List<Object> list3 = new ArrayList<>();
			for(int i=0; i < list.size(); i++) {
				IdeaTopic ideaTopic2 = list.get(i);
				Map<String, Object> map2 = new HashMap<String, Object>();
				
				map2.put("id", ideaTopic2.getId());
				map2.put("cat_id", ideaTopic2.getCat_id());
				map2.put("title", ideaTopic2.getTitle());
				map2.put("subtitle", ideaTopic2.getSubtitle());
				map2.put("source", ideaTopic2.getSource());
				map2.put("pic", ideaTopic2.getPic());
				map2.put("slide", ideaTopic2.getSlide());
				map2.put("starting_price", ideaTopic2.getStarting_price());
				map2.put("goods_ids", ideaTopic2.getGoods_ids());
				map2.put("info", ideaTopic2.getId());
				map2.put("url", ideaTopic2.getUrl());
				map2.put("keywords", ideaTopic2.getKeywords());
				map2.put("description", ideaTopic2.getDescription());
				map2.put("is_top", ideaTopic2.getIs_top());
				map2.put("is_show", ideaTopic2.getIs_show());
				map2.put("hits", ideaTopic2.getHits());
				map2.put("zan", ideaTopic2.getZan());
				map2.put("add_date", DateUtil.format(ideaTopic2.getAdd_date(), DateUtil.FORMAT_YYYY_MM_DD));
				
				list3.add(map2);
			}
			
			pageResponse.setTotal(pageInfo.getTotal());
			pageResponse.setCurrent_page(page);
			pageResponse.setData(list3);
			pageResponse.setPer_page(pageInfo.getFirstPage());
			pageResponse.setLast_page(pageInfo.getLastPage());
			return JSONObject.toJSONString(pageResponse);
		} else {
			pageResponse.setTotal(0L);
			pageResponse.setCurrent_page(page);
			pageResponse.setData(pageInfo.getList());
			pageResponse.setPer_page(pageInfo.getFirstPage());
			pageResponse.setLast_page(pageInfo.getLastPage());
			return JSONObject.toJSONString(pageResponse);
		}
	}
	
	/**
	 * 初始化专题分类
	 * @param cat_id
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/api_topic_category", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String listTopicCategory() {
		IdeaTopicCategory ideaTopicCategory = new IdeaTopicCategory();
		ideaTopicCategory.setParent_id(0L);
		List<IdeaTopicCategory> list = ideaTopicCategoryService.getAll(ideaTopicCategory, "id asc");
		
		if(list != null && !list.isEmpty()) {
			List<Object> li = new ArrayList<Object>();
			for(int i=0; i < list.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				IdeaTopicCategory category = list.get(i);
				IdeaTopicCategory ideaTopic2 = new IdeaTopicCategory();
				ideaTopic2.setParent_id(category.getId());
				List<IdeaTopicCategory> list2 = ideaTopicCategoryService.getAll(ideaTopic2, "id asc");
				List<Object> li2 = new ArrayList<Object>();
				Map<String, Object> map3 = new HashMap<String, Object>();
				map3.put("id", category.getId());
				map3.put("title", category.getTitle());
				li2.add(map3);
				for(IdeaTopicCategory category2 : list2) {
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("id", category2.getId());
					map2.put("title", category2.getTitle());
					li2.add(map2);
				}
				map.put("id", category.getId());
				map.put("title", category.getTitle());
				map.put("child", li2);
				li.add(map);
			}
			return JSONObject.toJSONString(li);
		} else {
			list = new ArrayList<IdeaTopicCategory>();
		}
		return JSONObject.toJSONString(list);
	}
}
