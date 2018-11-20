package com.shop.controller.manager.article;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.shop.exception.BusinessException;
import com.shop.model.common.BaseResponse;
import com.shop.model.common.LayuiResponse;
import com.shop.model.dto.Article;
import com.shop.model.dto.ArticleModel;
import com.shop.model.po.IdeaArticle;
import com.shop.service.IdeaArticleService;
import com.shop.util.DateUtil;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/article/details")
public class IdeaArticleController {

	@Autowired
	private IdeaArticleService ideaArticleService;
	
	/**
	 * 获得文章信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<Article> pageInfo = ideaArticleService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
				StringUtil.strToLong(map.get("cat_id")), map.get("title"));
		if(pageInfo.getTotal() > 0){
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 更新信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody IdeaArticle ideaArticle) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaArticle.getId() != null) {
			if(ideaArticle.getId() == null) {
				return baseResponse.setError(500, "数据错误，请重新输入");
			}
			if(ideaArticle.getCat_id() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaArticle.getTitle() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaArticle.getIs_show() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			/*if(ideaArticle.getIs_top() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}*/
			if(ideaArticle.getSequence() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			ideaArticle.setAdd_date(DateUtil.getTimestamp());
			
			ideaArticleService.update(ideaArticle);
			return baseResponse.setValue("更新成功", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 添加信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody IdeaArticle ideaArticle) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaArticle.getId() != null) {
			return baseResponse.setError(500, "数据错误，请重新输入");
		}
		if(ideaArticle.getCat_id() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaArticle.getTitle() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaArticle.getIs_show() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		/*if(ideaArticle.getIs_top() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}*/
		if(ideaArticle.getSequence() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		ideaArticle.setAdd_date(DateUtil.getTimestamp());
		
		ideaArticleService.save(ideaArticle);
		return baseResponse.setValue("添加成功", null);
	}
	
	/**
	 * 删除单个信息
	 * @param id
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse delete(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			ideaArticleService.deleteById(map.get("id"));
			return baseResponse.setValue("信息已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/deletes", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deletes(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && RegexUtil.matches(RegexUtil.IDS_REGEX, map.get("ids"))) {
			ideaArticleService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("信息已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 获得所有文章信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info/all", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listAllInfo() {
		LayuiResponse layuiResponse = new LayuiResponse();
		List<ArticleModel> list = ideaArticleService.listAllInfo();
		if(list != null && !list.isEmpty()){
			return layuiResponse.setSuccess(null, list, 10000);
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
}
