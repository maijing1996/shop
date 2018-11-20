package com.shop.controller.manager.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.exception.BusinessException;
import com.shop.model.common.BaseResponse;
import com.shop.model.common.LayuiResponse;
import com.shop.model.po.IdeaWxConfig;
import com.shop.service.IdeaWxConfigService;

@Controller
@ResponseBody
@RequestMapping("/manager/wechat/config")
public class WxConfigController {

	@Autowired
	private IdeaWxConfigService ideaWxConfigService;
	
	/**
	 * 获得微信配置信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo() {
		LayuiResponse layuiResponse = new LayuiResponse();
		IdeaWxConfig ideaWxConfig = ideaWxConfigService.getInfo();
		if(ideaWxConfig != null){
			layuiResponse.setCode(200);
			layuiResponse.setData(ideaWxConfig);
			return layuiResponse;
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 更新信息
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody IdeaWxConfig ideaWxConfig) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaWxConfig.getId() != null) {
			ideaWxConfigService.update(ideaWxConfig);
			return baseResponse.setValue("更新成功", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
