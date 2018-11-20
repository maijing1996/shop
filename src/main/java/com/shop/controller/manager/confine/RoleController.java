package com.shop.controller.manager.confine;

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
import com.shop.model.dto.MenuPackage;
import com.shop.model.po.IdeaAdminRole;
import com.shop.service.IdeaAdminMenuService;
import com.shop.service.IdeaAdminRoleService;
import com.shop.util.RegexUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/role")
public class RoleController {

	@Autowired
	private IdeaAdminRoleService ideaAdminRoleService;
	@Autowired
	private IdeaAdminMenuService ideaAdminMenuService;
	
	/**
	 * 获取管理员角色数据
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo() {
		LayuiResponse layuiResponse = new LayuiResponse();
		List<IdeaAdminRole> list = ideaAdminRoleService.listInfo();
		if(list != null && list.size() > 0) {
			return layuiResponse.setSuccess(null, list, 0);
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 删除单个角色信息
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse delete(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			ideaAdminRoleService.deleteById(map.get("id"));
			return baseResponse.setValue("管理员角色已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 批量删除角色信息
	 * @param ids
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/deletes", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deletes(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && RegexUtil.matches(RegexUtil.IDS_REGEX, map.get("ids"))) {
			ideaAdminRoleService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("管理员角色已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 修改角色信息
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody IdeaAdminRole ideaAdminRole) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaAdminRole.getId() != null) {
			ideaAdminRoleService.update(ideaAdminRole);
			return baseResponse.setValue("管理员角色信息已修改！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 角色信息注册
	 * @param ideaAdminRole
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody IdeaAdminRole ideaAdminRole) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaAdminRole.getId() == null) {
			if(ideaAdminRole.getTitle() == null) {
				return baseResponse.setError(404, "请输入管理员角色名称！");
			}
			ideaAdminRoleService.save(ideaAdminRole);
			return baseResponse.setValue("管理员角色信息已添加！", null);
		} else {
			return baseResponse.setError(404, "操作失败，非法操作！");
		}
	}
	
	/**
	 * 获得菜单列表
	 * @param ideaAdminRole
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/menu", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse menu() throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		List<MenuPackage> menuPackage = ideaAdminMenuService.menu();
		return baseResponse.setValue(null, menuPackage);
	}
}
