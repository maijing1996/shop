package com.shop.controller.manager.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.shop.exception.BusinessException;
import com.shop.model.common.BaseResponse;
import com.shop.model.common.LayuiResponse;
import com.shop.model.dto.CheckState;
import com.shop.model.dto.Paylog;
import com.shop.model.po.IdeaUser;
import com.shop.model.po.IdeaUserPaylog;
import com.shop.service.IdeaUserPaylogService;
import com.shop.service.IdeaUserService;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/member/withdrawal")
public class WithdrawalController {
	
	@Autowired
	private IdeaUserPaylogService ideaUserPaylogService;
	@Autowired
	private IdeaUserService ideaUserService;
	
	/**
	 * 获得用户提现信息
	 * @param nickName
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<Paylog> pageInfo = ideaUserPaylogService.withdrawal(StringUtil.strToInt(map.get("page")),
				StringUtil.strToInt(map.get("size")), map.get("nickName"));
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 会员提现审核操作
	 * state -1:拒绝 0:审核 1:通过
	 * @param nickName
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/check", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse setPayState(@RequestBody CheckState checkState) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(checkState.getId() != null && checkState.getState() != null) {
			if(checkState.getState() == 1) {
				IdeaUserPaylog ideaUserPaylog = new IdeaUserPaylog();
				ideaUserPaylog.setId(checkState.getId());
				ideaUserPaylog.setPay_state(1);
				ideaUserPaylogService.update(ideaUserPaylog);
			}  else if(checkState.getState() == -1) {
				IdeaUserPaylog ideaUserPaylog = ideaUserPaylogService.getById(checkState.getId());
				if(ideaUserPaylog != null) {
					ideaUserPaylog.setPay_state(-1);
					ideaUserPaylogService.update(ideaUserPaylog);
					
					IdeaUser ideaUser = new IdeaUser();
					ideaUser.setId(ideaUserPaylog.getUser_id());
					ideaUser.setUser_money(ideaUserPaylog.getFee());
					ideaUserService.update(ideaUser);
				} else {
					return baseResponse.setError(404, "提现失败！");
				}
			} else {
				return baseResponse.setError(405, "参数有误！");
			}
			
			return baseResponse.setValue("会员提现申请已审核", null);
		} else {
			return baseResponse.setError(405, "会员提现申请审核失败！");
		}
	}
}
