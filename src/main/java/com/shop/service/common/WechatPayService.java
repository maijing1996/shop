package com.shop.service.common;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shop.conf.MySiteSetting;
import com.shop.model.dto.wechat.MpWeixinPayUtil;
import com.shop.model.dto.wechat.MpWeixinUtil;
import com.shop.model.dto.wechat.UnifiedOrderReq;
import com.shop.model.dto.wechat.UnifiedOrderResp;
import com.shop.model.po.IdeaShopPay;
import com.shop.model.po.IdeaWxConfig;
import com.shop.service.IdeaShopPayService;
import com.shop.service.IdeaWxConfigService;
import com.shop.util.MD5Util;
import com.shop.util.StringUtil;

@Service
public class WechatPayService {

	private Logger logger = LoggerFactory.getLogger(WechatPayService.class);
	
	@Autowired
    private MpWeixinPayUtil mpWeixinPayUtil;
	@Autowired
	private IdeaWxConfigService ideaWxConfigService;
	@Autowired
	private IdeaShopPayService ideaShopPayService;
	@Autowired
	private MySiteSetting mySiteSetting;
	
	/**
	 * 设置微信必须的参数
	 * @param map
	 * @param ip
	 * @param openId
	 * @return
	 * @throws Exception 
	 */
	public JSONObject setWechatVal(String ip, String openId, Double price, String orderSn, String userId) throws Exception {
		
		IdeaWxConfig ideaWxConfig = ideaWxConfigService.getById(1L);
		IdeaShopPay ideaShopPay = ideaShopPayService.getById(1L);
		
		Map<String, String> map = new HashMap<String, String>();
		//公众账号ID
		map.put("appId", ideaWxConfig.getAppid());
    	//商户号
		map.put("mchId", ideaShopPay.getWx_mchid());
		//用户openId
		map.put("openId", openId);
    	//随机字符串
		String nonceStr = UUID.randomUUID().toString().replace("-", "");
		map.put("nonceStr", nonceStr);
    	//商品描述
		map.put("body", "Payment for registration!");
		//商户订单号（32位）
		map.put("outTradeNo", orderSn);
    	//终端IP
		map.put("ip", ip);
    	//通知地址
		map.put("notifyUrl", mySiteSetting.getHost() + ideaShopPay.getWx_notify_url());
    	//交易类型
		map.put("tradeType", "JSAPI");
		//商家秘钥
		map.put("mchKey", ideaShopPay.getWx_appkey());
		//支付金额
		Double dou = price * 100;
		String total_fee = Integer.valueOf(dou.intValue()).toString();
		map.put("price", total_fee);
		//
		map.put("attach", "userId:"+userId);

		//生成并发起支付预订单
		UnifiedOrderResp resq = UnifiedOrderReq(map);
		//生成支付签名
		JSONObject jsonObject = getSign(map, resq);
		return jsonObject;
	}
	
	/**
     * 发起支付订单
     * @param map
     * @param request
     * @return
     * @throws Exception
     */
    private UnifiedOrderResp UnifiedOrderReq(Map<String, String> map) throws Exception{
    	
        UnifiedOrderReq unifiedOrderReq = new UnifiedOrderReq();
        //公众账号ID
        unifiedOrderReq.setAppid(map.get("appId"));
		//商户号
		unifiedOrderReq.setMch_id(map.get("mchId"));
		//微信用户openId
        unifiedOrderReq.setOpenid(map.get("openId"));
        //随机字符串
        unifiedOrderReq.setNonce_str(map.get("nonceStr"));
        //商品描述
        unifiedOrderReq.setBody("body");
        //商户订单号（32位）
        unifiedOrderReq.setOut_trade_no(map.get("outTradeNo"));
        //交易总金额
        unifiedOrderReq.setTotal_fee(StringUtil.strToInt(map.get("price")));
        //终端IP
        unifiedOrderReq.setSpbill_create_ip(map.get("ip"));
        //通知地址
        unifiedOrderReq.setNotify_url(map.get("notifyUrl"));
        //交易类型
        unifiedOrderReq.setTrade_type(map.get("tradeType"));
        //
        unifiedOrderReq.setAttach(map.get("attach"));
        
        //封装文件
        UnifiedOrderResp resq = mpWeixinPayUtil.unifiredOrderWithOutSslCert(
        		map.get("myAppId"),
        		map.get("mchId"),
        		map.get("mchKey"),
        		unifiedOrderReq);
        logger.info("=====发起支付订单成功=====");
        return resq;
    }
    
    /**
     * 生成支付签名
     */
    public JSONObject getSign(Map<String, String> map, UnifiedOrderResp resq){
    	//生成支付签名
		SortedMap<String, String> mapS = new TreeMap<String, String>();
		String timeStamp = Long.toString(System.currentTimeMillis());
		String nonce_str =  UUID.randomUUID().toString().replace("-", "");
		mapS.put("appId", map.get("appId"));
		mapS.put("timeStamp", timeStamp);
		mapS.put("nonceStr", nonce_str);
		mapS.put("package", "prepay_id=" + resq.getPrepay_id());
		mapS.put("signType", "MD5");
		String signStr = MpWeixinUtil.sortAscii(mapS) + "&key=" + map.get("mchKey");
		String sign = MD5Util.MD5(signStr).toUpperCase();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("appId", map.get("appId"));
		jsonObject.put("timeStamp", timeStamp);
		jsonObject.put("nonceStr", nonce_str);
		jsonObject.put("package", "prepay_id=" + resq.getPrepay_id());
		jsonObject.put("signType", "MD5");
		jsonObject.put("paySign", sign);
		
		logger.info("=====生成支付签名成功=====");
		return jsonObject;
    }
    
    /**
     * 获得access_token
     * @return
     */
    /*public String getAccessToken(){
    	JsapiTicketRes jsapiTicketRes = (JsapiTicketRes) redisUtil.get("we_jsapi_ticket");
    	if(jsapiTicketRes != null && jsapiTicketRes.getTicket() != null){
    		return jsapiTicketRes.getTicket();
    	} else {
    		String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
    				+ mySiteSetting.getMyAppId() + "&secret=" + mySiteSetting.getAppSecret();
    		AccessTokenRes accessTokenRes = HttpUtil.httpRequest(URL, "GET", null, AccessTokenRes.class);
    		if(accessTokenRes.getAccess_token() != null){
    			redisUtil.set("we_access_token", accessTokenRes, new Long(5400));
    			try{
    				return getJsapiTicket(accessTokenRes.getAccess_token());
    			} catch (Exception e) {
    				logger.info("jsapi_ticket 获取失败！");
    				return null;
				}
    		} else {
    			return null;
    		}
    	}
    }*/
    
    /**
     * 获得 jsapi_ticket
     * @return
     */
    /*public String getJsapiTicket(String access_token){
    	String URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
    	JsapiTicketRes jsapiTicketRes = HttpUtil.httpRequest(URL, "GET", null, JsapiTicketRes.class);
		if(jsapiTicketRes.getTicket() != null){
			redisUtil.set("we_jsapi_ticket", jsapiTicketRes, new Long(5400));
			return jsapiTicketRes.getTicket(); 
		} else {
			return null;
		}
    }*/
}
