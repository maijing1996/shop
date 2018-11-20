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
import com.shop.model.po.IdeaShopPay;
import com.shop.service.IdeaShopPayService;

@Controller
@ResponseBody
@RequestMapping("/manager/shop/payment")
public class PaymentController {

	@Autowired
	private IdeaShopPayService ideaShopPayService;
	
	/**
	 * 获得交易设置
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo() {
		LayuiResponse layuiResponse = new LayuiResponse();
		IdeaShopPay ideaShopPay = ideaShopPayService.getInfo();
		if(ideaShopPay != null){
			layuiResponse.setCode(200);
			layuiResponse.setData(ideaShopPay);
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
	public BaseResponse update(@RequestBody IdeaShopPay ideaShopPay) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaShopPay.getId() != null) {
			if(ideaShopPay.getWx_pay() == null) {
				return baseResponse.setError(404, "信息错误");
			} else if(ideaShopPay.getWx_pay() == 1) {
				if(ideaShopPay.getWx_appkey() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopPay.getWx_mchid() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopPay.getWx_notify_url() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
			}
			if(ideaShopPay.getAli_pay() == null) {
				return baseResponse.setError(404, "信息错误");
			} else if(ideaShopPay.getAli_pay() == 1) {
				if(ideaShopPay.getAli_notify_url() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopPay.getAli_private_key() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopPay.getAli_public_key() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopPay.getAli_return_url() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopPay.getAli_appid() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
			}
			
			ideaShopPayService.update(ideaShopPay);
			return baseResponse.setValue("更新成功", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
