package com.shop.controller.manager.shop;

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
import com.shop.model.dto.AdminMenu;
import com.shop.model.po.IdeaAdminMenu;
import com.shop.service.IdeaAdminMenuService;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/shop/menu")
public class MenuController {
	
	@Autowired
	private IdeaAdminMenuService ideaAdminMenuService;

	/**
	 * 查询后台菜单模块信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<AdminMenu> pageInfo = ideaAdminMenuService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
				StringUtil.strToLong(map.get("parent_id")));
		if(pageInfo.getTotal() > 0){
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 更新信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody IdeaAdminMenu ideaAdminMenu) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaAdminMenu.getId() != null) {
			if(ideaAdminMenu.getTitle() == null || "".equals(ideaAdminMenu.getTitle().trim())) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaAdminMenu.getIs_show() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaAdminMenu.getIs_turn() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaAdminMenu.getController() == null || "".equals(ideaAdminMenu.getController().trim())) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			
			ideaAdminMenuService.update(ideaAdminMenu);
			return baseResponse.setValue("更新成功", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 添加信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody IdeaAdminMenu ideaAdminMenu) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaAdminMenu.getId() != null) {
			return baseResponse.setError(500, "数据错误，请重新输入");
		}
		if(ideaAdminMenu.getTitle() == null || "".equals(ideaAdminMenu.getTitle().trim())) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaAdminMenu.getIs_show() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaAdminMenu.getIs_turn() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaAdminMenu.getType() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaAdminMenu.getController() == null || "".equals(ideaAdminMenu.getController().trim())) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaAdminMenu.getSequence() <= 0) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		
		ideaAdminMenuService.save(ideaAdminMenu);
		return baseResponse.setValue("添加成功", null);
	}
	
	/**
	 * 删除单个信息
	 * @param id
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse delete(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			ideaAdminMenuService.deleteById(map.get("id"));
			return baseResponse.setValue("信息已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
