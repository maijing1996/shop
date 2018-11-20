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
	
	//微信回复功能
	table.render({
		elem: '#OM-wechat-reply',
		url: '/manager/wechat/reply/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号'},
			{field: 'title', title: '标题'},
			{field: 'info', title: '回复内容'},
			{field: 'trigger_type', title: '触发类型'},
			{field: 'reply_type', title: '回复类型'},
			{title: '操作', width: 200, align:'center', fixed: 'right', toolbar: '#table-wechat-reply'}
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
  
	//微信回复功能框
	table.on('tool(OM-wechat-reply)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此用户信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/wechat/reply/delete",
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
	                        	table.reload('OM-wechat-reply', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			view('OM-wechat-reply-temp').render('wechat/component/reply-edit', data).done(function() {
				//初始化html 内容
				$("#test").append(
					"<div id='span_keys' class='layui-form-item'>"
						+"<label class='layui-form-label'>关键词：</label>"
						+"<div class='layui-input-inline'>"
							+"<input type='text' name='keyword' placeholder='请输入关键词' value="+data.keyword+" class='layui-input'>"
						+"</div>"
						+"<div class='layui-form-mid layui-word-aux'>多个请用,隔开</div>"
					+"</div>"
				);
				$("#test2").append(
					"<div id='span_info' class='layui-form-item'>"
						+"<label class='layui-form-label'>文本内容：</label>"
						+"<div class=layui-input-block>"
							+"<textarea name='info' placeholder='请输入内容' class='layui-textarea'>"+data.info+"</textarea>"
						+"</div>"
					+"</div>"
				);
				
				//触发类型
				if(data.trigger_type == '关键词回复') {
					$("#trigger_type").append(
							"<option value='0' selected>关键词回复</option>"
							+ "<option value='1'>关注后回复</option>");
					$(".cla").css("display","block");
				} else {
					$("#trigger_type").append(
							"<option value='0'>关键词回复</option>"
							+ "<option value='1' selected>关注后回复</option>");
					$(".cla").css("display","none");
				}
				
				//回复类型
				if(data.reply_type == '文本内容') {
					$("#reply_type").append(
							"<option value='0' selected>文本内容</option>"
							+ "<option value='1'>海报图片</option>");
					$(".cla2").css("display","block");
				} else {
					$("#reply_type").append(
							"<option value='0'>文本内容</option>"
							+"<option value='1' selected>海报图片</option>");
					$(".cla2").css("display","none");
				}
				
				//事件监听
				form.on('select(m_trigger_type)',function (datas) {
			    	if(datas.value == 1) {
			    		data.reply_type ="关注后回复";
			    		$(".cla2").css("display","none");
			    	} else {
			    		data.reply_type ="关键词回复";
			    		$(".cla2").css("display","block");
			    	}
			    	//动态加载
			    	form.render();
			    });
				form.on('select(m_reply_type)',function (datas) {
			    	if(datas.value == 1) {
			    		data.reply_type ="海报图片";
			    		$(".cla").css("display","none");
			    	} else {
			    		data.reply_type ="文本内容";
			    		$(".cla").css("display","block");
			    	}
			    	//动态加载
			    	form.render();
			    });
				
				//动态加载
				form.render();
				
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//校验
		    		if(field.title === '') {
	      				layer.msg('请填写名称');
	      				return false;
	      			}
		    		if(field.trigger_type === '0') {
		    			if(field.keyword === '') {
		    				layer.msg('请填写关键字');
		      				return false;
		    			}
		    		}
		    		if(field.reply_type === '0') {
		    			if(field.info === '') {
		    				layer.msg('请填写文本信息');
		      				return false;
		    			}
		    		}
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/wechat/reply/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/wechat/wechat-reply');
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
	
	//微信菜单模块
	table.render({
		elem: '#OM-wechat-menu',
		url: '/manager/wechat/menu/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 100},
			{field: 'title', title: '菜单名称'},
			{field: 'info', title: '链接地址/关键字'},
			{field: 'show', title: '是否显示'},
			{title: '操作', width: 400, align:'center', fixed: 'right', toolbar: '#table-wechat-menu'}
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
  
	table.on('tool(OM-wechat-menu)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此用户信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/wechat/menu/delete",
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
	                        	table.reload('OM-wechat-menu', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			view('OM-module-temp').render('wechat/component/menu-edit', data).done(function() {
				//响应类型
				if(data.type == 0) {
					$("#m_type").append(
							"<option value='0' selected>链接</option>"
							+ "<option value='1'>关键词</option>");
					$(".class-info2").css("display","none");
				} else {
					$("#m_type").append(
							"<option value='0'>链接</option>"
							+ "<option value='1' selected>关键词</option>");
					$(".class-info").css("display","none");
				}
				//动态加载
				form.render();
				
				//事件监听
				form.on('select(m_type)',function (datas) {
					if(datas.value == 0) {
						$(".class-info").css("display","block");
						$(".class-info2").css("display","none");
					} else {
						$(".class-info").css("display","none");
						$(".class-info2").css("display","block");
					}
			    	//动态加载
					form.render();
			    });
				
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//校验
		    		if(field.title === '') {
	      				layer.msg('请填写名称');
	      				return false;
	      			}
		    		
		    		if(field.type === '') {
		    			layer.msg('请选择类型');
	      				return false;
		    		} else if(field.type === '0') {
		    			if(field.info === '') {
		    				layer.msg('请填写链接');
		      				return false;
		    			}
		    		} else if(field.type2 === '1') {
		    			if(field.info === '') {
		    				layer.msg('请填写关键字');
		      				return false;
		    			} else {
		    				field.info = field.info2;
		    			}
		    		} else {
		    			layer.msg('输入有误');
	      				return false;
		    		}
		    		
		    		if(field.sequence == '') {
		    			layer.msg('请输入排序');
	      				return false;
		    		} else if(field.sequence < 1){
		    			layer.msg('请输入正确的排序');
	      				return false;
		    		}
		    		
		    		if(field.is_show == 'on') {
		    			field.is_show = 1;
		    		} else {
		    			field.is_show = 0;
		    		}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/wechat/menu/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/wechat/wechat-menu');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
			});
		} else if(obj.event === 'edit-son') {
			view('OM-module-temp').render('wechat/component/wechat-menu-son', data).done(function() {
				//微信菜单模块
				table.render({
					elem: '#OM-wechat-menu-son',
					url: '/manager/wechat/menu/info',
					cols: [[
						{type: 'checkbox', fixed: 'left'},
						{field: 'id', title: '编号', width: 100},
						{field: 'title', title: '菜单名称'},
						{field: 'info', title: '链接地址/关键字'},
						{field: 'show', title: '是否显示'},
						{title: '操作', width: 350, align:'center', fixed: 'right', toolbar: '#table-wechat-menu-son'}
					]],
					where: {"parent_id": data.id},
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
				
				table.on('tool(OM-wechat-menu-son)', function(obj){
					var data2 = obj.data;
					if(obj.event === 'del'){
						layer.confirm('确定删除此用户信息？', function(index){
							layer.close(index);
							
							//数据封装
							var req = {"id": data2.id};
							//执行 Ajax 后重载
				          	$.ajax({
								type: "POST",
								url: "/manager/wechat/menu/delete",
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
				                        	table.reload('OM-wechat-menu', null);
				                        }
				                    });
								}, error: function(){
									location.hash='/system/404';
								}
					    	});
						});
					} else if(obj.event === 'edit') {
						view('OM-module-temp').render('wechat/component/menu-edit-son', data2).done(function() {
							//响应类型
							if(data.type == 0) {
								$("#m_type").append(
										"<option value='0' selected>链接</option>"
										+ "<option value='1'>关键词</option>");
								$(".class-info2").css("display","none");
							} else {
								$("#m_type").append(
										"<option value='0'>链接</option>"
										+ "<option value='1' selected>关键词</option>");
								$(".class-info").css("display","none");
							}
							console.log(JSON.stringify(data2));
							//动态加载
							form.render();
							//事件监听
							form.on('select(m_type)',function (datas) {
								if(datas.value == 0) {
									$(".class-info").css("display","block");
									$(".class-info2").css("display","none");
								} else {
									$(".class-info").css("display","none");
									$(".class-info2").css("display","block");
								}
						    	//动态加载
								form.render();
						    });
							
							//提交监听
							form.on('submit(OM-form-submit)', function(data){
					    		var field = data.field;
					    		//校验
					    		if(field.title === '') {
				      				layer.msg('请填写名称');
				      				return false;
				      			}
					    		
					    		if(field.type === '') {
					    			layer.msg('请选择类型');
				      				return false;
					    		} else if(field.type === '0') {
					    			if(field.info === '') {
					    				layer.msg('请填写链接');
					      				return false;
					    			}
					    		} else if(field.type2 === '1') {
					    			if(field.info === '') {
					    				layer.msg('请填写关键字');
					      				return false;
					    			} else {
					    				field.info = field.info2;
					    			}
					    		} else {
					    			layer.msg('输入有误');
				      				return false;
					    		}
					    		
					    		if(field.sequence == '') {
					    			layer.msg('请输入排序');
				      				return false;
					    		} else if(field.sequence < 1){
					    			layer.msg('请输入正确的排序');
				      				return false;
					    		}
					    		
					    		if(field.is_show == 'on') {
					    			field.is_show = 1;
					    		} else {
					    			field.is_show = 0;
					    		}
					    		
					    		//提交
					    		$.ajax({
					    			type: "POST",
									url: "/manager/wechat/menu/update",
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
				        	    				window.history.pushState(null, null, '/start/index.html#/wechat/wechat-menu');
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
		} else if(obj.event === 'son-add') {
			view('OM-module-temp').render('wechat/component/menu-add-son', data).done(function() {
				//初始化
				$(".class-info2").css("display","none");
				//动态加载
				form.render();
				//事件监听
				form.on('select(m_type)',function (datas) {
					if(datas.value == 0) {
						$(".class-info").css("display","block");
						$(".class-info2").css("display","none");
					} else {
						$(".class-info").css("display","none");
						$(".class-info2").css("display","block");
					}
			    	//动态加载
					form.render();
			    });
				
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//校验
		    		if(field.title === '') {
	      				layer.msg('请填写名称');
	      				return false;
	      			}
		    		
		    		if(field.type === '') {
		    			layer.msg('请选择类型');
	      				return false;
		    		} else if(field.type === '0') {
		    			if(field.info === '') {
		    				layer.msg('请填写链接');
		      				return false;
		    			}
		    		} else if(field.type === '1') {
		    			if(field.info2 === '') {
		    				layer.msg('请填写关键字');
		      				return false;
		    			} else {
		    				field.info = field.info2;
		    			}
		    		} else {
		    			layer.msg('输入有误');
	      				return false;
		    		}
		    		
		    		if(field.sequence == '') {
		    			layer.msg('请输入排序');
	      				return false;
		    		} else if(field.sequence < 1){
		    			layer.msg('请输入正确的排序');
	      				return false;
		    		}
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/wechat/menu/insert",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/wechat/wechat-menu');
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
	
	exports('wechat', {})
});