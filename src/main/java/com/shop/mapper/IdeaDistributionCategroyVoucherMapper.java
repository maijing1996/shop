package com.shop.mapper;

import org.apache.ibatis.annotations.Param;

import com.shop.model.po.IdeaDistributionCategroyVoucher;
import com.shop.util.MyMapper;

public interface IdeaDistributionCategroyVoucherMapper extends MyMapper<IdeaDistributionCategroyVoucher> {

	int deleteByParentId(@Param("parentId") long parentId);
}
