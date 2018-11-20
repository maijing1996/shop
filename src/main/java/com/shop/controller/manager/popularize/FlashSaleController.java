package com.shop.controller.manager.popularize;

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
import com.shop.model.dto.FlashSale;
import com.shop.model.po.IdeaFlashSale;
import com.shop.model.po.IdeaGoodsSpecPrice;
import com.shop.service.IdeaFlashSaleService;
import com.shop.service.IdeaGoodsSpecPriceService;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/popularize/flashsale")
public class FlashSaleController {

	@Autowired
	private IdeaFlashSaleService ideaFlashSaleService;
	@Autowired
	private IdeaGoodsSpecPriceService ideaGoodsSpecPriceService;
	
	/**
	 * 获取限时抢购的信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<FlashSale> pageInfo = ideaFlashSaleService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
				map.get("title"));
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
	public BaseResponse update(@RequestBody IdeaFlashSale ideaFlashSale) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaFlashSale.getId() != null) {
			if(ideaFlashSale.getAmount() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaFlashSale.getPer_amount() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaFlashSale.getPrice() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			} else if(ideaFlashSale.getPrice() <= 0) {
				return baseResponse.setError(404, "格式不对，价钱需要大于0");
			}
			if(ideaFlashSale.getSale_bdate() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaFlashSale.getSale_edate() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaFlashSale.getTitle() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaFlashSale.getSequence() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if("".equals(ideaFlashSale.getSpec_key())) {
				ideaFlashSale.setSpec_key(null);
			}
			if("".equals(ideaFlashSale.getSpec_name())) {
				ideaFlashSale.setSpec_name(null);
			}
			
			ideaFlashSaleService.update(ideaFlashSale);
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
	public BaseResponse save(@RequestBody IdeaFlashSale ideaFlashSale) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaFlashSale.getId() != null) {
			return baseResponse.setError(500, "数据错误，请重新输入");
		}
		if(ideaFlashSale.getAmount() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaFlashSale.getGoods_id() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaFlashSale.getPer_amount() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaFlashSale.getPrice() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		} else if(ideaFlashSale.getPrice() <= 0) {
			return baseResponse.setError(404, "格式不对，价钱需要大于0");
		}
		if(ideaFlashSale.getSale_bdate() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaFlashSale.getSale_edate() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaFlashSale.getTitle() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaFlashSale.getSequence() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		
		ideaFlashSaleService.save(ideaFlashSale);
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
			ideaFlashSaleService.deleteById(map.get("id"));
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
			ideaFlashSaleService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("信息已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 获得商品列表
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/goods", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse goods(@RequestBody Map<String, String> map) throws BusinessException {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<IdeaGoodsSpecPrice> pageInfo = ideaGoodsSpecPriceService.listInfo(StringUtil.strToInt(map.get("page")),
				StringUtil.strToInt(map.get("size")), map.get("keys_name"));
		if(pageInfo.getTotal() > 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(204, "没有数据");
		}
	} 
}
