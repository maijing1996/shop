package com.shop.model.po;

import com.shop.model.common.BaseEntity;

/**
 * 配送设置
 * 2018年9月5日
 */
public class IdeaShopDispatching extends BaseEntity {
	
	private Integer express_switch;//快递开关(1:开启 0:关闭)
	private Integer first_weight;//首重重量(KG)
	private Double first_price;//首重价格(元)
	private Integer second_weight;//续重重量(KG)
	private Double second_price;//续重价格(元)
	private Integer pickup;//自提开关(1:开启 0:关闭
	private String pickup_name;//自提点联系人
	private String pickup_tel;//联系人手机
	private String pickup_addr;//自提点地址
	
	public Integer getExpress_switch() {
		return express_switch;
	}
	public void setExpress_switch(Integer express_switch) {
		this.express_switch = express_switch;
	}
	public Integer getFirst_weight() {
		return first_weight;
	}
	public void setFirst_weight(Integer first_weight) {
		this.first_weight = first_weight;
	}
	public Double getFirst_price() {
		return first_price;
	}
	public void setFirst_price(Double first_price) {
		this.first_price = first_price;
	}
	public Integer getSecond_weight() {
		return second_weight;
	}
	public void setSecond_weight(Integer second_weight) {
		this.second_weight = second_weight;
	}
	public Double getSecond_price() {
		return second_price;
	}
	public void setSecond_price(Double second_price) {
		this.second_price = second_price;
	}
	public Integer getPickup() {
		return pickup;
	}
	public void setPickup(Integer pickup) {
		this.pickup = pickup;
	}
	public String getPickup_name() {
		return pickup_name;
	}
	public void setPickup_name(String pickup_name) {
		this.pickup_name = pickup_name;
	}
	public String getPickup_tel() {
		return pickup_tel;
	}
	public void setPickup_tel(String pickup_tel) {
		this.pickup_tel = pickup_tel;
	}
	public String getPickup_addr() {
		return pickup_addr;
	}
	public void setPickup_addr(String pickup_addr) {
		this.pickup_addr = pickup_addr;
	}
}
