<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script id="productBindShowListTemplate" type="x-tmpl-mustache">
{{#productBindShowList}}
 <tr role="row" class="order-name odd" data-id="{{id}}"><!--even -->
	<td><input type="hidden" id="productId{{id}}" value="">{{productId}}</td>
	<td><input type="hidden" id="productMaterialname{{id}}" value="{{productMaterialname}}"> {{productMaterialname}}</td>
	<td><input type="hidden" id="productMaterialsource{{id}}" value="{{productMaterialsource}}">{{productMaterialsource}}</td>
	<td><input type="hidden" id="productTargetweight{{id}}" value="{{productTargetweight}}">{{productTargetweight}}</td>
	<td>
		<div class="hidden-sm hidden-xs action-buttons">
			 <a  class="btn  blue productBind product-bindShow btn-danger" href="#" data-id="{{id}}">
				{{bind}}
			</a>
		</div>
	</td>
</tr>
{{/productBindShowList}}
</script>