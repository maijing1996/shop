package com.shop.mapper;

import java.util.List;

import com.shop.model.po.IdeaAdminRole;
import com.shop.util.MyMapper;

public interface IdeaAdminRoleMapper extends MyMapper<IdeaAdminRole> {

	List<IdeaAdminRole> listInfo();
}
