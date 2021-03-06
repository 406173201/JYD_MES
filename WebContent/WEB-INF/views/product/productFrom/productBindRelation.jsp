<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/template/product/productBindShowList.jsp"%>
<%@ include file="/template/product/productBindDoList.jsp"%>
<%@ include file="/common/productBindDoShowPage.jsp"%>
<div id="productBindRelation" style="display: none;">
	<div id="do-productBindRelation" class="col-sm-6">
		<div class="table-header ">未绑定材料列表</div>
		<div class="col-sm-12">
			<div id="dynamic-table_wrapper" class="col-sm-12"
				style="margin: 10px 1px 15px 1px">
				<input type="hidden" id="bind-id" value=""> <label>
				<input type="hidden" id="product-realweight"  value=""> <label>
					原料编号 </label> <input type="text" readonly="readonly" id="ProductId2"
					name="productId" /> <label> 理论剩余重量</label> <input type="text"
					readonly="readonly" id="productBakweight2" name="productBakweight" />
				<br> <label> 剩余重量 </label><input type="text"
					readonly="readonly" id="productLeftweight2"
					name="productLeftweight" /> <input type="hidden" id="bind-id"
					value=""> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label>材料来源
					<select id="search_msource2" name="search_msource"
					aria-controls="dynamic-table" class=" input-sm">
						<option value="钢锭">钢锭</option>
						<option value="钢材">钢材</option>
						<option value="废料">废料</option>
						<option value="外协件">外协件</option>
						<option value="外购件">外购件</option>
				</select>
				</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="btn btn-info fa fa-check researchbind"
					style="margin-bottom: 6px;" type="button">刷新</button>

			</div>
			<br>
			<br>

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
							colspan="1">材料名称</th>
						<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
							colspan="1">材料来源</th>
						<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
							colspan="1">工艺重量</th>
						<th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">操作</th>
					</tr>
				</thead>
				<tbody id="productBindKids">

				</tbody>
			</table>
			<div class="row" id="productBindListPage"></div>

		</div>
	</div>


	<div id="show-productBindRelation" class="col-sm-6">
		<div class="table-header ">已绑定材料列表</div>
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
						colspan="1">材料名称</th>
					<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
						colspan="1">材料来源</th>
					<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
						colspan="1">工艺重量</th>
					<th class="sorting_disabled" rowspan="1" colspan="1" aria-label="">操作</th>
				</tr>
			</thead>
			<tbody id="productUnBind">

			</tbody>
		</table>
		<div class="row" id="productBindListPage"></div>
	</div>

</div>

