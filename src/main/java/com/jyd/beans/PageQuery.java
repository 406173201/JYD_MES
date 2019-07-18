package com.jyd.beans;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class PageQuery {

    @Getter
    @Setter
    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;

    @Getter
    @Setter
    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize = 10;

    @Setter
    private int offset;

    
    
    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }

	public PageQuery(int pageNo, int pageSize) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public PageQuery(int pageNo, int pageSize, int offset) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.offset = offset;
	}
	public PageQuery() {
		super();
	}
	
}
