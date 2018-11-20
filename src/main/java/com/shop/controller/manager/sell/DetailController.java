package com.shop.controller.manager.sell;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.shop.model.common.LayuiResponse;
import com.shop.model.dto.SellDetail;
import com.shop.service.IdeaOrderService;
import com.shop.util.DateUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/sell/detail")
public class DetailController {
	
	@Autowired
	private IdeaOrderService ideaOrderService;
	
	/**
	 * 获得销售详情信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse sellDetail(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		Long startTime = null, endTime = null;
		if(map.get("startTime") != null && !"".equals(map.get("startTime"))) {
			startTime = DateUtil.parseDate(map.get("startTime")).getTime()/1000;
		}
		if(map.get("endTime") != null && !"".equals(map.get("endTime"))) {
			endTime = DateUtil.parseDate(map.get("endTime")).getTime()/1000;
		}
		PageInfo<SellDetail> pageInfo = ideaOrderService.sellDetail(StringUtil.strToInt(map.get("page")),
				StringUtil.strToInt(map.get("size")), startTime, endTime, map.get("title"));
		if(pageInfo.getTotal() > 0){
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
}
