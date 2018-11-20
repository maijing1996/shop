/**

 @Name：order 管理员管理模块控制
 @Author：偶木
 @Date：2018-08-28
    
 */

 
layui.define(['table', 'form'], function(exports){
	var $ = layui.$,
	admin = layui.admin,
	view = layui.view,
	table = layui.table,
	form = layui.form;
	
	//管理员列表
	table.render({
		elem: '#OM-system-list',
		url: '/manager/admin/info', //模拟接口
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号'},
			{field: 'uid', title: '用户名'},
			{field: 'role_name', title: '角色类型'},
			{field: 'is_work', title: '用户状态'},
			{field: 'last_login_ip', title: '最近登录IP'},
			{field: 'last_login_time', title: '最近登录时间'},
			{title: '操作', width: 150, align:'center', fixed: 'right', toolbar: '#table-tookbar-list'}
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
  
	//管理员列表_监听工具条
	table.on('tool(OM-system-list)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此管理员角色？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/admin/delete",
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
	                        	table.reload('OM-system-list', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit'){
			view('OM-module-temp').render('power/component/manager-edit', data).done(function() {
				$("#m_role").append("<option value='0'>超级管理员</option>");
				//执行 Ajax 后重载
          		$.ajax({
					type: "POST",
					url: "/manager/role/info",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					success: function(data){
						var list = data.data;
						for(var i=0; list.length > i; i++) {
							var obj = list[i];
							$("#m_role").append("<option value="+obj.id+">"+obj.title+"</option>");
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
		    		//校验
		    		if(field.account === '') {
	      				layer.msg('请填写用户名');
	      				return false;
	      			}
		    		
		    		if(field.passwd2 != '' || field.passwd != '') {
		    			if((field.passwd2 === '' || field.passwd != '') || (field.passwd2 != '' || field.passwd === '')) {
		    				layer.msg('请填写密码');
		      				return false;
		    			} else if(field.passwd2.length < 6 || field.passwd.length < 6 || field.passwd2.length > 18 || field.passwd.length > 18) {
		      				layer.msg('请填写正确的密码');
		      				return false;
		      			} else if(field.passwd2 != field.passwd) {
		      				layer.msg('填写的两次密码不一致');
		      				return false;
		      			}
	      			}
		    		
		    		if(field.is_work === 'on') {
		    			field.is_work = 1;
		    		} else {
		    			field.is_work = 0;
		    		}

		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/admin/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/power/list');
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
	
	//管理员角色
	table.render({
		elem: '#OM-system-role',
		url: '/manager/role/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 200},
			{field: 'title', title: '角色名称', width: 200},
			{field: 'info', title: '角色描述'},
			{title: '操作', width: 200, align: 'center', fixed: 'right', toolbar: '#table-toolbar-role'}
		]],
		page: false,
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
  
	//管理员角色_监听工具条
	table.on('tool(OM-system-role)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此管理员角色？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/role/delete",
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
	                        	table.reload('OM-system-role', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit'){
			//执行 Ajax 后重载
      		$.ajax({
				type: "POST",
				url: "/manager/role/menu",
				dataType:'json',//预期服务器返回的数据类型
				contentType: "application/json; charset=utf-8",
				async: false,
				data: JSON.stringify({}),
				success: function(res){
					var ver = data.power.split(',');
					for(var i=0; i < res.result.length; i++) {
						var list = res.result[i].list;
						for(var j=0; j < list.length; j++) {
							var obj = list[j];
							var val2 = false;
							for(var z=0; z < ver.length; z++) {
								if(obj.id == parseInt(ver[z])) {
									val2 = true;
									break;
								}
							}
							obj.ioc = val2;
						}
					}
					data.result = res.result;
				}, error: function(){
					location.hash='/system/404';
				}
    		});
      		//
			view('OM-module-temp').render('power/component/role-edit', data).done(function() {
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data2){
					var field = data2.field;
					
					//获取checkbox[name='like']的值
		            var arr = new Array();
		            $("input:checkbox[name='m_power']:checked").each(function(i){
		                arr[i] = $(this).val();
		            });
		           	field.power = arr.join(",");//将数组合并成字符串
					
					//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/role/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/power/role');
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
	
	//系统操作日志
	table.render({
		elem: '#OM-system-log',
		url: '/manager/log/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'uid', title: '用户名'},
			{field: 'log_info', title: '日志内容'},
			{field: 'log_ip', title: '操作IP'},
			{field: 'log_state', title: '状态'},
			{field: 'add_date', title: '操作时间'},
			{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-toolbar-log'}
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
  
	//系统操作日志_监听工具条
	table.on('tool(OM-system-log)', function(obj){
		var data = obj.data;
		layer.confirm('确定删除此系统日志？', function(index){
			layer.close(index);
			
			//数据封装
			var req = {"id": data.id};
			//执行 Ajax 后重载
          	$.ajax({
				type: "POST",
				url: "/manager/log/delete",
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
                        	table.reload('OM-system-log', null);
                        }
                    });
				}, error: function(){
					location.hash='/system/404';
				}
	    	});
		});
	});
	
	exports('system', {})
});