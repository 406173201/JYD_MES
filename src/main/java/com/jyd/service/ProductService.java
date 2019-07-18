package com.jyd.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.jyd.beans.PageQuery;
import com.jyd.beans.PageResult;
import com.jyd.dao.MesProductCustomerMapper;
import com.jyd.dao.MesProductMapper;
import com.jyd.dto.SearchProductDto;
import com.jyd.exception.ParamException;
import com.jyd.exception.SysMineException;
import com.jyd.model.MesProduct;
import com.jyd.param.MesProductVo;
import com.jyd.param.SearchProductParam;
import com.jyd.util.BeanValidator;

@Service
public class ProductService {

	@Resource
	private MesProductCustomerMapper mesProductCustomerMapper;

	@Resource
	private MesProductMapper mesProductMapper;

	@Resource
	private SqlSession sqlSession;


	// 一开始就定义一个id生成器
	private IdGenerator ig = new IdGenerator();

	//绑定页面分页
	public PageResult<MesProduct> productBindSearchPage(SearchProductParam param, PageQuery page) {
		BeanValidator.check(param);
		BeanValidator.check(page);
		SearchProductDto dto= new SearchProductDto();
		if(StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%"+param.getKeyword()+"%");
		}
		if(StringUtils.isNotBlank(param.getSearch_msource())) {
			dto.setSearch_msource(param.getSearch_msource());
		}

		int count =mesProductCustomerMapper.countBySearchDto(dto);
		if(count > 0) {
			List<MesProduct> mesProducts =mesProductCustomerMapper.productBindSearchPage(dto,page);
			return PageResult.<MesProduct>builder().total(count).data(mesProducts).build();
		}
		return PageResult.<MesProduct>builder().build();
	}

	//查看库房页面分页
	public PageResult<MesProduct> seacheProductComeList(SearchProductParam param, PageQuery page) {
		BeanValidator.check(param);
		BeanValidator.check(page);
		SearchProductDto dto= new SearchProductDto();
		if(StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%"+param.getKeyword()+"%");
		}
		if(StringUtils.isNotBlank(param.getSearch_msource())) {
			dto.setSearch_msource(param.getSearch_msource());
		}

		int count =mesProductCustomerMapper.countBySearchDto(dto);
		if(count > 0) {
			List<MesProduct> mesProducts =mesProductCustomerMapper.seacheProductComeList(dto,page);
//			for (MesProduct mesProduct : mesProducts) {
//				System.out.println(mesProduct.toString());
//			}
			return PageResult.<MesProduct>builder().total(count).data(mesProducts).build();
		}
		return PageResult.<MesProduct>builder().build();
	}

	//钢锭查询分页
	public PageResult<MesProduct> seacheproductIronList(SearchProductParam param, PageQuery page) {
		BeanValidator.check(param);
		BeanValidator.check(page);
		SearchProductDto dto= new SearchProductDto();
		if(StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%"+param.getKeyword()+"%");
		}
		if(StringUtils.isNotBlank(param.getSearch_msource())) {
			dto.setSearch_msource("钢锭");
		}
		if(StringUtils.isNotBlank(param.getSearch_status())) {
			dto.setSearch_status(Integer.parseInt(param.getSearch_status()));
		}
		int count =mesProductCustomerMapper.countBySearchDto(dto);
		if(count > 0) {
			List<MesProduct> mesProducts =mesProductCustomerMapper.seacheProductIronList(dto,page);
			return PageResult.<MesProduct>builder().total(count).data(mesProducts).build();
		}
		return PageResult.<MesProduct>builder().build();
	}


	//批量倒库页面分页
	public PageResult<MesProduct> seacheProductList(SearchProductParam param, PageQuery page) {
		//校验
		BeanValidator.check(param);
		BeanValidator.check(page);
		SearchProductDto dto= new SearchProductDto();
		if(StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%"+param.getKeyword()+"%");
		}
		if(StringUtils.isNotBlank(param.getSearch_msource())) {
			dto.setSearch_msource(param.getSearch_msource());
		}
		int count =mesProductCustomerMapper.countBySearchDto(dto);
		if(count > 0) {
			List<MesProduct> mesProducts =mesProductCustomerMapper.searchproductList(dto,page);
//			for (MesProduct mesProduct : mesProducts) {
//				System.out.println(mesProduct.toString());
//			}
			return PageResult.<MesProduct>builder().total(count).data(mesProducts).build();
		}
		return PageResult.<MesProduct>builder().build();
	}

