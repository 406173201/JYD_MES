package com.jyd.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.jyd.dao.MesOrderCustomerMapper;
import com.jyd.beans.PageQuery;
import com.jyd.beans.PageResult;
import com.jyd.dao.MesOrderMapper;
import com.jyd.dao.MesPlanCustomerMapper;
import com.jyd.dao.MesPlanMapper;
import com.jyd.dto.SearchPlanDto;
import com.jyd.exception.ParamException;
import com.jyd.model.MesOrder;
import com.jyd.model.MesPlan;
import com.jyd.param.MesPlanVo;
import com.jyd.param.SearchPlanParam;
import com.jyd.util.BeanValidator;
import com.jyd.util.MyStringUtils;

@Service
public class PlanService {

	@Resource
	private MesPlanMapper mesPlanMapper;
	
	@Resource
	private MesOrderCustomerMapper mesOrderCustomerMapper;

	@Resource
	private MesPlanCustomerMapper mesPlanCustomerMapper;
	
	@Resource
	private SqlSession sqlSession;
	
	@Resource
	private MesOrderMapper mesOrderMapper;
	

	//添加未启动的计划
	public void insertSelective(MesOrder mesOrder) {
		//将mesOrder中数据被分到Plan相应的字段
		System.out.println("p1");
		MesPlan mesPlan =MesPlan.builder().planOrderid(mesOrder.getOrderId()).planProductname(mesOrder.getOrderProductname())//
				.planClientname(mesOrder.getOrderClientname()).planContractid(mesOrder.getOrderContractid()).planImgid(mesOrder.getOrderImgid())//
				.planMaterialname(mesOrder.getOrderMaterialname()).planCurrentstatus("计划").planCurrentremark("计划待执行").planSalestatus(mesOrder.getOrderSalestatus())//
				.planMaterialsource(mesOrder.getOrderMaterialsource()).planHurrystatus(mesOrder.getOrderHurrystatus()).planStatus(0).planCometime(mesOrder.getOrderCometime())//
				.planCommittime(mesOrder.getOrderCommittime()).planInventorystatus(mesOrder.getOrderInventorystatus()).build();
		//设置登陆者
		System.out.println("p2");
		mesPlan.setPlanOperator("user01");
		System.out.println("p3");
		mesPlan.setPlanOperateIp("127.0.0.1");
		System.out.println("p4");
		mesPlan.setPlanOperateTime(new Date());
		System.out.println("p5");
		//批量处理sql
		MesPlanMapper mesPlanBatchMapper= sqlSession.getMapper(MesPlanMapper.class);
		System.out.println(6);
		mesPlanBatchMapper.insert(mesPlan);
	}
	
	//分页
	public Object searchPageList(SearchPlanParam param, PageQuery page) {
		//校验页码是否为空
		BeanValidator.check(page);
		//SearchPlanParam VO------->SearchPlanDto dto
		SearchPlanDto dto= new SearchPlanDto();
		if(StringUtils.isNotBlank(param.getKeyword())) {
			dto.setKeyword("%"+param.getKeyword()+"%");
		}
		if(StringUtils.isNotBlank(param.getSearch_status())) {
			dto.setSearch_status(Integer.parseInt(param.getSearch_status()));
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
		
		int count=mesPlanCustomerMapper.countBySearchPlanDto(dto);
		if(count>0) {
			List<MesPlan> ps=mesPlanCustomerMapper.getPageListBySearchDto(dto, page);
			return PageResult.<MesPlan>builder().total(count).data(ps).build();
		}
		return PageResult.<MesPlan>builder().build();
	}
	//启动订单，创建未启动计划
	public void startPlansByOrderIds(String[] idArray) {
		for (String OrderId : idArray) {
			//类型转换
			Integer orderId = Integer.parseInt(OrderId);
			//检验是否存在
			MesOrder order=mesOrderMapper.selectByPrimaryKey(orderId);
			//查询内容非空，使用google的guava工具类
			Preconditions.checkNotNull(order);
			//批量启动
			insertSelective(order);
		}
		
	}
	//批量启动
	public void planBatchStartAjax(String ids) {
		if( ids!=null && ids.length()>0) {
			//批量启动
			MesPlanMapper mesPlanMapper=sqlSession.getMapper(MesPlanMapper.class);
			String [] idArray=ids.split(",");
			String[] idsTemp=idArray[0].split("&");
			String[] datesTemp=idArray[1].split("&");
			String startTime=datesTemp[0];
			String endTime=datesTemp[1];
			
			for(int i=0;i<idsTemp.length;i++) {
				MesPlan mesPlan=new MesPlan();
				mesPlan.setId(Integer.parseInt(idsTemp[i]));
				mesPlan.setPlanWorkstarttime(MyStringUtils.string2Date(startTime,null));
				mesPlan.setPlanWorkendtime(MyStringUtils.string2Date(endTime,null));
				mesPlan.setPlanStatus(1);
				mesPlan.setPlanCurrentremark("计划已启动");
				mesPlanMapper.updateByPrimaryKeySelective(mesPlan);
				
//				//半成品材料 生成
//				MesPlan plan=mesPlanMapper.selectByPrimaryKey(Integer.parseInt(idsTemp[i]));
//				//产生半成品材料
//				String orderid=plan.getPlanOrderid();
//				MesOrder order=mesOrderCustomerMapper.selectByOrderId(orderid);
//				//product
//				MesProduct mesProduct=MesProduct.builder().productId(UUIDUtil.generateUUID())//
//						.productOrderid(order.getId()).productPlanid(plan.getId())//
//						.productMaterialname(order.getOrderMaterialname())//
//						.productImgid(order.getOrderImgid())//
//						.productMaterialsource(order.getOrderMaterialsource())//\
//						.productStatus(0).build();
//				mesProduct.setProductOperateIp("127.0.0.1");
//				mesProduct.setProductOperator("user01");
//				mesProduct.setProductOperateTime(new Date());
//				mesProductMapper.insertSelective(mesProduct);
			}
		}
			
	}

	public void updateAjax(MesPlanVo mesPlanVo) {
		//校验
		BeanValidator.check(mesPlanVo);
		MesPlan mesPlan=new MesPlan();
		//vo-po
		BeanUtils.copyProperties(mesPlanVo, mesPlan);
		mesPlan.setPlanCometime(MyStringUtils.string2Date(mesPlanVo.getPlanCometime(),null));
		mesPlan.setPlanCommittime(MyStringUtils.string2Date(mesPlanVo.getPlanCommittime(),null));
		mesPlan.setPlanWorkstarttime(MyStringUtils.string2Date(mesPlanVo.getPlanWorkstarttime(),null));
		mesPlan.setPlanWorkendtime(MyStringUtils.string2Date(mesPlanVo.getPlanWorkendtime(),null));
		mesPlan.setPlanOperator("user01");
		mesPlan.setPlanOperateIp("127.0.0.1");
		mesPlan.setPlanOperateTime(new Date());
		//更新
		mesPlanMapper.updateByPrimaryKeySelective(mesPlan);
	}
	
}
