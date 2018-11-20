package com.shop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaCouponMapper;
import com.shop.model.dto.Coupon;
import com.shop.model.po.IdeaCoupon;
import com.shop.service.common.BaseService;
import com.shop.util.DateUtil;
import com.shop.util.StringUtil;

@Service
public class IdeaCouponService extends BaseService<IdeaCoupon, IdeaCouponMapper> {

	@Override
	protected String getTableName() {
		return "idea_coupon";
	}

	/**
	 * 获得 优惠券信息
	 * @param page
	 * @param size
	 * @param title
	 * @return
	 */
	public PageInfo<Coupon> listInfo(Integer page, Integer size, String title) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<Coupon> list = mapper.listInfo(title);
		PageInfo<Coupon> pageInfo = new PageInfo<Coupon>(list);
		return pageInfo;
	}
	
	/**
	 * 获取已领取未使用的代金券列表
	 * @param userId
	 * @return
	 */
	/*public List<Map<String, Object>> getVoucher(Long userId) {
		List<Map<String, Object>> list = mapper.getVoucher(userId);
		List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
		if(list != null && !list.isEmpty()) {
			for(int i=0; i < list.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Object> map1 = list.get(i);
				if((map1.get("type").toString()) == 4) {
					map.put("type", "抵用券");
					map.put("use_edate", "不限时间");
				} else {
					if((Integer)map1.get("type") == 5) {
						Long date = ((Long)map1.get("add_date") + 2592000)*1000;
						String edate = DateUtil.format(date, DateUtil.FORMAT_YYYY_MM_DD);
						map.put("type", "优惠券");
						map.put("use_edate", edate);
					} else {
						map.put("type", "优惠券");
						map.put("use_edate", DateUtil.format((Long) map1.get("use_edate")*1000, DateUtil.FORMAT_YYYY_MM_DD));
					}
				}
				
				map.put("id", map1.get("id"));
				map.put("title", map1.get("title"));
				map.put("yh_price", map1.get("yh_price"));
				map.put("min_price", map1.get("min_price"));
				list2.add(map);
				//ic.id, ic.title, ic.type, ic.yh_price, ic.min_price, ic.use_edate, icu.add_date
			}
		}
		return list2;
	}*/
	
	/**
	 * 获得优惠券列表
	 * @param couponSn
	 * @return
	 */
	public List<IdeaCoupon> getInfoByIds(List<String> couponSn, Long userId) {
		return mapper.getInfoByIds(couponSn, userId);
	}
	
	/**
	 * 将优惠券设置为已使用
	 * @param couponSn
	 * @param userId
	 * @return
	 */
	public int updateUseCoupon(List<String> couponSn, Long userId) {
		return mapper.updateUseCoupon(couponSn, userId);
	}
	
	/**
	 * 获取代金券名称，已领取未使用的代金券列表
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getUseVoucher(Long userId, Double price) {
		List<Map<String, Object>> list = mapper.getUseVoucher(userId, price);
		List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
		if(list != null && !list.isEmpty()) {
			Long now = DateUtil.getTimestamp();
			for(int i=0; i < list.size(); i++) {
				/*if(list2.size() < 5) {*/
				Map<String, Object> map1 = list.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				if(StringUtil.strToInt(map1.get("type").toString()) == 4) {
					map.put("id", map1.get("id"));
					map.put("type", map1.get("type"));
					map.put("title", map1.get("title"));
					map.put("yh_price", map1.get("yh_price"));
					map.put("min_price", map1.get("min_price"));
					map.put("is_search", 0);
					
					list2.add(map);
				} else {
					if(StringUtil.strToInt(map1.get("type").toString()) == 5) {
						Long date = StringUtil.strToLong(map1.get("add_date").toString()) + 2592000L;
						if(now < date) {
							map.put("id", map1.get("id"));
							map.put("type", map1.get("type"));
							map.put("title", map1.get("title"));
							map.put("yh_price", map1.get("yh_price"));
							map.put("min_price", map1.get("min_price"));
							map.put("is_search", 0);
							
							list2.add(map);
						}
					} else {
						Long date = StringUtil.strToLong(map1.get("use_edate").toString());
						if(date > now) {
							map.put("id", map1.get("id"));
							map.put("type", map1.get("type"));
							map.put("title", map1.get("title"));
							map.put("yh_price", map1.get("yh_price"));
							map.put("min_price", map1.get("min_price"));
							map.put("is_search", 0);
							
							list2.add(map);
						}
					}
				}
				/*} else {
					break;
				}*/
			}
		}
		return list2;
	}
	
	/**
	 * 获得我的代金券
	 * @param userId
	 * @param type
	 * @return
	 */
	public PageInfo<Map<String, Object>> listVoucher(Integer page, Integer size, Long userId, Integer type) {
		List<Map<String, Object>> list = mapper.listVoucher(userId, null);
		List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
		if(list != null && !list.isEmpty()) {
			Long now = DateUtil.getTimestamp();
			for(int i=0; i < list.size(); i++) {
				Map<String, Object> map1 = list.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				if(StringUtil.strToInt(map1.get("type").toString()) == 4) {
					map.put("type", "抵用券");
					map.put("use_edate", "不限时间");
					map.put("id", map1.get("id"));
					map.put("title", map1.get("title"));
					map.put("yh_price", map1.get("yh_price"));
					map.put("min_price", map1.get("min_price"));
					map.put("is_use", map1.get("is_use"));
					
					list2.add(map);
				} else {
					if(StringUtil.strToInt(map1.get("type").toString()) == 5) {
						Long date = StringUtil.strToLong(map1.get("add_date").toString()) + 2592000;
						if(now < date) {
							String edate = DateUtil.format(date*1000, DateUtil.FORMAT_YYYY_MM_DD);
							map.put("id", map1.get("id"));
							map.put("type", "抵用券");
							map.put("title", map1.get("title"));
							map.put("use_edate", edate);
							map.put("yh_price", map1.get("yh_price"));
							map.put("min_price", map1.get("min_price"));
							map.put("is_use", map1.get("is_use"));
							
							list2.add(map);
						}
					} else {
						Long date = StringUtil.strToLong(map1.get("use_edate").toString());
						if(date > now) {
							String use_edate = DateUtil.format(date*1000, DateUtil.FORMAT_YYYY_MM_DD);
							map.put("id", map1.get("id"));
							map.put("type", "优惠券");
							map.put("title", map1.get("title"));
							map.put("use_edate", use_edate);
							map.put("yh_price", map1.get("yh_price"));
							map.put("min_price", map1.get("min_price"));
							map.put("is_use", map1.get("is_use"));
							
							list2.add(map);
						}
					}
				}
			}
		}
		
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 15);
		}
		
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list2);
		return pageInfo;
	}
}
