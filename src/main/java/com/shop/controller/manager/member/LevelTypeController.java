package com.shop.controller.manager.member;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.exception.BusinessException;
import com.shop.model.common.BaseResponse;
import com.shop.model.common.LayuiResponse;
import com.shop.model.dto.UserLevel;
import com.shop.model.po.IdeaUserLevel;
import com.shop.service.IdeaUserLevelService;
import com.shop.util.RegexUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/member/level")
public class LevelTypeController {

	@Autowired
	private IdeaUserLevelService ideaUserLevelService;
	
	/**
	 * 获取会员等级类别信息
	 * @param request
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		List<IdeaUserLevel> list = ideaUserLevelService.listInfo(map.get("nickName"));
		if(list != null) {
			return layuiResponse.setSuccess(null, list, 0);
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 删除单个等级信息
	 * @param id
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deleteById(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			ideaUserLevelService.deleteById(map.get("id"));
			return baseResponse.setValue("删除成功！", true);
		} else {
			return baseResponse.setError(404, "删除对象不明确");
		}
	}
	
	/**
	 * 批量删除等级信息
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/deletes", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deleteByIds(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && RegexUtil.matches(RegexUtil.IDS_REGEX, map.get("ids"))) {
			ideaUserLevelService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("删除成功！", true);
		} else {
			return baseResponse.setError(404, "删除对象不明确");
		}
	}
	
	/**
	 * 修改会员等级信息
	 * @param userLevel
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody UserLevel userLevel) throws BusinessException {
		
		BaseResponse baseResponse = new BaseResponse();
		if(userLevel.getId() != null) {
			//数据封装
			IdeaUserLevel ideaUserLevel = new IdeaUserLevel();
			ideaUserLevel.setId(userLevel.getId());
			if(userLevel.getAmount() != null && userLevel.getAmount() > -1) {
				ideaUserLevel.setAmount(userLevel.getAmount());
			}
			if(userLevel.getRebate() != null && userLevel.getRebate() > -1 && userLevel.getRebate() < 101) {
				ideaUserLevel.setRebate(userLevel.getRebate());
			}
			if(userLevel.getInfo() != null) {
				ideaUserLevel.setInfo(userLevel.getInfo());
			}
			if(userLevel.getTitle() != null && !"".equals(userLevel.getTitle().trim())) {
				ideaUserLevel.setTitle(userLevel.getTitle().trim());
			}
			
			ideaUserLevelService.update(ideaUserLevel);
			baseResponse.setValue("更新成功", true);
		} else {
			baseResponse.setError(404, "操作对象不明确！");
		}
		return baseResponse;
	}
	
	/**
	 * 插入会员等级信息
	 * @param userLevel
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody UserLevel userLevel) throws BusinessException {
		
		BaseResponse baseResponse = new BaseResponse();
		if(userLevel.getAmount() == null || userLevel.getAmount() < 0) {
			return baseResponse.setError(404, "消费额没有输入！");
		}
		if(userLevel.getRebate() == null || userLevel.getRebate() < 0 || userLevel.getRebate() > 100) {
			return baseResponse.setError(404, "折扣率没有输入！");
		}
		if(userLevel.getTitle() == null || "".equals(userLevel.getTitle().trim())) {
			return baseResponse.setError(404, "标题没有输入！");
		}
		
		IdeaUserLevel ideaUserLevel = new IdeaUserLevel();
		ideaUserLevel.setAmount(userLevel.getAmount());
		ideaUserLevel.setInfo(userLevel.getInfo());
		ideaUserLevel.setRebate(userLevel.getRebate());
		ideaUserLevel.setTitle(userLevel.getTitle().trim());
		
		ideaUserLevelService.save(ideaUserLevel);
		return baseResponse.setValue("添加等级成功", true);
	}
}
