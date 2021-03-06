<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>材料操作</title>
<!-- jsp动态导入 -->
<%@ include file="/common/backend_common.jsp"%>
<%@ include file="/common/page.jsp"%>
<%@ include file="/template/product/productBindListTemplate.jsp"%>

</head>
<body class="no-skin" youdao="bind" style="background: white">
	<input id="gritter-light" checked="" type="checkbox"
		class="ace ace-switch ace-switch-5" />
	<div class="page-header">
		<h1>
			材料操作 <small><i class="ace-icon fa fa-angle-double-right"></i>
				材料绑定 </small>
		</h1>
	</div>
	<div class="main-content-inner" id="bindSearch">
		<div class="col-sm-12">
			<div class="col-xs-12">
				<div class="table-header">材料列表&nbsp;&nbsp;</div>
				<div>
					<div id="dynamic-table_wrapper"
						class="dataTables_wrapper form-inline no-footer">
						<div class="row">

							<div class="col-xs-12">
								<div class="dataTables_length" id="dynamic-table_length">
									<label> 展示 <select id="pageSize"
										name="dynamic-table_length" aria-controls="dynamic-table"
										class="form-control input-sm">
											<option value="10">10</option>
									</select> 条记录
									</label> <input id="keyword" type="search" name="keyword"
										class="form-control input-sm" placeholder="关键词"
										aria-controls="dynamic-table"> <label>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;材料来源 <select
										id="search_msource" name="search_msource"
										aria-controls="dynamic-table" class="form-control input-sm">
											<option value=""></option>
											<option value="钢材">钢材</option>
											<option value="废料">废料</option>
											<option value="外购件">外购件</option>
											<option value="外协件">外协件</option>
									</select> &nbsp;&nbsp;&nbsp;&nbsp;
										<button class="btn btn-info fa fa-check research"
											style="margin-bottom: 6px;" type="button">刷新</button>
								</div>
							</div>

						</div>
						<table id="dynamic-table"
							class="table table-striped table-bordered table-hover dataTable no-footer"
							role="grid" aria-describedby="dynamic-table_info"
							style="font-size: 14px">
							<thead>
								<tr role="row">
									<input type="hidden" id="id" name="id" class="id" />
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">材料自编号</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">父级自编号</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">材料名称</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">材料来源</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">工艺重量</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">投料重量</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">剩余重量</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">绑定剩余</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">炉号</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">图号</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">钢锭类别</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">锭型</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">状态</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">备注</th>
									<th class="sorting_disabled" rowspan="1" colspan="1"
										aria-label="">修改</th>
								</tr>
							</thead>
							<tbody id="productBindList">
								
							</tbody>
						</table>
						<div class="row" id="productBindPage">
						
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="productFrom/productBindRelation.jsp"%>
	<script type="text/javascript" src="productBindList.js"></script>
</body>
</html>