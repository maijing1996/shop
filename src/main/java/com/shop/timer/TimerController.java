package com.shop.timer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.shop.exception.BusinessException;
import com.shop.model.po.IdeaGoodsSpecPrice;
import com.shop.model.po.IdeaOrder;
import com.shop.model.po.IdeaShopTransaction;
import com.shop.model.po.IdeaUser;
import com.shop.service.IdeaGoodsSpecPriceService;
import com.shop.service.IdeaOrderService;
import com.shop.service.IdeaShopTransactionService;
import com.shop.service.IdeaUserService;
import com.shop.util.DateUtil;

@Component
public class TimerController {

	private Logger logger = LoggerFactory.getLogger(TimerController.class);
	
	@Autowired
	private IdeaOrderService ideaOrderService;
	@Autowired
	private IdeaUserService ideaUserService;
	@Autowired
	private IdeaGoodsSpecPriceService ideaGoodsSpecPriceService;
	@Autowired
	private IdeaShopTransactionService ideaShopTransactionService;
	
	@Scheduled(cron=" 0 0/5 * * * ?") //测试时间"0 0/1 * * * ?  正式时间：   0 0/5 * * * ?"
    public void executeFileDownLoadTask() {
		
		try {
			//获得超过半小时没有支付的订单
			StringBuffer buffer = new StringBuffer();
			IdeaShopTransaction ideaShopTransaction = ideaShopTransactionService.getById(1L);
			int unpaid = ideaShopTransaction.getUnpaid()*60;
			int ic = 0;
			List<IdeaOrder> list = ideaOrderService.getNotPayOrder(unpaid);
			if(list != null && !list.isEmpty()){
				ic = list.size();
				for(IdeaOrder ideaOrder : list) {
					ideaOrder.setOrder_state(-1);
					ideaOrderService.update(ideaOrder);
					
					//库存恢复
					IdeaGoodsSpecPrice ideaGoodsSpecPrice = ideaGoodsSpecPriceService.getAllById(ideaOrder.getSpecId());
					ideaGoodsSpecPrice.setStock(ideaGoodsSpecPrice.getStock() + ideaOrder.getAmount());
					ideaGoodsSpecPriceService.update(ideaGoodsSpecPrice);
					
					//用户积分恢复
					IdeaUser ideaUser = ideaUserService.getById(ideaOrder.getUser_id());
					Integer integral = ideaUser.getIntegral() + ideaOrder.getUse_jf();
					ideaUser.setIntegral(integral);
					ideaUserService.update(ideaUser);
					
					//扣除积分记录改为拒绝
					//ideaUserPaylogService.updateAll(ideaUser.getId(), ideaOrder.getId());
					
					buffer.append(ideaOrder.getId());
					buffer.append(",");
				}
	    	}
			logger.info("本次取消的订单数量："+ic+"，本次取消的订单编号："+buffer.toString());
		} catch (BusinessException e) {
			logger.error(JSONObject.toJSONString(e));
		}
    }
	
	/**
	 * 自动收货
	 */
	@Scheduled(cron=" 0 30 0 * * ?") //测试时间"0 0/1 * * * ?  正式时间：   0 0/5 * * * ?"
    public void executeGainGoodsTask() {
		try {
			//获得超过半小时没有支付的订单
			StringBuffer buffer = new StringBuffer();
			IdeaShopTransaction ideaShopTransaction = ideaShopTransactionService.getById(1L);
			long shipments = DateUtil.getTimestamp() - ideaShopTransaction.getShipments() * 86400;
			int ic = 0;
			List<IdeaOrder> list = ideaOrderService.getGainGoods(shipments);
			if(list != null && !list.isEmpty()){
				ic = list.size();
				for(IdeaOrder ideaOrder : list) {
					ideaOrderService.gainGoods(ideaOrder.getUser_id(), ideaOrder.getId());
					
					buffer.append(ideaOrder.getId());
					buffer.append(",");
				}
	    	}
			logger.info("本次自动收货的的订单数量："+ic+"，本次自动收货的订单编号："+buffer.toString());
		} catch (BusinessException e) {
			logger.error(JSONObject.toJSONString(e));
		}
	}
	
	/*@Scheduled(cron=" 0 0/10 * * * ?") //测试时间"0 0/1 * * * ?  正式时间：   0 0/5 * * * ?"
    public void executeFileDownLoadeTask() throws BusinessException {
		List<IdeaUser> list = ideaUserService.getAll(null, null);
		IdeaWxConfig ideaWxConfig = ideaWxConfigService.getInfo();
		for(IdeaUser ideaUser : list) {
			if(ideaUser.getDistribution_qrcode_index() == null || ideaUser.getDistribution_qrcode_person() == null) {
				//获得access_token
				String url4 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ideaWxConfig.getAppid()+"&secret="+ideaWxConfig.getApp_secret();
				AccessToken accessToken = HttpUtil.httpRequest(url4, "GET", null, AccessToken.class);
				
				if(accessToken.getAccess_token() != null) {
					//返回路径没处理好
					String url = HttpUtil.getminiqrQr(ideaUser.getId().toString(), accessToken.getAccess_token(), "pages/index/index", mySiteSetting.getHost());//首页二维码
					String url2 = HttpUtil.getminiqrQr(ideaUser.getId().toString(), accessToken.getAccess_token(), "pages/user/apply", mySiteSetting.getHost());//个人中心二维码
					ideaUser.setDistribution_qrcode_index(url);
					ideaUser.setDistribution_qrcode_person(url2);
					ideaUserService.update(ideaUser);
				}
			}
		}
	}*/
}
