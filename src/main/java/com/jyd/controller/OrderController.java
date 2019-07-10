package com.jyd.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyd.beans.PageQuery;
import com.jyd.beans.PageResult;
import com.jyd.common.JsonData;
import com.jyd.model.MesOrder;
import com.jyd.param.MesOrderVo;
import com.jyd.param.SearchOrderParam;
import com.jyd.service.OrderService;


@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Resource
	private OrderService orderService;
	
	private String FPath="order/";
	
	@RequestMapping("/order.page")
    public String orderPage() {
        return FPath+"order";   
    }
	
	@RequestMapping("/orderBatch.page")
    public String orderBatchPage() {
        return FPath+"orderBatch";   
    }
	
	@RequestMapping("/order.json")
	@ResponseBody
	public JsonData orderJson(PageQuery page,SearchOrderParam param) {
		PageResult<MesOrder> pr=(PageResult<MesOrder>) orderService.searchPageList(param,page);
		return JsonData.success(pr);
	}
	
	//add date to table named order
	@RequestMapping("/insert.json")
	@ResponseBody
	public JsonData insertAjax(MesOrderVo orderVo) {
		System.out.println(orderVo.toString());
		   orderService.insertOrders(orderVo);
		return JsonData.success();
	}
	
	//updata
	@RequestMapping("/update.json")
	@ResponseBody
	public JsonData updataAjax(MesOrderVo orderVo) {
		System.out.println(orderVo.toString());
		   orderService.updataOrders(orderVo);
		return JsonData.success();
	}
}
