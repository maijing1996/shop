package com.shop.controller.manager.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.shop.exception.BusinessException;
import com.shop.model.common.BaseResponse;
import com.shop.model.common.LayuiResponse;
import com.shop.model.dto.Member;
import com.shop.model.dto.User;
import com.shop.model.dto.UserUpdate;
import com.shop.model.po.IdeaDistributionCategroy;
import com.shop.model.po.IdeaUser;
import com.shop.model.po.IdeaUserPaylog;
import com.shop.service.IdeaDistributionCategroyService;
import com.shop.service.IdeaUserService;
import com.shop.util.DateUtil;
import com.shop.util.MD5Util;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/member/user")
public class MemberController {

	@Autowired
	private IdeaUserService ideaUserService;
	@Autowired
	private IdeaDistributionCategroyService ideaDistributionCategroyService;
	
	/**
	 * 获取用户信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<User> pageInfo = ideaUserService.listInfo(StringUtil.strToInt(map.get("page")),
				StringUtil.strToInt(map.get("size")), map.get("nickName"));
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 删除单个会员
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse delete(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			ideaUserService.deleteById(map.get("id"));
			return baseResponse.setValue("删除用户成功！", null);
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 批量删除用户成功
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/deletes", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deletes(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && RegexUtil.matches(RegexUtil.IDS_REGEX, map.get("ids"))) {
			ideaUserService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("删除用户成功！", null);
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 添加会员
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody Member member) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(member.getAccount() == null || !RegexUtil.matches(RegexUtil.ACCOUNT_REGEX, member.getAccount())
				|| member.getAccount().length() < 4) {
			return baseResponse.setError(404, "用户账户输入有误！");
		}
		if(member.getPasswd() == null || !RegexUtil.matches(RegexUtil.PASSWORD_REGEX, member.getPasswd())) {
			return baseResponse.setError(404, "用户密码输入有误！");
		}
		
		String pwd = MD5Util.MD5(member.getPasswd());
		IdeaUser ideaUser = new IdeaUser();
		ideaUser.setUid(member.getAccount());
		ideaUser.setPwd(pwd);
		ideaUserService.save(ideaUser);
		return baseResponse.setValue("添加会员成功！", null);
	}
	
	/**
	 * 修改用户信息
	 * @param member
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody UserUpdate userUpdate) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(userUpdate.getId() != null) {
			IdeaUser ideaUser = new IdeaUser();
			if(userUpdate.getEmail() != null && RegexUtil.matches(RegexUtil.EMAIL_REGEX, userUpdate.getEmail())) {
				ideaUser.setEmail(userUpdate.getEmail());
			}
			if(userUpdate.getIntegral() != null) {
				ideaUser.setIntegral(userUpdate.getIntegral());
			}
			if(userUpdate.getIs_fx() != null) {
				ideaUser.setIs_fx(userUpdate.getIs_fx());
			}
			if(userUpdate.getIs_work() != null) {
				ideaUser.setIs_work(userUpdate.getIs_work());
			}
			if(userUpdate.getNickname() != null) {
				ideaUser.setOauth_nickname(userUpdate.getNickname());
			}
			if(userUpdate.getPasswd() != null) {
				ideaUser.setPwd(userUpdate.getPasswd());
			}
			if(userUpdate.getPhone() != null && RegexUtil.matches(RegexUtil.PHONE_REGEX, userUpdate.getPhone())) {
				ideaUser.setMobile(userUpdate.getPhone());
			}
			if(userUpdate.getUser_money() != null) {
				ideaUser.setUser_money(userUpdate.getUser_money());
			}
			if(userUpdate.getPasswd() != null) {
				if(RegexUtil.matches(RegexUtil.PASSWORD_REGEX, userUpdate.getPasswd()) && userUpdate.getPasswd().length() > 5
						&& userUpdate.getPasswd().length() < 19) {
					String pwd = MD5Util.MD5(userUpdate.getPasswd());
					ideaUser.setPwd(pwd);
				} else {
					return baseResponse.setError(405, "密码输入有误！");
				}
			}
			
			ideaUserService.update(ideaUser);
			return baseResponse.setValue("修改会员信息成功！", null);
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 调整用户金额
	 * 调整用户积分
	 * @param userUpdate
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/neaten", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse updateMoney(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && map.get("id") != null) {
			Long id = StringUtil.strToLong(map.get("id"));
			Integer state = StringUtil.strToInt(map.get("state"));
			Integer type = StringUtil.strToInt(map.get("type"));
			Integer type2 = 1;
			
			IdeaUser ideaUser = ideaUserService.getById(id);
			if(ideaUser != null) {
				if(state == 1) {
					Double amount = StringUtil.strToDouble(map.get("amount"));
					if(amount == null || amount <= 0) {
						return baseResponse.setError(404, "输入有误，请重新输入");
					}
					
					if(type == 1) {
						Double money = ideaUser.getUser_money() + amount;
						ideaUser.setUser_money(money);
					} else {
						if(amount > ideaUser.getUser_money()) {
							return baseResponse.setError(404, "余额不足，不允许调整");
						}
						Double money = ideaUser.getUser_money() - amount;
						ideaUser.setUser_money(money);
					}
				} else {
					Integer amount = StringUtil.strToInt(map.get("amount"));
					if(amount == null || amount < 0) {
						return baseResponse.setError(404, "输入有误，请重新输入");
					}
					
					if(type == 1) {
						Integer integral = ideaUser.getIntegral() + amount;
						ideaUser.setIntegral(integral);
					} else {
						if(amount > ideaUser.getIntegral()) {
							return baseResponse.setError(404, "积分不足，不允许调整");
						}
						Integer integral = ideaUser.getIntegral() - StringUtil.strToInt(map.get("amount"));
						ideaUser.setIntegral(integral);
					}
				}
			}
			
			IdeaUserPaylog ideaUserPaylog = new IdeaUserPaylog();
			ideaUserPaylog.setAccount_fee(0D);
			ideaUserPaylog.setAdd_date(DateUtil.getTimestamp());
			ideaUserPaylog.setFee(StringUtil.strToDouble(map.get("amount")));
			ideaUserPaylog.setInfo(map.get("info"));
			ideaUserPaylog.setOrder_id(0L);
			ideaUserPaylog.setPay_state(1);
			ideaUserPaylog.setPay_type(3);
			ideaUserPaylog.setState(0);
			ideaUserPaylog.setType(type2);
			ideaUserPaylog.setUser_id(id);
			
			ideaUserService.updateAll(ideaUser, ideaUserPaylog);
			return baseResponse.setValue("修改信息成功！", null);
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 更改分销商信息
	 * type 1 成为分销商   2取消身份，3取消绑定
	 * @param member
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/identity", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse identity(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && StringUtil.strToInt(map.get("type")) != null && StringUtil.strToInt(map.get("id")) != null
				&& StringUtil.strToInt(map.get("lev")) != null) {
			
			int type = StringUtil.strToInt(map.get("type"));
			if(type == 1) {
				IdeaDistributionCategroy ideaDistributionCategroy= ideaDistributionCategroyService.getById(StringUtil.strToLong(map.get("lev")));
				
				IdeaUser ideaUser = new IdeaUser();
				ideaUser.setNote(ideaDistributionCategroy.getName());
				ideaUser.setId(StringUtil.strToLong(map.get("id")));
				ideaUser.setDistribution_lev(StringUtil.strToLong(map.get("lev")));
				ideaUser.setDistribution_apply_add_time(DateUtil.getTimestamp());
				ideaUser.setDistribution_pass_add_time(DateUtil.getTimestamp());
				ideaUser.setIs_fx(1);
				ideaUserService.update(ideaUser);
				
				return baseResponse.setValue("操作成功！", null);
			} else if(type == 2) {
				IdeaUser ideaUser = new IdeaUser();
				ideaUser.setId(StringUtil.strToLong(map.get("id")));
				ideaUser.setIs_fx(0);
				ideaUser.setDistribution_lev(0L);
				ideaUser.setDistribution_refunse_add_time(DateUtil.getTimestamp());
				ideaUser.setDistribution_pass_add_time(0L);
				ideaUser.setDistribution_apply_add_time(0L);
				ideaUser.setNote("普通会员");
				
				//解除推荐信息
				ideaUserService.cancelBind(ideaUser.getId());
				ideaUserService.update(ideaUser);
				
				return baseResponse.setValue("操作成功！", null);
			} else if(type == 3) {
				IdeaUser ideaUser = new IdeaUser();
				ideaUser.setId(StringUtil.strToLong(map.get("id")));
				ideaUser.setDistribution_recommend_uid(0L);
				ideaUserService.update(ideaUser);
				
				return baseResponse.setValue("操作成功！", null);
			} else {
				return baseResponse.setError(405, "数据有误！");
			}
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 获取需要绑定的分销商信息
	 * @param member
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/merchant", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String merchant(@RequestBody Map<String, String> map) throws BusinessException {
		if(map != null && StringUtil.strToLong(map.get("id")) != null) {
			Map<String, Object> map2 = ideaUserService.getMerchant(StringUtil.strToLong(map.get("id")));
			return JSONObject.toJSONString(map2);
		} else {
			return null;
		}
	}
}
