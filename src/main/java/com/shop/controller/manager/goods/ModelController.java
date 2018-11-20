package com.shop.controller.manager.goods;

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
import com.shop.model.dto.OptionBox;
import com.shop.model.po.IdeaGoodsType;
import com.shop.service.IdeaGoodsTypeService;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/goods/model")
public class ModelController {

	@Autowired
	private IdeaGoodsTypeService ideaGoodsTypeService;
	
	/**
	 * 获取商品模型信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<IdeaGoodsType> pageInfo = ideaGoodsTypeService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
				map.get("title"));
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 商品模型下拉选项框的值列表
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/getOptionBox", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listOptionBox() {
		LayuiResponse layuiResponse = new LayuiResponse();
		List<OptionBox> list = ideaGoodsTypeService.listOptionBox();
		if(!list.isEmpty()) {
			return layuiResponse.setSuccess(null, list, list.size());
		} else {
			return layuiResponse.setSuccess(null, list, list.size());
		}
	}
	
	/**
	 * 修改商品模型
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody IdeaGoodsType ideaGoodsType) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaGoodsType.getId() != null) {
			if(ideaGoodsType.getTitle() != null) {
				ideaGoodsTypeService.update(ideaGoodsType);
				return baseResponse.setValue("商品模型已经修改！", null);
			} else {
				return baseResponse.setError(404, "没有需要修改的信息！");
			}
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 添加商品模型
	 * @param title
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map == null || "".equals(map.get("title").trim())) {
			return baseResponse.setError(404, "请输入商品模型名称！");
		}
		IdeaGoodsType ideaGoodsType = new IdeaGoodsType();
		ideaGoodsType.setTitle(map.get("title").trim());
		
		ideaGoodsTypeService.save(ideaGoodsType);
		return baseResponse.setValue("商品模型已经添加", null);
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
			ideaGoodsTypeService.deleteById(map.get("id"));
			return baseResponse.setValue("管理员角色已经删除！", null);
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
			ideaGoodsTypeService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("管理员角色已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
