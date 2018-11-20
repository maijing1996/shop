package com.shop.controller.manager.goods;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.shop.exception.BusinessException;
import com.shop.model.common.BaseResponse;
import com.shop.model.common.LayuiResponse;
import com.shop.model.dto.Goods;
import com.shop.model.po.IdeaGoods;
import com.shop.service.IdeaGoodsService;
import com.shop.util.DateUtil;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/goods/article")
public class GoodsController {

	@Autowired
	private IdeaGoodsService ideaGoodsService;
	
	/**
	 * 获得商品信息
	 * 是否是积分商品  1是 0否
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<Goods> pageInfo = ideaGoodsService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
				map.get("keys"), StringUtil.strToLong(map.get("cat_id")), 0);
		
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 更新商品信息
	 * 
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody IdeaGoods ideaGoods) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaGoods.getId() != null) {
			ideaGoodsService.updateAll(ideaGoods);
			return baseResponse.setValue("商品修改成功", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 添加商品信息
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody IdeaGoods ideaGoods) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		ideaGoods.setAdd_date(DateUtil.getTimestamp());
		ideaGoods.setIs_delete(0);
		ideaGoods.setSales(0);
		ideaGoods.setUse_jf(0);
		ideaGoods.setIs_jf(0);
		
		ideaGoodsService.saveAll(ideaGoods);
		return baseResponse.setValue("商品添加成功", null);
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
			ideaGoodsService.deleteById(map.get("id"));
			return baseResponse.setValue("商品信息已经删除！", null);
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
			ideaGoodsService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("商品信息已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 通过ids获得数据
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/ids", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse listByids(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && !"".equals(map.get("ids").trim())) {
			List<IdeaGoods> list = ideaGoodsService.getByIds(map.get("ids"));
			return baseResponse.setValue(null, list);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 商品分类
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/categroy", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String categroy(@RequestBody Map<String, String> map) throws BusinessException {
		Long catId = StringUtil.strToLong(map.get("cat_id"));
		if(catId != null) {
			List<IdeaGoods> list = ideaGoodsService.getCategroy(catId);
			return JSONObject.toJSONString(list);
		}
		return null;
	}
	
	/**
	 * 人群分类
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/person", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String person(@RequestBody Map<String, String> map) throws BusinessException {
		Long personId = StringUtil.strToLong(map.get("person_id"));
		if(personId != null) {
			List<IdeaGoods> list = ideaGoodsService.getPerson(personId);
			return JSONObject.toJSONString(list);
		}
		return null;
	}
}
