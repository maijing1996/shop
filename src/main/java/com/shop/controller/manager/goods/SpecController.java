package com.shop.controller.manager.goods;

import java.util.ArrayList;
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
import com.shop.model.dto.GoodsSpec;
import com.shop.model.dto.GoodsSpecInfo;
import com.shop.model.dto.SpecPrice;
import com.shop.model.po.IdeaGoodsSpec;
import com.shop.model.po.IdeaGoodsSpecItem;
import com.shop.service.IdeaGoodsSpecItemService;
import com.shop.service.IdeaGoodsSpecService;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/goods/spec")
public class SpecController {

	@Autowired
	private IdeaGoodsSpecService ideaGoodsSpecService;
	@Autowired
	private IdeaGoodsSpecItemService ideaGoodsSpecItemService;
	
	/**
	 * 获得所有规格信息
	 * @param goodsSpec
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody GoodsSpecInfo goodsSpec) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<GoodsSpec> pageInfo = ideaGoodsSpecService.listInfo(goodsSpec.getPage(), goodsSpec.getSize(), 	goodsSpec.getType_id());
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 修改商品规格信息
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody GoodsSpecInfo goodsSpec) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(goodsSpec.getId() != null) {
			IdeaGoodsSpec ideaGoodsSpec = new IdeaGoodsSpec();
			ideaGoodsSpec.setId(goodsSpec.getId());
			if(goodsSpec.getIs_search() != null) {
				ideaGoodsSpec.setIs_search(goodsSpec.getIs_search());
			}
			if(goodsSpec.getType_id() != null) {
				ideaGoodsSpec.setType_id(goodsSpec.getType_id());
			}
			if(goodsSpec.getTitle() != null) {
				ideaGoodsSpec.setTitle(goodsSpec.getTitle());
			}
			if(goodsSpec.getSequence() != null && goodsSpec.getSequence() > 0) {
				ideaGoodsSpec.setSequence(goodsSpec.getSequence());
			}
			if(goodsSpec.getItem() != null && !RegexUtil.matches(RegexUtil.SERIES_REGEX, goodsSpec.getItem())) {
				return baseResponse.setError(404, "规格输入有误，操作不处理！");
			}
			
			ideaGoodsSpecService.updateSpec(ideaGoodsSpec, goodsSpec.getItem());
			return baseResponse.setValue("商品已经修改！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 添加商品规格信息
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody GoodsSpecInfo goodsSpec) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(goodsSpec.getType_id() == null) {
			return baseResponse.setError(404, "请选择所属模型！");
		}
		if(goodsSpec.getTitle() == null || "".equals(goodsSpec.getTitle().trim())) {
			return baseResponse.setError(404, "请输入名称！");
		}
		if(goodsSpec.getSequence() == null || goodsSpec.getSequence() <= 0) {
			return baseResponse.setError(404, "排序输入有误，操作不处理！");
		}
		
		if(goodsSpec.getItem() != null && !RegexUtil.matches(RegexUtil.SERIES_REGEX, goodsSpec.getItem())) {
			return baseResponse.setError(404, "规格输入有误，操作不处理！");
		}
		
		IdeaGoodsSpec ideaGoodsSpec = new IdeaGoodsSpec();
		ideaGoodsSpec.setIs_search(goodsSpec.getIs_search());
		ideaGoodsSpec.setType_id(goodsSpec.getType_id());
		ideaGoodsSpec.setTitle(goodsSpec.getTitle());
		ideaGoodsSpec.setSequence(goodsSpec.getSequence());
		
		ideaGoodsSpecService.saveSpec(ideaGoodsSpec, goodsSpec.getItem());
		return baseResponse.setValue("商品规格已经添加！", null);
	}
	
	/**
	 * 删除单个商品规格
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse delete(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			ideaGoodsSpecService.deleteById(map.get("id"));
			return baseResponse.setValue("管理员角色已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 批量删除商品规格
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/deletes", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deletes(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && RegexUtil.matches(RegexUtil.IDS_REGEX, map.get("ids"))) {
			ideaGoodsSpecService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("管理员角色已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 规格详情
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/item", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse item(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && RegexUtil.matches(RegexUtil.IDS_REGEX2, map.get("ids"))) {
			String[] arr = map.get("ids").split(",");
			List<IdeaGoodsSpecItem> list = new ArrayList<IdeaGoodsSpecItem>();
			for(String id : arr) {
				IdeaGoodsSpecItem ideaGoodsSpecItem = new IdeaGoodsSpecItem();
				ideaGoodsSpecItem.setSpec_id(StringUtil.strToLong(id));
				List<IdeaGoodsSpecItem> list2 = ideaGoodsSpecItemService.getAll(ideaGoodsSpecItem, null);
				
				list.addAll(list2);
			}
			return baseResponse.setValue(null, list);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	@RequestMapping(value="/price", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse price(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null &&  map.get("goods_id") != null && map.get("type_id") != null) {
			IdeaGoodsSpec ideaGoodsSpec = new IdeaGoodsSpec();
			ideaGoodsSpec.setType_id(StringUtil.strToLong(map.get("type_id")));
			List<IdeaGoodsSpec> list = ideaGoodsSpecService.getAll(ideaGoodsSpec, null);
			
			if(list != null && !list.isEmpty()) {
				for(IdeaGoodsSpec ideaGoodsSpec2 : list) {
					IdeaGoodsSpecItem ideaGoodsSpecItem = new IdeaGoodsSpecItem();
					ideaGoodsSpecItem.setSpec_id(ideaGoodsSpec2.getId());
					List<IdeaGoodsSpecItem> list2 = ideaGoodsSpecItemService.getAll(ideaGoodsSpecItem, null);
					ideaGoodsSpec2.setList(list2);
				}
				
				List<SpecPrice> list2 = ideaGoodsSpecService.getAllInfoByGoods(StringUtil.strToLong(map.get("goods_id")));
				for(IdeaGoodsSpec ideaGoodsSpec2 : list) {
					List<IdeaGoodsSpecItem> list3 = ideaGoodsSpec2.getList();
					for(IdeaGoodsSpecItem ideaGoodsSpecItem : list3) {
						for(SpecPrice specPrice : list2) {
							if(ideaGoodsSpecItem.getId().equals(specPrice.getItem_id())) {
								ideaGoodsSpecItem.setRes("1");
								ideaGoodsSpecItem.setPrice(specPrice);
							}
							if(ideaGoodsSpec2.getId() == specPrice.getSpec_id()) {
								ideaGoodsSpec2.setRes("1");
							}
						}
					}
				}
			}
			
			return baseResponse.setValue(null, list);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	@RequestMapping(value="/iteminfo", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse iteminfo(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && map.get("type_id") != null) {
			
			IdeaGoodsSpec ideaGoodsSpec = new IdeaGoodsSpec();
			ideaGoodsSpec.setType_id(StringUtil.strToLong(map.get("type_id")));
			List<IdeaGoodsSpec> list = ideaGoodsSpecService.getAll(ideaGoodsSpec, null);
			
			if(list != null && !list.isEmpty()) {
				for(IdeaGoodsSpec ideaGoodsSpec2 : list) {
					IdeaGoodsSpecItem ideaGoodsSpecItem = new IdeaGoodsSpecItem();
					ideaGoodsSpecItem.setSpec_id(ideaGoodsSpec2.getId());
					List<IdeaGoodsSpecItem> list2 = ideaGoodsSpecItemService.getAll(ideaGoodsSpecItem, null);
					ideaGoodsSpec2.setList(list2);
				}
			}
			return baseResponse.setValue(null, list);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
