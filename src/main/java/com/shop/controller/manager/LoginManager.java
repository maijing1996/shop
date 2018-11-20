package com.shop.controller.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.controller.BaseController;
import com.shop.exception.BusinessException;
import com.shop.model.common.BaseResponse;
import com.shop.model.common.LayuiResponse;
import com.shop.model.dto.LoginInfo;
import com.shop.model.dto.Menu;
import com.shop.model.dto.RedisUser;
import com.shop.model.po.IdeaAdmin;
import com.shop.model.po.IdeaAdminLog;
import com.shop.model.po.IdeaAdminRole;
import com.shop.service.IdeaAdminLogService;
import com.shop.service.IdeaAdminMenuService;
import com.shop.service.IdeaAdminRoleService;
import com.shop.service.IdeaAdminService;
import com.shop.util.DateUtil;
import com.shop.util.HttpUtil;
import com.shop.util.MD5Util;

@Controller
@ResponseBody
@RequestMapping("/manager/login")
public class LoginManager extends BaseController {

	@Autowired
	private IdeaAdminService ideaAdminService;
	@Autowired
	private IdeaAdminMenuService ideaAdminMenuService;
	@Autowired
	private IdeaAdminLogService ideaAdminLogService;
	@Autowired
	private IdeaAdminRoleService ideaAdminRoleService;
	
	/**
	 * 管理系统登陆操作
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping(value="/enter", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse login(HttpServletRequest request, @RequestBody LoginInfo loginInfo) {
		BaseResponse baseResponse = new BaseResponse();
		if(loginInfo.getAccount() == null) {
			return baseResponse.setError(404, "请输入账号！");
		}
		if(loginInfo.getPasswd() == null) {
			return baseResponse.setError(404, "请输入密码！");
		}
		
		String passwd = MD5Util.MD5(loginInfo.getPasswd());
		RedisUser redisUser = ideaAdminService.login(loginInfo.getAccount(), passwd);
		
		String ip = HttpUtil.getClientIP(request);
		if(redisUser != null) {
			if(redisUser.getIs_work() == 1) {
				this.setCurrentUser(request, redisUser);
				
				try {
					//更新登录状态
					IdeaAdmin ideaAdmin = ideaAdminService.getById(redisUser.getId());
					ideaAdmin.setLast_login_ip(ip);
					ideaAdmin.setLast_login_time(DateUtil.getTimestamp());
					ideaAdmin.setLogin_times(ideaAdmin.getLogin_times() + 1);
					ideaAdminService.update(ideaAdmin);
					
					//系统日志
					IdeaAdminLog ideaAdminLog = new IdeaAdminLog();
					ideaAdminLog.setAdd_date(DateUtil.getTimestamp());
					ideaAdminLog.setLog_info("用户登录成功！");
					ideaAdminLog.setLog_ip(ip);
					ideaAdminLog.setLog_state(1);
					ideaAdminLog.setLog_type(1);
					ideaAdminLog.setLog_url("/manager/login/enter");
					ideaAdminLog.setUid(loginInfo.getAccount());
					ideaAdminLogService.save(ideaAdminLog);
				} catch (BusinessException e) {
					e.printStackTrace();
				}
				
				return baseResponse.setValue(null, redisUser.getId());
			} else {
				return baseResponse.setError(405, "用户未激活！");
			}
		} else {
			//系统日志
			try {
				IdeaAdminLog ideaAdminLog = new IdeaAdminLog();
				ideaAdminLog.setAdd_date(DateUtil.getTimestamp());
				ideaAdminLog.setLog_info("账号密码错误！");
				ideaAdminLog.setLog_ip(ip);
				ideaAdminLog.setLog_state(0);
				ideaAdminLog.setLog_type(1);
				ideaAdminLog.setLog_url("/manager/login/enter");
				ideaAdminLog.setUid(loginInfo.getAccount());
			
				ideaAdminLogService.save(ideaAdminLog);
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			return baseResponse.setError(405, "账号密码错误！");
		}
	}
	
	/**
	 * 管理系统登陆操作
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping(value="/exit", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String exitCurrent(HttpServletRequest request) {
		BaseResponse baseResponse = new BaseResponse();
		this.exitCurrentUser(request);
		baseResponse.setValue("退出成功", null);
		return JSONObject.toJSONString(baseResponse);
	}
	
	/**
	 * 后台菜单
	 * @return
	 */
	@RequestMapping(value="/menu", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String menu(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String key = mySiteSetting.getSessionName() + request.getSession().getId();
		RedisUser redisUser = (RedisUser) redisUtil.get(key);
		if(redisUser != null) {
			if(redisUser.getRole_id() == 0) {
				List<Menu> list = ideaAdminMenuService.getMenu();
				if(list != null && !list.isEmpty()) {
					for(Menu menu : list) {
						if(menu.getIs_turn() == 1) {
							menu.setJump(menu.getName());
						}
					}
					
					map.put("data", list);
					map.put("code", 200);
					map.put("msg", "");
				} else {
					map.put("data", null);
					map.put("code", 204);
					map.put("msg", "没有菜单信息");
				}
			} else if(redisUser.getRole_id() != null) {
				IdeaAdminRole ideaAdminRole = ideaAdminRoleService.getById(redisUser.getRole_id());
				List<Menu> list = ideaAdminMenuService.getMenu2(ideaAdminRole.getPower());
				if(list != null && !list.isEmpty()) {
					for(Menu menu : list) {
						if(menu.getIs_turn() == 1) {
							menu.setJump(menu.getName());
						}
					}
					
					map.put("data", list);
					map.put("code", 200);
					map.put("msg", "");
				} else {
					map.put("data", null);
					map.put("code", 204);
					map.put("msg", "没有菜单信息");
				}
			} else {
				map.put("data", null);
				map.put("code", 204);
				map.put("msg", "没有菜单信息");
			}
		} else {
			map.put("data", "");
			map.put("code", 1001);
			map.put("msg", "登录信息过期，请刷新页面或重新登录！");
		}
		
		/*List<Menu> list = ideaAdminMenuService.getMenu();
		if(list != null && !list.isEmpty()) {
			for(Menu menu : list) {
				if(menu.getIs_turn() == 1) {
					menu.setJump(menu.getName());
				}
			}
			
			map.put("data", list);
			map.put("code", 200);
			map.put("msg", "");
		} else {
			map.put("data", null);
			map.put("code", 204);
			map.put("msg", "没有菜单信息");
		}*/
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 重新登录
	 * @return
	 */
	@RequestMapping(value="/restart", produces="application/json;charset=utf-8")
	public String restart() {
		LayuiResponse layuiResponse = new LayuiResponse();
		layuiResponse.setCode(1001);
		layuiResponse.setMsg("登录信息过期，请刷新页面或重新登录！");
		return JSONObject.toJSONString(layuiResponse);
	}
}
