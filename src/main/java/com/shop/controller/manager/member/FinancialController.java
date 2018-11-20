package com.shop.controller.manager.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.shop.model.common.LayuiResponse;
import com.shop.model.dto.TradeDetails;
import com.shop.service.IdeaUserPaylogService;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/member/financial")
public class FinancialController {

	@Autowired
	private IdeaUserPaylogService ideaUserPaylogService;
	
	/**
	 * 获取资金明细信息
	 * //类型(1:加余额2:减余额3:加积分4:减积分5:加佣金6:减佣金)
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<TradeDetails> pageInfo = ideaUserPaylogService.listInfo(StringUtil.strToInt(map.get("page")),
				StringUtil.strToInt(map.get("size")), 1, 2, map.get("nickName"));
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 获取积分明细信息
	 * //类型(1:加余额2:减余额3:加积分4:减积分5:加佣金6:减佣金)
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/integral", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse integral(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<TradeDetails> pageInfo = ideaUserPaylogService.listInfo(StringUtil.strToInt(map.get("page")),
				StringUtil.strToInt(map.get("size")), 3, 4, map.get("nickName"));
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
}
