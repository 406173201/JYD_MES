package com.jyd.param;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 
 * 使用hibernate validator出现上面的错误， 需要 注意

@NotNull 和 @NotEmpty  和@NotBlank 区别

@NotEmpty 用在集合类上面
@NotBlank 用在String上面
@NotNull    用在基本类型上
 * 
 * 
 * */
@Setter
@ToString
@Getter
public class MesOrderVo{
	//接收批量生成的order个数
	@Min(1)
	private Integer count=1;//这个数字就算是没有值，默认是1
	
    private Integer id;

    private String orderId;
    
    @NotBlank(message="客户名称不能为空")
    private String orderClientname;
    
    @NotBlank(message="零件名称不能为空")
    private String orderProductname;

    @NotBlank(message="合同编号不能为空")
    private String orderContractid;

    @NotBlank(message="图号不能为空")
    private String orderImgid;

    @NotBlank(message="材料名称不能为空")
    private String orderMaterialname;

    private Date orderCometime;

    private Date orderCommittime;

    @NotNull(message="库存不能为空")
    private Integer orderInventorystatus;

    @NotBlank(message="图号不能为空")
    private String orderSalestatus;

    @NotBlank(message="交货状态不能为空")
    private String orderMaterialsource;

    @NotNull(message="是否特急不能为空")
    private Integer orderHurrystatus;

    @NotNull(message="状态不能为空")
    private Integer orderStatus;

    private String orderRemark;
    
    @NotBlank(message="来料日期不可以为空")
    private String comeTime;
    
    @NotBlank(message="提交日期不可以为空")
    private String commitTime;

	

    
}
