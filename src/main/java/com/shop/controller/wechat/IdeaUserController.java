package com.shop.controller.wechat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.exception.BusinessException;
import com.shop.model.dto.OrderInfo;
import com.shop.model.dto.wechat.ObjectResponse;
import com.shop.model.dto.wechat.PhoneNumber;
import com.shop.model.po.IdeaAds;
import com.shop.model.po.IdeaConfig;
import com.shop.model.po.IdeaCouponPerson;
import com.shop.model.po.IdeaDistributionCategroy;
import com.shop.model.po.IdeaGoods;
import com.shop.model.po.IdeaOrder;
import com.shop.model.po.IdeaOrderGoods;
import com.shop.model.po.IdeaPickUp;
import com.shop.model.po.IdeaUser;
import com.shop.model.po.IdeaUserAddress;
import com.shop.model.po.IdeaUserPaylog;
import com.shop.service.IdeaAdsService;
import com.shop.service.IdeaConfigService;
import com.shop.service.IdeaCouponPersonService;
import com.shop.service.IdeaDistributionCategroyService;
import com.shop.service.IdeaGoodsService;
import com.shop.service.IdeaOrderGoodsService;
import com.shop.service.IdeaOrderService;
import com.shop.service.IdeaPickUpService;
import com.shop.service.IdeaUserAddressService;
import com.shop.service.IdeaUserLevelService;
import com.shop.service.IdeaUserPaylogService;
import com.shop.service.IdeaUserService;
import com.shop.util.AESUtil;
import com.shop.util.DateUtil;
import com.shop.util.JsonUtil;
import com.shop.util.RedisUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/")
public class IdeaUserController {
	
	private static Logger logger = LoggerFactory.getLogger(IdeaUserController.class);
	
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private IdeaUserAddressService ideaUserAddressService;
	@Autowired
	private IdeaUserService ideaUserService;
	@Autowired
	private IdeaOrderService ideaOrderService;
	@Autowired
	private IdeaUserPaylogService ideaUserPaylogService;
	@Autowired
	private IdeaDistributionCategroyService ideaDistributionCategroyService;
	@Autowired
	private IdeaOrderGoodsService ideaOrderGoodsService;
	@Autowired
	private IdeaPickUpService ideaPickUpService;
	@Autowired
	private IdeaGoodsService ideaGoodsService;
	@Autowired
	private IdeaCouponPersonService ideaCouponPersonService;
	@Autowired
	private IdeaConfigService ideaConfigService;
	@Autowired
	private IdeaUserLevelService ideaUserLevelService;
	@Autowired
	private IdeaAdsService ideaAdsService;

