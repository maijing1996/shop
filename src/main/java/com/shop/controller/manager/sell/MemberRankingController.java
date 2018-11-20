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
import com.github.pagehelper.PageInfo;
import com.shop.model.common.LayuiResponse;
import com.shop.model.dto.MemberRanking;
import com.shop.model.dto.UserStatis;
import com.shop.service.IdeaUserService;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/sell/member")
public class MemberRankingController {
	
	@Autowired
	private IdeaUserService ideaUserService;

	/**
	 * 获得会员排行信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<MemberRanking> pageInfo = ideaUserService.getRanking(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
				map.get("nickname"));
		if(pageInfo.getTotal() > 0){
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 新增会员统计
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/statistic", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String statistic(@RequestBody Map<String, String> map) {
		Map<String, Object> map2 = new HashMap<String, Object>();
		List<UserStatis> list = ideaUserService.statistic(StringUtil.strToLong(map.get("date")));
		
		if(list != null && !list.isEmpty()) {
			List<String> x = new ArrayList<String>();
			List<Integer> y = new ArrayList<Integer>();
			int total = 0;
			for(int i=0; i < list.size(); i++) {
				UserStatis userStatis = list.get(i);
				
				total = total + userStatis.getAmount();
				x.add(userStatis.getDate());
				y.add(userStatis.getAmount());
			}
			
			map2.put("amount", y);
			map2.put("date", x);
			map2.put("total", total);
			map2.put("today", y.get(y.size()-1));
			map2.put("code", 200);
		} else {
			map2.put("code", 204);
		}
		return JSONObject.toJSONString(map2);
	}
}
