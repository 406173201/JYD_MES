package com.jyd.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Service;

import com.jyd.exception.ParamException;
import com.google.common.base.Preconditions;
import com.jyd.beans.PageQuery;
import com.jyd.beans.PageResult;
import com.jyd.dao.MesOrderCustomerMapper;
import com.jyd.dao.MesOrderMapper;
import com.jyd.dto.SearchOrderDto;
import com.jyd.exception.SysMineException;
import com.jyd.model.MesOrder;
import com.jyd.param.MesOrderVo;
import com.jyd.param.SearchOrderParam;
import com.jyd.util.BeanValidator;
import com.jyd.util.MyStringUtils;

@Service
public class OrderService {
	
	@Resource
	private MesOrderCustomerMapper mesOrderCustomerMapper;
	
	@Resource
	private MesOrderMapper mesOrderMapper;
	
	@Resource
	private SqlSession sqlSession;
	
	@Resource
	private PlanService planService;
	
	// 一开始就定义一个id生成器
		private IdGenerator ig = new IdGenerator();

		//批量
		public void orderBatchStartAjax(String ids) {
			
			if(ids != null && ids.length() > 0) {
				//处理字符串，获取所有的id值并且存放到数组idArray中
				String[] idArray = ids.split("&");
				//批量处理
				//数据库中使用 update mes_order set order_status=1 where id in  [x1,x2,x3....]
				mesOrderCustomerMapper.batchStart(idArray);
				//批量启动订单
				planService.startPlansByOrderIds(idArray);
			}
			
		}
		
		
		//分页
		public Object searchPageList(SearchOrderParam param, PageQuery page) {
			//校验
			BeanValidator.check(param);
			BeanValidator.check(page);
			
			SearchOrderDto dto=new SearchOrderDto();
			if(StringUtils.isNotBlank(param.getKeyword())) {
				dto.setKeyword("%"+param.getKeyword()+"%");
			}
			if(StringUtils.isNotBlank(param.getSearch_status())) {
//				System.out.println(param.getSearch_status());
				dto.setSearch_status(Integer.parseInt(param.getSearch_status()));
//				System.out.println(dto.getSearch_status());
			}
			try {
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
				if(StringUtils.isNotBlank(param.getFromTime())) {
					dto.setFromTime(dateFormat.parse(param.getFromTime()));
				}
				if(StringUtils.isNotBlank(param.getToTime())) {
					dto.setToTime(dateFormat.parse(param.getToTime()));
				}
			} catch (Exception e) {
				throw new ParamException("传入的日期格式有问题，正确格式为：yyyy-MM-dd");
			}
			
			int count=mesOrderCustomerMapper.countBySearchDto(dto); 
			if(count>0) {
				List<MesOrder> orderList=mesOrderCustomerMapper.getPageListBySearchDto(dto, page);
				return PageResult.<MesOrder>builder().total(count).data(orderList).build();
			}
			return PageResult.<MesOrder>builder().build();
		}	
		
