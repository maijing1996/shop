package com.shop.controller.manager.popularize;

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
import com.shop.model.dto.Coupon;
import com.shop.model.dto.CouponUser;
import com.shop.model.po.IdeaCoupon;
import com.shop.service.IdeaCouponService;
import com.shop.service.IdeaCouponUserService;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/popularize/coupon")
public class DiscountCouponController {

	@Autowired
	private IdeaCouponService ideaCouponService;
	@Autowired
	private IdeaCouponUserService ideaCouponUserService;
	
	/**
	 * 获得多对象信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<Coupon> pageInfo = ideaCouponService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")),
				map.get("title"));
		if(pageInfo.getTotal() > 0){
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 查看优惠券领取使用情况
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/coupon", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listCoupon(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		Long coupon_id = StringUtil.strToLong(map.get("coupon_id"));
		if(coupon_id == null) {
			return layuiResponse.setError(404, "参数有误");
		}
		PageInfo<CouponUser> pageInfo = ideaCouponUserService.listInfo(StringUtil.strToInt(map.get("page")), StringUtil.strToInt(map.get("size")), coupon_id);
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
	public BaseResponse update(@RequestBody IdeaCoupon ideaCoupon) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaCoupon.getId() != null) {
			if(ideaCoupon.getMin_price() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaCoupon.getSend_bdate() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaCoupon.getSend_edate() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaCoupon.getTitle() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaCoupon.getType() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaCoupon.getUse_bdate() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaCoupon.getUse_edate() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			if(ideaCoupon.getYh_price() == null) {
				return baseResponse.setError(404, "信息不全，请填写完整输入");
			}
			
			ideaCouponService.update(ideaCoupon);
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
	public BaseResponse save(@RequestBody IdeaCoupon ideaCoupon) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(ideaCoupon.getId() != null) {
			return baseResponse.setError(500, "数据错误，请重新输入");
		}
		if(ideaCoupon.getMin_price() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaCoupon.getSend_bdate() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaCoupon.getSend_edate() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaCoupon.getTitle() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaCoupon.getType() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaCoupon.getUse_bdate() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaCoupon.getUse_edate() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		if(ideaCoupon.getYh_price() == null) {
			return baseResponse.setError(404, "信息不全，请填写完整输入");
		}
		
		ideaCouponService.save(ideaCoupon);
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
			ideaCouponService.deleteById(map.get("id"));
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
			ideaCouponService.deleteByIds(map.get("ids"));
			return baseResponse.setValue("信息已经删除！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确");
		}
	}
}
