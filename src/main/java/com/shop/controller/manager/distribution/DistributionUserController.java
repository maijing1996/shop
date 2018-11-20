package com.shop.controller.manager.distribution;

import java.util.ArrayList;
import java.util.List;
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
import com.shop.model.dto.DistributionUser;
import com.shop.model.po.IdeaCouponUser;
import com.shop.model.po.IdeaDcenter;
import com.shop.model.po.IdeaDistributionCategroy;
import com.shop.model.po.IdeaDistributionCategroyPrice;
import com.shop.model.po.IdeaDistributionCategroyVoucher;
import com.shop.model.po.IdeaUser;
import com.shop.model.po.IdeaUserPaylog;
import com.shop.service.IdeaCouponUserService;
import com.shop.service.IdeaDcenterService;
import com.shop.service.IdeaDistributionCategroyPriceService;
import com.shop.service.IdeaDistributionCategroyService;
import com.shop.service.IdeaDistributionCategroyVoucherService;
import com.shop.service.IdeaUserPaylogService;
import com.shop.service.IdeaUserService;
import com.shop.util.DateUtil;
import com.shop.util.StringUtil;

//分销商成员信息控制
@Controller
@ResponseBody
@RequestMapping("/manager/distribution/distributionUser")
public class DistributionUserController {
	
	@Autowired
	private IdeaUserService ideaUserService;
	@Autowired
	private IdeaDistributionCategroyService ideaDistributionCategroyService;
	@Autowired
	private IdeaDistributionCategroyPriceService ideaDistributionCategroyPriceService;
	@Autowired
	private IdeaUserPaylogService ideaUserPaylogService;
	@Autowired
	private IdeaDistributionCategroyVoucherService ideaDistributionCategroyVoucherService;
	@Autowired
	private IdeaCouponUserService ideaCouponUserService;
	@Autowired
	private IdeaDcenterService ideaDcenterService;
	