	/**
	 * 添加用户地址
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_address", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String apiAddress(HttpServletRequest request, @RequestBody Map<String, String> map) throws BusinessException {
		Map<String, String> map3 = new HashMap<String, String>();
		if(map != null && map.get("m_openid") != null){
			Long userId = 0L;
			@SuppressWarnings("unchecked")
			Map<String, Object> map2 = (Map<String, Object>) redisUtil.get(map.get("m_openid"));
			if(map2 != null) {
				userId = StringUtil.strToLong(map2.get("userId").toString());
			} else {
				IdeaUser ideaUser = ideaUserService.isExist(null, map.get("m_openid"));
				if(ideaUser != null && ideaUser.getId() != null) {
					userId = ideaUser.getId();
					
					Map<String, Object> userMap = new HashMap<String, Object>();
					userMap.put("unionId", ideaUser.getSession_key());
					userMap.put("userId", ideaUser.getId());
					redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				} else {
					map.put("res", "false");
					return JSONObject.toJSONString(map3);
				}
			}
			
			IdeaUserAddress ideaUserAddress = new IdeaUserAddress();
			ideaUserAddress.setAddress(map.get("m_address"));
			ideaUserAddress.setCity(map.get("m_city"));
			ideaUserAddress.setCounty(map.get("m_county"));
			ideaUserAddress.setUser_id(userId);
			ideaUserAddress.setTel(map.get("m_tel"));
			ideaUserAddress.setProvince(map.get("m_province"));
			ideaUserAddress.setName(map.get("m_name"));
			
			int val = StringUtil.strToInt(map.get("m_default"));
			if(val == 1) {
				IdeaUserAddress ideaUserAddress1 = new IdeaUserAddress();
				ideaUserAddress1.setIs_default(1);
				ideaUserAddress1.setUser_id(userId);
				List<IdeaUserAddress> list = ideaUserAddressService.getAll(ideaUserAddress1, null);
				if(list != null && !list.isEmpty()) {
					for(IdeaUserAddress ideaUserAddress2 : list) {
						ideaUserAddress2.setIs_default(0);
						ideaUserAddressService.update(ideaUserAddress2);
					}
				}
			}
			ideaUserAddress.setIs_default(val);
			ideaUserAddressService.save(ideaUserAddress);
			
			map.put("res", "true");
		} else {
			map.put("res", "false");
		}
		
		return JSONObject.toJSONString(map3);
	}
	
	/**
	 * 获取分销商列表
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/api_apply", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiApply(@RequestParam(name="open_id", required=false) String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//获得广告
		IdeaAds ideaAds = new IdeaAds();
		ideaAds.setIs_show(1);
		ideaAds.setType(2);
		List<IdeaAds> listI = ideaAdsService.getAll(ideaAds, "sequence ASC");
		if(listI == null) {
			listI = new ArrayList<IdeaAds>();
		}
		
		//获得分销商类型
		if(openId != null && !"".equals(openId)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map2 = (Map<String, Object>) redisUtil.get(openId);
			IdeaUser ideaUser = null;
			if(map2 != null) {
				ideaUser = ideaUserService.getById(StringUtil.strToLong(map2.get("userId").toString()));
			} else {
				ideaUser = ideaUserService.isExist(null, openId);
				if(ideaUser != null) {
					Map<String, Object> userMap = new HashMap<String, Object>();
					userMap.put("unionId", ideaUser.getSession_key());
					userMap.put("userId", ideaUser.getId());
					redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				} else {
					List<String> list = new ArrayList<String>();
					map.put("list", list);
					map.put("target_nickName", "");
					map.put("target_uid", "");
					map.put("ads", "");
					map.put("msg", "非正常用户登录");
					return JSONObject.toJSONString(map); 
				}
			}
			
			List<IdeaDistributionCategroy> list = ideaDistributionCategroyService.listInfoByUserId();
			map.put("list", list);
			if(ideaUser.getDistribution_recommend_uid() != 0) {
				IdeaUser ideaUser2 = ideaUserService.getById(ideaUser.getDistribution_recommend_uid());
				if(ideaUser2 != null && ideaUser2.getIs_fx() == 1 && ideaUser2.getOauth_nickname() != null) {
					map.put("target_nickName", ideaUser2.getOauth_nickname());
					map.put("target_uid", ideaUser.getDistribution_recommend_uid());
					map.put("ads", listI);
				} else {
					List<IdeaDistributionCategroy> list2 = ideaDistributionCategroyService.listInfoByUserId();
					map.put("list", list2);
					map.put("target_nickName", "");
					map.put("target_uid", "");
					map.put("ads", listI);
				}
			} else {
				List<IdeaDistributionCategroy> list2 = ideaDistributionCategroyService.listInfoByUserId();
				map.put("list", list2);
				map.put("target_uid", "");
				map.put("target_nickName", "");
				map.put("ads", listI);
			}
		} else {
			List<IdeaDistributionCategroy> list = ideaDistributionCategroyService.listInfoByUserId();
			map.put("list", list);
			map.put("target_uid", "");
			map.put("target_nickName", "");
			map.put("ads", listI);
		}
		
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 获得用户信息,
	 * @param id
	 * @param m_openid
	 * @return
	 */
	@RequestMapping(value="/api_getuser", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiGetUser(HttpServletRequest request, @RequestParam(name="id") Long parentId, @RequestParam(name="m_openid") String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map2 = (Map<String, Object>) redisUtil.get(openId);
		if(map2 != null) {
			IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map2.get("userId").toString()));
			map.put("data", ideaUser);
		} else {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map.put("data", ideaUser);
			} else {
				map.put("data", "登录异常，该用户未授权登录");
			}
		}
		
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 用户上下级关系绑定
	 * @param id
	 * @param targetId
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_binduser", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiBinduser(@RequestParam(name="id") Long id, @RequestParam(name="targetId") Long targetId) throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		IdeaUser ideaUser = new IdeaUser();
		ideaUser.setId(id);
		ideaUser.setPid(targetId);
		ideaUserService.update(ideaUser);
		
		map.put("code", 1000);
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 绑定推荐人关系
	 * @param openId
	 * @param targetId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/api_binds", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiBinds(@RequestParam(name="open_id") String openId, @RequestParam(name="targetId") Long targetId) throws BusinessException {
		ObjectResponse objectResponse = new ObjectResponse();
		if(openId != null && targetId != null) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) redisUtil.get(openId);
			if(map != null) {
				IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map.get("userId").toString()));
				if(ideaUser != null) {
					if(targetId > 9999 && ideaUser.getDistribution_recommend_uid() == 0) {
						if(targetId != ideaUser.getId()) {
							IdeaUser ideaUser2 = ideaUserService.getById(targetId);
							if(ideaUser2 != null ) {
								ideaUser.setDistribution_recommend_uid(ideaUser2.getId());
								ideaUserService.update(ideaUser);
								
								objectResponse.setMsg("推荐关系已绑定");
							} else {
								objectResponse.setMsg("推荐人不存在");
							}
						} else {
							objectResponse.setMsg("自己推荐自己");
						}
					} else {
						objectResponse.setMsg("已有推荐绑定关系");
					}
				} else {
					objectResponse.setMsg("用户不存在");
				}
			} else {
				IdeaUser ideaUser = ideaUserService.isExist(null, openId);
				if(ideaUser != null) {
					Map<String, Object> userMap = new HashMap<String, Object>();
					userMap.put("unionId", ideaUser.getSession_key());
					userMap.put("userId", ideaUser.getId());
					redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
					
					if(targetId > 9999 && ideaUser.getDistribution_recommend_uid() == 0) {
						if(targetId != ideaUser.getId()) {
							IdeaUser ideaUser2 = ideaUserService.getById(targetId);
							if(ideaUser2 != null ) {
								ideaUser.setDistribution_recommend_uid(ideaUser2.getId());
								ideaUserService.update(ideaUser);
								
								objectResponse.setMsg("推荐关系已绑定");
							} else {
								objectResponse.setMsg("推荐人不存在");
							}
						} else {
							objectResponse.setMsg("自己推荐自己");
						}
					} else {
						objectResponse.setMsg("已有推荐绑定关系");
					}
				} else {
					objectResponse.setMsg("登录异常，该用户未授权登录");
					return JSONObject.toJSONString(objectResponse);
				}
			}
		} else {
			objectResponse.setMsg("参数错误");
		}
		
		objectResponse.setCode(200);
		return JSONObject.toJSONString(objectResponse);
	}
	
	/**
	 * 获取订单列表
	 * @param state
	 * @param m_openid
	 * @return
	 */
	/*@RequestMapping(value="/api_dorder", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiDorder(HttpServletRequest request, @RequestParam(name="state") String state, @RequestParam(name="m_openid") String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map2 = (Map<String, Object>) redisUtil.get(openId);
		if(map2 != null) {
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("unionId", ideaUser.getSession_key());
			userMap.put("userId", ideaUser.getId());
			redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
		} else {
			
		}
		IdeaUser ideaUser = ideaUserService.getById((Long) map2.get("userId"));
		
		map.put("data", ideaUser);
		return JSONObject.toJSONString(map);
	}*/
	
	/**
	 * 取消订单
	 * @param state
	 * @param m_openid
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_order/{id}", method=RequestMethod.PUT, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String cancelOrder(@PathVariable("id") Long id, @RequestBody Map<String, String> map2) throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		IdeaOrder ideaOrder = new IdeaOrder();
		ideaOrder.setId(id);
		ideaOrder.setOrder_state(-1);
		ideaOrderService.update(ideaOrder);
		map.put("code", 1001);
		map.put("msg", null);
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 删除订单
	 * @param id
	 * @param m_openid
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_order/{id}", method=RequestMethod.DELETE, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String delOrder(@PathVariable("id") Long id, @RequestBody Map<String, String> map2) throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		ideaOrderService.deleteById(id);
		map.put("code", 1001);
		map.put("msg", null);
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 获取地址信息
	 * 
	 * @param id
	 * @param m_openid
	 * @return
	 */
	@RequestMapping(value="/api_address/{id}", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String getAddress(@PathVariable("id") Long id, @RequestParam(name="m_openid", required=false) String openId) {
		IdeaUserAddress ideaUserAddress = ideaUserAddressService.getById(id);
		return JSONObject.toJSONString(ideaUserAddress);
	}
	
	/**
	 * 删除地址信息
	 * 
	 * @param id
	 * @param m_openid
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_address/{id}", method=RequestMethod.DELETE, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String deleteAdd(@PathVariable("id") Long id, @RequestParam(name="m_openid", required=false) String openId) throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		ideaUserAddressService.deleteById(id);
		map.put("code", 1001);
		map.put("msg", "删除成功");
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 编辑地址
	 * @param id
	 * @param m_province
	 * @param m_city
	 * @param m_county
	 * @param m_address
	 * @param m_name
	 * @param m_tel
	 * @param m_default
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_address/{id}", method=RequestMethod.PUT, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String udpAddress(@PathVariable("id") Long id, @RequestBody Map<String, String> map1) throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		Long userId = null;
		@SuppressWarnings("unchecked")
		Map<String, Object> map2 = (Map<String, Object>) redisUtil.get(map1.get("open_id"));
		if(map2 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, map1.get("open_id"));
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				userId = ideaUser.getId();
			} else {
				map.put("code", 204);
				map.put("msg", "登录异常，该用户未授权登录");
				return JSONObject.toJSONString(map);
			}
		} else {
			userId = StringUtil.strToLong(map2.get("userId").toString());
		}
		
		Integer val = StringUtil.strToInt(map1.get("m_default"));
		IdeaUserAddress ideaUserAddress = new IdeaUserAddress();
		ideaUserAddress.setCity(map1.get("m_city"));
		ideaUserAddress.setAddress(map1.get("m_address"));
		ideaUserAddress.setCounty(map1.get("m_county"));
		ideaUserAddress.setId(id);
		ideaUserAddress.setIs_default(0);
		ideaUserAddress.setTel(map1.get("m_tel"));
		ideaUserAddress.setProvince(map1.get("m_province"));
		ideaUserAddress.setName(map1.get("m_name"));
		
		if(val != null && val == 1 && userId != null) {
			IdeaUserAddress ideaUserAddress1 = new IdeaUserAddress();
			ideaUserAddress1.setIs_default(1);
			ideaUserAddress1.setUser_id(userId);
			List<IdeaUserAddress> list = ideaUserAddressService.getAll(ideaUserAddress1, null);
			if(list != null && !list.isEmpty()) {
				for(IdeaUserAddress ideaUserAddress2 : list) {
					ideaUserAddress2.setIs_default(0);
					ideaUserAddressService.update(ideaUserAddress2);
				}
			}
			ideaUserAddress.setIs_default(1);
		}
		ideaUserAddressService.update(ideaUserAddress);
		
		map.put("code", 1001);
		map.put("msg", "删除成功");
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 获取用户信息
	 * @param openID
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_user/{openId}", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiUser(@PathVariable("openId") String openId) throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map2 = (Map<String, Object>) redisUtil.get(openId);
		if(map2 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map2 = userMap;
			} else {
				map.put("code", 204);
				map.put("msg", "登录异常，该用户未授权登录");
				return JSONObject.toJSONString(map);
			}
		}
		
		Long userId = StringUtil.strToLong(map2.get("userId").toString());
		
		//status 0 未申请， 1申请未通过，2通过
		IdeaUser ideaUser = ideaUserService.getById(userId);
		if(ideaUser.getIs_fx() == 1) {
			map.put("d_status", 2);
			map.put("d_name", ideaUser.getNote());
		} else {
			if(ideaUser.getDistribution_apply_add_time() > 0){
				map.put("d_status", 1);
				map.put("d_name", "普通会员");
			} else if(ideaUser.getDistribution_refunse_add_time() > 0) {
				map.put("d_status", 0);
				map.put("d_name", ideaUser.getNote());
			} else {
				map.put("d_status", 0);
				map.put("d_name", ideaUser.getNote());
			}
		}
		
		//获得我的团队分销商订单总量
		int group_order_count = 0;
		//我的分销订单计算
		List<OrderInfo> list11 = ideaOrderService.listMyGroupOrder(ideaUser.getId());
		if(list11 != null && !list11.isEmpty()) {
			group_order_count = list11.size();
		}
		
		//分销订单总量
		long order_count = 0;
		PageInfo<OrderInfo> pageInfo = ideaOrderService.getDistributionOrder(1, 1000, ideaUser.getId());
		if(pageInfo != null && pageInfo.getTotal() > 0) {
			order_count = pageInfo.getTotal();
		}
		
		//我的团队人数总量，既我推荐的人，等级比我低的分销商
		int group_count = 0;
		IdeaUser ideaUser4 = new IdeaUser();
		ideaUser4.setIs_fx(1);
		ideaUser4.setDistribution_recommend_uid(userId);
		List<IdeaUser> list = ideaUserService.getAll(ideaUser4, null);
		
		if(list != null && !list.isEmpty()) {
			//获得我下级的分销商身份有多少级
			int size = 1;
			boolean res = true;
			Long fid = ideaUser.getDistribution_lev();
			List<Long> disIdList = new ArrayList<Long>();
			List<IdeaDistributionCategroy> list2 = ideaDistributionCategroyService.getAll(null, null);
			for(int i=0; i < list2.size(); i++) {
				for(IdeaDistributionCategroy ideaDistributionCategroy : list2) {
					if(ideaDistributionCategroy.getFid() == fid) {
						fid = ideaDistributionCategroy.getId();
						size++;
						res = false;
						disIdList.add(ideaDistributionCategroy.getId());
						break;
					}
				}
				if(res) {
					break;
				}
			}
			
			//开始获取分销用户信息及统计
			//我的分销订单计算
			List<IdeaUser> list3 = list;
			for(int i=0; i < size; i++) {
				//开始获取分销用户信息及统计
				if(list3.size() > 0) {
					StringBuffer buffer = new StringBuffer();
					for(IdeaUser ideaUser3 : list3) {
						//统计我的团队人数
						if(disIdList.size() > 0) {
							for(Long disId : disIdList) {
								if(disId == ideaUser3.getDistribution_lev()) {
									group_count++;
								}
							}
						}
						
						buffer.append(ideaUser3.getId());
						buffer.append(",");
					}
					if(buffer.length() > 0) {
						buffer.deleteCharAt(buffer.length()-1);
						list3 = ideaUserService.getUserByIds(buffer.toString());
						if(list3 == null) {
							list3 = new ArrayList<IdeaUser>();
						}
					}
				}
			}
		}
		
		//我的推荐人数总量
		int recommend_count = 0;
		IdeaUser ideaUser3 = new IdeaUser();
		ideaUser3.setDistribution_recommend_uid(ideaUser.getId());
		List<IdeaUser> list3 = ideaUserService.getAll(ideaUser3, null);
		if(list3 != null && !list3.isEmpty()) {
			recommend_count = list3.size();
		}
		
		//分销佣金（元）待统计
		double distribut_money_statistics = 0;
		Integer val = ideaUserPaylogService.getAllByUserId(ideaUser.getId(), 0, 5);
		if(val != null) {
			distribut_money_statistics = val;
		}
		
		//已经分销佣金（元）
		double distribut_money = 0;
		Integer val2 = ideaUserPaylogService.getAllByUserId(ideaUser.getId(), 1, 5);
		if(val2 != null) {
			distribut_money = val2;
		}
		
		//推荐奖励（元）待统计
		double recommend_money_statistics=0;
		Integer val3 = ideaUserPaylogService.getAllByUserId(ideaUser.getId(), 0, 6);
		if(val3 != null) {
			recommend_money_statistics = val3;
		}
		
		//已获得推荐奖励
		double recommend_money = 0;
		Integer val4 = ideaUserPaylogService.getAllByUserId(ideaUser.getId(), 1, 6);
		if(val4 != null) {
			recommend_money = val4;
		}
		
		//待结算金额
		double wait_money = recommend_money_statistics + distribut_money_statistics;
		//我的收益
		double earnings = recommend_money+distribut_money+wait_money;
		
		map.put("my_group_count", group_count);//我的团队人数总量，既我推荐的人，等级比我低的分销
		map.put("my_group_order_count", group_order_count);//团队订单总量
		map.put("distribut_money", distribut_money);//已经分销佣金（元）
		map.put("distribut_money_statistics", distribut_money_statistics);//分销佣金（元）待统计
		map.put("recommend_money", recommend_money);//已获得推荐奖励
		map.put("recommend_money_statistics", recommend_money_statistics);//推荐奖励（元）待统计
		map.put("integral", ideaUser.getIntegral());
		map.put("my_recommend_count", recommend_count);//我的推荐人数总量
		map.put("distribut_order_count", order_count);//分销订单总量
		map.put("distribution_lev", ideaUser.getDistribution_lev());
		//map.put("real_name", ideaUser.getReal_name());
		map.put("distribution_qrcode_index", ideaUser.getDistribution_qrcode_index());
		map.put("distribution_qrcode_person", ideaUser.getDistribution_qrcode_person());
		map.put("avatar", ideaUser.getAvatar());
		map.put("oauth_nickname", ideaUser.getOauth_nickname());
		map.put("user_money", ideaUser.getUser_money());//余额
		map.put("wait_money", wait_money);//待结算金额
		map.put("earnings", earnings);//待结算金额
		
		if(ideaUser.getEmail() != null) {
			map.put("email", ideaUser.getEmail());
		} else {
			map.put("email", "");
		}
		if(ideaUser.getUser_tel() != null) {
			map.put("tel", ideaUser.getUser_tel());
		} else {
			if(ideaUser.getMobile() != null) {
				map.put("tel", ideaUser.getMobile());
			} else {
				map.put("tel", "");
			}
		}
		if(ideaUser.getDistribution_recommend_uid() != null) {
			map.put("comm_id", ideaUser.getDistribution_recommend_uid());//推荐人
		} else {
			map.put("comm_id", "");//推荐人
		}
		if(ideaUser.getReal_name() != null) {
			map.put("real_name", ideaUser.getReal_name());
		} else {
			map.put("real_name", "");
		}
		if(ideaUser.getBirth() != null) {
			map.put("birth", ideaUser.getBirth());
		} else {
			map.put("birth", "");
		}
		if(ideaUser.getSex() == 1) {
			map.put("sex", "女");//1：女，2男
		} else if(ideaUser.getSex() == 2) {
			map.put("sex", "男");//1：女，2男
		} else {
			map.put("sex", "");//1：女，2男
		}
		
		List<IdeaConfig> list2 = ideaConfigService.getAllInfo();
		for(IdeaConfig config : list2) {
			if(config.getId() == 5L) {
				map.put("bag1", config.getValue());
			} else if(config.getId() == 6L) {
				map.put("bag2", config.getValue());
			} else if(config.getId() == 7L) {
				map.put("bag3", config.getValue());
			}
		}
		
		//订单数量
		List<Map<String, Integer>> lit = ideaOrderService.getOrderCount(ideaUser.getId());
		if(lit != null && !lit.isEmpty()) {
			for(Map<String, Integer> mb : lit) {
				if(mb.get("order_state") == 0) {
					map.put("productStatus1", mb.get("amount"));
				} else if(mb.get("order_state") == 1) {
					map.put("productStatus2", mb.get("amount"));
				} else {
					map.put("productStatus3", mb.get("amount"));
				}
			}
		} else {
			map.put("productStatus1", 0);
			map.put("productStatus2", 0);
			map.put("productStatus3", 0);
		}
		
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 获取我的钱包
	 * @param open_id
	 * @return
	 */
	@RequestMapping(value="/api_wallect", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiWallect(@RequestParam(name="open_id") String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map2 = (Map<String, Object>) redisUtil.get(openId);
		if(map2 != null) {
			List<IdeaUserPaylog> list = ideaUserPaylogService.getWallectByOpenId(StringUtil.strToLong(map2.get("userId").toString()));
			IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map2.get("userId").toString()));
			
			map.put("list", list);
			map.put("user_money", ideaUser.getUser_money());
		} else {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				List<IdeaUserPaylog> list = ideaUserPaylogService.getWallectByOpenId(ideaUser.getId());
				
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map.put("list", list);
				map.put("user_money", ideaUser.getUser_money());
			} else {
				List<String> list = new ArrayList<String>();
				map.put("list", list);
				map.put("user_money", 0);
			}
		}
		
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 0：获取我的团队信息
	 * 1：获取我的推荐信息
	 * 2：我的分销订单
	 * 3：我的团队订单
	 * @param type
	 * @param open_id
	 * @return
	 */
	@RequestMapping(value="/api_group", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiGroup(@RequestParam(name="open_id") String openId, @RequestParam(name="type", required=false) Integer type,
			@RequestParam(name="page", required=false) Integer page, @RequestParam(name="size", required=false) Integer size) {
		ObjectResponse objectResponse = new ObjectResponse();
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) redisUtil.get(openId);
		if(map == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map = userMap;
			} else {
				List<String> list7 = new ArrayList<String>();
				objectResponse.setCode(500);
				objectResponse.setData(list7);
				objectResponse.setMsg("用户登录信息过期");
				return JSONObject.toJSONString(objectResponse);
			}
		}
		
		//分类型处理接口功能
		if(type == 0) {
			//获取我的团队信息
			List<Object> list6 = new ArrayList<Object>();
			
			IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map.get("userId").toString()));
			IdeaUser ideaUser4 = new IdeaUser();
			ideaUser4.setIs_fx(1);
			ideaUser4.setDistribution_recommend_uid(ideaUser.getId());
			List<IdeaUser> list = ideaUserService.getAll(ideaUser4, null);
			
			if(list != null && !list.isEmpty()) {
				//获得我下级的分销商身份有多少级
				int length = 0;
				boolean res = true;
				Long fid = ideaUser.getDistribution_lev();
				List<Long> disIdList = new ArrayList<Long>();
				List<IdeaDistributionCategroy> list2 = ideaDistributionCategroyService.getAll(null, null);
				for(int i=0; i < list2.size(); i++) {
					for(IdeaDistributionCategroy ideaDistributionCategroy : list2) {
						if(ideaDistributionCategroy.getFid() == fid) {
							fid = ideaDistributionCategroy.getId();
							length++;
							res = false;
							disIdList.add(ideaDistributionCategroy.getId());
							break;
						}
					}
					if(res) {
						break;
					}
				}
				
				List<IdeaUser> list3 = list;
				for(int i=0; i < length; i++) {
					if(list3.size() > 0) {
						//校验那些需要查询的信息
						StringBuffer buffer = new StringBuffer();
						for(IdeaUser ideaUser3 : list3) {
							if(disIdList.size() > 0) {
								for(Long disId : disIdList) {
									if(disId == ideaUser3.getDistribution_lev()) {
										buffer.append(ideaUser3.getId());
										buffer.append(",");
										
										Map<String, Object> map2 = new HashMap<String, Object>();
										map2.put("nickname", ideaUser3.getOauth_nickname());
										map2.put("level", ideaUser3.getNote());
										if(ideaUser3.getDistribution_pass_add_time() != null) {
											map2.put("data", DateUtil.format(ideaUser3.getDistribution_pass_add_time()*1000, DateUtil.FORMAT_YYYY_MM_DD));
										} else {
											map2.put("data", DateUtil.format(1000, DateUtil.FORMAT_YYYY_MM_DD));
										}
										map2.put("pic", ideaUser3.getAvatar());
										
										list6.add(map2);
									}
								}
							}
						}
						//获取新的下级具体信息
						if(buffer.length() > 0) {
							buffer.deleteCharAt(buffer.length()-1);
							list3 = ideaUserService.getUserByIds(buffer.toString());
							if(list3 == null || list3.isEmpty()) {
								list3 = new ArrayList<IdeaUser>();
							}
						}
					}
				}
				
				if(page != null && size != null) {
					PageHelper.startPage(page, size);
				} else {
					PageHelper.startPage(1, 20);
				}
				
				PageInfo<Object> info = new PageInfo<Object>(list6);
				if(info != null && info.getTotal() > 0) {
					objectResponse.setData(info.getList());
					objectResponse.setCode(200);
					objectResponse.setMsg("我的团队信息");
					objectResponse.setCurrent_page(info.getPageNum());
					objectResponse.setLast_page(info.getLastPage());
					objectResponse.setPer_page(info.getFirstPage());
					objectResponse.setTotal(info.getTotal());
				} else {
					List<String> list7 = new ArrayList<String>();
					objectResponse.setData(list7);
					objectResponse.setCode(500);
					objectResponse.setMsg("没有我的团队信息");
				}
			} else {
				List<String> list7 = new ArrayList<String>();
				objectResponse.setData(list7);
				objectResponse.setCode(500);
				objectResponse.setMsg("您不是分销商，不能获取团队信息");
			}
			
			return JSONObject.toJSONString(objectResponse);
		} else if(type == 1) {
			//获取我的推荐信息
			IdeaUser ideaUser = new IdeaUser();
			ideaUser.setDistribution_recommend_uid((Long) map.get("userId"));
			List<IdeaUser> list = ideaUserService.getAll(ideaUser, null);
			
			if(list != null && !list.isEmpty()) {
				List<Object> list2 = new ArrayList<Object>();
				for(IdeaUser ideaUser2 :list) {
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("nickname", ideaUser2.getOauth_nickname());
					map2.put("pic", ideaUser2.getAvatar());
					map2.put("date", DateUtil.format(ideaUser2.getRecommend_date()*1000, DateUtil.FORMAT_YYYY_MM_DD));
					map2.put("level", ideaUser2.getNote());
					
					list2.add(map2);
				}
				
				if(page != null && size != null) {
					PageHelper.startPage(page, size);
				} else {
					PageHelper.startPage(1, 20);
				}
				PageInfo<Object> info = new PageInfo<Object>(list2);
				
				objectResponse.setCurrent_page(info.getPageNum());
				objectResponse.setLast_page(info.getLastPage());
				objectResponse.setPer_page(info.getFirstPage());
				objectResponse.setTotal(info.getTotal());
				objectResponse.setData(info.getList());
				objectResponse.setCode(200);
				objectResponse.setMsg("我的推荐信息");
			} else {
				List<String> list7 = new ArrayList<String>();
				objectResponse.setData(list7);
				objectResponse.setCode(500);
				objectResponse.setMsg("没有推荐信息");
			}
			return JSONObject.toJSONString(objectResponse);
		} else if(type == 2) {
			//我的分销订单
			IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map.get("userId").toString()));
			if(ideaUser.getIs_fx() == 1) {
				PageInfo<OrderInfo> pageInfo = ideaOrderService.getDistributionOrder(page, size, ideaUser.getId());
				if(pageInfo != null && pageInfo.getTotal() > 0) {
					
					objectResponse.setCurrent_page(pageInfo.getPageNum());
					objectResponse.setLast_page(pageInfo.getLastPage());
					objectResponse.setPer_page(pageInfo.getFirstPage());
					objectResponse.setTotal(pageInfo.getTotal());
					objectResponse.setData(pageInfo.getList());
					objectResponse.setCode(200);
					objectResponse.setMsg("我的分销订单");
				} else {
					List<String> list = new ArrayList<String>();
					objectResponse.setData(list);
					objectResponse.setCode(500);
					objectResponse.setMsg("没有分销订单");
				}
			}
			
			return JSONObject.toJSONString(objectResponse);
		} else if(type == 3) {
			//我的团队订单
			IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map.get("userId").toString()));
			if(ideaUser != null && ideaUser.getIs_fx() == 1) {
			
				List<OrderInfo> list4 = ideaOrderService.listMyGroupOrder(ideaUser.getId());
				if(list4 != null && !list4.isEmpty()) {
					if(page != null && size != null) {
						PageHelper.startPage(page, size, "id DESC");
					} else {
						PageHelper.startPage(1, 20, "id DESC");
					}
					
					PageInfo<OrderInfo> pageInfo = new PageInfo<OrderInfo>(list4);
					objectResponse.setCurrent_page(pageInfo.getPageNum());
					objectResponse.setLast_page(pageInfo.getLastPage());
					objectResponse.setPer_page(pageInfo.getFirstPage());
					objectResponse.setTotal(pageInfo.getTotal());
					objectResponse.setData(pageInfo.getList());
					objectResponse.setCode(200);
					objectResponse.setMsg("我的团队订单");
				} else {
					List<String> list2 = new ArrayList<String>();
					objectResponse.setData(list2);
					objectResponse.setCode(500);
					objectResponse.setMsg("您没有团队信息");
				}
				/*IdeaUser ideaUser4 = new IdeaUser();
				ideaUser4.setIs_fx(1);
				ideaUser4.setDistribution_recommend_uid(ideaUser.getId());
				List<IdeaUser> list = ideaUserService.getAll(ideaUser4, null);
				
				if(list != null && !list.isEmpty()) {
					int length = 1;
					//获得我下级的分销商身份有多少级
					boolean res = true;
					Long fid = ideaUser.getDistribution_lev();
					List<IdeaDistributionCategroy> list2 = ideaDistributionCategroyService.getAll(null, null);
					for(int i=0; i < list2.size(); i++) {
						for(IdeaDistributionCategroy ideaDistributionCategroy : list2) {
							if(ideaDistributionCategroy.getFid() == fid) {
								fid = ideaDistributionCategroy.getId();
								length++;
								res = false;
								break;
							}
						}
						if(res) {
							break;
						}
					}
					
					//我的团队分销订单
					List<IdeaUser> list3 = list;
					List<OrderInfo> list4 = new ArrayList<OrderInfo>();
					for(int i=0; i < length; i++) {
						//开始获取分销用户信息及统计
						if(list3.size() > 0) {
							StringBuffer buffer = new StringBuffer();
							for(IdeaUser ideaUser3 : list3) {
								buffer.append(ideaUser3.getId());
								buffer.append(",");
							}
							if(buffer.length() > 0) {
								buffer.deleteCharAt(buffer.length()-1);
								list3 = ideaUserService.getUserByIds(buffer.toString());
								if(list3 != null && !list3.isEmpty()) {
									List<OrderInfo> list5 = ideaOrderService.listMyGroupOrder(buffer.toString());
									if(list5 != null && !list5.isEmpty()) {
										list4.addAll(list5);
									}
								} else {
									list3 = new ArrayList<IdeaUser>();
								}
							}
						}
					}*/
					
					
			} else {
				List<String> list7 = new ArrayList<String>();
				objectResponse.setData(list7);
				objectResponse.setCode(500);
				objectResponse.setMsg("您不是分销商，不能获取团队订单信息");
			}

			return JSONObject.toJSONString(objectResponse);
		} else {
			List<String> list = new ArrayList<String>();
			objectResponse.setData(list);
			objectResponse.setCode(500);
			objectResponse.setMsg("参数有误");
			return JSONObject.toJSONString(objectResponse);
		}
	}
	
	/**
	 * 获取订单列表
	 * @param state
	 * @param m_openid
	 * @return
	 */
	@RequestMapping(value="/api_order", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiOrder(@RequestParam(name="state", required=false) Integer state, @RequestParam(name="m_openid") String openId) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map2 = (Map<String, Object>) redisUtil.get(openId);
		if(map2 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map2 = userMap;
			} else {
				List<String> list = new ArrayList<String>();
				list.add("登录异常，该用户未授权登录");
				return JSONObject.toJSONString(list);
			}
		}
		
		if(state == null || state == -1) {
			List<IdeaOrder> list = ideaOrderService.getInfoByOpenId((Long) map2.get("userId"), null);
			if(list != null && !list.isEmpty()) {
				for(int i=0; i < list.size(); i++) {
					IdeaOrder ideaOrder = list.get(i);
					IdeaOrderGoods ideaOrderGoods = new IdeaOrderGoods();
					ideaOrderGoods.setOrder_id(ideaOrder.getId());
					List<IdeaOrderGoods> list2 = ideaOrderGoodsService.getAll(ideaOrderGoods, null);
					List<Object> list3 = new ArrayList<>();
					for(int j=0; j < list2.size(); j++) {
						IdeaOrderGoods ideaOrderGoods2 =list2.get(j);
						Map<String, Object> map3 = new HashMap<String, Object>();
						map3.put("id", ideaOrderGoods2.getId());
						map3.put("order_id", ideaOrderGoods2.getOrder_id());
						map3.put("goods_id", ideaOrderGoods2.getGoods_id());
						map3.put("spec_key", ideaOrderGoods2.getSpec_key());
						map3.put("spec_item", ideaOrderGoods2.getSpec_item());
						map3.put("price", ideaOrderGoods2.getPrice());
						map3.put("rebate_price", ideaOrderGoods2.getRebate_price());
						map3.put("amount", ideaOrderGoods2.getAmount());
						map3.put("total_price", ideaOrderGoods2.getTotal_price());
						map3.put("is_comment", ideaOrderGoods2.getIs_comment());
						map3.put("add_date", DateUtil.format(ideaOrderGoods2.getAdd_date(), DateUtil.FORMAT_YYYY_MM_DD));
						
						IdeaGoods ideaGoods = ideaGoodsService.getById(ideaOrderGoods2.getGoods_id());
						map3.put("pic", ideaGoods.getPic());
						map3.put("title", ideaGoods.getTitle());
						
						list3.add(map3);
					}
					ideaOrder.setGoods(list3);
				}
			} else {
				list = new ArrayList<IdeaOrder>();
			}
			return JSONObject.toJSONString(list);
		} else {
			List<IdeaOrder> list = ideaOrderService.getInfoByOpenId((Long) map2.get("userId"), state);
			if(list != null && !list.isEmpty()) {
				for(int i=0; i < list.size(); i++) {
					IdeaOrder ideaOrder = list.get(i);
					IdeaOrderGoods ideaOrderGoods = new IdeaOrderGoods();
					ideaOrderGoods.setOrder_id(ideaOrder.getId());
					List<IdeaOrderGoods> list2 = ideaOrderGoodsService.getAll(ideaOrderGoods, null);
					List<Object> list3 = new ArrayList<>();
					for(int j=0; j < list2.size(); j++) {
						IdeaOrderGoods ideaOrderGoods2 =list2.get(j);
						Map<String, Object> map3 = new HashMap<String, Object>();
						map3.put("id", ideaOrderGoods2.getId());
						map3.put("order_id", ideaOrderGoods2.getOrder_id());
						map3.put("goods_id", ideaOrderGoods2.getGoods_id());
						map3.put("spec_key", ideaOrderGoods2.getSpec_key());
						map3.put("spec_item", ideaOrderGoods2.getSpec_item());
						map3.put("price", ideaOrderGoods2.getPrice());
						map3.put("rebate_price", ideaOrderGoods2.getRebate_price());
						map3.put("amount", ideaOrderGoods2.getAmount());
						map3.put("total_price", ideaOrderGoods2.getTotal_price());
						map3.put("is_comment", ideaOrderGoods2.getIs_comment());
						map3.put("add_date", DateUtil.format(ideaOrderGoods2.getAdd_date(), DateUtil.FORMAT_YYYY_MM_DD));
						
						IdeaGoods ideaGoods = ideaGoodsService.getById(ideaOrderGoods2.getGoods_id());
						map3.put("pic", ideaGoods.getPic());
						map3.put("title", ideaGoods.getTitle());
						
						list3.add(map3);
					}
					ideaOrder.setGoods(list3);
				}
			} else {
				list = new ArrayList<IdeaOrder>();
			}
			return JSONObject.toJSONString(list);
		}
	}
	
	/**
	 * 订单详情
	 * @param id
	 * @param open_id
	 * @return
	 */
	@RequestMapping(value="/api_order/{id}", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String getOrder(@PathVariable("id") Long id, @RequestParam(name="open_id") String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map2 = (Map<String, Object>) redisUtil.get(openId);
		if(map2 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map2 = userMap;
			} else {
				map.put("code", 204);
				map.put("msg", "登录异常，该用户未授权登录");
				return JSONObject.toJSONString(map);
			}
		}
		
		IdeaOrder ideaOrder = ideaOrderService.getById(id);
		IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map2.get("userId").toString()));
		
		map.put("id", ideaOrder.getId());
		map.put("order_sn", ideaOrder.getOrder_sn());
		map.put("user_id", ideaOrder.getUser_id());
		map.put("price", ideaOrder.getPrice());
		map.put("pay_price", ideaOrder.getPay_price());
		map.put("order_state", ideaOrder.getOrder_state());
		map.put("pay_type", ideaOrder.getPay_type());
		map.put("pay_sn", ideaOrder.getPay_sn());
		map.put("pay_date", ideaOrder.getPay_date());
		map.put("pay_terminal", ideaOrder.getPay_terminal());
		map.put("send_type", ideaOrder.getSend_type());
		map.put("info", ideaOrder.getInfo());
		map.put("name", ideaOrder.getName());
		map.put("tel", ideaOrder.getTel());
		map.put("address", ideaOrder.getAddress());
		map.put("express_price", ideaOrder.getExpress_price());
		map.put("express_title", ideaOrder.getExpress_title());
		map.put("express_sn", ideaOrder.getExpress_sn());
		map.put("express_date", ideaOrder.getExpress_date());
		map.put("pickup_sn", ideaOrder.getPickup_sn());
		map.put("coupon_id", ideaOrder.getCoupon_id());
		map.put("coupon_price", ideaOrder.getCoupon_price());
		map.put("rebate_price", ideaOrder.getRebate_price());
		map.put("discount_price", ideaOrder.getDiscount_price());
		map.put("jf_price", ideaOrder.getJf_price());
		map.put("trim_price", ideaOrder.getTrim_price());
		map.put("th_reason", ideaOrder.getTh_reason());
		map.put("is_comment", ideaOrder.getIs_comment());
		map.put("tc", ideaOrder.getTc());
		map.put("jf", ideaOrder.getJf());
		map.put("add_date", ideaOrder.getAdd_date());
		map.put("voucher_id", ideaOrder.getVoucher_id());
		map.put("voucher_price", ideaOrder.getVoucher_price());
		
		//获取折扣率
		if(ideaUser.getIs_fx() == 1) {
			IdeaDistributionCategroy ideaDistributionCategroy = ideaDistributionCategroyService.getById(ideaUser.getDistribution_lev());
			Integer ic = ideaDistributionCategroy.getBuy_discount();
			Double discount = Double.valueOf(ic.toString())/10;
			map.put("rebate", discount);
		} else {
			Map<String, Object> map3 = ideaUserLevelService.getLevelRebate(openId);
			Integer ic = StringUtil.strToInt(map3.get("rebate").toString());
			Double discount = Double.valueOf(ic.toString())/10;
			map.put("rebate", discount);
		}
		
		//自提信息
		if(ideaOrder.getSend_type() == 1) {
			IdeaPickUp ideaPickUp = ideaPickUpService.getById(1L);
			map.put("pickup_name", ideaPickUp.getPickup_name());
			map.put("pickup_tel", ideaPickUp.getPickup_tel());
			map.put("pickup_addr", ideaPickUp.getPickup_addr());
		} else {
			map.put("pickup_name", "");
			map.put("pickup_tel", "");
			map.put("pickup_addr", "");
		}
		
		IdeaOrderGoods ideaOrderGoods = new IdeaOrderGoods();
		ideaOrderGoods.setOrder_id(ideaOrder.getId());
		List<IdeaOrderGoods> list = ideaOrderGoodsService.getAll(ideaOrderGoods, null);
		List<Object> list3 = new ArrayList<>();
		for(int j=0; j < list.size(); j++) {
			IdeaOrderGoods ideaOrderGoods2 = list.get(j);
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("id", ideaOrderGoods2.getId());
			map3.put("order_id", ideaOrderGoods2.getOrder_id());
			map3.put("goods_id", ideaOrderGoods2.getGoods_id());
			map3.put("spec_key", ideaOrderGoods2.getSpec_key());
			map3.put("spec_item", ideaOrderGoods2.getSpec_item());
			map3.put("price", ideaOrderGoods2.getPrice());
			map3.put("rebate_price", ideaOrderGoods2.getRebate_price());
			map3.put("amount", ideaOrderGoods2.getAmount());
			map3.put("total_price", ideaOrderGoods2.getTotal_price());
			map3.put("is_comment", ideaOrderGoods2.getIs_comment());
			map3.put("add_date", DateUtil.format(ideaOrderGoods2.getAdd_date(), DateUtil.FORMAT_YYYY_MM_DD));
			
			IdeaGoods ideaGoods = ideaGoodsService.getById(ideaOrderGoods2.getGoods_id());
			map3.put("pic", ideaGoods.getPic());
			map3.put("title", ideaGoods.getTitle());
			
			list3.add(map3);
		}
		map.put("goods", list3);
		
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 获取我的积分列表
	 * @param open_id
	 * @return
	 */
	@RequestMapping(value="/api_total", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiTotal(HttpServletRequest request, @RequestParam(name="open_id") String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map2 = (Map<String, Object>) redisUtil.get(openId);
		if(map2 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				List<IdeaUserPaylog> list = ideaUserPaylogService.getInfoByOpenId(ideaUser.getId());
				map.put("myTotal", ideaUser.getIntegral());
				map.put("list", list);
				return JSONObject.toJSONString(map);
			} else {
				map.put("code", 204);
				map.put("msg", "登录异常，该用户未授权登录");
				return JSONObject.toJSONString(map);
			}
		} else {
			List<IdeaUserPaylog> list = ideaUserPaylogService.getInfoByOpenId(StringUtil.strToLong(map2.get("userId").toString()));
			
			IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map2.get("userId").toString()));
			map.put("myTotal", ideaUser.getIntegral());
			map.put("list", list);
			return JSONObject.toJSONString(map);
		}
	}
	
	/**
	 * 领取新人优惠券
	 * @param request
	 * @param openId
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_person_coupon", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String getPersonCoupon(@RequestParam(name="open_id") String openId) throws BusinessException {
		ObjectResponse objectResponse = new ObjectResponse();
		@SuppressWarnings("unchecked")
		Map<String, Object> map2 = (Map<String, Object>) redisUtil.get(openId);
		if(map2 == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map2 = userMap;
			} else {
				objectResponse.setCode(500);
				objectResponse.setData("");
				objectResponse.setMsg("登录异常，该用户未授权登录");
				return JSONObject.toJSONString(objectResponse);
			}
		}
		
		IdeaCouponPerson ideaCouponPerson = new IdeaCouponPerson();
		ideaCouponPerson.setOpen_id(openId);
		List<IdeaCouponPerson> list = ideaCouponPersonService.getAll(ideaCouponPerson, null);
		if(list == null || list.isEmpty()) {
			ideaCouponPersonService.gainCoupon(openId, (Long)map2.get("userId"));
			objectResponse.setCode(200);
			objectResponse.setData(null);
			objectResponse.setMsg("用户已经领取新人券");
			return JSONObject.toJSONString(objectResponse);
		} else {
			objectResponse.setCode(405);
			objectResponse.setData(null);
			objectResponse.setMsg("已经领取了新人券");
			return JSONObject.toJSONString(objectResponse);
		}
	}
	
	/**
	 * 新人礼包初始化页面
	 * @param openId
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/api_person_gift", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String getNewPersonGift(@RequestParam(name="open_id", required=false) String openId) throws BusinessException {
		
		IdeaConfig ideaConfig = ideaConfigService.getById(4L);
		Map<String, String> map = new HashMap<String, String>();
		map.put("pic", ideaConfig.getValue());
		
		Integer type = 1;//2领取，1未领取
		if(openId != null && !"".equals(openId)) {
			IdeaCouponPerson ideaCouponPerson = new IdeaCouponPerson();
			ideaCouponPerson.setOpen_id(openId);
			List<IdeaCouponPerson> list = ideaCouponPersonService.getAll(ideaCouponPerson, null);
			if(list != null && !list.isEmpty()) {
				type = 2;
			}
		}
		map.put("type", type.toString());
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 用户更新个人信息
	 * sex 1女，2男
	 * @param openId
	 * @param sex
	 * @param nickname
	 * @param birth
	 * @param email
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/api_upd_user", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiUpdUser(@RequestParam(name="open_id") String openId, @RequestParam(name="sex", required=false) Integer sex,
			@RequestParam(name="real_name", required=false) String nickname, @RequestParam(name="birth", required=false) String birth,
			@RequestParam(name="email", required=false) String email) throws BusinessException {

		ObjectResponse objectResponse = new ObjectResponse();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) redisUtil.get(openId);
		if(map == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map = userMap;
			} else {
				objectResponse.setCode(500);
				objectResponse.setMsg("登录异常，该用户未授权登录");
				return JSONObject.toJSONString(objectResponse);
			}
		}
		
		IdeaUser ideaUser = new IdeaUser();
		ideaUser.setId((Long) map.get("userId"));
		if(nickname != null && !"".equals(nickname)) {
			ideaUser.setReal_name(nickname);
		}
		if(birth != null && !"".equals(birth)) {
			ideaUser.setBirth(birth);
		}
		if(email != null && !"".equals(email)) {
			ideaUser.setEmail(email);
		}
		if(sex != null && !"".equals(sex)) {
			ideaUser.setSex(sex);
		}
		
		ideaUserService.update(ideaUser);
		objectResponse.setCode(200);
		objectResponse.setMsg("信息修改成功");
		
		return JSONObject.toJSONString(objectResponse);
	}
	
	/**
	 * 更新用户的电话号码
	 * @param openId
	 * @param data
	 * @param iv
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_upd_tel", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String apiUpdTel(@RequestParam(name="open_id") String openId, @RequestParam(name="encrypted") String encrypted,
			@RequestParam(name="iv") String iv) throws BusinessException {
		ObjectResponse objectResponse = new ObjectResponse();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) redisUtil.get(openId);
		if(map == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map = userMap;
			} else {
				objectResponse.setCode(204);
				objectResponse.setMsg("登录异常，该用户未授权登录");
				objectResponse.setData("");
				return JSONObject.toJSONString(objectResponse);
			}
		}
		
		logger.info("sessionKey:"+map.get("unionId").toString()+",encrypted:"+encrypted+",iv:"+iv);
		String json = AESUtil.wxDecrypt(encrypted, map.get("unionId").toString(), iv);
		logger.info("字符串："+json);
		PhoneNumber phoneNumber = JsonUtil.getObject(json, PhoneNumber.class);
		
		IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map.get("userId").toString()));
		ideaUser.setMobile(phoneNumber.getPurePhoneNumber());
		ideaUserService.update(ideaUser);
		
		objectResponse.setCode(200);
		objectResponse.setMsg("获取成功");
		objectResponse.setData(phoneNumber.getPurePhoneNumber());
		return JSONObject.toJSONString(objectResponse);
	}
	
	/**
	 * 查询余额
	 * @param request
	 * @param frozen_money
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="/api_withdrawal_dcenter", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String withDrawal(HttpServletRequest request, @RequestParam(name="open_id") String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		IdeaUser ideaUser = ideaUserService.getUser(openId);
		if(ideaUser != null) {
			Double money = ideaUser.getUser_money();
			map.put("data", money);
		} else {
			map.put("data", 0);
		}
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 提现
	 * @param request
	 * @param frozen_money
	 * @param openId
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_withdrawal", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String withDrawal(@RequestParam(name="open_id") String openId) throws BusinessException {
		ObjectResponse objectResponse = new ObjectResponse();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) redisUtil.get(openId);
		if(map == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map = userMap;
			} else {
				objectResponse.setCode(204);
				objectResponse.setMsg("登录异常，该用户未授权登录");
				objectResponse.setData("");
				return JSONObject.toJSONString(objectResponse);
			}
		}
		
		IdeaUser ideaUser = ideaUserService.getById(StringUtil.strToLong(map.get("userId").toString()));
		IdeaUserPaylog userPaylog = new IdeaUserPaylog();
		userPaylog.setUser_id(ideaUser.getId());
		userPaylog.setType(7);
		userPaylog.setFee(ideaUser.getUser_money());
		userPaylog.setPay_type(0);
		userPaylog.setInfo("用户提现");
		userPaylog.setAccount_fee(0D);
		userPaylog.setCash_type(0);
		userPaylog.setCash_info(null);
		userPaylog.setPay_state(0);
		userPaylog.setState(0);
		userPaylog.setAdd_date(DateUtil.getTimestamp());
		ideaUserPaylogService.save(userPaylog);
		
		ideaUser.setUser_money(0D);
		ideaUserService.update(ideaUser);
		
		objectResponse.setCode(200);
		objectResponse.setData(null);
		objectResponse.setMsg("提现成功");
		
		return JSONObject.toJSONString(objectResponse);
	}
	
	/**
	 * 收入明细 1
	 * 提现记录 2
	 * @param openId
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/api_paylog", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String payDetails(@RequestParam(name="open_id") String openId, @RequestParam(name="type") Integer type,
			@RequestParam(name="page")Integer page, @RequestParam(name="size")Integer size) {
		ObjectResponse objectResponse = new ObjectResponse();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) redisUtil.get(openId);
		if(map == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map = userMap;
			} else {
				objectResponse.setCode(204);
				objectResponse.setMsg("登录异常，该用户未授权登录");
				objectResponse.setData("");
				return JSONObject.toJSONString(objectResponse);
			}
		}
		
		//分类型实现功能
		if(type == 1) {
			PageInfo<Map<String, Object>> pageInfo = ideaUserPaylogService.getPayInfo(page, size, "1,2,5,6,7", (Long) map.get("userId"));
			if(pageInfo != null && pageInfo.getTotal() > 0) {
				List<Object> list = new ArrayList<Object>();
				for(int i=0; i < pageInfo.getList().size(); i++) {
					Map<String, Object> mg = pageInfo.getList().get(i);
					Map<String, Object> mm = new HashMap<String, Object>();
					//iup.type, iup.fee, iup.add_date, iu.oauth_nickname, iup.info, iup.pay_state
					//mm.put("type", value);
					//mm.put("nickname", value);
					mm.put("fee", mg.get("fee"));
					mm.put("info", mg.get("info"));
					mm.put("add_date", DateUtil.format((Long)mg.get("add_date") * 1000, DateUtil.FORMAT_YYYY_MM_DD));
					int resType = StringUtil.strToInt(mg.get("type").toString());
					if(resType == 1) {
						if(StringUtil.strToInt(mg.get("pay_state").toString()) == 1) {
							mm.put("result", "充值成功");
						} else {
							mm.put("result", "充值失败");
						}
					} else if(resType == 2){
						if(StringUtil.strToInt(mg.get("pay_state").toString()) == 1) {
							mm.put("result", "支付成功");
						} else {
							mm.put("result", "支付失败");
						}
					} else if(resType == 5) {
						mm.put("result", "分销提成");
					} else if(resType == 6) {
						mm.put("result", "推荐奖励");
					} else if(resType == 7) {
						if(StringUtil.strToInt(mg.get("pay_state").toString()) == 1) {
							mm.put("result", "通过提现");
						} else if(StringUtil.strToInt(mg.get("pay_state").toString()) == -1) {
							mm.put("result", "拒绝提现");
						} else {
							mm.put("result", "提现待审核");
						}
					} else {
						break;
					}
					list.add(mm);
				}
				
				objectResponse.setData(list);
				objectResponse.setCode(200);
				objectResponse.setMsg("信息返回");
				objectResponse.setCurrent_page(pageInfo.getPageNum());
				objectResponse.setLast_page(pageInfo.getLastPage());
				objectResponse.setPer_page(pageInfo.getPrePage());
				objectResponse.setTotal(pageInfo.getTotal());
			} else {
				List<String> list = new ArrayList<String>();
				objectResponse.setData(list);
				objectResponse.setCode(204);
				objectResponse.setMsg("没有数据");
				objectResponse.setCurrent_page(0);
				objectResponse.setLast_page(0);
				objectResponse.setPer_page(0);
				objectResponse.setTotal(0L);
			}
		} else if(type == 2) {
			PageInfo<Map<String, Object>> pageInfo = ideaUserPaylogService.getPayInfo(page, size, "7", (Long) map.get("userId"));
			if(pageInfo != null && pageInfo.getTotal() > 0) {
				List<Object> list = new ArrayList<Object>();
				for(int i=0; i < pageInfo.getList().size(); i++) {
					Map<String, Object> mg = pageInfo.getList().get(i);
					Map<String, Object> mm = new HashMap<String, Object>();
					//iup.type, iup.fee, iup.add_date, iu.oauth_nickname, iup.info, iup.pay_state
					//mm.put("type", value);
					//mm.put("nickname", value);
					mm.put("fee", mg.get("fee"));
					mm.put("add_date", DateUtil.format((Long)mg.get("add_date") * 1000, DateUtil.FORMAT_YYYY_MM_DD));
					mm.put("info", mg.get("info"));
					if((Integer)mg.get("pay_state") == 1) {
						mm.put("result", "提现成功");
					} else {
						mm.put("result", "提现失败");
					}
					list.add(mm);
				}
				
				objectResponse.setData(list);
				objectResponse.setCode(200);
				objectResponse.setMsg("信息返回");
				objectResponse.setCurrent_page(pageInfo.getPageNum());
				objectResponse.setLast_page(pageInfo.getLastPage());
				objectResponse.setPer_page(pageInfo.getPrePage());
				objectResponse.setTotal(pageInfo.getTotal());
			} else {
				objectResponse.setData("");
				objectResponse.setCode(500);
				objectResponse.setMsg("没有数据");
			}
		} else {
			objectResponse.setData("");
			objectResponse.setCode(500);
			objectResponse.setMsg("参数有误");
		}
		
		return JSONObject.toJSONString(objectResponse);
	}
	
	/**
	 * 获得用户信息
	 * @param openId
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/api_myinfo", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String payDetails(@RequestParam(name="open_id") String openId) {
		ObjectResponse objectResponse = new ObjectResponse();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) redisUtil.get(openId);
		if(map == null) {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				map = userMap;
			} else {
				objectResponse.setCode(500);
				objectResponse.setMsg("登录异常，该用户未授权登录");
				objectResponse.setData("");
				return JSONObject.toJSONString(objectResponse);
			}
		}
		
		IdeaUser ideaUser = ideaUserService.getById((Long)map.get("userId"));
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("id", ideaUser.getId());
		map1.put("pic", ideaUser.getAvatar());
		map1.put("nickname", ideaUser.getOauth_nickname());
		if(ideaUser.getReal_name() != null) {
			map1.put("real_name", ideaUser.getReal_name());
		} else {
			map1.put("real_name", "");
		}
		if(ideaUser.getSex() != null) {
			map1.put("sex", ideaUser.getSex());
		} else {
			map1.put("sex", "");
		}
		if(ideaUser.getBirth() != null) {
			map1.put("birth", ideaUser.getBirth());
		} else {
			map1.put("birth", "");
		}
		if(ideaUser.getEmail() != null) {
			map1.put("email", ideaUser.getEmail());
		} else {
			map1.put("email", "");
		}
		if(ideaUser.getMobile() != null) {
			map1.put("tel", ideaUser.getMobile());
		} else {
			map1.put("tel", "");
		}
		
		objectResponse.setData(map1);
		objectResponse.setCode(200);
		objectResponse.setMsg("信息已返回");
		return JSONObject.toJSONString(objectResponse);
	}
	
	/**
	 * 确认收货
	 * @param openId
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/api_gain_goods", method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=utf-8")
	public String gainGoods(@RequestParam(name="open_id") String openId, @RequestParam(name="order_sn") Long orderId) throws BusinessException {
		ObjectResponse objectResponse = new ObjectResponse();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) redisUtil.get(openId);
		if(map != null) {
			ideaOrderService.gainGoods(StringUtil.strToLong(map.get("userId").toString()), orderId);
			
			objectResponse.setCode(200);
			objectResponse.setMsg("已确认收货");
		} else {
			IdeaUser ideaUser = ideaUserService.isExist(null, openId);
			if(ideaUser != null) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("unionId", ideaUser.getSession_key());
				userMap.put("userId", ideaUser.getId());
				redisUtil.set(ideaUser.getXcx_openid(), userMap, 1800L);
				
				ideaOrderService.gainGoods(ideaUser.getId(), orderId);
				objectResponse.setCode(200);
				objectResponse.setMsg("已确认收货");
			} else {
				objectResponse.setCode(500);
				objectResponse.setMsg("登录异常，该用户未授权登录");
			}
		}
		return JSONObject.toJSONString(objectResponse);
	}
}
