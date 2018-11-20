package com.shop.mapper;

import com.shop.model.po.IdeaShopTransaction;
import com.shop.util.MyMapper;

public interface IdeaShopTransactionMapper extends MyMapper<IdeaShopTransaction>{
	
	IdeaShopTransaction getInfo();
}
