package com.shop.model.po;

import javax.persistence.Transient;

import com.shop.model.common.BaseEntity;

/**
 * 用户表
 * 偶木
 * 2018年8月22日
 */
public class IdeaUser extends BaseEntity {

	private String uuid;//uuid(唯一)
	private String uid;//用户名
	private String pwd;//密码
	private Integer sex;//0保密 1男 2女
	private Integer integral;//积分
	private Double user_money;//余额
	private Double frozen_money;//冻结金额
	private Double total_money;//累计消费额
	private Double distribut_money;//累计佣金
	private String nickname;//昵称
	private String email;//邮箱
	private String mobile;//手机号
	private Integer mobile_validated;//是否验证手机号
	private String oauth_type;//第三方登录来源
	private String openid;//第三方唯一ID
	private String xcx_openid;//小程序openid
	private String unionid;//第三方关联ID
	private String oauth_nickname;//第三方昵称
	private String avatar;//头像
	private Long pid;//上级ID
	private Long last_login_time;//最后登录时间
	private String last_login_ip;//最后登录Ip
	private Integer is_work;//激活
	private Integer is_fx;//是否分销商
	private String fx_goods;//已分销商品ids
	private String token;//API调用使用
	private Long add_date;//添加日期
	private Long distribution_lev;//分销商类别
	private Long distribution_apply_add_time;//分销商身份申请时间
	private Long distribution_pass_add_time;//分销商身份通过时间
	private Long distribution_refunse_add_time;//申请被拒绝的时间
	private Long distribution_recommend_uid;//推荐人UID
	private String distribution_qrcode;//分销分享二维码图片地址
	private Long dcenter;
	private String note;
	private String real_name;
	private Long level_id;//等级Id
	private String birth;
	private String distribution_qrcode_index;
	private String distribution_qrcode_person;
	private Long recommend_date;//推荐日期
	private String bag1;
	private String bag2;
	private String bag3;
	private String user_tel;
	private String pass_note;
	private String d_center_name;
	private Integer d_is_new;
	private String session_key;
	
	@Transient
	private Integer d_status;
	@Transient
	private String d_name;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Double getUser_money() {
		return user_money;
	}
	public void setUser_money(Double user_money) {
		this.user_money = user_money;
	}
	public Double getFrozen_money() {
		return frozen_money;
	}
	public void setFrozen_money(Double frozen_money) {
		this.frozen_money = frozen_money;
	}
	public Double getTotal_money() {
		return total_money;
	}
	public void setTotal_money(Double total_money) {
		this.total_money = total_money;
	}
	public Double getDistribut_money() {
		return distribut_money;
	}
	public void setDistribut_money(Double distribut_money) {
		this.distribut_money = distribut_money;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getMobile_validated() {
		return mobile_validated;
	}
	public void setMobile_validated(Integer mobile_validated) {
		this.mobile_validated = mobile_validated;
	}
	public String getOauth_type() {
		return oauth_type;
	}
	public void setOauth_type(String oauth_type) {
		this.oauth_type = oauth_type;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getXcx_openid() {
		return xcx_openid;
	}
	public void setXcx_openid(String xcx_openid) {
		this.xcx_openid = xcx_openid;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getOauth_nickname() {
		return oauth_nickname;
	}
	public void setOauth_nickname(String oauth_nickname) {
		this.oauth_nickname = oauth_nickname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Long getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(Long last_login_time) {
		this.last_login_time = last_login_time;
	}
	public String getLast_login_ip() {
		return last_login_ip;
	}
	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}
	public Integer getIs_work() {
		return is_work;
	}
	public void setIs_work(Integer is_work) {
		this.is_work = is_work;
	}
	public Integer getIs_fx() {
		return is_fx;
	}
	public void setIs_fx(Integer is_fx) {
		this.is_fx = is_fx;
	}
	public String getFx_goods() {
		return fx_goods;
	}
	public void setFx_goods(String fx_goods) {
		this.fx_goods = fx_goods;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Long add_date) {
		this.add_date = add_date;
	}
	public Long getDistribution_lev() {
		return distribution_lev;
	}
	public void setDistribution_lev(Long distribution_lev) {
		this.distribution_lev = distribution_lev;
	}
	public Long getDistribution_apply_add_time() {
		return distribution_apply_add_time;
	}
	public void setDistribution_apply_add_time(Long distribution_apply_add_time) {
		this.distribution_apply_add_time = distribution_apply_add_time;
	}
	public Long getDistribution_pass_add_time() {
		return distribution_pass_add_time;
	}
	public void setDistribution_pass_add_time(Long distribution_pass_add_time) {
		this.distribution_pass_add_time = distribution_pass_add_time;
	}
	public Long getDistribution_refunse_add_time() {
		return distribution_refunse_add_time;
	}
	public void setDistribution_refunse_add_time(Long distribution_refunse_add_time) {
		this.distribution_refunse_add_time = distribution_refunse_add_time;
	}
	public Long getDistribution_recommend_uid() {
		return distribution_recommend_uid;
	}
	public void setDistribution_recommend_uid(Long distribution_recommend_uid) {
		this.distribution_recommend_uid = distribution_recommend_uid;
	}
	public String getDistribution_qrcode() {
		return distribution_qrcode;
	}
	public void setDistribution_qrcode(String distribution_qrcode) {
		this.distribution_qrcode = distribution_qrcode;
	}
	public Long getLevel_id() {
		return level_id;
	}
	public void setLevel_id(Long level_id) {
		this.level_id = level_id;
	}
	public Integer getD_status() {
		return d_status;
	}
	public void setD_status(Integer d_status) {
		this.d_status = d_status;
	}
	public String getD_name() {
		return d_name;
	}
	public void setD_name(String d_name) {
		this.d_name = d_name;
	}
	public Long getDcenter() {
		return dcenter;
	}
	public void setDcenter(Long dcenter) {
		this.dcenter = dcenter;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getDistribution_qrcode_index() {
		return distribution_qrcode_index;
	}
	public void setDistribution_qrcode_index(String distribution_qrcode_index) {
		this.distribution_qrcode_index = distribution_qrcode_index;
	}
	public String getDistribution_qrcode_person() {
		return distribution_qrcode_person;
	}
	public void setDistribution_qrcode_person(String distribution_qrcode_person) {
		this.distribution_qrcode_person = distribution_qrcode_person;
	}
	public Long getRecommend_date() {
		return recommend_date;
	}
	public void setRecommend_date(Long recommend_date) {
		this.recommend_date = recommend_date;
	}
	public String getBag1() {
		return bag1;
	}
	public void setBag1(String bag1) {
		this.bag1 = bag1;
	}
	public String getBag2() {
		return bag2;
	}
	public void setBag2(String bag2) {
		this.bag2 = bag2;
	}
	public String getBag3() {
		return bag3;
	}
	public void setBag3(String bag3) {
		this.bag3 = bag3;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public String getPass_note() {
		return pass_note;
	}
	public void setPass_note(String pass_note) {
		this.pass_note = pass_note;
	}
	public String getD_center_name() {
		return d_center_name;
	}
	public void setD_center_name(String d_center_name) {
		this.d_center_name = d_center_name;
	}
	public Integer getD_is_new() {
		return d_is_new;
	}
	public void setD_is_new(Integer d_is_new) {
		this.d_is_new = d_is_new;
	}
	public String getSession_key() {
		return session_key;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
}
