package com.jyd.param;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MesProductVo {

	@Min(1)
	private Integer counts=1;//这个数字就算是没有值，默认是1

	private Integer id;// 这个不需要校验，自定auto生成

	private Integer pId;

	private String productId;//自动生成
	
	private Integer productOrderid;

	private Integer productPlanid;
	
	@Min(0)
	private String productTargetweight; //工艺重量
	@Min(0)
	private String productRealweight; //投料重量
	@Min(0)
	private String productLeftweight; //剩余重量
	@Min(0)
	private String productBakweight;//备份剩余重量
	
	@NotBlank(message = "钢锭类别不可以为空")
	private String productIrontype;//钢锭类别
	
	@NotBlank(message = "钢锭锭型不可以为空")
	private String productIrontypeweight;
	
	private String productMaterialname;

	private String productImgid;
	
	@NotBlank(message = "材料来源不可以为空")
	private String productMaterialsource;
	
	@NotBlank(message = "炉号不可以为空")
	private String productHeatid;
	
	private String productStatus;
	
	private String productRemark;

}
