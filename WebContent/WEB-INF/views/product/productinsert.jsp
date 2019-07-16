<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>材料管理</title>
<jsp:include page="/common/backend_common.jsp" />

</head>
<body class="no-skin" youdao="bind" style="background: white">
	<input id="gritter-light" checked="" type="checkbox"
		class="ace ace-switch ace-switch-5" />
	<div class="page-header">
		<h1>
			材料管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
				手动更新材料
			</small>
		</h1>
	</div>
	<div class="main-content-inner">

		<div class="col-sm-12">
			<div class="col-xs-12">
				<div class="table-header">材料列表</div>
				<div>
					<div id="dynamic-table_wrapper"
						class="dataTables_wrapper form-inline no-footer">
						
						<form action="/product/insert.json" id="materialForm"
							method="post" >
							
							<div class="row" style="background-color: #e4e6e9; height: 50px;">
								<div class="col-xs-2">炉号</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									<input type="text" id="input-productHeatid" name="productHeatid"
										class="text ui-widget-content ui-corner-all" />
								</div>
								
							</div>
							
							<div class="row" style="background-color: white; height: 50px;  padding-top: 7px;">
								<div class="col-xs-2">图号</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									<input type="text" id="input-productImgid" name="productImgid"
										class="text ui-widget-content ui-corner-all" />
								</div>
								<div class="col-xs-2">材料名称</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									<input type="text" id="input-productMaterialname"
										name="productMaterialname"
										class="text ui-widget-content ui-corner-all" />
								</div>
							</div>

							<div class="row"
								style="background-color: #e4e6e9; height: 50px; padding-top: 7px;">
								<div class="col-xs-2">材料来源</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									<select id="input-productMaterialsource"
										name="productMaterialsource" data-placeholder="选择类型"
										style="width: 170px; height: 30px">
										<option value="钢材">钢材</option>
										<option value="废料">废料</option>
										<option value="外购件">外购件</option>
										<option value="外协件">外协件</option>
										<option value="钢锭">钢锭</option>
									</select>
								</div>
								<div class="col-xs-2">工艺重量</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									&nbsp;<input type="number" id="input-productTargetweight"
										name="productTargetweight"
										class="text ui-widget-content ui-corner-all" value="0" />
								</div>
							</div>

							<div class="row"
								style="background-color: white; height: 50px; padding-top: 7px;">
								<div class="col-xs-2">投料重量</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									&nbsp;<input type="number" id="input-productRealweight"
										name="productRealweight"
										class="text ui-widget-content ui-corner-all" value="0" />
								</div>
								<div class="col-xs-2">剩余重量</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									&nbsp;<input type="number" id="input-productLeftweight"
										name="productLeftweight"
										class="text ui-widget-content ui-corner-all" value="0" />
								</div>
							</div>

							<div class="row"
								style="background-color: #e4e6e9; height: 50px; padding-top: 7px;">
								<div class="col-xs-2">锭型</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									<input type="number" id="input-productIrontypeweight"
										name="productIrontypeweight"
										class="text ui-widget-content ui-corner-all"  />
								</div>
								<div class="col-xs-2">钢锭类别</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									<input type="text" id="input-productIrontype"
										name="productIrontype"
										class="text ui-widget-content ui-corner-all"  />
								</div>
							</div>

							<div class="row"
								style="background-color: white; height: 50px; padding-top: 7px;">
								<div class="col-xs-2">是否启用</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									<select id="input-productStatus" name="productStatus"
										data-placeholder="状态" style="width: 170px; height: 30px">
										<option value="0">未启用</option>
										<option value="1">启用</option>
									</select>

								</div>
								<div class="col-xs-2">备注</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									<input type="text" id="input-productRemark"
										name="productRemark"
										class="text ui-widget-content ui-corner-all"  />
								</div>
							</div>

							<div class="row"
								style="background-color: #e4e6e9; height: 50px; padding-top: 7px;">
								<div class="col-xs-2">批量生成个数</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									<input type="number" id="input-counts" name="counts"
										class="text ui-widget-content ui-corner-all" value="0" />
								</div>
								<div class="col-xs-2">生成材料</div>
								<div class="dataTables_length col-xs-4"
									id="dynamic-table_length">
									&nbsp;<input type="submit" value="点击按钮"
										class="btn btn-info fa fa-check" />
								</div>
							</div>
						</form>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
