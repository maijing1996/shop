package com.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shop.model.dto.WxMenu;
import com.shop.model.po.IdeaWxMenu;
import com.shop.util.MyMapper;

public interface IdeaWxMenuMapper extends MyMapper<IdeaWxMenu> {

	List<WxMenu> listInfo(@Param("parentId") Long parentId);
}
