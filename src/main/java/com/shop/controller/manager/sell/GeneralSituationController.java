/*package com.shop.controller.manager.sell;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.model.common.LayuiResponse;
import com.shop.service.IdeaOrderService;

@Controller
@ResponseBody
@RequestMapping("/manager/sell/situation")
public class GeneralSituationController {

	@Autowired
	private IdeaOrderService ideaOrderService;
	
	*//**
	 * 获得多对象信息
	 * @param map
	 * @return
	 *//*
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<IdeaWxReply> pageInfo = ideaOrderService.generalSituation();
		if(pageInfo.getTotal() > 0){
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
		return layuiResponse;
	}
}
*/