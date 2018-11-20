package com.shop.controller.manager.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.exception.BusinessException;
import com.shop.model.common.BaseResponse;
import com.shop.model.common.LayuiResponse;
import com.shop.model.po.IdeaShopSites;
import com.shop.service.IdeaShopSitesService;

@Controller
@ResponseBody
@RequestMapping("/manager/shop/site")
public class SiteSettingsController {

	@Autowired
	private IdeaShopSitesService shopSitesService;
	
	/**
	 * 获得多对象信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo() {
		LayuiResponse layuiResponse = new LayuiResponse();
		IdeaShopSites ideaShopSites = shopSitesService.getInfo();
		if(ideaShopSites != null){
			layuiResponse.setData(ideaShopSites);
			layuiResponse.setCode(200);
			return layuiResponse;
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
	public BaseResponse update(@RequestBody IdeaShopSites ideaShopSites) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaShopSites.getId() != null) {
			if(ideaShopSites.getTitle() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaShopSites.getLogo() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaShopSites.getUpsize() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaShopSites.getUptype() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaShopSites.getReg_integral() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaShopSites.getDefault_stock() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaShopSites.getWarning_stock() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaShopSites.getCash() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaShopSites.getMini_cash() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			
			shopSitesService.update(ideaShopSites);
			return baseResponse.setValue("更新成功", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
