package com.jyd.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyd.beans.PageQuery;
import com.jyd.beans.PageResult;
import com.jyd.common.JsonData;
import com.jyd.model.MesOrder;
import com.jyd.model.MesPlan;
import com.jyd.param.MesPlanVo;
import com.jyd.param.SearchPlanParam;
import com.jyd.service.PlanService;

@Controller
@RequestMapping("/plan")
public class PlanController {
	
	private String FPath="plan/";
	
	@Resource
	PlanService planService;
	
	
	@RequestMapping("planStarted.page")
	public String planStartedPage() {
		return FPath+"planStarted";
	}
	
	@RequestMapping("/plan.page")
	public String planPage() {
		return FPath+"plan";
	}
	
	@RequestMapping("/plan.json")
	@ResponseBody
	public JsonData planAjax(PageQuery page, SearchPlanParam param) {
		PageResult<MesPlan> planPage=(PageResult<MesPlan>) planService.searchPageList(param,page);
		return JsonData.success(planPage);
	}
	
	@RequestMapping("/planBatchStart.json")
	@ResponseBody
	public JsonData planBatchStartAjax(String ids) {
		planService.planBatchStartAjax(ids);
		return JsonData.success();
	}
	
	//uptate
	@RequestMapping("/update.json")
	@ResponseBody
	public JsonData updateAjax(MesPlanVo mesPlanVo) {
		planService.updateAjax(mesPlanVo);
		return JsonData.success();
	}
}
