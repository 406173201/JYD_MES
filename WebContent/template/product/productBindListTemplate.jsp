<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script id="producBindListTemplate" type="x-tmpl-mustache">
{{#productBindList}}
 <tr role="row" class="order-name odd" data-id="{{id}}"><!--even -->
	<td>{{productId}}</td>
	<td>{{pId}}</td>
	<td>{{productMaterialname}}</td>
	<td>{{productMaterialsource}}</td>
	<td>{{productTargetweight}}</td>
	<td>{{productRealweight}}</td>
	<td>{{productLeftweight}}</td> 
	<td>{{product_bakweight}}</td>
	<td>{{productHeatid}}</td> 
	<td>{{productImgid}}</td> 
	<td>{{productIrontype}}</td> 
	<td>{{productIrontypeweight}}</td> 
	<td>{{#bold}}{{showStatus}}{{/bold}}</td> 
	<td>{{productRemark}}</td>
	<td>
		<div class="hidden-sm hidden-xs action-buttons">
			 <a class="btn blue product-bind" href="#" data-id="{{id}}">
				 点击绑定
			</a>
		</div>
	</td>
</tr>
{{/productBindList}}
</script>