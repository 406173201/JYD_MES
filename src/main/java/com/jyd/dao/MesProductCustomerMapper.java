package com.jyd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jyd.beans.PageQuery;
import com.jyd.dto.SearchProductDto;
import com.jyd.model.MesProduct;

public interface MesProductCustomerMapper {

	Long getProductCount();

	String getMaxId();

	String selectProductIdByintoDbId(@Param("intoDbId") String intoDbId);

	int countBySearchDto(@Param("dto") SearchProductDto dto);
	//product分页
	List<MesProduct> searchproductList(@Param("dto") SearchProductDto dto,@Param("page") PageQuery page);
	//倒库查询分页
	List<MesProduct> seacheProductComeList(@Param("dto") SearchProductDto dto,@Param("page") PageQuery page);
	//钢锭查询分页
	List<MesProduct> seacheProductIronList(@Param("dto") SearchProductDto dto,@Param("page") PageQuery page);

	void productBatchStartByid(@Param("arrayid") String[] arrayid);
	//绑定页面分页
	List<MesProduct> productBindSearchPage(@Param("dto") SearchProductDto dto,@Param("page") PageQuery page);

	
}
