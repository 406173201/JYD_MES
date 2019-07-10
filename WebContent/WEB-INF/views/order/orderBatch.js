$(function(){
	
//////////////////////////////////////////////////////////////
	$(".order-add").click(
	function() {
		//弹出框
		$("#dialog-order-form").dialog(
				{
					model : true,//背景不可点击
					title : "新建订单",//模态框标题
					open : function(event, ui) {
						$(".ui-dialog").css("width", "700px");//增加模态框的宽高
						$(".ui-dialog-titlebar-close",
								$(this).parent()).hide();//关闭默认叉叉
						optionStr = "";
						$("#orderForm")[0].reset();//清空模态框--jquery 将指定对象封装成了dom对象
					},
					buttons : {
//						"添加" : function(e) {
//							//阻止一下默认事件
//							e.preventDefault();
//							//发送新增order的数据和接收添加后的回收信息
//							updateOrder(true, function(data) {
//								//增加成功了
//								//提示增加用户成功信息
//								showMessage("新增订单", data.msg,
//										true);
//								$("#dialog-order-form").dialog(
//										"close");
//	                         	loadOrderList();//根据参数查看
//							}, function(data) {
//								//增加失败了
//								//						alert("添加失败了");
//								//信息显示
//								showMessage("新增订单", data.msg,
//										false);
//								//						$("#dialog-order-form").dialog("close");
//							});
//						},
//						"取消" : function() {
//							$("#dialog-order-form").dialog(
//									"close");
//						}
					}
				});	
		});
	//////////////////////////////////////////////////////////////
	
})