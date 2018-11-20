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
	
	//广告
	table.render({
		elem: '#OM-shop-ads',
		url: '/manager/shop/ads/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号'},
			{field: 'title', title: '名称'},
			{field: 'pic', title: '图片', templet: '#img-tpl'},
			{field: 'info', title: '描述'},
			{field: 'type', title: '类型'},
			{field: 'sequence', title: '排序'},
			{field: 'is_show', title: '是否显示'},
			{title: '操作', width: 200, align:'center', fixed: 'right', toolbar: '#table-shop-ads'}
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
  
	//监听工具条
	table.on('tool(OM-shop-ads)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/shop/ads/delete",
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
	                        	table.reload('OM-shop-ads', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			view('OM-module-temp').render('shop/component/advertising-edit', data).done(function(){
				//
				if(data.type == '积分中心幻灯片') {
					$("#type").append("<option value='1'>首页幻灯片</option>"
							+ "<option value='2' selected>积分中心幻灯片</option>"
							+ "<option value='3'>分销申请幻灯片</option>");
				} else if(data.type == '小程序幻灯片') {
					$("#type").append("<option value='1'>首页幻灯片</option>"
							+ "<option value='2'>积分中心幻灯片</option>"
							+ "<option value='3' selected>小程序幻灯片</option>");
				} else {
					$("#type").append("<option value='1'>首页幻灯片</option>"
							+ "<option value='2'>积分中心幻灯片</option>"
							+ "<option value='3'>分销申请幻灯片</option>");
				}
				
				//动态加载
				form.render();
				
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//校验
		    		if(field.is_show === 'on') {
		    			field.is_show = 1;
		    		} else {
		    			field.is_show = 0;
		    		}
		    		if(field.sequence < 0){
		    			layer.msg('排序输入有误');
		    			return false;
		    		}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/shop/ads/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/shop/advertising');
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
	
	//后台菜单
	table.render({
		elem: '#OM-shop-menu',
		url: '/manager/shop/menu/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 100},
			{field: 'title', title: '名称', width: 250},
			{field: 'ico', title: '图标', width: 100, templet: '#imgTpl'},
			{field: 'controller', title: '地址'},
			{field: 'type', title: '类型', width: 100},
			{field: 'sequence', title: '排序', width: 100},
			{field: 'is_show', title: '是否显示', width: 100},
			{title: '操作', width: 350, align:'center', fixed: 'right', toolbar: '#table-shop-menu'}
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
  
	//监听工具条
	table.on('tool(OM-shop-menu)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/shop/menu/delete",
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
	                        	table.reload('OM-member-list', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			view('OM-module-temp').render('shop/component/menu-edit', data).done(function(){
				//动态加载
				form.render();
				
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//校验
		    		if(field.is_turn === 'on') {
		    			field.is_turn = 1;
		    		} else {
		    			field.is_turn = 0;
		    		}
		    		if(field.is_show === 'on') {
		    			field.is_show = 1;
		    		} else {
		    			field.is_show = 0;
		    		}
		    		if(field.sequence < 0) {
		    			layer.msg('排序输入有误');
		    			return false;
		    		}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/shop/menu/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/shop/menu');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
			});
		} else if(obj.event === 'son') {
			view('OM-module-temp').render('shop/component/menu-son', data).done(function(){
				console.log(JSON.stringify(data));
				//后台子级菜单
				table.render({
					elem: '#OM-shop-menu-son',
					url: '/manager/shop/menu/info',
					cols: [[
						{type: 'checkbox', fixed: 'left'},
						{field: 'id', title: '编号', width: 100},
						{field: 'title', title: '名称', width: 250},
						{field: 'ico', title: '图标', width: 100, templet: '#imgTpl'},
						{field: 'controller', title: '地址'},
						{field: 'type', title: '类型', width: 100},
						{field: 'sequence', title: '排序', width: 100},
						{field: 'is_show', title: '是否显示', width: 100},
						{title: '操作', width: 250, align:'center', fixed: 'right', toolbar: '#table-shop-menu-son'}
					]],
					where: {'parent_id': data.id},
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
			  
				//监听工具条
				table.on('tool(OM-shop-menu-son)', function(obj){
					var data2 = obj.data;
					if(obj.event === 'del'){
						layer.confirm('确定删除此信息？', function(index){
							layer.close(index);
							
							//数据封装
							var req = {"id": data2.id};
							//执行 Ajax 后重载
				          	$.ajax({
								type: "POST",
								url: "/manager/shop/menu/delete",
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
				                        	table.reload('OM-member-list', null);
				                        }
				                    });
								}, error: function(){
									location.hash='/system/404';
								}
					    	});
						});
					} else if(obj.event === 'edit') {
						view('OM-module-temp').render('shop/component/menu-son-edit', data2).done(function(){
							//动态加载
							form.render();
							
							//提交监听
							form.on('submit(OM-form-submit)', function(data){
					    		var field = data.field;
					    		//校验
					    		if(field.is_show === 'on') {
					    			field.is_show = 1;
					    		} else {
					    			field.is_show = 0;
					    		}
					    		if(field.sequence < 0){
					    			layer.msg('排序输入有误');
					    			return false;
					    		}
					    		
					    		//提交
					    		$.ajax({
					    			type: "POST",
									url: "/manager/shop/menu/update",
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
				        	    				window.history.pushState(null, null, '/start/index.html#/shop/menu');
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
			});
		}  else if(obj.event === 'son-add') {
			view('OM-module-temp').render('shop/component/menu-son-add', data).done(function(){
				//动态加载
				form.render();
				
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//校验
		    		if(field.is_show === 'on') {
		    			field.is_show = 1;
		    		} else {
		    			field.is_show = 0;
		    		}
		    		if(field.sequence < 0){
		    			layer.msg('排序输入有误');
		    			return false;
		    		}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/shop/menu/insert",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/shop/menu');
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
	
	exports('shop', {})
});