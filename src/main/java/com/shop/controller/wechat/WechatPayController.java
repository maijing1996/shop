package com.shop.controller.wechat;

import java.io.OutputStream;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.shop.exception.BusinessException;
import com.shop.model.dto.wechat.MpWeixinPayUtil;
import com.shop.model.dto.wechat.MpWeixinUtil;
import com.shop.model.dto.wechat.ReturnData;
import com.shop.model.dto.wechat.WechatReturnPayOrder;
import com.shop.model.po.IdeaShopPay;
import com.shop.service.IdeaOrderService;
import com.shop.service.IdeaShopPayService;
import com.shop.util.MD5Util;
import com.shop.util.XmlUtil;

@Controller
@RequestMapping("/")
public class WechatPayController {

	private Logger logger = LoggerFactory.getLogger(WechatPayController.class);
	
	@Autowired
    private MpWeixinPayUtil mpWeixinPayUtil;
	@Autowired
	private IdeaShopPayService ideaShopPayService;
	@Autowired
	private IdeaOrderService ideaOrderService;
	
	/**
     * 1.对微信返回的结果进行处理
	 * 2.对微信服务器进行应答
     *
     * @param request
     * @return
     */
    @RequestMapping(value="/orderPayNotice", method = RequestMethod.POST)
    public void orderPay(HttpServletRequest request, HttpServletResponse response){
    	
    	ReturnData returnData = new ReturnData();
    	try {
			//生成xml字符
			String xml = mpWeixinPayUtil.parseXml(request);
			logger.info("---------------------微信平台返回数据------------------------------");
			logger.info(xml);
			if (xml != null && xml != "") {
				//解析xml
				WechatReturnPayOrder notifyReq = XmlUtil.parseObject(xml, WechatReturnPayOrder.class);
				String return_code = notifyReq.getReturn_code();
				logger.info("---------------------业务是否成功: " + return_code +"------------------------------");
				if ("SUCCESS".equals(return_code)) {
					//拼接StringA
					logger.info("---------------------生成新的sign------------------------------");
					SortedMap<String, String> map = new TreeMap<String, String>();
					JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(notifyReq));
					for (String key : object.keySet()) {
						if (!key.equals("sign")) {
							Object value = object.get(key);
							if (value != null) {
								map.put(key, value.toString());
							}
						}
					}
					IdeaShopPay ideaShopPay = ideaShopPayService.getById(1L);
					String signStr = MpWeixinUtil.sortAscii(map) + "&key=" + ideaShopPay.getWx_appkey();
					logger.info("signStr" + signStr);
					String sign = MD5Util.MD5(signStr).toUpperCase();
					//校验签名  不校验金额
					logger.info("---------------------开始校验sign:" + sign + "------------------------------");
					if (sign.equals(notifyReq.getSign())) {
						logger.info("---------------------相应的订单是否已经保存------------------------------");
						String out_trade_no = notifyReq.getOut_trade_no();
						boolean boo = ideaOrderService.verifyInfoIsNull(out_trade_no);
						//业务逻辑
						if(boo){
							try {
								logger.info("---------------------开始保存账单,更改用户支付状态------------------------------");
								//保存订单信息  报名信息
								ideaOrderService.saveOrder(notifyReq);
								logger.info("支付成功账单:" + JSONObject.toJSONString(notifyReq));
								returnData.setReturn_code("SUCCESS");
								returnData.setReturn_msg("OK");
							} catch (BusinessException e) {
								//防止数据保存不成功（这时候需要查看日志文件来获取出错的原因，并且手动更改数据库---程序员---）
								logger.info("---------------------这是出错数据------------------------------");
								logger.error("没保存账号数据：" + JSONObject.toJSONString(notifyReq));
								returnData.setReturn_code("SUCCESS");
								returnData.setReturn_msg("OK");
							}
						} else {
							returnData.setReturn_code("SUCCESS");
							returnData.setReturn_msg("OK！");
						}
					} else {
						logger.error("支付校验失败!");
						returnData.setReturn_code("FAIL");
						returnData.setReturn_msg("签名校验失败！");
					}
				} else {
					try {
						logger.info("---------------------开始保存账单,更改用户支付状态------------------------------");
						//保存订单信息
						ideaOrderService.saveOrder(notifyReq);
						logger.info("支付成功账单:" + JSONObject.toJSONString(notifyReq));
					} catch (BusinessException e) {
						//防止数据保存不成功（这时候需要查看日志文件来获取出错的原因，并且手动更改数据库---程序员---）
						logger.info("---------------------这是出错数据------------------------------");
						logger.error("没保存账号数据：" + JSONObject.toJSONString(notifyReq));
					}
					logger.error("支付校验失败!");
					returnData.setReturn_code("FAIL");
					returnData.setReturn_msg("支付校验失败！");
				}
			} else {
				logger.error("字节流转变为想xml出错");
				logger.error(xml);
			}
			//返回消息给微信
			OutputStream stream = response.getOutputStream();
			String rString = XmlUtil.toXML(returnData, ReturnData.class);
			stream.write(rString.getBytes("UTF-8"));
			logger.info("---------------------返回消息成功------------------------------");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			logger.info("---------------------返回消息失败------------------------------");
		}
    }
}
