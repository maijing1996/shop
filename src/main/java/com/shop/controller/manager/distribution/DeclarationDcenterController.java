package com.shop.controller.manager.distribution;

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
import com.shop.model.po.IdeaDcenter;
import com.shop.service.IdeaDcenterService;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/distribution/dcenter")
public class DeclarationDcenterController {

	@Autowired
	private IdeaDcenterService ideaDcenterService;

	/**
	 * 查询报表中心的信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/info", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<IdeaDcenter> pageInfo = ideaDcenterService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")));
		if (pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据！");
		}
	}

	/**
	 * 添加报单信息
	 * @param ideaDcenter
	 * @return
	 * @throws BusinessException
	 */
	/*@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse insert(@RequestBody IdeaDcenter ideaDcenter) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaDcenter.getId() == null) {
			if (ideaDcenter.getName() != null && !"".equals(ideaDcenter.getName().trim())) {
				boolean boo = ideaDcenterService.isEmpty(ideaDcenter.getName());
				if(boo) {
					ideaDcenterService.update(ideaDcenter);
					return baseResponse.setValue("操作成功！", null);
				} else {
					return baseResponse.setValue("报单已经存在，请重新输入！", null);
				}
			} else {
				return baseResponse.setError(404, "请输入名称！");
			}
		} else {
			return baseResponse.setError(404, "数据错误，请重新输入！");
		}
	}*/
	
	/**
	 * 修改保单信息
	 * @param ideaDcenter
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse update(@RequestBody IdeaDcenter ideaDcenter) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaDcenter.getId() != null && ideaDcenter.getName() != null && !"".equals(ideaDcenter.getName().trim())) {
			boolean boo = ideaDcenterService.isEmpty(ideaDcenter.getName().trim());
			if(boo) {
				ideaDcenterService.update(ideaDcenter);
				return baseResponse.setValue("信息已经修改", null);
			} else {
				return baseResponse.setValue("报单已经存在，请重新输入！", null);
			}
		} else {
			return baseResponse.setError(404, "操作对象不明确！");
		}
	}
	
	/**
	 * 删除报单数据
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse delete(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			ideaDcenterService.deleteById(map.get("id"));
			return baseResponse.setValue("信息已经删除", null);
		}else {
			return baseResponse.setError(404, "操作对象不明确！");
		}
	}
}