	public void updateProduct(MesProductVo productVo) {
		BeanValidator.check(productVo);
		//添加前进行查询
		MesProduct before=mesProductMapper.selectByPrimaryKey(productVo.getId());
		Preconditions.checkNotNull(before, "待更新的材料不存在");
		if(before.getProductBakweight()!=before.getProductLeftweight()) {
			throw new SysMineException("存在绑定关系");
		}
		//vo -- do
		try {
			MesProduct updateProduct=MesProduct.builder()
					.id(productVo.getId())//id
					.productId(productVo.getProductId())//材料自编号
					.productMaterialname(productVo.getProductMaterialname())//材料名称
					.productMaterialsource(productVo.getProductMaterialsource())//材料来源
					.productTargetweight(Float.valueOf(productVo.getProductTargetweight()))//工艺重量
					.productRealweight(Float.valueOf(productVo.getProductRealweight()))//投料重量
					.productLeftweight(Float.valueOf(productVo.getProductLeftweight()))//剩余重量
					.productHeatid(productVo.getProductHeatid())//炉号
					.productImgid(productVo.getProductImgid())//图号
					.productIrontype(productVo.getProductIrontype())//钢锭类别
					.productIrontypeweight(Float.valueOf(productVo.getProductIrontypeweight()))//锭型
					.productRemark(productVo.getProductRemark())//材料备注
					.build();
			updateProduct.setProductOperator("user01");
			updateProduct.setProductOperateIp("127.0.0.1");
			updateProduct.setProductOperateTime(new Date());

			mesProductMapper.updateByPrimaryKeySelective(updateProduct);
		} catch (Exception e) {
			throw new SysMineException("更改过程有问题");
		}
	}

	//批量添加
	public void insertFrom(MesProductVo mesProductVo) {
		//校验mesProductVo
		BeanValidator.check(mesProductVo);
		//获取添加的次数
		Integer counts=mesProductVo.getCounts();
		// zx180001 zx180002
		List<String> ids=createProductIdsDefault(Long.valueOf(counts));
		System.out.println("ids----------->"+ids);
		//sql的批量处理
		MesProductMapper mesProducBatchMapper=sqlSession.getMapper(MesProductMapper.class);
		for(String id:ids) {
			try {
				//vo---po
				MesProduct mesProduct=MesProduct.builder().productId(id)//id
						.productImgid(mesProductVo.getProductImgid())//图号
						.productTargetweight(Float.parseFloat(mesProductVo.getProductTargetweight()))//工艺重量
						.productRealweight(Float.parseFloat(mesProductVo.getProductRealweight()))//投料重量
						.productLeftweight(Float.parseFloat(mesProductVo.getProductLeftweight()))//剩余重量
						.productBakweight(Float.parseFloat(mesProductVo.getProductLeftweight()))//备份剩余重量
						.productIrontype(mesProductVo.getProductIrontype())//钢锭类别
						.productIrontypeweight(Float.parseFloat(mesProductVo.getProductIrontypeweight()))//钢锭锭型
						.productStatus(Integer.parseInt(mesProductVo.getProductStatus()))//状态
						.productMaterialsource(mesProductVo.getProductMaterialsource())//材料来源
						.productMaterialname(mesProductVo.getProductMaterialname())//材料名称
						.productRemark(mesProductVo.getProductRemark())//备注
						.productHeatid(mesProductVo.getProductHeatid())//炉号
						.build();
				mesProduct.setProductOperator("user01");
				mesProduct.setProductOperateIp("127.0.0.1");
				mesProduct.setProductOperateTime(new Date());

				//批量添加
				mesProducBatchMapper.insert(mesProduct);
			} catch (Exception e) {
				throw new SysMineException("创建过程有问题");
			}

		}

	}

	//product.jsp的批量启动
	public void productBatchStart(String ids) {
		System.out.println(ids);
		if(ids!=null && ids.length()>0) {
			String[] Arrayid= ids.split("&");
			mesProductCustomerMapper.productBatchStartByid(Arrayid);
		}
	}

