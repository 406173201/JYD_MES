package com.jyd.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.jyd.dao.MesProductCustomerMapper;
import com.jyd.dao.MesProductMapper;
import com.jyd.exception.SysMineException;
import com.jyd.model.MesProduct;
import com.jyd.param.MesProductVo;
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
						.build();

				mesProduct.setProductOperator("user01");
				mesProduct.setProductOperateIp("127.0.0.1");
				mesProduct.setProductOperateTime(new Date());


				//批量添加
				//				mesProducBatchMapper.insert(mesProduct);
			} catch (Exception e) {
				throw new SysMineException("创建过程有问题");
			}

		}

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
				System.out.println("setCurrentdbidscount------------------->"+this.currentdbidscount);
				//根据最大的product_id进行循环，判断出空出的product_id值，并添加入ids
				for(int i=1;i<=dbMaxIdInt;i++) {
					String intoDbId=getIdPre() +  getIdAfter(i);
					String dbProduct_id=mesProductCustomerMapper.selectProductIdByintoDbId("\'"+intoDbId+"\'");
					System.out.println("intoDbId---------->"+intoDbId);
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
			System.out.println(2);
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
