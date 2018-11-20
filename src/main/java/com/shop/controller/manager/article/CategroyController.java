package com.shop.controller.manager.article;

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
import com.shop.model.dto.ArticleCategory;
import com.shop.model.po.IdeaArticleCategory;
import com.shop.service.IdeaArticleCategoryService;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/article/categroy")
public class CategroyController {

	@Autowired
	private IdeaArticleCategoryService ideaArticleCategoryService;
	
	/**
	 * 获取文章分类信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<ArticleCategory> pageInfo = ideaArticleCategoryService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
				StringUtil.strToLong(map.get("id")), StringUtil.strToLong(map.get("parentId")));
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
	public BaseResponse update(@RequestBody IdeaArticleCategory ideaArticleCategory) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaArticleCategory.getId() != null) {
			if(ideaArticleCategory.getTitle() == null && "".equals(ideaArticleCategory.getTitle().trim())) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaArticleCategory.getWap_title() == null && "".equals(ideaArticleCategory.getWap_title().trim())) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaArticleCategory.getIs_show() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaArticleCategory.getIs_top() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaArticleCategory.getSequence() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			
			ideaArticleCategory.setTitle(ideaArticleCategory.getTitle().trim());
			ideaArticleCategory.setWap_title(ideaArticleCategory.getWap_title().trim());
			ideaArticleCategoryService.update(ideaArticleCategory);
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
	public BaseResponse save(@RequestBody IdeaArticleCategory ideaArticleCategory) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaArticleCategory.getId() != null) {
			return baseResponse.setError(500, "数据错误，请重新输入");
		}
		if(ideaArticleCategory.getTitle() == null && "".equals(ideaArticleCategory.getTitle().trim())) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaArticleCategory.getWap_title() == null && "".equals(ideaArticleCategory.getWap_title().trim())) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaArticleCategory.getIs_show() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaArticleCategory.getIs_top() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaArticleCategory.getSequence() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		
		ideaArticleCategory.setTitle(ideaArticleCategory.getTitle().trim());
		ideaArticleCategory.setWap_title(ideaArticleCategory.getWap_title().trim());
		ideaArticleCategoryService.save(ideaArticleCategory);
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
			ideaArticleCategoryService.deleteById(map.get("id"));
			return baseResponse.setValue("信息已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
