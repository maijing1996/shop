package com.shop.controller.manager.wechat;

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
import com.shop.model.dto.WxMenu;
import com.shop.model.po.IdeaWxMenu;
import com.shop.service.IdeaWxMenuService;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/wechat/menu")
public class WechatMenuController {
	
	@Autowired
	private IdeaWxMenuService ideaWxMenuService;
	
	/**
	 * 获取微信菜单信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<WxMenu> pageInfo = ideaWxMenuService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
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
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody IdeaWxMenu ideaWxMenu) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaWxMenu.getId() != null) {
			if(ideaWxMenu.getTitle() == null || "".equals(ideaWxMenu.getTitle().trim())) {
				return baseResponse.setError(404, "信息不全，请填写完整"); 
			}
			if(ideaWxMenu.getInfo() == null || "".equals(ideaWxMenu.getInfo())) {
				return baseResponse.setError(404, "信息不全，请填写完整"); 
			}
			if(ideaWxMenu.getType() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整"); 
			}
			if(ideaWxMenu.getSequence() == null || ideaWxMenu.getSequence() < 1) {
				return baseResponse.setError(404, "信息不全，请填写完整"); 
			}
			if(ideaWxMenu.getIs_show() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整"); 
			}
			
			ideaWxMenuService.update(ideaWxMenu);
			return baseResponse.setValue("更新成功", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 添加信息
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse save(@RequestBody IdeaWxMenu ideaWxMenu) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaWxMenu.getId() != null) {
			return baseResponse.setError(500, "数据错误，请重新输入");
		}
		if(ideaWxMenu.getInfo() == null || "".equals(ideaWxMenu.getInfo().trim())) {
			return baseResponse.setError(404, "信息不全，请填写完整");
		}
		if(ideaWxMenu.getIs_show() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整");
		}
		if(ideaWxMenu.getSequence() == null || ideaWxMenu.getSequence() < 1) {
			return baseResponse.setError(404, "信息不全，请填写完整");
		}
		if(ideaWxMenu.getTitle() == null || "".equals(ideaWxMenu.getTitle().trim())) {
			return baseResponse.setError(404, "信息不全，请填写完整");
		}
		if(ideaWxMenu.getType() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整");
		}
		
		ideaWxMenuService.save(ideaWxMenu);
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
			ideaWxMenuService.deleteById(map.get("id"));
			return baseResponse.setValue("信息已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/deletes", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deletes(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && RegexUtil.matches(RegexUtil.IDS_REGEX, map.get("ids"))) {
			ideaWxMenuService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("信息已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
