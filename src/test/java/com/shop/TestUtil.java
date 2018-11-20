package com.shop;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shop.conf.MySiteSetting;
import com.shop.controller.manager.LoginManager;
import com.shop.controller.manager.confine.RoleController;
import com.shop.controller.manager.distribution.DeclarationDcenterController;
import com.shop.controller.manager.distribution.DistributionIdentityController;
import com.shop.controller.manager.distribution.DistributionUserController;
import com.shop.controller.manager.order.ShopEvaluateController;
import com.shop.controller.wechat.IdeaOrderController;
import com.shop.exception.BusinessException;
import com.shop.model.dto.wechat.AccessToken;
import com.shop.model.po.IdeaUser;
import com.shop.model.po.IdeaWxConfig;
import com.shop.service.IdeaUserService;
import com.shop.service.IdeaWxConfigService;
import com.shop.util.HttpUtil;
 
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port:0")// 使用0表示端口号随机，也可以具体指定如8888这样的固定端口
public class TestUtil {
	
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private RoleController roleController;
	@Autowired
	private ShopEvaluateController shopEvaluateController;
	@Autowired
	private DistributionIdentityController distributionIdentityController;
	@Autowired
	private DeclarationDcenterController declarationDcenterController;
	@Autowired
	private DistributionUserController distributionUserController;
	@Autowired
	private IdeaOrderController ideaOrderController;
	@Autowired
	private MySiteSetting mySiteSetting;
	@Autowired
	private IdeaWxConfigService ideaWxConfigService;
	@Autowired
	private IdeaUserService ideaUserService;
	
	/*@Test
	public void test03(){
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setAccount("admin");
		loginInfo.setPasswd("admin");
		BaseResponse addTeamMember = loginManager.login(loginInfo);
		Object json = JSONObject.toJSON(addTeamMember);
		System.out.println(json.toString());
	}*/
	
	/*
	@Test
	public void test03() throws BusinessException{
		IdeaAdminRole ideaAdminRole = new IdeaAdminRole();
		ideaAdminRole.setId(90L);
		ideaAdminRole.setInfo("ddd");
		BaseResponse baseResponse = roleController.update(ideaAdminRole);
		Object json = JSONObject.toJSON(baseResponse);
		System.out.println(json.toString());
	}
	
	/*@Test
	public void test05() {
		BaseResponse comment = shopEvaluateController.findComment("");
		Object json = JSONObject.toJSON(comment);
		System.out.println(json.toString());
	}*/
	
	/*@Test
	public void test06() {
		BaseResponse baseResponse = shopEvaluateController.findCommentById((long) 12);
		Object json = JSONObject.toJSON(baseResponse);
		System.out.println(json.toString());
	}
	
	@Test
	public void test07() throws BusinessException {
		BaseResponse baseResponse = shopEvaluateController.deleteById((long) 16);
		Object json = JSONObject.toJSON(baseResponse);
		System.out.println(json.toString());
	}
	
	@Test
	public void test08() {
		String ids = "16";
		BaseResponse baseResponse;
		try {
			baseResponse = shopEvaluateController.deleteByIds(ids);
			Object json = JSONObject.toJSON(baseResponse);
			System.out.println(json.toString());
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}*/

	
	/*@Test
	public void test09() throws BusinessException {
		BaseResponse baseResponse = distributionIdentityController.listInfo();
		Object json = JSONObject.toJSON(baseResponse);
		System.out.println(json.toString());
	}
	*/
	/*@Test
	public void test10() throws BusinessException {
		IdeaDcenter i = new IdeaDcenter();
		i.setId((long) 2);
		i.setName("东莞");
		BaseResponse baseResponse = declarationDcenterController.insert(i);
		Object json = JSONObject.toJSON(baseResponse);
		System.out.println(json.toString());
	}*/
	
	/*@Test
	public void test11() throws BusinessException {
		InsertDistributionMessage i = new InsertDistributionMessage();
		i.setId((long) 28);
		i.setApply_amount(20000.00);
		i.setCommission(17.00);
		i.setBuy_discount(10.00);
		i.setFid((long) 9);
		i.setIs_sale(1);
		i.setName("test2");
		i.setRecommend_distribution_commission(30.00);
		i.setAmount("9");
		distributionIdentityController.update(i);
	}*/
	
	/*@Test
	public void test10() throws BusinessException {
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
