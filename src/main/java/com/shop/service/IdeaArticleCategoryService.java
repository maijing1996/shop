package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.IdeaArticleCategoryMapper;
import com.shop.model.dto.ArticleCategory;
import com.shop.model.po.IdeaArticleCategory;
import com.shop.service.common.BaseService;

@Service
public class IdeaArticleCategoryService extends BaseService<IdeaArticleCategory, IdeaArticleCategoryMapper> {

	@Override
	protected String getTableName() {
		return "idea_article_category";
	}

	/**
	 * 获取文章分类信息
	 * @param page
	 * @param size
	 * @param id
	 * @return
	 */
	public PageInfo<ArticleCategory> listInfo(Integer page, Integer size, Long id, Long parentId) {
		if(page != null && size != null) {
			PageHelper.startPage(page, size);
		} else {
			PageHelper.startPage(1, 20);
		}
		
		List<ArticleCategory> list = mapper.listInfo(id, parentId);
		PageInfo<ArticleCategory> pageInfo = new PageInfo<ArticleCategory>(list);
		return pageInfo;
	}
}
