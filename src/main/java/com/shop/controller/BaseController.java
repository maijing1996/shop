package com.shop.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.shop.conf.MySiteSetting;
import com.shop.model.dto.RedisUser;
import com.shop.util.RedisUtil;

@Controller
public abstract class BaseController {

	private Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	public RedisUtil redisUtil;
	@Autowired
	public MySiteSetting mySiteSetting;
	
	/**
	 * 获得 sessioName
	 * @return
	 */
	public String getSessionName() {
		return mySiteSetting.getSessionName();
	}

	/**
	 * 获取当前登录用户，未登录返回NULL
	 * 
	 * @param request
	 * @return
	 */
	public RedisUser getCurrentUser(HttpServletRequest request) {
		String key = this.getSessionName() + request.getSession().getId();
		return (RedisUser) redisUtil.get(key);
	}

	/**
	 * 设置当前登录用户
	 * 
	 * @param request
	 * @param user
	 */
	public synchronized boolean setCurrentUser(HttpServletRequest request, RedisUser redisUser) {
		
		String redisKey = "login_" + redisUser.getId();
		
		//判断是不是抢位登录
		String str = (String) redisUtil.get(redisKey);
		if(str != null && !"".equals(str)) {
			logger.info("该用户被顶号登录！");
			redisUtil.expire(str, 0L);
		} 
		
		// 保存用户数据到redis内
		String key = this.getSessionName() + request.getSession().getId();
		redisUtil.set(key, redisUser, 30 * 60L); // 缓存30分钟
		//request.getSession().setAttribute(key, manager);
		//将登录信息放入Redis中
		//String redisKey = "login_" + redisUser.getId();
		redisUtil.set(redisKey, key, 30 * 60L); // 缓存30分钟
		return true;
	}

	/**
	 * 退出当前用户
	 * 
	 * @param request
	 */
	public void exitCurrentUser(HttpServletRequest request) {
		String key = this.getSessionName() + request.getSession().getId();
		RedisUser redisUser = (RedisUser) redisUtil.get(key);
		//Manager manager = (Manager) request.getSession().getAttribute(key);
		if(redisUser != null) {
			//request.getSession().removeAttribute(key);
			redisUtil.expire(key, 0L);
			String redisKey = "login_" + redisUser.getId();
			redisUtil.expire(redisKey, 0L);
		}
	}
}
