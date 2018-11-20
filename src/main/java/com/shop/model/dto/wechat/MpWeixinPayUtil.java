package com.shop.model.dto.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shop.util.HttpUtil;
import com.shop.util.MD5Util;
import com.shop.util.XmlUtil;

@Service
public class MpWeixinPayUtil extends MpWeixinBaseUtil {

	private static Logger logger = Logger.getLogger(MpWeixinPayUtil.class);

	/**
	 * 微信支付订单生成接口(带SslCert)
	 * 
	 * @param mpAppId
	 * @param req
	 * @return
	 */
	public UnifiedOrderResp unifiredOrderWithSslCert(String mpAppId, String mchId, String mchKey, String sslCertPath,
													 UnifiedOrderReq req) {

		UnifiedOrderResp resp = null;
		String sslCertFile = sslCertPath + mchId + "_cert.p12";

		super.setMpAppId(mpAppId);

		try {
			// 生成Sign签名
			SortedMap<String, String> map = new TreeMap<String, String>();
			JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(req));
			for (String key : object.keySet()) {
				if (!key.equals("sign")) {
					Object value = object.get(key);
					if (value != null) {
						map.put(key, value.toString());
					}
				}
			}
			String signStr = MpWeixinUtil.sortAscii(map) + "&key=" + mchKey;
			String sign = MD5Util.MD5(signStr).toUpperCase();
			req.setSign(sign);

			String unifiedOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			String reqXml = XmlUtil.toXML(req, UnifiedOrderReq.class);
			String respXml = HttpUtil.httpRequestStringWithSSLCert(unifiedOrderUrl, "POST", reqXml, sslCertFile, mchId);
			resp = XmlUtil.parseObject(respXml, UnifiedOrderResp.class);
		} catch (Exception ex) {
			logger.error("调用微信支付接口生成订单失败！", ex);
		}

		return resp;
	}
	
	/**
	 * 微信支付订单生成接口(不带SslCert)
	 *
	 * @param mpAppId
	 * @param req
	 * @return
	 */
	public synchronized UnifiedOrderResp unifiredOrderWithOutSslCert(String mpAppId, String mchId, String mchKey,
														UnifiedOrderReq req) {
		UnifiedOrderResp resp = null;

		super.setMpAppId(mpAppId);

		try {
			// 生成Sign签名
			SortedMap<String, String> map = new TreeMap<String, String>();
			JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(req));
			for (String key : object.keySet()) {
				if (!key.equals("sign")) {
					Object value = object.get(key);
					if (value != null) {
						map.put(key, value.toString());
					}
				}
			}
			String signStr = MpWeixinUtil.sortAscii(map) + "&key=" + mchKey;
			String sign = MD5Util.MD5(signStr).toUpperCase();
			req.setSign(sign);
			String unifiedOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			String reqXml = XmlUtil.toXML(req, UnifiedOrderReq.class);
			resp =  HttpUtil.httpRequestXMLToClazz(unifiedOrderUrl, "POST", reqXml, UnifiedOrderResp.class);
		} catch (Exception ex) {
			logger.error("调用微信支付接口生成订单失败！", ex);
		}

		return resp;
	}


	/**
	 * 生成xml文件
	 */
	public String parseXml(HttpServletRequest request){
		String xml = null;
		try {
			InputStream inputStream;
			inputStream = request.getInputStream();
			StringBuffer sb=new StringBuffer();
			String s;
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while ((s = in.readLine()) != null) {
				sb.append(s);
			}
			xml=sb.toString();
		} catch (IOException e) {
			logger.error("字节流转变为想xml出错");
			e.printStackTrace();
		}
		return xml;
	}
	
	/**
	 * 生成订单号
	 */
	public String getOut_trade_no(String str1, String str2){
		StringBuffer buffer = new StringBuffer();
		buffer.append(str1);
		buffer.append(str2);
		for(int i = 0; i < 50; i++){
			if(buffer.length() == 32){
				break;
			} else if(buffer.length() > 32){
				buffer.delete(32, buffer.length());
				break;
			} else {
				Random random = new Random();
				int ic= random.nextInt(11);
				buffer.append(ic);
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 获得上一天的日期
	 * @return
	 */
	public String getYesterday(){
		
		Calendar calendar = Calendar.getInstance();//获取日历实例  
		calendar.setTime(new Date()); //日期设置为今天

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		calendar.add(Calendar.DAY_OF_MONTH, -1); 
		String yesterday= sdf.format(calendar.getTime());//获得昨天日期
		return yesterday;
	}
}