	public void insertOrders(MesOrderVo orderVo) {
		//校验数据
		BeanValidator.check(orderVo);
		//先判断是否是批量添加
		Integer counts=orderVo.getCount();
		// 根据counts的个数，生成需要添加的ids的数据集合
		// zx180001 zx180002
		List<String> ids=createOrderIdsDefault(Long.valueOf(counts));
		//sql的批量处理
		MesOrderMapper mesOrderBatchMapper=sqlSession.getMapper(MesOrderMapper.class);
		for(String id:ids) {
			try {
				//vo --- po
				MesOrder mesOrder=MesOrder.builder().orderId(id)
						.orderClientname(orderVo.getOrderClientname())//
						.orderProductname(orderVo.getOrderProductname()).orderContractid(orderVo.getOrderContractid())//
						.orderImgid(orderVo.getOrderImgid()).orderMaterialname(orderVo.getOrderMaterialname())
						.orderCometime(MyStringUtils.string2Date(orderVo.getComeTime(), null))//
						.orderCommittime(MyStringUtils.string2Date(orderVo.getCommitTime(), null))
						.orderInventorystatus(orderVo.getOrderInventorystatus()).orderStatus(orderVo.getOrderStatus())//
						.orderMaterialsource(orderVo.getOrderMaterialsource())
						.orderHurrystatus(orderVo.getOrderHurrystatus()).orderStatus(orderVo.getOrderStatus())
						.orderRemark(orderVo.getOrderRemark()).build();
				//设置登陆信息
				//
				mesOrder.setOrderOperator("tom");
				mesOrder.setOrderOperateIp("127.0.0.1");
				mesOrder.setOrderOperateTime(new Date());
				//批量添加未启动的订单
				//
				if(mesOrder.getOrderStatus() == 1) {
					planService.insertSelective(mesOrder);
				}
				mesOrderBatchMapper.insertSelective(mesOrder);
			} catch (Exception e) {
				throw new SysMineException("创建过程有问题");
			}
		}
		
	}
	// 获取数据库所有的数量
		public Long getOrderCount() {
			return mesOrderCustomerMapper.getOrderCount();
		}

	
	// 获取id集合
		public List<String> createOrderIdsDefault(Long ocounts) {
			if (ig == null) {
				ig = new IdGenerator();
			}

			ig.setCurrentdbidscount(getOrderCount());
			List<String> list = ig.initIds(ocounts);
			ig.clear();
			return list;
		}


	
		//uptate 
		public void updataOrders(MesOrderVo orderVo) {
			BeanValidator.check(orderVo);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//做查询判断是否存在
			MesOrder before = mesOrderMapper.selectByPrimaryKey(orderVo.getId());
			Preconditions.checkNotNull(before, "待更新的材料不存在");
			//vo -- po
			try {
				MesOrder after = MesOrder.builder().id(orderVo.getId())
						.orderClientname(orderVo.getOrderClientname())//
						.orderProductname(orderVo.getOrderProductname()).orderContractid(orderVo.getOrderContractid())//
						.orderImgid(orderVo.getOrderImgid()).orderMaterialname(orderVo.getOrderMaterialname())
						.orderCometime(MyStringUtils.string2Date(orderVo.getComeTime(), null))//
						.orderCommittime(MyStringUtils.string2Date(orderVo.getCommitTime(), null))
						.orderInventorystatus(orderVo.getOrderInventorystatus()).orderStatus(orderVo.getOrderStatus())//
						.orderMaterialsource(orderVo.getOrderMaterialsource())
						.orderHurrystatus(orderVo.getOrderHurrystatus()).orderStatus(orderVo.getOrderStatus())
						.orderRemark(orderVo.getOrderRemark()).build();
				
				// 设置用户的登录信息
				// TODO
				after.setOrderOperator("tom");
				after.setOrderOperateIp("127.0.0.1");
				after.setOrderOperateTime(new Date());
				mesOrderMapper.updateByPrimaryKeySelective(after);
			} catch (Exception e) {
				throw new SysMineException("更改过程有问题");
			}
			
			
			
		}
		
		

	///////////////////////////////////////////////////////////////////////////////////////////////
	// 1 默认生成代码
	// 2 手工生成代码
	// id生成器
	class IdGenerator {
		// 数量起始位置
		private Long currentdbidscount;
		private List<String> ids = new ArrayList<String>();
		private String idpre;
		private String yearstr;
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

		public String getYearstr() {
			return yearstr;
		}

		public void setYearstr(String yearstr) {
			this.yearstr = yearstr;
		}

		public String getIdafter() {
			return idafter;
		}

		public void setIdafter(String idafter) {
			this.idafter = idafter;
		}

		public List<String> initIds(Long ocounts) {
			for (int i = 0; i < ocounts; i++) {
				this.ids.add(getIdPre() + yearStr() + getIdAfter(i));
			}
			return this.ids;
		}

		//
		private String getIdAfter(int addcount) {
			// 系统默认生成5位 ZX1700001
			int goallength = 5;
			// 获取数据库order的总数量+1+循环次数(addcount)
			int count = this.currentdbidscount.intValue() + 1 + addcount;
			StringBuilder sBuilder = new StringBuilder("");
			// 计算与5位数的差值
			int length = goallength - new String(count + "").length();
			for (int i = 0; i < length; i++) {
				sBuilder.append("0");
			}
			sBuilder.append(count + "");
			return sBuilder.toString();
		}

		private String getIdPre() {
			// idpre==null?this.idpre="ZX":this.idpre=idpre;
			this.idpre = "ZX";
			return this.idpre;
		}

		private String yearStr() {
			Date currentdate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String yearstr = sdf.format(currentdate).substring(2, 4);
			return yearstr;
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
