package com.shop.controller.manager.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.exception.BusinessException;
import com.shop.model.common.BaseResponse;
import com.shop.model.common.LayuiResponse;
import com.shop.model.po.IdeaShopDispatching;
import com.shop.service.IdeaShopDispatchingService;

@Controller
@ResponseBody
@RequestMapping("/manager/shop/delivery")
public class DeliveryController {

	@Autowired
	private IdeaShopDispatchingService ideaShopDispatchingService;
	
	/**
	 * 获得配送信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo() {
		LayuiResponse layuiResponse = new LayuiResponse();
		IdeaShopDispatching ideaShopDispatching = ideaShopDispatchingService.getInfo();
		if(ideaShopDispatching != null){
			layuiResponse.setCode(200);
			layuiResponse.setData(ideaShopDispatching);
			return layuiResponse;
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
	public BaseResponse update(@RequestBody IdeaShopDispatching ideaShopDispatching) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaShopDispatching.getId() != null) {
			if(ideaShopDispatching.getExpress_switch() == null) {
				return baseResponse.setError(404, "信息错误");
			} else if(ideaShopDispatching.getExpress_switch() == 1) {
				if(ideaShopDispatching.getFirst_price() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopDispatching.getFirst_weight() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopDispatching.getSecond_price() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopDispatching.getSecond_weight() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
			}
			if(ideaShopDispatching.getPickup() == null) {
				return baseResponse.setError(404, "信息错误");
			} else if(ideaShopDispatching.getPickup() == 1) {
				if(ideaShopDispatching.getPickup_addr() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopDispatching.getPickup_name() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopDispatching.getPickup_tel() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
			}
			
			ideaShopDispatchingService.update(ideaShopDispatching);
			return baseResponse.setValue("更新成功", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
