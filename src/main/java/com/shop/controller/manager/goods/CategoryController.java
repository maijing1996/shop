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
import com.shop.model.dto.Category;
import com.shop.model.po.IdeaGoodsCategory;
import com.shop.service.IdeaGoodsCategoryService;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/goods/categroy")
public class CategoryController {
	
	@Autowired
	private IdeaGoodsCategoryService ideaGoodsCategoryService;
	
	/**
	 * 获得商品类型信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<Category> pageInfo = ideaGoodsCategoryService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
				StringUtil.strToLong(map.get("parent_id")));
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 修改商品类型信息
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		Long id = StringUtil.strToLong(map.get("id"));
		if(id != null) {
			IdeaGoodsCategory ideaGoodsCategory = new IdeaGoodsCategory();
			if(map.get("parent_id") != null) {
				ideaGoodsCategory.setParent_id(StringUtil.strToLong(map.get("parent_id")));
			}
			if(map.get("title") != null && !"".equals(map.get("title").trim())) {
				ideaGoodsCategory.setTitle(map.get("title"));
			}
			if(map.get("wap_title") != null && !"".equals(map.get("wap_title").trim())) {
				ideaGoodsCategory.setWap_title(map.get("wap_title"));
			}
			if(map.get("pic") != null && !"".equals(map.get("pic").trim())) {
				ideaGoodsCategory.setPic(map.get("pic"));
			}
			if(map.get("slide_pic") != null && !"".equals(map.get("slide_pic"))) {
				ideaGoodsCategory.setSlide_pic(map.get("slide_pic"));
			}
			if(map.get("info") != null && !"".equals(map.get("info"))) {
				ideaGoodsCategory.setInfo(map.get("info"));
			}
			if(map.get("keywords") != null && !"".equals(map.get("keywords"))) {
				ideaGoodsCategory.setKeywords(map.get("keywords"));
			}
			if(map.get("description") != null && !"".equals(map.get("description"))) {
				ideaGoodsCategory.setDescription(map.get("description"));
			}
			/*if(StringUtil.strToInt(map.get("sequence")) != null) {
				boolean res = ideaGoodsCategoryService.isExists(StringUtil.strToInt(map.get("sequence")));
				if(res) {
					return baseResponse.setError(405, "该排序数值已经存在，请重新输入！");
				} else {
					ideaGoodsCategory.setSequence(StringUtil.strToInt(map.get("sequence")));
				}
			}*/
			if(map.get("is_top") != null) {
				ideaGoodsCategory.setIs_top(StringUtil.strToInt(map.get("is_top")));
			}
			if(map.get("is_show") != null) {
				ideaGoodsCategory.setIs_show(StringUtil.strToInt(map.get("is_show")));
			}
			if(map.get("en_title") != null) {
				ideaGoodsCategory.setEn_title(map.get("en_title"));
			}
			if(map.get("custom_url") != null) {
				ideaGoodsCategory.setCustom_url(map.get("custom_url"));
			}
			
			ideaGoodsCategoryService.update(ideaGoodsCategory);
			return baseResponse.setValue("商品类型已经修改！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 添加商品类型信息
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		
		if(map.get("title") == null || "".equals(map.get("title").trim())) {
			return baseResponse.setError(404, "请输入名称");
		}
		if(map.get("wap_title") == null || "".equals(map.get("wap_title").trim())) {
			return baseResponse.setError(404, "请输入手机名称");
		}
		if(map.get("en_title") == null || "".equals(map.get("en_title").trim())) {
			return baseResponse.setError(404, "请输入英文名称");
		}
		if(map.get("custom_url") == null || "".equals(map.get("custom_url").trim())) {
			return baseResponse.setError(404, "请输入自定义跳转链接");
		}
		if(map.get("sequence") == null || StringUtil.strToInt(map.get("sequence")) <=0) {
			return baseResponse.setError(404, "请输入排序顺序");
		}/* else {
			boolean res = ideaGoodsCategoryService.isExists(StringUtil.strToInt(map.get("sequence")));
			if(res) {
				return baseResponse.setError(405, "该排序数值已经存在，请重新输入！");
			}
		}*/
		
		IdeaGoodsCategory ideaGoodsCategory = new IdeaGoodsCategory();
		ideaGoodsCategory.setTitle(map.get("title"));
		ideaGoodsCategory.setWap_title(map.get("wap_title"));
		ideaGoodsCategory.setPic(map.get("pic"));
		ideaGoodsCategory.setSlide_pic(map.get("slide_pic"));
		ideaGoodsCategory.setInfo(map.get("info"));
		ideaGoodsCategory.setKeywords(map.get("keywords"));
		ideaGoodsCategory.setDescription(map.get("description"));
		ideaGoodsCategory.setSequence(StringUtil.strToInt(map.get("sequence")));
		ideaGoodsCategory.setIs_top(StringUtil.strToInt(map.get("is_top")));
		ideaGoodsCategory.setIs_show(StringUtil.strToInt(map.get("is_show")));
		ideaGoodsCategory.setEn_title(map.get("en_title"));
		ideaGoodsCategory.setCustom_url(map.get("custom_url"));
		if(map.get("parent_id") != null) {
			ideaGoodsCategory.setParent_id(StringUtil.strToLong(map.get("parent_id")));
		}

		ideaGoodsCategoryService.save(ideaGoodsCategory);
		return baseResponse.setValue("商品类型已经创建！", null);
	}
	
	/**
	 * 单个删除
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse delete(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			ideaGoodsCategoryService.deleteById(map.get("id"));
			return baseResponse.setValue("商品类型已经删除！", null);
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
			ideaGoodsCategoryService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("商品类型已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 获得商品类型信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info/all", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listAllInfo() {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<Category> pageInfo = ideaGoodsCategoryService.listAllInfo();
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
}
