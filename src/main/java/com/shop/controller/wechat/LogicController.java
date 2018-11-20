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
import com.shop.conf.MySiteSetting;
import com.shop.exception.BusinessException;
import com.shop.model.dto.wechat.AccessToken;
import com.shop.model.po.IdeaUser;
import com.shop.model.po.IdeaUserPaylog;
import com.shop.model.po.IdeaWxConfig;
import com.shop.model.to.wechat.applet.Loginc;
import com.shop.service.IdeaUserLevelService;
import com.shop.service.IdeaUserPaylogService;
import com.shop.service.IdeaUserService;
import com.shop.service.IdeaWxConfigService;
import com.shop.util.DateUtil;
import com.shop.util.HttpUtil;
import com.shop.util.MD5Util;
import com.shop.util.RedisUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/api/Logic")
public class LogicController {

	private Logger logger = LoggerFactory.getLogger(LogicController.class);
	
	@Autowired
	private IdeaWxConfigService ideaWxConfigService;
	@Autowired
	private IdeaUserService ideaUserService;
	@Autowired
	private IdeaUserLevelService ideaUserLevelService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private MySiteSetting mySiteSetting;
	@Autowired
	private IdeaUserPaylogService ideaUserPaylogService;

	/**
	 * 微信授权登录
	 * 
	 * @param open_id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/WxLogin", method = RequestMethod.GET, produces = "application/x-www-form-urlencoded;charset=utf-8")
	public String apiApply(@RequestParam(name="code") String code, @RequestParam(name="nickname") String nickname,
			@RequestParam(name="avatar") String avatar, @RequestParam(name="uid", required=false) Long uId){
		Map<String, String> map = new HashMap<String, String>();
		IdeaWxConfig ideaWxConfig = ideaWxConfigService.getInfo();
		StringBuffer buffer = new StringBuffer();
		buffer.append("https://api.weixin.qq.com/sns/jscode2session?appid=");
		buffer.append(ideaWxConfig.getAppid());
		buffer.append("&secret=");
		buffer.append(ideaWxConfig.getApp_secret());
		buffer.append("&js_code=");
		buffer.append(code);
		buffer.append("&grant_type=authorization_code");
		
		try{
			Loginc loginc = HttpUtil.httpRequest(buffer.toString(), "GET", null, Loginc.class);

			if (loginc.getErrcode() == null) {
				String url = null;
				String url2 = null;
				IdeaUser ideaUser = new IdeaUser();
				if (loginc.getUnionid() != null) {
					ideaUser = ideaUserService.isExist(null, loginc.getOpenid());
					if (ideaUser == null) {
						Long levelId = ideaUserLevelService.getLevelId(0);
						if (levelId == null) {
							levelId = 0L;
						}
						
						//nickname = StringUtil.filterEmoji(nickname);
						String pwd = MD5Util.MD5("123456");
						String uuid = StringUtil.getUuid(loginc.getOpenid());
						ideaUser = new IdeaUser();
						ideaUser.setUuid(uuid);
						ideaUser.setOauth_nickname(nickname);
						ideaUser.setAvatar(avatar);
						ideaUser.setOauth_type("小程序");
						ideaUser.setXcx_openid(loginc.getOpenid());
						ideaUser.setUnionid(loginc.getUnionid());
						ideaUser.setLevel_id(levelId);
						ideaUser.setAdd_date(DateUtil.getTimestamp());
						ideaUser.setIntegral(50);
						ideaUser.setPwd(pwd);
						ideaUser.setIs_work(1);
						ideaUser.setUid("user"+ideaUser.getId());
						ideaUser.setToken(StringUtil.getOrderSn(20));
						ideaUser.setDistribution_recommend_uid(0L);
						
						logger.info(JSONObject.toJSONString(ideaUser));
						ideaUserService.save(ideaUser);
						ideaUser.setUid("user"+ideaUser.getId());
						ideaUserService.update(ideaUser);
						
						//用户支付记录
						IdeaUserPaylog ideaUserPaylog = new IdeaUserPaylog();
						ideaUserPaylog.setAccount_fee(0.0);
						ideaUserPaylog.setAdd_date(DateUtil.getTimestamp());
						ideaUserPaylog.setFee(50.0);
						ideaUserPaylog.setInfo("注册赠送积分");
						ideaUserPaylog.setUser_id(ideaUser.getId());
						ideaUserPaylog.setType(3);
						ideaUserPaylog.setPay_state(1);
						ideaUserPaylog.setPay_type(0);
						ideaUserPaylog.setOrder_id(0L);
						ideaUserPaylogService.save(ideaUserPaylog);
					}
				} else {
					ideaUser = ideaUserService.isExist(null, loginc.getOpenid());
					if(ideaUser == null) {
						Long levelId = ideaUserLevelService.getLevelId(0);
						if (levelId == null) {
							levelId = 0L;
						}
						
						//nickname = StringUtil.filterEmoji(nickname);
						String pwd = MD5Util.MD5("123456");
						String uuid = StringUtil.getUuid(loginc.getOpenid());
						ideaUser = new IdeaUser();
						ideaUser.setUuid(uuid);
						ideaUser.setOauth_nickname(nickname);
						ideaUser.setAvatar(avatar);
						ideaUser.setOauth_type("小程序");
						ideaUser.setXcx_openid(loginc.getOpenid());
						ideaUser.setUnionid(loginc.getUnionid());
						ideaUser.setLevel_id(levelId);
						ideaUser.setAdd_date(DateUtil.getTimestamp());
						ideaUser.setIntegral(50);
						ideaUser.setPwd(pwd);
						ideaUser.setIs_work(1);
						ideaUser.setToken(StringUtil.getOrderSn(20));
						ideaUser.setDistribution_recommend_uid(0L);
						
						logger.info(JSONObject.toJSONString(ideaUser));
						ideaUserService.save(ideaUser);
						ideaUser.setUid("user"+ideaUser.getId());
						ideaUserService.update(ideaUser);
						
						//用户支付记录
						IdeaUserPaylog ideaUserPaylog = new IdeaUserPaylog();
						ideaUserPaylog.setAccount_fee(0.0);
						ideaUserPaylog.setAdd_date(DateUtil.getTimestamp());
						ideaUserPaylog.setFee(50.0);
						ideaUserPaylog.setInfo("注册赠送积分");
						ideaUserPaylog.setUser_id(ideaUser.getId());
						ideaUserPaylog.setType(3);
						ideaUserPaylog.setPay_state(1);
						ideaUserPaylog.setPay_type(3);
						ideaUserPaylog.setOrder_id(0L);
						ideaUserPaylogService.save(ideaUserPaylog);
					}
				}
				
				if(ideaUser.getDistribution_qrcode_index() == null || ideaUser.getDistribution_qrcode_person() == null) {
					//获得access_token
					String url4 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ideaWxConfig.getAppid()+"&secret="+ideaWxConfig.getApp_secret();
					AccessToken accessToken = HttpUtil.httpRequest(url4, "GET", null, AccessToken.class);
					
					if(accessToken.getAccess_token() != null) {
						//返回路径没处理好
						url = HttpUtil.getminiqrQr(ideaUser.getId().toString(), accessToken.getAccess_token(), "pages/index/index", mySiteSetting.getHost());//首页二维码
						url2 = HttpUtil.getminiqrQr(ideaUser.getId().toString(), accessToken.getAccess_token(), "pages/user/apply", mySiteSetting.getHost());//个人中心二维码
						ideaUser.setDistribution_qrcode_index(url);
						ideaUser.setDistribution_qrcode_person(url2);
						ideaUserService.update(ideaUser);
					}
				} else {
					url = ideaUser.getDistribution_qrcode_index();
					url = ideaUser.getDistribution_qrcode_person();
				}
				
				if(uId != null && uId != ideaUser.getId() && ideaUser.getDistribution_recommend_uid() == 0) {
					IdeaUser ideaUser2 = ideaUserService .getById(uId);
					if(ideaUser2 != null && ideaUser2.getIs_fx() == 1) {
						ideaUser.setRecommend_date(DateUtil.getTimestamp());
						ideaUser.setDistribution_recommend_uid(uId);
						ideaUserService.update(ideaUser);
					}
				}
				
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("unionId", loginc.getSession_key());
				map2.put("userId", ideaUser.getId());
				//redisUtil.set(loginc.getOpenid(), map2, 21600L);
				redisUtil.set(loginc.getOpenid(), map2, 1800L);
				
				map.put("session_id", loginc.getOpenid());
				map.put("success", "true");
				map.put("userId", ideaUser.getId().toString());
			} else {
				map.put("success", "false");
			}
			return JSONObject.toJSONString(map);
		} catch (Exception e) {
			logger.error("微信code 获取信息出错："+JSONObject.toJSONString(e));
			map.put("success", "false");
			return JSONObject.toJSONString(map);
		}
	}
}
