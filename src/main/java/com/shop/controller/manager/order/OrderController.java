package com.shop.controller.manager.order;

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
import com.shop.model.dto.ExpressPrice;
import com.shop.model.dto.Order;
import com.shop.model.dto.OrderGoods;
import com.shop.model.po.IdeaExpressCompany;
import com.shop.model.po.IdeaOrder;
import com.shop.service.IdeaExpressCompanyService;
import com.shop.service.IdeaOrderGoodsService;
import com.shop.service.IdeaOrderService;
import com.shop.util.DateUtil;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/order/order")
public class OrderController {

	@Autowired
	private IdeaOrderService ideaOrderService;
	@Autowired
	private IdeaOrderGoodsService ideaOrderGoodsService;
	@Autowired
	private IdeaExpressCompanyService ideaExpressCompanyService;
	
	/**
	 * 获得订单信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		Long startTime = null, endTime = null;
		if(map.get("startTime") != null && !"".equals(map.get("startTime"))) {
			startTime = DateUtil.parseDate(map.get("startTime")).getTime()/1000;
		}
		if(map.get("endTime") != null && !"".equals(map.get("endTime"))) {
			endTime = DateUtil.parseDate(map.get("endTime")).getTime()/1000;
		}
		PageInfo<Order> pageInfo = ideaOrderService.listInfo(StringUtil.strToInt(map.get("page")),
				StringUtil.strToInt(map.get("size")), startTime, endTime, map.get("nickName"), StringUtil.strToInt(map.get("status")));
		
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 批量删除订单
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/deletes", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deletes(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && RegexUtil.matches(RegexUtil.IDS_REGEX, map.get("ids"))) {
			boolean boo = ideaOrderService.deleteByIds(map.get("ids"));
			if(boo) {
				return baseResponse.setValue("删除订单成功！", null);
			} else {
				return baseResponse.setError(500, "删除订单失败！");
			}
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 订单详细
	 * 
	 * 基本信息
	 * 收货信息
	 * 商品信息
	 * 费用信息
	 * 
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/details", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse details(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			//订单详细
			Order order = ideaOrderService.details(map.get("id"));
			//商品详情
			List<OrderGoods> list = ideaOrderGoodsService.getOrderGoods(map.get("id"));
			order.setList(list);
			return baseResponse.setValue(null, order);
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 修改订单的状态
	 * 
	 * 订单状态 0:待付款 1:待发货 2:待收货 3:已完成 -1:取消订单
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/state", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse setState(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		
		if(map.get("state") != null && !"".equals(map.get("state").trim())) {
			String[] arr = map.get("state").split("&");
			if(arr[0] != null && arr[1] != null && StringUtil.strToInt(arr[0]) != null && StringUtil.strToLong(arr[1]) != null) {
				IdeaOrder ideaOrder = new IdeaOrder();
				ideaOrder.setId(StringUtil.strToLong(arr[1]));
				ideaOrder.setOrder_state(StringUtil.strToInt(arr[0]));
				if(StringUtil.strToInt(arr[0]) == 2) {
					IdeaExpressCompany ideaExpressCompany = ideaExpressCompanyService.getById(StringUtil.strToLong(map.get("id")));
					ideaOrder.setExpress_code(ideaExpressCompany.getCode());
					ideaOrder.setExpress_date(DateUtil.getTimestamp());
					ideaOrder.setExpress_sn(map.get("express_sn"));
					ideaOrder.setExpress_title(ideaExpressCompany.getReal_name());
				}
				ideaOrderService.update(ideaOrder);
			}
			return baseResponse.setValue("订单状态修改成功", null);
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 修改运费
	 * @param checkState
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/price", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse setPrice(@RequestBody ExpressPrice expressPrice) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(expressPrice.getId() != null && expressPrice.getPrice() != null && expressPrice.getPrice() >= 0) {
			IdeaOrder ideaOrder = ideaOrderService.getById(expressPrice.getId());
			Double pay = ideaOrder.getPay_price()+expressPrice.getTrim_price() - expressPrice.getPrice(); 
			
			ideaOrder.setExpress_price(expressPrice.getPrice());
			ideaOrder.setTrim_price(expressPrice.getTrim_price());
			ideaOrder.setPay_price(pay);
			
			ideaOrderService.update(ideaOrder);
			return baseResponse.setValue("运费修改成功", null);
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 快递公司
	 * @param expressPrice
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/express", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse expressCompany() throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		List<IdeaExpressCompany> list = ideaExpressCompanyService.getAll(null, null);
		return baseResponse.setValue(null, list);
	}
}
