package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.Article;
import com.shop.model.dto.ArticleModel;
import com.shop.model.po.IdeaArticle;
import com.shop.util.MyMapper;

public interface IdeaArticleMapper extends MyMapper<IdeaArticle> {

	List<Article> listInfo(@Param("catId") Long catId, @Param("title") String title);
	
	List<ArticleModel> listAllInfo();
}
