<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script id="producCometListTemplate" type="x-tmpl-mustache">
{{#productComeList}}
 <tr role="row" class="order-name odd" data-id="{{id}}"><!--even -->
	<td>{{productId}}</td>
	<td>{{parentProduct.productId}}</td>
	<td>{{productMaterialname}}</td>
	<td>{{productMaterialsource}}</td>
	<td>{{productTargetweight}}</td>
	<td>{{productRealweight}}</td>
	<td>{{productLeftweight}}</td> 
	<td>{{productHeatid}}</td> 
	<td>{{productImgid}}</td> 
	<td>{{productIrontype}}</td> 
	<td>{{productIrontypeweight}}</td> 
	<td>{{#bold}}{{showStatus}}{{/bold}}</td> 
	<td>{{productRemark}}</td>
	<td>
		<div class="hidden-sm hidden-xs action-buttons">
			 <a class="blue product-edit" href="#" data-id="{{id}}">
				  <i class="ace-icon fa fa-pencil bigger-100"></i>
			</a>
		</div>
	</td>
</tr>
{{/productComeList}}
</script>