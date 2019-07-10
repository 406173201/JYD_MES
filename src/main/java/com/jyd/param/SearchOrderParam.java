package com.jyd.param;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchOrderParam {
	
	private String keyword;
	
	private String fromTime;
	
	private String toTime;
	
	private String search_status;
}
