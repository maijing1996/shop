package com.shop.controller.manager.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.shop.exception.BusinessException;
import com.shop.model.common.BaseResponse;
import com.shop.model.common.LayuiResponse;
import com.shop.model.dto.Comment;
import com.shop.model.po.IdeaGoodsComment;
import com.shop.service.IdeaGoodsCommentService;
import com.shop.util.RegexUtil;
import com.shop.util.StringUtil;

@Controller
@ResponseBody
@RequestMapping("/manager/order/evaluate")
public class ShopEvaluateController {
	
	@Autowired
	private IdeaGoodsCommentService ideaGoodsCommentService;
	
	/**
	 * 查询商品评论信息
	 * @param uid
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public LayuiResponse listInfo(@RequestBody Map<String, String> map) {
		LayuiResponse layuiResponse = new LayuiResponse();
		PageInfo<Comment> pageInfo = ideaGoodsCommentService.listInfo(StringUtil.strToInt(map.get("page")),
				StringUtil.strToInt(map.get("size")), map.get("userName"));
		
		if(pageInfo.getTotal() != 0) {
			return layuiResponse.setSuccess(null, pageInfo.getList(), pageInfo.getTotal());
		} else {
			return layuiResponse.setError(404, "没有数据");
		}
	}
	
	/**
	 * 根据id删除商品评论
	 * @param ids
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deleteById(@RequestBody Map<String, Long> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("id") != null) {
			boolean result = ideaGoodsCommentService.deleteById(map.get("id"));
			if(result) {
				return baseResponse.setValue("删除评论成功！", result);
			}else {
				return baseResponse.setError(500, "删除评论失败！");
			}
		}else {
			return baseResponse.setError(404, "删除对象不明确");
		}
	}
	
	/**
	 * 批量删除商品评论数据
	 * @param ids
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/deletes", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse deleteByIds(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map.get("ids") != null && RegexUtil.matches(RegexUtil.IDS_REGEX, map.get("ids"))) {
			boolean result = ideaGoodsCommentService.deleteByIds(map.get("ids"));
			if(result) {
				return baseResponse.setValue("删除评论成功！", result);
			}else {
				return baseResponse.setError(500, "删除评论失败！");
			}
		}else {
			return baseResponse.setError(404, "删除对象不明确");
		}
	}
	
	/**
	 * 商品评论修改
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	public BaseResponse update(@RequestBody Map<String, String> map) throws BusinessException {
		BaseResponse baseResponse = new BaseResponse();
		if(map != null && map.get("id") != null) {
			IdeaGoodsComment ideaGoodsComment = new IdeaGoodsComment();
			ideaGoodsComment.setId(StringUtil.strToLong(map.get("id")));
			ideaGoodsComment.setIs_show(StringUtil.strToInt(map.get("is_show")));
			
			ideaGoodsCommentService.update(ideaGoodsComment);
			return baseResponse.setValue("修改成功！", null);
		}else {
			return baseResponse.setError(404, "删除对象不明确");
		}
	}
}