	//绑定操作（do）页面分页
	public PageResult<MesProduct> productBindDoSearchPage(SearchProductParam param, PageQuery page) {
		BeanValidator.check(param);
		BeanValidator.check(page);
		SearchProductDto dto= new SearchProductDto();
		if(StringUtils.isNotBlank(param.getSearch_msource())) {
			dto.setSearch_msource(param.getSearch_msource());
		}

		int count =mesProductCustomerMapper.countBySearchweight(dto);
		if(count > 0) {
			List<MesProduct> mesProducts =mesProductCustomerMapper.SearchByweight(dto,page);
			return PageResult.<MesProduct>builder().total(count).data(mesProducts).build();
		}
		return PageResult.<MesProduct>builder().build();
	}

	//解绑操作页面（Show UnBIND）分页
	public PageResult<MesProduct> productBindShowSearchPage(SearchProductParam param, PageQuery page) {
		BeanValidator.check(param);
		BeanValidator.check(page);
		SearchProductDto dto= new SearchProductDto();
		if(StringUtils.isNotBlank(param.getPId())) {
			dto.setPId(Integer.parseInt(param.getPId()));
		}
		if(StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%"+param.getKeyword()+"%");
		}
		if(StringUtils.isNotBlank(param.getSearch_msource())) {
			dto.setSearch_msource(param.getSearch_msource());
		}
		System.out.println(dto.toString());
		List<MesProduct> mesProducts =mesProductCustomerMapper.countBindKidsByPid(dto,new PageQuery(1,999999));
		if(mesProducts.size()>0) {
			return PageResult.<MesProduct>builder().total(mesProducts.size()).data(mesProducts).build();
		}
		return PageResult.<MesProduct>builder().build();
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//绑定操作
	public void productBindKids(MesProductVo mesProductVo) {
		//		BeanValidator.check(mesProductVo);
		MesProduct pDto=new MesProduct();
		MesProduct cDto=new MesProduct();
		int pid=0;
		Float pBakweightP=0f;
		Float cproductTargetweight=0f;
		if(mesProductVo.getPId()!=0) {
			pid=mesProductVo.getPId();
		}
		if(StringUtils.isNotBlank(mesProductVo.getProductBakweight())) {
			pBakweightP=Float.parseFloat(mesProductVo.getProductBakweight());
		}
		if(StringUtils.isNotBlank(mesProductVo.getProductTargetweight())) {
			cproductTargetweight=Float.parseFloat(mesProductVo.getProductTargetweight());
		}

		if(pBakweightP-cproductTargetweight < 0) {
			throw new SysMineException("要不够用了，不能在绑定啦！！！！！");
		}
		////////////////////////////
		//更新父级材料信息
		pDto.setId(pid);
		pDto.setProductBakweight(pBakweightP-cproductTargetweight);
		mesProductMapper.updateByPrimaryKeySelective(pDto);
		////////////////////////
		//更新子材料信息
		cDto.setId(mesProductVo.getId());
		cDto.setProductBakweight(cproductTargetweight);
		cDto.setpId(pid);
		cDto.setProductStatus(1);
		mesProductMapper.updateByPrimaryKeySelective(cDto);

	}


	//解绑方法
	public void productUnbindKids(MesProductVo mesProductVo) {
		MesProduct pDto=new MesProduct();
		MesProduct cDto=new MesProduct();
		int pid=0;
		Float pBakweightP=0f;
		Float cproductTargetweight=0f;
		if(mesProductVo.getPId()!=0) {
			pid=mesProductVo.getPId();
		}
		if(StringUtils.isNotBlank(mesProductVo.getProductBakweight())) {
			pBakweightP=Float.parseFloat(mesProductVo.getProductBakweight());
		}
		if(StringUtils.isNotBlank(mesProductVo.getProductTargetweight())) {
			cproductTargetweight=Float.parseFloat(mesProductVo.getProductTargetweight());
		}
		////////////////////////////
		//更新父级材料信息
		pDto.setId(pid);
		pDto.setProductBakweight(pBakweightP+cproductTargetweight);
		mesProductMapper.updateByPrimaryKeySelective(pDto);
		////////////////////////
		//更新子材料信息
		cDto.setId(mesProductVo.getId());
		cDto.setProductBakweight(new Float(0));
		cDto.setpId(null);
		cDto.setProductStatus(0);
		mesProductMapper.updateByPrimaryKeySelective(cDto);
		mesProductCustomerMapper.updateKidsToNoPid(cDto.getId());
	}


	// 获取数据库所有的数量
	public Long getProductCount() {
		return mesProductCustomerMapper.getProductCount();
	}


	// 获取id集合
	public List<String> createProductIdsDefault(Long ocounts) {
		if (ig == null) {
			ig = new IdGenerator();
		}

		ig.setCurrentdbidscount(getProductCount());
		List<String> list = ig.initIds(ocounts);
		ig.clear();
		return list;
	}




	///////////////////////////////////////////////////////////////////////////////////////////////
	// 1 默认生成代码
	// 2 手工生成代码
	// id生成器
	class IdGenerator{
		// 数量起始位置
		private Long currentdbidscount;
		private List<String> ids = new ArrayList<String>();
		private String idpre;
		private String idafter;

		public IdGenerator() {
		}

		public Long getCurrentdbidscount() {
			return currentdbidscount;
		}

		public void setCurrentdbidscount(Long currentdbidscount) {
			this.currentdbidscount = currentdbidscount;
			if (null == this.ids) {
				this.ids = new ArrayList<String>();
			}
		}

		public List<String> getIds() {
			return ids;
		}

		public void setIds(List<String> ids) {
			this.ids = ids;
		}

		public String getIdpre() {
			return idpre;
		}

		public void setIdpre(String idpre) {
			this.idpre = idpre;
		}


		public String getIdafter() {
			return idafter;
		}

		public void setIdafter(String idafter) {
			this.idafter = idafter;
		}

		public List<String> initIds(Long ocounts) {
			System.err.println("ocounts: "+ocounts);
			//获取最大的数据库中最大的product-id
			String dbMaxId=mesProductCustomerMapper.getMaxId();
			System.err.println("dbMaxId: "+dbMaxId);

			Integer dbMaxIdInt =0;
			if(dbMaxId!=null) {
				//处理了 dbMaxId "ZX_P0000xx"---"xx"
				String dbMaxIdReplace_ZXP=dbMaxId.replace("ZX_P","");
				System.err.println("dbMaxIdReplace_ZXP"+dbMaxIdReplace_ZXP);
				String dbMaxIDReplace_0=dbMaxIdReplace_ZXP.replace("0","");
				System.err.println("dbMaxIDReplace_0---------->"+dbMaxIDReplace_0);
				//String --- int
				dbMaxIdInt=Integer.parseInt(dbMaxIDReplace_0);
				setCurrentdbidscount(new Long(0));
				//根据最大的product_id进行循环，判断出空出的product_id值，并添加入ids
				for(int i=1;i<=dbMaxIdInt;i++) {
					String intoDbId=getIdPre() +  getIdAfter(i);
					String dbProduct_id=mesProductCustomerMapper.selectProductIdByintoDbId("\'"+intoDbId+"\'");
					if(dbProduct_id==null) {
						this.ids.add(intoDbId);
						continue;
					}else if(dbProduct_id!=null||i!=ocounts) {
						continue;
					}else if(i==ocounts){// 添加的条数小于数据库中最大的id值
						System.err.println("i==ocounts");
						setCurrentdbidscount((long)(dbMaxIdInt));
						break;
					}
				}
			}
			// 添加的条数大与数据库中最大的id值
			//取出目前ids中的长度
			int idsSize=this.ids.size();
			setCurrentdbidscount((long)(dbMaxIdInt+1));//从数据库中最大的id值后一位开始   ？+1
			//正常添加	
			for (int i = 0; i < (ocounts-idsSize); i++) {
				this.ids.add(getIdPre() +  getIdAfter(i));
			}
			return this.ids;
		}

		private String getIdAfter(int addcount) {
			// 系统默认生成6位 ZX_P000001
			int goallength = 6;
			// 获取数据库Product的总数量+1+循环次数(addcount)
			int count = this.currentdbidscount.intValue() + 1 + addcount;
			StringBuilder sBuilder = new StringBuilder("");
			// 计算与6位数的差值
			int length = goallength - new String(count + "").length();
			for (int i = 0; i < length; i++) {
				sBuilder.append("0");
			}
			sBuilder.append(count + "");
			return sBuilder.toString();
		}

		private String getIdPre() {
			// idpre==null?this.idpre="ZX":this.idpre=idpre;
			this.idpre = "ZX_P";
			return this.idpre;
		}

		public void clear() {
			this.ids = null;
		}

		@Override
		public String toString() {
			return "IdGenerator [ids=" + ids + "]";
		}
	}





}
