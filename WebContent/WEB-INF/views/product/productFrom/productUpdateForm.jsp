<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<div id="dialog-productUpdate-form" style="display: none;">
	<form id="productUpdateForm">
		<table
			class="table table-striped table-bproducted table-hover dataTable no-footer"
			role="grid">
			<input type="hidden" name="id" id="input-Id2" value="" />
			<tr>
				<td><label for="input-productId">材料自编号</label></td>
				<td><input id="input-productId2" type="text" name="productId"
					value="" class="text ui-widget-content ui-corner-all"></td>
				<td><label for="productMaterialname">材料名称</label></td>
				<td><input id="input-productMaterialname2" type="text"
					name="productMaterialname" value=""
					class="text ui-widget-content ui-corner-all"></td>
			</tr>
			<tr>
				<td><label for="productMaterialsource">材料来源</label></td>
				<td><select id="input-productMaterialsource2"
					name="productMaterialsource" data-placeholder="选择类型"
					style="width: 150px;">
						<option value="钢材">钢材</option>
						<option value="废料">废料</option>
						<option value="外协件">外协件</option>
						<option value="外购件">外购件</option>
				</select></td>
				<td><label for="productTargetweight">工艺重量</label></td>
				<td><input id="input-productTargetweight2" type="number"
					name="productTargetweight" value=""
					class="text ui-widget-content ui-corner-all"></td>
			</tr>
			<tr>

				<td><label for="productRealweight">投料重量</label></td>
				<td><input id="input-productRealweight2" type="number"
					name="productRealweight" value=""
					class="text ui-widget-content ui-corner-all"></td>
				<td><label for="productLeftweight">剩余重量</label></td>
				<td><input id="input-productLeftweight2" type="number"
					name="productLeftweight" value=""
					class=" text ui-widget-content ui-corner-all" ></td>

			</tr>
			<tr>
				<td><label for="productHeatid">炉号</label></td>
				<td><input id="input-productHeatid2" type="text"
					name="productHeatid" value=""
					class=" text ui-widget-content ui-corner-all"></td>
			</tr>
			<tr>
				<td><label for="productImgid">图号</label></td>
				<td><input id="input-productImgid2" type="text"
					name="productImgid" value=""
					class=" text ui-widget-content ui-corner-all"></td>
				<td><label for="productIrontype">钢锭类别</label></td>
				<td><input id="input-productIrontype2" type="text"
					name="productIrontype" value=""
					class=" text ui-widget-content ui-corner-all"></td>
			</tr>
			<tr>
				<td><label for="productIrontypeweight">锭型</label></td>
				<td><input id="input-productIrontypeweight2"
					name="productIrontypeweight" type="number"
					class=" text ui-widget-content ui-corner-all" />
				<td><label for="productRemark">备注</label></td>
				<td><input id="input-productRemark2" name="productRemark"
					type="text" class=" text ui-widget-content ui-corner-all" />
			</tr>

		</table>
	</form>
</div>