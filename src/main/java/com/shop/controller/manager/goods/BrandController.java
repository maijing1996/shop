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
import com.shop.model.dto.Brand;
import com.shop.model.po.IdeaBrand;
import com.shop.service.IdeaBrandService;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/goods/brand")
public class BrandController {

	@Autowired
	private IdeaBrandService ideaBrandService;
	
	/**
	 * 获取品牌信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<Brand> pageInfo = ideaBrandService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
				map.get("title"));
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 修改品牌信息
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody IdeaBrand ideaBrand) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaBrand.getId() != null) {
			/*if(ideaBrand.getSequence() != null) {
				boolean bo = ideaBrandService.isExists(ideaBrand.getId(), ideaBrand.getSequence());
				if(bo) {
					return baseResponse.setError(405, "该排序数值已经存在，请重新输入！");
				}
			}*/
			ideaBrandService.update(ideaBrand);
			return baseResponse.setValue("商品品牌已经修改！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确！");
		}
	}
	
	/**
	 * 添加品牌信息
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody IdeaBrand ideaBrand) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaBrand.getTitle() == null) {
			return baseResponse.setError(404, "请输入商品品牌名称！");
		}
		if(ideaBrand.getSequence() == null) {
			return baseResponse.setError(404, "请输入排序的数值！");
		} else if(ideaBrand.getSequence() != null) {
			/*boolean bo = ideaBrandService.isExists(null, ideaBrand.getSequence());
			if(bo) {
				return baseResponse.setError(405, "该排序数值已经存在，请重新输入！");
			}*/
		}
		
		ideaBrandService.save(ideaBrand);
		return baseResponse.setValue("已经添加商品品牌信息！", null);
	}
	
	/**
	 * 删除单个品牌信息
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse delete(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			ideaBrandService.deleteById(map.get("id"));
			return baseResponse.setValue("品牌信息已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 批量删除品牌信息
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/deletes", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deletes(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && RegexUtil.matches(RegexUtil.IDS_REGEX, map.get("ids"))) {
			ideaBrandService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("品牌信息已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
