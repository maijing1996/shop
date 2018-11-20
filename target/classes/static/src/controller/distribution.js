/**

 @Name：order 会员模块控制
 @Author：偶木
 @Date：2018-09-05
    
 */


layui.define(['table', 'form'], function(exports){
	var $ = layui.$,
	admin = layui.admin,
	view = layui.view,
	table = layui.table,
	form = layui.form;
	
	table.render({
		elem: '#OM-distribution-identity',
		url: '/manager/distribution/identity/info',
		cols: [[
			{type: 'checkbox', fixed: 'left', width: 100},
			{field: 'id', title: '编号', width: 100},
			{field: 'name', title: '名称', width: 150},
			{field: 'apply_amount', title: '申请所需金额', width: 150},
			{field: 'list', title: '获得代金券', templet: '#imgTpl'},
			{field: 'buy_discount', title: '商品折扣%', width: 150},
			{field: 'fname', title: '上级名称', width: 150},
			{field: 'commission', title: '分销佣金比例%', width: 150},
			{field: 'is_sale', title: '是否显示', width: 100},
			{title: '操作', width: 200, align:'center', fixed: 'right', toolbar: '#table-distribution-identity'}
		]],
		page: true,
		limit: 30,
		height: 'full-320',

		//ajax
		contentType: 'application/json; charset=UTF-8',
		method: 'post',
		request: {
			limitName: 'size'
		},
		response: {
			statusCode: 200
		},
		
		text: '对不起，加载出现异常！'
	});
  
	table.on('tool(OM-distribution-identity)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/distribution/identity/delete",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify(req),
					success: function(data){
						layer.open({
	                        content: data.desc,
	                        btn: ['确认'],
	                        yes: function(index, layero) {
	                        	layer.close(index);
	                        	table.reload('OM-distribution-identity', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			//初始化
    		$.ajax({
    			type: "POST",
				url: "/manager/distribution/identity/coupon",
				dataType:'json',//预期服务器返回的数据类型
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify({}),
				async: false,
				success: function(data2){
					for(var i=0; i < data2.length; i++) {
						var param = false;
						var obj = data2[i];
						for(var j=0; j < data.list.length; j++) {
							var obj2 = data.list[j];
							if(obj2.id == obj.id) {
								param = true;
								obj.number = obj2.number;
								break;
							}
						}
						obj.param = param;
					}
					data.coupon = data2;
				}, error: function(){
					location.hash='/system/404';
				}
    		});
    		
    		//初始化
    		$.ajax({
    			type: "POST",
				url: "/manager/distribution/identity/comm",
				dataType:'json',//预期服务器返回的数据类型
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify({"id":data.id}),
				async: false,
				success: function(data2){
					data.comm = data2;
				}, error: function(){
					location.hash='/system/404';
				}
    		});
    		
    		//视图
			view('OM-module-temp').render('distribution/component/identity-edit', data).done(function() {
				//
				if(data.fid == '0') {
					$("#fid").append("<option value='0' selected>顶级身份</option>");
				} else {
					$("#fid").append("<option value='0'>顶级身份</option>");
				}
				//初始化
	    		$.ajax({
	    			type: "POST",
					url: "/manager/distribution/identity/info",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					data: JSON.stringify({}),
					async: false,
					success: function(data2){
						var list = data2.data;
						for(var i=0; i < list.length; i++) {
							var obj = list[i];
							if(data.id != obj.id) {
								if(obj.id == 1 || obj.is_apply == 1) {
									if(data.fid === obj.id) {
										$("#fid").append("<option value="+obj.id+" selected>"+obj.name+"</option>");
									} else {
										$("#fid").append("<option value="+obj.id+">"+obj.name+"</option>");
									}
								}
							}
						}
					}, error: function(){
						location.hash='/system/404';
					}
	    		});
				//动态加载
				form.render();
				
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//获取checkbox[name='like']的值
		            var arr = new Array();
		            $("input:checkbox[name='m_voucher']:checked").each(function(i){
		                arr[i] = $(this).val();
		            });
		           	field.vouchers = arr.join(",");//将数组合并成字符串
		
		    		var idList=new Array();
		    		idList=$('input[name="ids"]').map(function(){ return $(this).val(); }).get();
		    		field.ids = idList.join(",");
		    		
		    		var priceList=new Array();
		    		priceList=$('input[name="price"]').map(function(){ return $(this).val(); }).get();
		    		field.price = priceList.join(",");
		    		
		    		if(field.is_sale == 'on') {
		    			field.is_sale = 1;
		    		} else {
		    			field.is_sale = 0;
		    		}
		    		if(field.is_apply == 'on') {
		    			field.is_apply = 1;
		    		} else {
		    			field.is_apply = 0;
		    		}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/distribution/identity/update",
						dataType:'json',//预期服务器返回的数据类型
						contentType: "application/json; charset=utf-8",
						async: true,
						data: JSON.stringify(field),
						success: function(data){
							//直接跳转不做任何处理且提示
							layer.open({
	                  	      	content: data.desc,
	                   	     	btn: ['确认'],
	                   	     	yes: function(index, layero) {
	                        		layer.close(index);
	                        		//刷新当前页面
	        			    		window.location.reload();
	        			    		//修改路径，防止由于url 问题无法跳转的情况
	        	    				window.history.pushState(null, null, '/start/index.html#/distribution/identity');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
			});
		}
	});
	
	table.render({
		elem: '#OM-distribution-list',
		url: '/manager/distribution/distributionUser/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 100},
			{field: 'uid', title: '用户名'},
			{field: 'oauth_nickname', title: '会员昵称'},
			{field: 'integral', title: '积分'},
			{field: 'user_money', title: '余额'},
			{field: 'name', title: '申请身份'},
			{field: 'is_work', title: '状态'},
			{field: 'distribution_apply_add_time', title: '申请时间'},
			{title: '操作', width: 200, align:'center', fixed: 'right', toolbar: '#table-distribution-list'}
		]],
		page: true,
		limit: 30,
		height: 'full-320',

		//ajax
		contentType: 'application/json; charset=UTF-8',
		method: 'post',
		request: {
			limitName: 'size'
		},
		response: {
			statusCode: 200
		},
		
		text: '对不起，加载出现异常！'
	});
  
	table.on('tool(OM-distribution-list)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定拒绝此用户成为分销商？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id, "state":-1};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/distribution/distributionUser/check",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify(req),
					success: function(data){
						layer.open({
	                        content: data.desc,
	                        btn: ['确认'],
	                        yes: function(index, layero) {
	                        	layer.close(index);
	                        	table.reload('OM-distribution-list', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			view('OM-module-temp').render('distribution/component/distribution-edit', data).done(function() {
				//
				$("#fid").append("<option value='-1'>撤销分销身份</option>");
				//初始化
	    		$.ajax({
	    			type: "POST",
					url: "/manager/distribution/identity/info",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					data: JSON.stringify({}),
					async: false,
					success: function(data2){
						var list = data2.data;
						for(var i=0; i < list.length; i++) {
							var obj = list[i];
							if(obj.id == 1 || obj.is_apply == 1) {
								if(data.fid != '' && data.fid === obj.id) {
									$("#fid").append("<option value="+obj.id+" selected>"+obj.name+"</option>");
								} else {
									$("#fid").append("<option value="+obj.id+">"+obj.name+"</option>");
								}
							}
						}
					}, error: function(){
						location.hash='/system/404';
					}
	    		});
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		var req = {"id":field.id, "lev": field.lev, "name": field.real_name};
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/distribution/distributionUser/update",
						dataType:'json',//预期服务器返回的数据类型
						contentType: "application/json; charset=utf-8",
						async: false,
						data: JSON.stringify(req),
						success: function(data){
							//直接跳转不做任何处理且提示
							layer.open({
	                  	      	content: data.desc,
	                   	     	btn: ['确认'],
	                   	     	yes: function(index, layero) {
	                        		layer.close(index);
	                        		//刷新当前页面
	        			    		window.location.reload();
	        			    		//修改路径，防止由于url 问题无法跳转的情况
	        	    				window.history.pushState(null, null, '/start/index.html#/distribution/list');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
			});
		} else if(obj.event === 'pass') {
			view('OM-module-temp').render('distribution/component/distribution-pass', data).done(function() {
				//初始化
	    		$.ajax({
	    			type: "POST",
					url: "/manager/distribution/dcenter/info",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					data: JSON.stringify({}),
					async: false,
					success: function(data2){
						var list = data2.data;
						if(list != null && list.length > 0) {
							for(var i=0; i < list.length; i++) {
								var obj = list[i];
								if(data.fid != '' && data.fid === obj.id) {
									$("#fid").append("<option value="+obj.id+" selected>"+obj.name+"</option>");
								} else {
									$("#fid").append("<option value="+obj.id+">"+obj.name+"</option>");
								}
							}
						}
					}, error: function(){
						location.hash='/system/404';
					}
	    		});
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/distribution/distributionUser/check",
						dataType:'json',//预期服务器返回的数据类型
						contentType: "application/json; charset=utf-8",
						async: false,
						data: JSON.stringify(field),
						success: function(data){
							//直接跳转不做任何处理且提示
							layer.open({
	                  	      	content: data.desc,
	                   	     	btn: ['确认'],
	                   	     	yes: function(index, layero) {
	                        		layer.close(index);
	                        		//刷新当前页面
	        			    		window.location.reload();
	        			    		//修改路径，防止由于url 问题无法跳转的情况
	        	    				window.history.pushState(null, null, '/start/index.html#/distribution/list');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
			});
		}
	});
	
	table.render({
		elem: '#OM-distribution-dcenter',
		url: '/manager/distribution/dcenter/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 100},
			{field: 'name', title: '名称'},
			{title: '操作', width: 200, align:'center', fixed: 'right', toolbar: '#table-distribution-dcenter'}
		]],
		page: true,
		limit: 30,
		height: 'full-320',

		//ajax
		contentType: 'application/json; charset=UTF-8',
		method: 'post',
		request: {
			limitName: 'size'
		},
		response: {
			statusCode: 200
		},
		
		text: '对不起，加载出现异常！'
	});
  
	table.on('tool(OM-distribution-dcenter)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/distribution/dcenter/delete",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify(req),
					success: function(data){
						layer.open({
	                        content: data.desc,
	                        btn: ['确认'],
	                        yes: function(index, layero) {
	                        	layer.close(index);
	                        	table.reload('OM-distribution-dcenter', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			view('OM-module-temp').render('distribution/component/bills-edit', data).done(function() {
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/distribution/dcenter/update",
						dataType:'json',//预期服务器返回的数据类型
						contentType: "application/json; charset=utf-8",
						async: false,
						data: JSON.stringify(field),
						success: function(data){
							//直接跳转不做任何处理且提示
							layer.open({
	                  	      	content: data.desc,
	                   	     	btn: ['确认'],
	                   	     	yes: function(index, layero) {
	                        		layer.close(index);
	                        		//刷新当前页面
	        			    		window.location.reload();
	        			    		//修改路径，防止由于url 问题无法跳转的情况
	        	    				window.history.pushState(null, null, '/start/index.html#/distribution/bills');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
			});
		}
	});
	
	exports('distribution', {})
});