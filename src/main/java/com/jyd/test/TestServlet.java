package com.jyd.test;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jyd.dao.MesOrderMapper;
import com.jyd.model.MesOrder;

@Service
public class TestServlet {

	@Resource
	MesOrderMapper mesOrderMapper;
	
	public MesOrder getMesOrders() {
		return mesOrderMapper.selectByPrimaryKey(358);
	}

}
