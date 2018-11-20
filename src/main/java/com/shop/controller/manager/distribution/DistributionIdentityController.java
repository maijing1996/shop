package com.shop.controller.manager.distribution;

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
import com.shop.model.dto.DistributionIdentity;
import com.shop.model.po.IdeaCoupon;
import com.shop.model.po.IdeaDistributionCategroy;
import com.shop.model.po.IdeaDistributionCategroyPrice;
import com.shop.service.IdeaCouponService;
import com.shop.service.IdeaDistributionCategroyPriceService;
import com.shop.service.IdeaDistributionCategroyService;
import com.shop.util.StringUtil;

//分销商分类控制
@Controller
@ResponseBody
@RequestMapping("/manager/distribution/identity")
public class DistributionIdentityController {
	
	@Autowired
	private IdeaDistributionCategroyService ideaDistributionCategroyService;
	@Autowired
	private IdeaCouponService ideaCouponService;
	@Autowired
	private IdeaDistributionCategroyPriceService ideaDistributionCategroyPriceService;
	
	/**
	 * 查询显示分销商分类控制
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map){
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<DistributionIdentity> pageInfo = ideaDistributionCategroyService.listInfo(StringUtil.strToInt(map.get("page")),
				StringUtil.strToInt(map.get("size")));
		if (pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据！");
		}
	}
	
	/**
	 * 删除单个分销身份
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse delete(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			ideaDistributionCategroyService.deleteAll(map.get("id"));
			return baseResponse.setValue("信息已经删除！", null);
		}else {
			return baseResponse.setError(404, "操作对象不明确！");
		}
	}
	
	/**
	 * 修改分销商
	 * @param insertDistributionMessage
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		
		Long id = StringUtil.strToLong(map.get("id"));
		if(id != null) {
			IdeaDistributionCategroy ideaDistributionCategroy = new IdeaDistributionCategroy();
			ideaDistributionCategroy.setId(id);
			
			if(map.get("name") != null && !"".equals(map.get("name").trim())) {
				ideaDistributionCategroy.setName(map.get("name").trim());
			}
			Double commission = StringUtil.strToDouble(map.get("commission")); 
			if(commission != null && commission >=0 && commission <= 100) {
				ideaDistributionCategroy.setCommission(StringUtil.strToDouble(map.get("commission")));
			}
			Double amount = StringUtil.strToDouble(map.get("apply_amount"));
			if(amount != null && amount >= 0) {
				ideaDistributionCategroy.setApply_amount(amount);
			}
			Integer buy = StringUtil.strToInt(map.get("buy_discount"));
			if(buy != null && buy >= 0 && buy <= 100) {
				ideaDistributionCategroy.setBuy_discount(buy);
			}
			
			ideaDistributionCategroy.setIs_apply(StringUtil.strToInt(map.get("is_apply")));
			ideaDistributionCategroy.setIs_sale(StringUtil.strToInt(map.get("is_sale")));
			ideaDistributionCategroy.setFid(StringUtil.strToLong(map.get("fid")));
			
			boolean result = ideaDistributionCategroyService.updateDistribution(map, ideaDistributionCategroy);
			
			if(result) {
				return baseResponse.setValue("操作成功！", null);
			}else {
				return baseResponse.setError(404, "操作失败！");
			}
		} else {
			return baseResponse.setError(404, "操作失败！");
		}
	}
	
	/**
	 * 获得分销代金券
	 * @param insertDistributionMessage
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/coupon", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String coupon() throws BusinessException {
		IdeaCoupon ideaCoupon = new IdeaCoupon();
		ideaCoupon.setType(4);
		List<IdeaCoupon> list = ideaCouponService.getAll(ideaCoupon, null);
		return JSONObject.toJSONString(list);
	}
	
	/**
	 * 获得分销佣金明细
	 * @param insertDistributionMessage
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/comm", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String comm(@RequestBody Map<String, Long> map) throws BusinessException {
		IdeaDistributionCategroyPrice ideaDistributionCategroyPrice = new IdeaDistributionCategroyPrice();
		ideaDistributionCategroyPrice.setThis_id(map.get("id"));
		List<IdeaDistributionCategroyPrice> list = ideaDistributionCategroyPriceService.getAll(ideaDistributionCategroyPrice, null);
		return JSONObject.toJSONString(list);
	}
}
