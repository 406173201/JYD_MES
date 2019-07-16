$(function(){
	
///////////////////////////////////////////////////////////////////////////
	//页面开始加载
	//执行分页逻辑
	//定义一些全局变量
	var productMap = {};//准备一个map格式的仓库，等待存储从后台返回过来的数据
	var optionStr;//选项参数
	var pageSize;//页码条数
	var pageNo;//当前页
	var url;//查询url
	var keyword;//关键字
	var search_msource;//查询材料来源

	//加载模板内容进入html
	//01从模板中获取页面布局内容
	//orderListTemplate就是mustache模板的id值
	var producBindListTemplate = $("#producBindListTemplate").html();
	//02使用mustache模板加载这串内容
	//只是把准备好的页面模板拿出来，放在解析引擎中，准备让引擎往里面填充数据（渲染视图）
	Mustache.parse(producBindListTemplate);
	//渲染分页列表
	//调用分页函数
	loadProductList();
	//点击刷新的时候也需要调用分页函数
	$(".research").click(function(e) {
		e.preventDefault();
		$("#productBindPage .pageNo").val(1);
		loadProductList();
	});
	//定义调用分页函数，一定是当前的查询条件下（keyword，search_status。。）的分页
	function loadProductList(urlnew) {
		//获取页面当前需要查询的还留在页码上的信息
		//在当前页中找到需要调用的页码条数
		pageSize = $("#pageSize").val();
		//当前页
		pageNo = $("#productBindPage .pageNo").val() || 1;
		if (urlnew) {
			url = urlnew;
		} else {
			url = "/product/productBindSearchPage.json";
		}
		keyword = $("#keyword").val();
		search_msource = $("#search_msource").val();
		//发送请求
		$.ajax({
			url : url,
			data : {//左面是数据名称-键，右面是值
				pageNo : pageNo,
				pageSize : pageSize,
				keyword : keyword,
				search_msource : search_msource,
			},
			type : 'POST',
			success : function(result) {//jsondata  jsondata.getData=pageResult  pageResult.getData=list
				//渲染order列表和页面--列表+分页一起填充数据显示条目
				renderOrderListAndPage(result, url);
				console.log(result);
			}
		});
	}
	
	function renderOrderListAndPage(result, url) {
		//从数据库返回过来的数据集合result
		if (result.ret) {
			//再次初始化查询条件
			url = "/product/productBindSearchPage.json";
			keyword = $("#keyword").val();
			search_msource = $("#search_msource").val();
			//如果查询到数据库中有符合条件的order列表
			if (result.data.total > 0) {
				var rendered = Mustache.render(
						producBindListTemplate,
						
						{
							"productBindList" : result.data.data,//{{#orderList}}--List-(result.data.data-list<MesOrder>)
							"showStatus" : function() {
								return this.productStatus == 1 ? '有效'
										: (this.productStatus == 0 ? '无效'
												: '删除');
							},
							"bold" : function() {
								return function(text, render) {
									var status = render(text);
									if (status == '有效') {
										return "<span class='label label-sm label-success'>有效</span>";
									} else if (status == '无效') {
										return "<span class='label label-sm label-warning'>无效</span>";
									} else {
										return "<span class='label'>删除</span>";
									}
								}
							}
						});
				$.each(result.data.data, function(i, product) {//java-增强for
					productMap[product.id] = product;
				});
				$('#productBindList').html(rendered);
			} else {
				$('#productBindList').html('');
			}
			bindProductClick();//点击绑定操作
			var pageSize = $("#pageSize").val();
			var pageNo = $("#productBindPage .pageNo").val() || 1;
			//渲染页码
			renderPage(
					url,
					result.data.total,
					pageNo,
					pageSize,
					result.data.total > 0 ? result.data.data.length : 0,
							"productBindPage", loadProductList);
		} else {
			showMessage("获取订单列表", result.msg, false);
		}
	}
	
	function bindProductClick(){
		$(".product-bind").click(function(e) {
			//阻止默认事件
			e.preventDefault();
			//阻止事件传播
			e.stopPropagation();
			var productId = $(this).attr("data-id");
			$("#bindSearch").hide();
			$("#productBindRelation").show();
			var tageProduct_P=productMap[productId];
			if(tageProduct_P){
				console.log(tageProduct_P.productId);
				$("#bind-id").val(tageProduct_P.id);//id
				$("#productBakweight").val(tageProduct_P.productBakweight);//理论剩余重量
				$("#productLeftweight").val(tageProduct_P.productLeftweight);//剩余重量
				$("#ProductId").val(tageProduct_P.productId);//编号
			}
//			$.ajax({
//				url : "/product/productBindRelation.json",
//				data : {//左面是数据名称-键，右面是值
//					BindId : productId,
//				},
//				type : 'POST',
//				success : function(result) {
//					
//				}
//			})
		})
	}
})