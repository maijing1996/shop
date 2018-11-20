package com.shop.controller.wechat;

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
import com.shop.model.po.IdeaArticle;
import com.shop.service.IdeaArticleService;
import com.shop.util.RegexUtil;

@Controller
@ResponseBody
@RequestMapping("/")
public class ArticleController {

	@Autowired
	private IdeaArticleService ideaArticleService;
	
	/**
	 * 查看文章详情
	 * @param open_id
	 * @return
	 */
	@RequestMapping(value="/api_article/{id}", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiTopic(@PathVariable("id") Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		IdeaArticle ideaArticle = ideaArticleService.getById(id);
		map.put("data", ideaArticle);
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 获取文章列表
	 * @param size
	 * @param cat
	 * @return
	 */
	@RequestMapping(value="/api_article", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiTopic(@RequestParam(name="size", required=false) Integer size, @RequestParam(name="cat", required=false) String cat) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(cat != null && RegexUtil.matches(RegexUtil.IDS_REGEX, cat)) {
			List<IdeaArticle> lsit = ideaArticleService.getByIds(cat);
			map.put("data", lsit);
		} else if(size != null) {
			List<IdeaArticle> lsit = ideaArticleService.getAll(null, size, null);
			map.put("data", lsit);
		} else {
			List<IdeaArticle> lsit = ideaArticleService.getAll(null, null);
			map.put("data", lsit);
		}
		return JSONObject.toJSONString(map);
	}
}
