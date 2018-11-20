package com.shop.controller.manager.goods;

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
import com.shop.model.dto.PersonCategory;
import com.shop.model.po.IdeaPersonCategory;
import com.shop.service.IdeaPersonCategoryService;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/goods/person")
public class PersonCategoryController {

	@Autowired
	private IdeaPersonCategoryService ideaPersonCategoryService;
	
	/**
	 * 获得多对象信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<PersonCategory> pageInfo = ideaPersonCategoryService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
				StringUtil.strToLong(map.get("id")));
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 获得所有的信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info/all", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listAllInfo() {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<PersonCategory> pageInfo = ideaPersonCategoryService.listAllInfo();
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 更新信息
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && map.get("id") != null) {
			IdeaPersonCategory ideaPersonCategory = new IdeaPersonCategory();
			ideaPersonCategory.setId(StringUtil.strToLong(map.get("id")));
			
			if(map.get("title") != null && !"".equals(map.get("title"))) {
				ideaPersonCategory.setTitle(map.get("title").trim());
			}
			if(map.get("wap_title") != null && !"".equals(map.get("wap_title"))) {
				ideaPersonCategory.setWap_title(map.get("wap_title").trim());
			}
			if(map.get("en_title") != null && !"".equals(map.get("en_title"))) {
				ideaPersonCategory.setEn_title(map.get("en_title").trim());
			}
			if(map.get("custom_url") != null && !"".equals(map.get("custom_url"))) {
				ideaPersonCategory.setCustom_url(map.get("custom_url").trim());
			}
			if(map.get("sequence") != null && StringUtil.strToInt(map.get("sequence")) > 0) {
				ideaPersonCategory.setSequence(StringUtil.strToInt(map.get("sequence")));
			}
			if(map.get("parent_id") != null) {
				ideaPersonCategory.setParent_id(StringUtil.strToLong(map.get("parent_id")));
			}
			if(map.get("keywords") != null && !"".equals(map.get("keywords"))) {
				ideaPersonCategory.setKeywords(map.get("keywords").trim());
			}
			if(map.get("info") != null && !"".equals(map.get("info"))) {
				ideaPersonCategory.setInfo(map.get("info").trim());
			}
			if(map.get("description") != null && !"".equals(map.get("description"))) {
				ideaPersonCategory.setDescription(map.get("description").trim());
			}
			if(map.get("pic") != null && !"".equals(map.get("pic"))) {
				ideaPersonCategory.setPic(map.get("pic").trim());
			}
			if(map.get("slide_pic") != null && !"".equals(map.get("slide_pic"))) {
				ideaPersonCategory.setSlide_pic(map.get("slide_pic").trim());
			}
			ideaPersonCategory.setIs_show(StringUtil.strToInt(map.get("is_show")));
			ideaPersonCategory.setIs_top(StringUtil.strToInt(map.get("is_top")));
			
			ideaPersonCategoryService.update(ideaPersonCategory);
			return baseResponse.setValue("修改成功", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 添加信息
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null) {
			if(map.get("title") == null || "".equals(map.get("title"))) {
				return baseResponse.setError(404, "操作不处理，参数缺失");
			}
			if(map.get("wap_title") == null || "".equals(map.get("wap_title"))) {
				return baseResponse.setError(404, "操作不处理，参数缺失");
			}
			if(map.get("en_title") == null || "".equals(map.get("en_title"))) {
				return baseResponse.setError(404, "操作不处理，参数缺失");
			}
			if(map.get("custom_url") == null || "".equals(map.get("custom_url"))) {
				return baseResponse.setError(404, "操作不处理，参数缺失");
			}
			if(map.get("sequence") == null || StringUtil.strToInt(map.get("sequence")) <= 0) {
				return baseResponse.setError(404, "操作不处理，参数缺失");
			}
			
			IdeaPersonCategory ideaPersonCategory = new IdeaPersonCategory();
			ideaPersonCategory.setCustom_url(map.get("custom_url").trim());
			ideaPersonCategory.setDescription(map.get("description").trim());
			ideaPersonCategory.setEn_title(map.get("en_title").trim());
			ideaPersonCategory.setInfo(map.get("info").trim());
			ideaPersonCategory.setIs_show(StringUtil.strToInt(map.get("is_show")));
			ideaPersonCategory.setIs_top(StringUtil.strToInt(map.get("is_top")));
			ideaPersonCategory.setKeywords(map.get("keywords").trim());
			ideaPersonCategory.setPic(map.get("pic").trim());
			ideaPersonCategory.setSequence(StringUtil.strToInt(map.get("sequence")));
			ideaPersonCategory.setSlide_pic(map.get("slide_pic").trim());
			ideaPersonCategory.setTitle(map.get("title").trim());
			ideaPersonCategory.setWap_title(map.get("wap_title").trim());
			if(map.get("parent_id") != null) {
				ideaPersonCategory.setParent_id(StringUtil.strToLong(map.get("parent_id")));
			}
			
			ideaPersonCategoryService.save(ideaPersonCategory);
			return baseResponse.setValue("添加成功", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
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
			ideaPersonCategoryService.deleteById(map.get("id"));
			return baseResponse.setValue("人群类型已经删除！", null);
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
			ideaPersonCategoryService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("人群类型已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
