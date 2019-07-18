package com.jyd.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchProductParam {
	
	private String pId;
	
	private String keyword;

	private String search_msource;
	
	private String search_status;
}
