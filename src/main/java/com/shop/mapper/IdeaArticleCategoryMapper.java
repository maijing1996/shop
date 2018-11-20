package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.ArticleCategory;
import com.shop.model.po.IdeaArticleCategory;
import com.shop.util.MyMapper;

public interface IdeaArticleCategoryMapper extends MyMapper<IdeaArticleCategory> {

	List<ArticleCategory> listInfo(@Param("id") Long id, @Param("parentId") Long parentId);
}
