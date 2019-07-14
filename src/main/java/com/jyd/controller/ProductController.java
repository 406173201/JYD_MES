package com.jyd.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyd.common.JsonData;
import com.jyd.param.MesProductVo;
import com.jyd.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	private String FPath="product/";
	
	@Resource
	private ProductService mesProductService;
	
	//转向productinsert页面
	@RequestMapping("/productinsert.page")
	public String productInsertPage() {
		return FPath+"productinsert";
	}
	
	@RequestMapping("/insert.json")
	public String InsertFrom(MesProductVo mesProductVo) {
		System.out.println("mesProductVo-------->"+mesProductVo);
		mesProductService.insertFrom(mesProductVo);
		if(null==mesProductVo.getCounts()||mesProductVo.getCounts()==0) {
			return FPath+"productinsert";
		}
		return "order/order";
	}
}
