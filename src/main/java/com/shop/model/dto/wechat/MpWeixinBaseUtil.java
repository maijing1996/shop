package com.shop.model.dto.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;
import com.shop.conf.MySiteSetting;
import com.shop.util.HttpUtil;
import com.shop.util.RedisUtil;

@Service
public class MpWeixinBaseUtil {

	private String mpAppId;

	@Autowired
	private MySiteSetting mySiteSetting;

	@Autowired
	private RedisUtil redisUtil;

	public String getMpAppId() {
		return mpAppId;
	}

	public void setMpAppId(String mpAppId) {
		this.mpAppId = mpAppId;
	}

	/*public String getAccessToken() {

		String key = "mp.weixin.token_" + mpAppId;
		String token = redisUtil.exists(key) ? redisUtil.get(key).toString() : null;

		if (StringUtils.isNullOrEmpty(token)) {
				String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
				getAccessTokenUrl = String.format(getAccessTokenUrl, mySiteSetting.getMyAppId(), mySiteSetting.getAppSecret());
				AccessTokenResp accessTokenMap = HttpUtil.httpRequest(getAccessTokenUrl, "GET", null,
						AccessTokenResp.class);
				if (accessTokenMap != null && accessTokenMap.getErrcode() == 0) {
					token = accessTokenMap.getAccess_token();
					redisUtil.set(key, token, 6000L);
				}
		}

		return token;
	}

	public String getAccessToken(boolean refresh) {

		String key = "mp.weixin.token_" + mpAppId;
		String token = redisUtil.exists(key) ? redisUtil.get(key).toString() : null;

		if (refresh) {
			token = null;
		}

		if (StringUtils.isNullOrEmpty(token)) {
				String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
				getAccessTokenUrl = String.format(getAccessTokenUrl, mySiteSetting.getMyAppId(), mySiteSetting.getAppSecret());
				AccessTokenResp accessTokenMap = HttpUtil.httpRequest(getAccessTokenUrl, "GET", null,
						AccessTokenResp.class);
				if (accessTokenMap.getErrcode() == 0) {
					token = accessTokenMap.getAccess_token();
					redisUtil.set(key, token, 6000L);
				}
		}

		return token;
	}*/
}
