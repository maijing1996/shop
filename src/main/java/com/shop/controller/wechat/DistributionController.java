package com.shop.controller.wechat;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.exception.BusinessException;
import com.shop.model.dto.wechat.ObjectResponse;
import com.shop.model.po.IdeaUser;
import com.shop.service.IdeaUserService;
import com.shop.util.DateUtil;
import com.shop.util.RedisUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/")
public class DistributionController {

	private Logger logger = LoggerFactory.getLogger(DistributionController.class);
	
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private IdeaUserService ideaUserService;
	
	/**
	 * 用户申请成为分销商
	 * @param openId
	 * @param disId
	 * @param nickname
	 * @param commId
	 * @param tel
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/api_apply_distribution", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiUpdUser(@RequestParam(name="open_id") String openId, @RequestParam(name="dis_id") Long disId,
			@RequestParam(name="real_name") String nickname, @RequestParam(name="target_id") Long commId,
			@RequestParam(name="mobile") String mobile) throws BusinessException {

		ObjectResponse objectResponse = new ObjectResponse();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) redisUtil.get(openId);
		if(map == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map = userMap;
			} else {
				objectResponse.setCode(204);
				objectResponse.setMsg("登录异常，该用户未授权登录");
				return JSONObject.toJSONString(objectResponse);
			}
		}
		
		IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map.get("userId").toString()));
		if(ideaUser.getIs_fx() == 0 && ideaUser.getDistribution_apply_add_time() == 0 
				&& ideaUser.getDistribution_pass_add_time() == 0 && ideaUser.getDistribution_lev() == 0) {
			
			if(nickname == null || "".equals(nickname)) {
				objectResponse.setCode(500);
				objectResponse.setMsg("信息不完整");
				return JSONObject.toJSONString(objectResponse);
			}
			if(commId != null) {
				logger.info("推荐人修改，推荐人Id:" + commId);
				ideaUser.setDistribution_recommend_uid(commId);
			}
			if(mobile == null || "".equals(mobile)) {
				objectResponse.setCode(500);
				objectResponse.setMsg("信息不完整");
				return JSONObject.toJSONString(objectResponse);
			}
			if(disId == null) {
				objectResponse.setCode(500);
				objectResponse.setMsg("信息不完整");
				return JSONObject.toJSONString(objectResponse);
			}
			
			ideaUser.setDistribution_apply_add_time(DateUtil.getTimestamp());
			ideaUser.setDistribution_lev(disId);
			ideaUser.setReal_name(nickname);
			ideaUser.setUser_tel(mobile);
			ideaUserService.update(ideaUser);
			
			objectResponse.setCode(200);
			objectResponse.setMsg("提交申请成为分销商成功");
		} else {
			objectResponse.setCode(204);
			objectResponse.setMsg("您已经提交了申请");
		}
		
		return JSONObject.toJSONString(objectResponse);
	}
}
