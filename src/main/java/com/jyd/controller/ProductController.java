package com.jyd.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyd.beans.PageQuery;
import com.jyd.beans.PageResult;
import com.jyd.common.JsonData;
import com.jyd.model.MesProduct;
import com.jyd.param.MesProductVo;
import com.jyd.param.SearchProductParam;
import com.jyd.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private String FPath="product/";

	@Resource
	private ProductService mesProductService;

	
	//转向材料绑定页面
	@RequestMapping("/productBindList.page")
	public String productBindList() {
		return FPath+"productBindList";
	}
	
	//转向/productIron页面
	@RequestMapping("/productIron.page")
	public String productIronPage() {
		return FPath+"productIron";
	}
	
	//转向productCome页面
	@RequestMapping("/productCome.page")
	public String productComePage() {
		return FPath+"productCome";
	}

	//转向productinsert页面
	@RequestMapping("/productinsert.page")
	public String productInsertPage() {
		return FPath+"productinsert";
	}

	//转向product页面
	@RequestMapping("/product.page")
	public String productPage() {
		return FPath+"product";
	}
	//批量添加添加product
	@RequestMapping("/insert.json")
	public String InsertFrom(MesProductVo mesProductVo) {
//		System.out.println("mesProductVo-------->"+mesProductVo);
		mesProductService.insertFrom(mesProductVo);
		if(null==mesProductVo.getCounts()||mesProductVo.getCounts()==0) {
			return FPath+"productinsert";
		}
		if("钢锭".equals(mesProductVo.getProductMaterialname()) ){
			return FPath+"productIron";
		}
		return FPath+"product";
	}
	
	
	
	//product分页
	@ResponseBody
	@RequestMapping("/product.json")
	public JsonData seacheProductList(SearchProductParam param,PageQuery page) {
		System.out.println(param.toString());
		PageResult<MesProduct> MesProductLsit=mesProductService.seacheProductList(param,page);
		return JsonData.success(MesProductLsit);
	}
	
	//更新product
	@ResponseBody
	@RequestMapping("/update.json")
	public JsonData updateProduct(MesProductVo productVo) {
		mesProductService.updateProduct(productVo);
		return JsonData.success();
	}
	
	//批量添加
	@ResponseBody
	@RequestMapping("/productBatchStart.json")
	public JsonData productBatchStart(String ids) {
		mesProductService.productBatchStart(ids);
		return JsonData.success();
	}
	
	//productCome页面分页
	@ResponseBody
	@RequestMapping("/productCome.json")
	public JsonData seacheProductComeList(SearchProductParam param,PageQuery page) {
		System.out.println(param.toString());
		PageResult<MesProduct> MesProductComeLsit=mesProductService.seacheProductComeList(param,page);
		return JsonData.success(MesProductComeLsit);
	}
	///productIron页面分页
	@ResponseBody
	@RequestMapping("/productIron.json")
	public JsonData seacheproductIronList(SearchProductParam param,PageQuery page) {
		System.out.println(param.toString());
		PageResult<MesProduct> MesProductComeLsit=mesProductService.seacheproductIronList(param,page);
		return JsonData.success(MesProductComeLsit);
	}
	//绑定页面分页
	@ResponseBody
	@RequestMapping("/productBindSearchPage.json")
	public JsonData productBindSearchPage(SearchProductParam param,PageQuery page) {
		System.out.println(param.toString());
		PageResult<MesProduct> MesProductComeLsit=mesProductService.productBindSearchPage(param,page);
		return JsonData.success(MesProductComeLsit);
	}
}
