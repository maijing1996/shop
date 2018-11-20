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
import com.shop.model.po.IdeaShopMessage;
import com.shop.service.IdeaShopMessageService;

@Controller
@ResponseBody
@RequestMapping("/manager/shop/message")
public class MessageController {

	@Autowired
	private IdeaShopMessageService ideaShopMessageService;
	
	/**
	 * 获得多对象信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo() {
		LayuiResponse layuiResponse = new LayuiResponse();
		IdeaShopMessage ideaShopMessage = ideaShopMessageService.getById(1L);
		layuiResponse.setCode(200);
		layuiResponse.setData(ideaShopMessage);
		return layuiResponse;
	}
	
	/**
	 * 更新信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody IdeaShopMessage ideaShopMessage) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaShopMessage.getId() != null) {
			
			ideaShopMessageService.update(ideaShopMessage);
			return baseResponse.setValue("更新成功", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
