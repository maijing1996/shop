package com.shop.controller.manager.confine;

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
import com.shop.model.dto.Manager;
import com.shop.model.dto.ManagerSave;
import com.shop.model.po.IdeaAdmin;
import com.shop.service.IdeaAdminService;
import com.shop.util.MD5Util;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/admin")
public class ManagerController {

	@Autowired
	private IdeaAdminService ideaAdminService;
	
	/**
	 * 获得管理员信息
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) throws BusinessException {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<Manager> pageInfo = ideaAdminService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")));
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 删除单个管理员信息
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse delete(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			ideaAdminService.deleteById(StringUtil.strToLong(map.get("id")));
			return baseResponse.setValue("删除管理员成功", null);
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 批量删除管理员消息
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/deletes", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deletes(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && RegexUtil.matches(RegexUtil.IDS_REGEX, map.get("ids"))) {
			ideaAdminService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("删除管理员成功", null);
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 修改管理员信息
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody ManagerSave managerSave) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(managerSave.getId() != null) {
			IdeaAdmin ideaAdmin = new IdeaAdmin();
			ideaAdmin.setId(managerSave.getId());
			if(managerSave.getAccount() != null && RegexUtil.matches(RegexUtil.ACCOUNT_REGEX, managerSave.getAccount())
					|| managerSave.getAccount().length() < 4) {
				ideaAdmin.setUid(managerSave.getAccount());
			}
			if(managerSave.getPasswd() != null && RegexUtil.matches(RegexUtil.ACCOUNT_REGEX, managerSave.getPasswd())) {
				String pwd = MD5Util.MD5(managerSave.getPasswd());
				ideaAdmin.setPwd(pwd);
			}
			if(managerSave.getRole_id() != null) {
				ideaAdmin.setRole_id(managerSave.getRole_id());
			}
			if(managerSave.getIs_work() != null) {
				ideaAdmin.setIs_work(managerSave.getIs_work());
			}
			
			ideaAdminService.update(ideaAdmin);
			return baseResponse.setValue("管理员修改成功！", null);
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 添加管理员信息
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody ManagerSave managerSave) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(managerSave.getAccount() == null || !RegexUtil.matches(RegexUtil.ACCOUNT_REGEX, managerSave.getAccount())
				|| managerSave.getAccount().length() < 4) {
			return baseResponse.setError(404, "请输入正确的用户名信息！");
		}
		if(managerSave.getPasswd() == null || !RegexUtil.matches(RegexUtil.ACCOUNT_REGEX, managerSave.getPasswd())) {
			return baseResponse.setError(404, "请输入正确的登录密码！");
		}
		if(managerSave.getRole_id() == null) {
			return baseResponse.setError(404, "请输入角色类型！");
		}
		if(managerSave.getIs_work() == null) {
			return baseResponse.setError(404, "请确认是否需要激活！");
		}
		
		String pwd = MD5Util.MD5(managerSave.getPasswd());
		IdeaAdmin ideaAdmin = new IdeaAdmin();
		ideaAdmin.setUid(managerSave.getAccount());
		ideaAdmin.setRole_id(managerSave.getRole_id());
		ideaAdmin.setIs_work(managerSave.getIs_work());
		ideaAdmin.setPwd(pwd);
		
		ideaAdminService.save(ideaAdmin);
		return baseResponse.setValue("管理员已经添加成功", null);
	}
}
