package com.jyd.dao;

import org.apache.ibatis.annotations.Param;

public interface MesProductCustomerMapper {

	Long getProductCount();

	String getMaxId();

	String selectProductIdByintoDbId(@Param("intoDbId") String intoDbId);
	
}
