package com.jyd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jyd.beans.PageQuery;
import com.jyd.dto.SearchPlanDto;
import com.jyd.model.MesPlan;

public interface MesPlanCustomerMapper {

	int countBySearchPlanDto(@Param("dto") SearchPlanDto dto);

	List<MesPlan> getPageListBySearchDto(@Param("dto") SearchPlanDto dto, @Param("page") PageQuery page);

}
