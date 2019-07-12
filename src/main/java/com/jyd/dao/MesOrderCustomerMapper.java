package com.jyd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jyd.beans.PageQuery;
import com.jyd.dto.SearchOrderDto;
import com.jyd.model.MesOrder;

public interface MesOrderCustomerMapper {

	Long getOrderCount();

	int countBySearchDto(@Param("dto") SearchOrderDto dto);

	List<MesOrder> getPageListBySearchDto(@Param("dto") SearchOrderDto dto,@Param("page") PageQuery page);

	void batchStart(@Param("ids") String[] idArray);



}