	/**
	 * 获得分销商的列表
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listDistributionUser(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<DistributionUser> pageInfo = ideaUserService.listDistributionUser(StringUtil.strToInt(map.get("page")),
				StringUtil.strToInt(map.get("size")), map.get("uid"),StringUtil.strToLong(map.get("id")));
		if (pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据！");
		}
	}
	
	/**
	 * 修改分销商信息
	 * @param uid
	 * @param name
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && StringUtil.strToLong(map.get("id")) != null) {
			IdeaUser ideaUser = new IdeaUser();
			ideaUser.setId(StringUtil.strToLong(map.get("id")));
			if(StringUtil.strToLong(map.get("lev")) == null) {
				return baseResponse.setError(404, "参数异常，请联系管理员！");
			} else if(StringUtil.strToLong(map.get("lev")) == -1) {
				ideaUser.setIs_fx(0);
				ideaUser.setDistribution_lev(0L);
				ideaUser.setDistribution_refunse_add_time(DateUtil.getTimestamp());
				ideaUser.setDistribution_pass_add_time(0L);
				ideaUser.setDistribution_apply_add_time(0L);
				ideaUser.setNote("普通会员");
				
				//解除推荐信息
				ideaUserService.cancelBind(ideaUser.getId());
				ideaDcenterService.deleteByUserId(ideaUser.getId());
				ideaUserService.update(ideaUser);
			} else {
				ideaUser = ideaUserService.getById(ideaUser.getId());
				
				Long relev = ideaUser.getDistribution_lev();
				Long lev = StringUtil.strToLong(map.get("lev"));
				IdeaDistributionCategroy ideaDistributionCategroy= ideaDistributionCategroyService.getById(lev);
				ideaUser.setDistribution_lev(lev);
				ideaUser.setNote(ideaDistributionCategroy.getName());
				ideaUserService.update(ideaUser);
				
				if(ideaUser.getDistribution_lev() == 1) {
					IdeaDcenter ideaDcenter = new IdeaDcenter();
					ideaDcenter.setUser_id(ideaUser.getId());
					List<IdeaDcenter> list = ideaDcenterService.getAll(ideaDcenter, null);
					if(list == null || list.isEmpty()) {
						ideaDcenter.setName(map.get("name")+"共享店");
						ideaDcenterService.save(ideaDcenter);
					}
				} else if(relev == 1) {
					ideaDcenterService.deleteByUserId(ideaUser.getId());
				}
			}
			
			return baseResponse.setValue("操作成功！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确！");
		}
	}
	
	/**
	 * 通过/拒绝审核
	 * @param distribution_lev
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/check", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse check(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && StringUtil.strToLong(map.get("id")) != null) {
			if(map.get("state") == null) {
				return baseResponse.setError(404, "参数异常，请联系管理员！");
			} else if(StringUtil.strToInt(map.get("state")) == 1) {
				//申请人信息修改
				IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map.get("id")));
				ideaUser.setIs_fx(1);
				ideaUser.setDcenter(StringUtil.strToLong(map.get("dcenter")));
				ideaUser.setDistribution_pass_add_time(DateUtil.getTimestamp());
				ideaUser.setPass_note(map.get("pass_note"));
				ideaUser.setNote(map.get("level"));
				ideaUserService.update(ideaUser);
				
				//给予优惠券
				IdeaDistributionCategroyVoucher ideaDistributionCategroyVoucher = new IdeaDistributionCategroyVoucher();
				ideaDistributionCategroyVoucher.setCid(ideaUser.getDistribution_lev());
				List<IdeaDistributionCategroyVoucher> list2 = ideaDistributionCategroyVoucherService.getAll(ideaDistributionCategroyVoucher, null);
				if(list2 != null && !list2.isEmpty()) {
					List<IdeaCouponUser> list3 = new ArrayList<IdeaCouponUser>();
					for(IdeaDistributionCategroyVoucher ideaDistributionCategroyVoucher2 : list2) {
						for(int i=0; i < ideaDistributionCategroyVoucher2.getUnit(); i++) {
							IdeaCouponUser ideaCouponUser = new IdeaCouponUser();
							ideaCouponUser.setAdd_date(DateUtil.getTimestamp());
							ideaCouponUser.setCoupon_id(ideaDistributionCategroyVoucher2.getVid());
							ideaCouponUser.setCoupon_sn(StringUtil.getCouponSn(8));
							ideaCouponUser.setIs_use(0);
							ideaCouponUser.setIs_delete(0);
							ideaCouponUser.setUser_id(StringUtil.strToLong(map.get("id")));
							
							list3.add(ideaCouponUser);
						}
					}
					
					ideaCouponUserService.insertList(list3);
				}
				
				//推荐人给予佣金
				if(ideaUser.getDistribution_recommend_uid() != null && ideaUser.getDistribution_recommend_uid() != 0L) {
					IdeaUser ideaUser2 = ideaUserService.getById(ideaUser.getDistribution_recommend_uid());
					if(ideaUser2 != null && ideaUser2.getIs_fx() == 1) {
						IdeaDistributionCategroyPrice ideaDistributionCategroyPrice = new IdeaDistributionCategroyPrice();
						ideaDistributionCategroyPrice.setThis_id(ideaUser2.getDistribution_lev());
						ideaDistributionCategroyPrice.setTarget_id(ideaUser.getDistribution_lev());
						List<IdeaDistributionCategroyPrice> list = ideaDistributionCategroyPriceService.getAll(ideaDistributionCategroyPrice, null);
						if(list != null && !list.isEmpty()) {
							IdeaDistributionCategroyPrice ideaDistributionCategroyPrice2 = list.get(0);
							Double money = ideaUser2.getUser_money() + ideaDistributionCategroyPrice2.getPrice();
							ideaUser2.setUser_money(money);
							ideaUserService.update(ideaUser2);
							
							IdeaUserPaylog userPaylog = new IdeaUserPaylog();
							userPaylog.setUser_id(ideaUser2.getId());
							userPaylog.setType(6);
							userPaylog.setFee(ideaUser.getUser_money());
							userPaylog.setPay_type(3);
							userPaylog.setInfo("推荐奖励，被推荐的用户："+ideaUser.getId());
							userPaylog.setAccount_fee(money);
							userPaylog.setCash_type(0);
							userPaylog.setCash_info(null);
							userPaylog.setPay_state(1);
							userPaylog.setAdd_date(DateUtil.getTimestamp());
							ideaUserPaylogService.save(userPaylog);
						}
					}
				}
				
				if(ideaUser.getDistribution_lev() == 1) {
					IdeaDcenter ideaDcenter = new IdeaDcenter();
					ideaDcenter.setUser_id(ideaUser.getId());
					List<IdeaDcenter> list = ideaDcenterService.getAll(ideaDcenter, null);
					if(list == null || list.isEmpty()) {
						ideaDcenter.setName(ideaUser.getReal_name()+"共享店");
						ideaDcenterService.save(ideaDcenter);
					}
				}
			} else if(StringUtil.strToInt(map.get("state")) == -1) {
				IdeaUser ideaUser = new IdeaUser();
				ideaUser.setId(StringUtil.strToLong(map.get("id")));
				ideaUser.setIs_fx(0);
				ideaUser.setDistribution_lev(0L);
				ideaUser.setDistribution_refunse_add_time(DateUtil.getTimestamp());
				ideaUser.setDistribution_pass_add_time(0L);
				ideaUser.setDistribution_apply_add_time(0L);
				ideaUser.setPass_note("");
				ideaUser.setNote("普通会员");
				ideaUserService.update(ideaUser);
				
			} else {
				return baseResponse.setError(404, "参数异常，请联系管理员！");
			}
			
			return baseResponse.setValue("操作成功！", null);
		} else {
			return baseResponse.setError(404, "操作对象不明确！");
		}
	}
	
	/**
	 * 后台添加人员
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse insert(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<DistributionUser> pageInfo = ideaUserService.listDistributionUser(StringUtil.strToInt(map.get("page")),
				StringUtil.strToInt(map.get("size")), map.get("uid"),StringUtil.strToLong(map.get("id")));
		if (pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据！");
		}
	}
}
