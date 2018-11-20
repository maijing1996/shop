package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaArticleMapper;
import com.shop.model.dto.Article;
import com.shop.model.dto.ArticleModel;
import com.shop.model.po.IdeaArticle;
import com.shop.service.common.BaseService;

@Service
public class IdeaArticleService extends BaseService<IdeaArticle, IdeaArticleMapper> {

	@Override
	protected String getTableName() {
		return "idea_article";
	}

	/**
	 * 获得文章信息
	 * @param page
	 * @param size
	 * @param catId
	 * @param title
	 * @return
	 */
	public PageInfo<Article> listInfo(Integer page, Integer size, Long catId, String title) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<Article> list = mapper.listInfo(catId, title);
		PageInfo<Article> pageInfo = new PageInfo<Article>(list);
		return pageInfo;
	}
	
	/**
	 * 获得文章信息
	 * @return
	 */
	public List<ArticleModel> listAllInfo() {
		return mapper.listAllInfo();
	}
}
