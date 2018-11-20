package com.shop.controller.manager.sell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.model.dto.OrderStatic;
import com.shop.service.IdeaOrderService;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/sell/statistics")
public class StatisticsController {

	@Autowired
	private IdeaOrderService ideaOrderService;
	
	/**
	 * 获得多对象信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/statistic", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String listInfo(@RequestBody Map<String, String> map) {
		Map<String, Object> map2 = new HashMap<String, Object>();
		List<OrderStatic> list = ideaOrderService.statistics(StringUtil.strToLong(map.get("date")));
		
		if(list != null && !list.isEmpty()) {
			List<String> x = new ArrayList<String>();
			List<Double> y = new ArrayList<Double>();
			List<Double> y2 = new ArrayList<Double>();
			List<Integer> y3 = new ArrayList<Integer>();
			for(int i=0; i < list.size(); i++) {
				OrderStatic orderStatic = list.get(i);
				
				x.add(orderStatic.getDate());
				y.add(orderStatic.getPrice());
				y2.add(orderStatic.getSingle());
				y3.add(orderStatic.getAmount());
			}

			map2.put("amount", y3);
			map2.put("price", y);
			map2.put("single", y2);
			map2.put("date", x);
			map2.put("code", 200);
		} else {
			map2.put("code", 204);
		}
		return JSONObject.toJSONString(map2);
	}
}
