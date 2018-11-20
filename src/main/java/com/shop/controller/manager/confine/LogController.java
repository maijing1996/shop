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
import com.shop.model.po.IdeaAdminLog;
import com.shop.service.IdeaAdminLogService;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/log")
public class LogController {

	@Autowired
	private IdeaAdminLogService ideaAdminLogService;
	
	/**
	 * 获得日志信息
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) throws BusinessException {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<IdeaAdminLog> pageInfo = ideaAdminLogService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
				StringUtil.strToInt(map.get("state")));
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据！");
		}
	}
	
	/**
	 * 删除单个系统操作日志
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse delete(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			boolean boo = ideaAdminLogService.deleteById(map.get("id"));
			if(boo) {
				return baseResponse.setValue("删除系统操作日志成功！", null);
			} else {
				return baseResponse.setError(500, "删除系统操作日志失败！");
			}
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
	
	/**
	 * 批量删除系统操作日志
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/deletes", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deletes(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && RegexUtil.matches(RegexUtil.IDS_REGEX, map.get("ids"))) {
			boolean boo = ideaAdminLogService.deleteByIds(map.get("ids"));
			if(boo) {
				return baseResponse.setValue("删除系统操作日志成功！", null);
			} else {
				return baseResponse.setError(500, "删除系统操作日志失败！");
			}
		} else {
			return baseResponse.setError(405, "操作对象不明确！");
		}
	}
}
