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
import com.shop.model.po.IdeaShopTransaction;
import com.shop.service.IdeaShopTransactionService;

@Controller
@ResponseBody
@RequestMapping("/manager/shop/transaction")
public class TransactionController {

	@Autowired
	private IdeaShopTransactionService ideaShopTransactionService;
	
	/**
	 * 获得多对象信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo() {
		LayuiResponse layuiResponse = new LayuiResponse();
		IdeaShopTransaction ideaShopTransaction = ideaShopTransactionService.getInfo();
		if(ideaShopTransaction != null){
			layuiResponse.setCode(200);
			layuiResponse.setData(ideaShopTransaction);
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
	public BaseResponse update(@RequestBody IdeaShopTransaction ideaShopTransaction) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaShopTransaction.getId() != null) {
			if(ideaShopTransaction.getFull_cut() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaShopTransaction.getShipments() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaShopTransaction.getUse_ratio_integral() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaShopTransaction.getUnpaid() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaShopTransaction.getIntegral_exchange() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			} else if(ideaShopTransaction.getIntegral_exchange() == 1) {
				if(ideaShopTransaction.getMini_integral() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopTransaction.getIntegral_exchange_proportion() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
				if(ideaShopTransaction.getIntegral_exchange() == null) {
					return baseResponse.setError(404, "信息不全，请填写完整输入");
				}
			}
			
			ideaShopTransactionService.update(ideaShopTransaction);
			return baseResponse.setValue("更新成功", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
