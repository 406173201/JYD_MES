package com.jyd.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyd.beans.PageQuery;
import com.jyd.beans.PageResult;
import com.jyd.common.JsonData;
import com.jyd.common.SameUrlData;
import com.jyd.exception.ParamException;
import com.jyd.exception.SysMineException;
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
	@SameUrlData//防止重复提交
	public String InsertFrom(MesProductVo mesProductVo) {
		//		System.out.println("mesProductVo-------->"+mesProductVo);
		if(Integer.parseInt(mesProductVo.getProductRealweight() )<Integer.parseInt( mesProductVo.getProductLeftweight())) {
			throw new ParamException("剩余重量可以大于投料重量吗？？？？？？？？？？？");
		}
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
		System.out.println("productVo--------"+productVo);
		if(Integer.parseInt(productVo.getProductRealweight()) < Integer.parseInt( productVo.getProductLeftweight())) {
			throw new ParamException("剩余重量可以大于投料重量吗？？？？？？？？？？？");
		}
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

	//绑定操作页面（DO BIND）分页
	@ResponseBody
	@RequestMapping("/productBindDoSearchPage.json")
	public JsonData productBindDoSearchPage(SearchProductParam param,PageQuery page) {
		PageResult<MesProduct> MesProductComeLsit=mesProductService.productBindDoSearchPage(param,page);
		return JsonData.success(MesProductComeLsit);
	}

	//解绑操作页面（Show UnBIND）分页
	@ResponseBody
	@RequestMapping("/productBindShowSearchPage.json")
	public JsonData productBindShowSearchPage(SearchProductParam param,PageQuery page) {
		System.err.println("------->"+page.toString());
		System.err.println("------->"+param.toString());
		PageResult<MesProduct> MesProductComeLsit=mesProductService.productBindShowSearchPage(param,page);
		return JsonData.success(MesProductComeLsit);
	}

	//绑定操作
	@ResponseBody
	@RequestMapping("/productBindKids.json")
	public JsonData productBindKids(MesProductVo mesProductVo) {//productBakweightP 父材料备份重量 
		//mesProductVo.pid为页面上将要绑定的父级id
		//productBakweight 为父级的备份重量
		mesProductService.productBindKids(mesProductVo);
		return JsonData.success();
	}

	
	//解绑操作
	@ResponseBody
	@RequestMapping("/productUnbindKids.json")
	public JsonData productUnbindKidsAjax(MesProductVo mesProductVo) {//productBakweightP 父材料备份重量 
		//mesProductVo.pid为页面上将要绑定的父级id
		//productBakweight 为父级的备份重量
		mesProductService.productUnbindKids(mesProductVo);
		return JsonData.success();
	}

}
