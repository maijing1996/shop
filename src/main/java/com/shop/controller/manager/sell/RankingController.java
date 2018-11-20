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
import com.shop.service.IdeaGoodsService;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/sell/rangking")
public class RankingController {

	@Autowired
	private IdeaGoodsService ideaGoodsService;
	
	/**
	 * 获得销售排行信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse getSellRanking(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<SellDetail> pageInfo = ideaGoodsService.getSellRanking(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")));
		if(pageInfo.getTotal() > 0){
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
}
