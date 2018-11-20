package com.shop.controller.manager.home;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.exception.BusinessException;
import com.shop.model.common.BaseResponse;
import com.shop.model.po.IdeaConfig;
import com.shop.model.po.IdeaGoods;
import com.shop.model.po.IdeaGoodsCategory;
import com.shop.model.po.IdeaIndex;
import com.shop.model.po.IdeaPersonCategory;
import com.shop.service.IdeaConfigService;
import com.shop.service.IdeaGoodsCategoryService;
import com.shop.service.IdeaGoodsService;
import com.shop.service.IdeaIndexService;
import com.shop.service.IdeaPersonCategoryService;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/home/page")
public class HomeController {

	@Autowired
	private IdeaIndexService ideaIndexService;
	@Autowired
	private IdeaGoodsService ideaGoodsService;
	@Autowired
	private IdeaConfigService ideaConfigService;
	@Autowired
	private IdeaGoodsCategoryService ideaGoodsCategoryService;
	@Autowired
	private IdeaPersonCategoryService ideaPersonCategoryService;
	
	/**
	 * 获得各模块的初始化参数
	 * 模块类型  1专题模块  2商品类型模块  3文章模块 4XX模块  5人气模块英文标题
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/index", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String index(@RequestBody Map<String, String> map) {
		if("2".equals(map.get("type"))) {
			IdeaIndex ideaIndex = new IdeaIndex();
			ideaIndex.setType(StringUtil.strToInt(map.get("type")));
			List<IdeaIndex> list = ideaIndexService.getAll(ideaIndex, "id DESC");
			for(IdeaIndex ideaIndex2 : list) {
				if(ideaIndex2.getB_type() == 1) {
					IdeaGoodsCategory ideaGoodsCategory = ideaGoodsCategoryService.getById(StringUtil.strToLong(ideaIndex2.getValue()));
					ideaIndex2.setValue(ideaGoodsCategory.getTitle());
				} else {
					IdeaPersonCategory ideaPersonCategory = ideaPersonCategoryService.getById(StringUtil.strToLong(ideaIndex2.getValue()));
					ideaIndex2.setValue(ideaPersonCategory.getTitle());
				}
			}
			return JSONObject.toJSONString(list);
		} else if("4".equals(map.get("type"))) {
			IdeaIndex ideaIndex = new IdeaIndex();
			ideaIndex.setType(StringUtil.strToInt(map.get("type")));
			List<IdeaIndex> list = ideaIndexService.getAll(ideaIndex, "id DESC");
			for(IdeaIndex ideaIndex2 : list) {
				if(ideaIndex2.getB_type() == 1) {
					IdeaGoodsCategory ideaGoodsCategory = ideaGoodsCategoryService.getById(StringUtil.strToLong(ideaIndex2.getValue()));
					if(ideaGoodsCategory != null) {
						ideaIndex2.setValue("商品分类："+ideaGoodsCategory.getTitle());
					}
				} else if(ideaIndex2.getB_type() == 2) {
					IdeaPersonCategory ideaPersonCategory = ideaPersonCategoryService.getById(StringUtil.strToLong(ideaIndex2.getValue()));
					if(ideaPersonCategory != null) {
						ideaIndex2.setValue("人群分类："+ideaPersonCategory.getTitle());
					}
				}
			}
			return JSONObject.toJSONString(list);
		} else {
			IdeaIndex ideaIndex = new IdeaIndex();
			ideaIndex.setType(StringUtil.strToInt(map.get("type")));
			List<IdeaIndex> list = ideaIndexService.getAll(ideaIndex, "id DESC");
			return JSONObject.toJSONString(list);
			
		}
	}
	
	/**
	 * 获得各模块的初始化参数
	 * type 1 地址， 3优惠券主图，4优惠券副图，5.6.7分享图，
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/config", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public String config(@RequestBody Map<String, String> map) {
		if(map != null && map.get("type") != null) {
			if("1".equals(map.get("type"))) {
				List<IdeaConfig> list = ideaConfigService.getByIds("1,2");
				return JSONObject.toJSONString(list);
			} else if("3".equals(map.get("type"))) {
				IdeaConfig ideaConfig = ideaConfigService.getById(3L);
				return JSONObject.toJSONString(ideaConfig);
			} else if("4".equals(map.get("type"))) {
				IdeaConfig ideaConfig = ideaConfigService.getById(4L);
				return JSONObject.toJSONString(ideaConfig);
			} else if("5".equals(map.get("type"))) {
				IdeaConfig ideaConfig = ideaConfigService.getById(5L);
				return JSONObject.toJSONString(ideaConfig);
			} else if("6".equals(map.get("type"))) {
				IdeaConfig ideaConfig = ideaConfigService.getById(6L);
				return JSONObject.toJSONString(ideaConfig);
			} else if("7".equals(map.get("type"))) {
				IdeaConfig ideaConfig = ideaConfigService.getById(7L);
				return JSONObject.toJSONString(ideaConfig);
			} else {
				return "0";
			}
		} else {
			return "0";
		}
	}
	
	/**
	 * 首页删除
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/index/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse indexDelete(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && map.get("id") != null) {
			ideaIndexService.deleteById(map.get("id"));
			return baseResponse.setValue("删除成功", null);
		} else {
			return baseResponse.setError(404, "参数有误");
		}
	}
	
	/**
	 * 首页添加
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/index/insert", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse indexInsert(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && map.get("id") == null) {
			IdeaIndex ideaIndex = new IdeaIndex();
			if(map.get("pic_type") != null && StringUtil.strToInt(map.get("pic_type")) != null) {
				ideaIndex.setPic_type(StringUtil.strToInt(map.get("pic_type")));
			}
			if(map.get("title") != null && !"".equals(map.get("title").trim())) {
				ideaIndex.setTitle(map.get("title").trim());
			}
			if(map.get("url") != null && !"".equals(map.get("url").trim())) {
				ideaIndex.setUrl(map.get("url").trim());
			}
			if(map.get("pic") != null && !"".equals(map.get("pic").trim())) {
				ideaIndex.setPic(map.get("pic").trim());
			}
			if(map.get("type") != null && StringUtil.strToInt(map.get("type")) != null) {
				ideaIndex.setType(StringUtil.strToInt(map.get("type")));
			}
			if(map.get("value") != null && !"".equals(map.get("value").trim())) {
				ideaIndex.setValue(map.get("value").trim());
			}
			if(map.get("sort") != null && StringUtil.strToInt(map.get("sort")) != null) {
				ideaIndex.setSort(StringUtil.strToInt(map.get("sort")));
			}
			if(map.get("jump_type") != null && StringUtil.strToInt(map.get("jump_type")) != null) {
				ideaIndex.setJump_type(StringUtil.strToInt(map.get("jump_type")));
			}
			if(map.get("b_type") != null && StringUtil.strToInt(map.get("b_type")) != null) {
				ideaIndex.setB_type(StringUtil.strToInt(map.get("b_type")));
			}
			ideaIndexService.save(ideaIndex);
			
			//
			if(map.get("goodsIdss") != null && !"".equals(map.get("goodsIdss").trim())) {
				List<IdeaGoods> list = ideaGoodsService.getByIds(map.get("goodsIdss"));
				
				if(list != null && !list.isEmpty()) {
					if("1".equals(map.get("b_type"))) {
						List<IdeaGoods> listc = ideaGoodsService.getCategroy(StringUtil.strToLong(map.get("value")));
						for(IdeaGoods ideaGoods : listc) {
							for(IdeaGoods ideaGoods2 : list) {
								if(ideaGoods2.getId() == ideaGoods.getId()) {
									ideaGoods.setIs_index(1);
									break;
								} else {
									ideaGoods.setIs_index(0);
								}
							}
							ideaGoodsService.update(ideaGoods);
						}
					} else if("2".equals(map.get("b_type"))) {
						List<IdeaGoods> listp = ideaGoodsService.getPerson(StringUtil.strToLong(map.get("value")));
						for(IdeaGoods ideaGoods : listp) {
							for(IdeaGoods ideaGoods2 : list) {
								if(ideaGoods2.getId() == ideaGoods.getId()) {
									ideaGoods.setIs_index(1);
									break;
								} else {
									ideaGoods.setIs_index(0);
								}
							}
							ideaGoodsService.update(ideaGoods);
						}
					}
				}
			}
			return baseResponse.setValue("添加成功", null);
		} else {
			return baseResponse.setError(404, "参数有误");
		}
	}
	
	/**
	 * 获得信息
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/index/id", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse getIndexInfoId(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && map.get("id") != null) {
			IdeaIndex ideaIndex = ideaIndexService.getById(map.get("id"));
			return baseResponse.setValue("删除成功", ideaIndex);
		} else {
			return baseResponse.setError(404, "参数有误");
		}
	}
	
	/**
	 * 首页更新
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/index/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse indexUpdate(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && map.get("id") != null) {
			IdeaIndex ideaIndex = new IdeaIndex();
			ideaIndex.setId(StringUtil.strToLong(map.get("id")));
			if(map.get("pic_type") != null && StringUtil.strToInt(map.get("pic_type")) != null) {
				ideaIndex.setPic_type(StringUtil.strToInt(map.get("pic_type")));
			}
			if(map.get("title") != null && !"".equals(map.get("title").trim())) {
				ideaIndex.setTitle(map.get("title").trim());
			}
			if(map.get("url") != null && !"".equals(map.get("url").trim())) {
				ideaIndex.setUrl(map.get("url").trim());
			}
			if(map.get("pic") != null && !"".equals(map.get("pic").trim())) {
				ideaIndex.setPic(map.get("pic").trim());
			}
			if(map.get("type") != null && StringUtil.strToInt(map.get("type")) != null) {
				ideaIndex.setType(StringUtil.strToInt(map.get("type")));
			}
			if(map.get("value") != null && !"".equals(map.get("value").trim())) {
				ideaIndex.setValue(map.get("value").trim());
			}
			if(map.get("sort") != null && StringUtil.strToInt(map.get("sort")) != null) {
				ideaIndex.setSort(StringUtil.strToInt(map.get("sort")));
			}
			if(map.get("jump_type") != null && StringUtil.strToInt(map.get("jump_type")) != null) {
				ideaIndex.setJump_type(StringUtil.strToInt(map.get("jump_type")));
			}
			if(map.get("b_type") != null && StringUtil.strToInt(map.get("b_type")) != null) {
				ideaIndex.setB_type(StringUtil.strToInt(map.get("b_type")));
			}
			
			ideaIndexService.update(ideaIndex);
			
			//
			if(map.get("goodsIdss") != null && !"".equals(map.get("goodsIdss").trim())) {
				List<IdeaGoods> list = ideaGoodsService.getByIds(map.get("goodsIdss"));
				
				if(list != null && !list.isEmpty()) {
					if("1".equals(map.get("b_type"))) {
						List<IdeaGoods> listc = ideaGoodsService.getCategroy(StringUtil.strToLong(map.get("value")));
						for(IdeaGoods ideaGoods : listc) {
							for(IdeaGoods ideaGoods2 : list) {
								if(ideaGoods2.getId() == ideaGoods.getId()) {
									ideaGoods.setIs_index(1);
									break;
								} else {
									ideaGoods.setIs_index(0);
								}
							}
							ideaGoodsService.update(ideaGoods);
						}
					} else if("2".equals(map.get("b_type"))) {
						List<IdeaGoods> listp = ideaGoodsService.getPerson(StringUtil.strToLong(map.get("value")));
						for(IdeaGoods ideaGoods : listp) {
							for(IdeaGoods ideaGoods2 : list) {
								if(ideaGoods2.getId() == ideaGoods.getId()) {
									ideaGoods.setIs_index(1);
									break;
								} else {
									ideaGoods.setIs_index(0);
								}
							}
							ideaGoodsService.update(ideaGoods);
						}
					}
				}
			}
			return baseResponse.setValue("修改成功", null);
		} else {
			return baseResponse.setError(404, "参数有误");
		}
	}
	
	/**
	 * 配置更新
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/config/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse configUpdate(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && map.get("id") != null) {
			IdeaConfig ideaConfig = new IdeaConfig();
			ideaConfig.setId(StringUtil.strToLong(map.get("id")));
			if(map.get("pic") != null && !"".equals(map.get("pic").trim())) {
				ideaConfig.setValue(map.get("pic"));
			}
			
			ideaConfigService.update(ideaConfig);
			return baseResponse.setValue("修改成功", null);
		} else {
			return baseResponse.setError(404, "参数有误");
		}
	}
	
	/**
	 * 修改发货地址
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/config/address", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse addressUpdate(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && map.get("address_1") != null && map.get("address_2") != null) {
			IdeaConfig ideaConfig = new IdeaConfig();
			ideaConfig.setId(1L);
			ideaConfig.setValue(map.get("address_1").trim());
			ideaConfigService.update(ideaConfig);
			
			IdeaConfig ideaConfig2 = new IdeaConfig();
			ideaConfig2.setId(2L);
			ideaConfig2.setValue(map.get("address_2").trim());
			ideaConfigService.update(ideaConfig2);
			return baseResponse.setValue("修改成功", null);
		} else {
			return baseResponse.setError(404, "参数有误");
		}
	}
}
