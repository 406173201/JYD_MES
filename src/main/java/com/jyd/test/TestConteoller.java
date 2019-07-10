package com.jyd.test;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jyd.model.MesOrder;



@Controller
@RequestMapping("/test")
public class TestConteoller {
	
	@Resource
	private TestServlet testServlet;
	
	@RequestMapping("/test.page")
    public ModelAndView test() {
        return new ModelAndView("test");
    }
	
	@RequestMapping("/index.page")
    public String index() {
        return "admin";
    }
	
	@RequestMapping("/test.json")
	public ModelAndView indexJson(MesOrder mss) {
		System.out.println(mss.getOrderClientname());
		
		ModelAndView mv=new ModelAndView();
		MesOrder ms=testServlet.getMesOrders();
		mv.addObject("name",ms.getOrderClientname());//q
		mv.setViewName("test");
		return mv;
	}
}
