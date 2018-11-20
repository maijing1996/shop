/**

 @Name：order 会员模块控制
 @Author：偶木
 @Date：2018-08-28
    
 */


layui.define(['table', 'form'], function(exports){
	var $ = layui.$,
	admin = layui.admin,
	view = layui.view,
	table = layui.table,
	form = layui.form;
	
	//会员列表
	table.render({
		elem: '#OM-member-list',
		url: '/manager/member/user/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号'},
			{field: 'oauth_nickname', title: '会员昵称'},
			{field: 'integral', title: '剩余积分'},
			{field: 'user_money', title: '剩余金额'},
			{field: 'lev', title: '会员等级'},
			{field: 'is_work', title: '用户状态'},
			{field: 'is_fx', title: '分销人员'},
			{field: 'add_date', title: '注册时间'},
			{title: '操作', width: 350, align:'center', fixed: 'right', toolbar: '#table-useradmin-webuser'}
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
  
	//会员列表_监听工具条
	table.on('tool(OM-member-list)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/member/user/delete",
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
			view('OM-module-temp').render('member/component/member-edit', data).done(function(){
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//校验
		    		if(field.passwd2 != '' || field.passwd != '') {
		    			if((field.passwd2 != '' && field.passwd === '') || (field.passwd2 === '' && field.passwd != '')) {
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
		    		if(field.tel === ''){
		    			
		    		} else if(!(/^1(3|4|5|7|8)\d{9}$/.test(field.tel))) {
	      				layer.msg('请填写正确的电话号码');
	      				return false;
	      			}
		    		if(field.email === '') {
		    			
		    		} else if(!(/^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/.test(field.email))) {
	      				layer.msg('请填写正确的电话号码');
	      				return false;
	      			}
		    		
		    		if(field.is_work == 'on') {
		    			field.is_work = 1;
		    		} else {
		    			field.is_work = 0;
		    		}
		    		
		    		if(field.is_fx == 'on') {
		    			field.is_fx = 1;
		    		} else {
		    			field.is_fx = 0;
		    		}

		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/member/user/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/member/list');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
				
				//点击事件监听
				$('#money').on('click', function(){
					var id = $(this).data('type');
					admin.popup({
						title: '余额调整',
						area: ['500px', '450px'],
						id: 'OM-popup-user-account',
						success: function(layero, index) {
							view(this.id).render('member/component/edit_account', null).done(function() {
								form.render(null, 'OM-form-account');
								//监听提交
								form.on('submit(OM-user-account)', function(data){
									var field = data.field; //获取提交的字段
									 
									if(field.amount === '') {
										layer.msg('请输入正确的数额');
					      				return false;
									}
									
									var req = {"type":field.type,"amount":field.amount,"info":field.info,"state":1,"id":id};
									//提交
						    		$.ajax({
						    			type: "POST",
										url: "/manager/member/user/neaten",
										dataType:'json',//预期服务器返回的数据类型
										contentType: "application/json; charset=utf-8",
										async: true,
										data: JSON.stringify(req),
										success: function(data){
											layer.open({
					                  	      	content: data.desc,
					                   	     	btn: ['确认'],
					                   	     	yes: function(index, layero) {
					                        		layer.close(index);
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
					return false;
				});
				
				$('#integral').on('click', function(){
					var id = $(this).data('type');
					admin.popup({
						 title: '积分调整',
						 area: ['500px', '450px'],
						 id: 'OM-popup-user-account',
						 success: function(layero, index) {
							 view(this.id).render('member/component/edit_account', null).done(function() {
								 form.render(null, 'OM-form-account');
								 //监听提交
								 form.on('submit(OM-user-account)', function(data){
									var field = data.field; //获取提交的字段
									
									if(field.amount === '') {
										layer.msg('请输入正确的数额');
					      				return false;
									}
									
									var req = {"type":field.type,"amount":field.amount,"info":field.info,"state":2,"id":id};
									//提交
						    		$.ajax({
						    			type: "POST",
										url: "/manager/member/user/neaten",
										dataType:'json',//预期服务器返回的数据类型
										contentType: "application/json; charset=utf-8",
										async: true,
										data: JSON.stringify(req),
										success: function(data){
											layer.open({
					                  	      	content: data.desc,
					                   	     	btn: ['确认'],
					                   	     	yes: function(index, layero) {
					                        		layer.close(index);
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
					return false;
				});
			});
		} else if(obj.event === 'role') {
			var data14 = '';
			//初始化
    		$.ajax({
    			type: "POST",
				url: "/manager/member/user/merchant",
				dataType:'json',//预期服务器返回的数据类型
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify({"id":data.id}),
				async: false,
				success: function(data2){
					data14 = data2;
				}, error: function(){
					location.hash='/system/404';
				}
    		});
			view('OM-module-temp').render('member/component/consultant_edit', data14).done(function(){
				//动态加载
				form.render();
				//同意绑定提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		if(field.lev == '') {
		    			layer.msg('没有绑定信息，不能提交');
		    			return false;
		    		}
		    		field.type = 1;
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/member/user/identity",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/member/list');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
				//解除绑定提交监听
				form.on('submit(OM-form-submit2)', function(data){
		    		var field = data.field;
		    		if(field.lev == '') {
		    			layer.msg('没有绑定信息，不能提交');
		    			return false;
		    		}
		    		field.type = 2;
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/member/user/identity",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/member/list');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
				//提交清推荐监听
				form.on('submit(OM-form-submit-reset)', function(data){
		    		var field = data.field;
		    		field.type = 3;
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/member/user/identity",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/member/list');
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
	
	//积分明细
	table.render({
		elem: '#OM-member-integral',
		url: '/manager/member/financial/integral', //模拟接口
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'oauth_nickname', title: '会员昵称'},
			{field: 'fee', title: '积分数额'},
			/*{field: 'account_fee', title: '剩余积分'},*/
			{field: 'info', title: '积分描述'},
			{field: 'type', title: '积分状态'},
			{field: 'add_date', title: '操作日期'}
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
  
	//会员等级
	table.render({
		elem: '#OM-member-level',
		url: '/manager/member/level/info', //模拟接口
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号'},
			{field: 'title', title: '等级名称'},
			{field: 'amount', title: '消费额度'},
			{field: 'rebate', title: '折扣率'},
			{field: 'info', title: '等级描述'},
			{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-toolbar-level'}
		]],
		page: false,
		height: 'full-320',
		
		//ajax
		contentType: 'application/json; charset=UTF-8',
		method: 'post',
		response: {
			statusCode: 200
		},
		
		text: '对不起，加载出现异常！'
	});
  
	//会员等级_监听工具条
	table.on('tool(OM-member-level)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此会员等级？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/member/level/delete",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: true,
					data: JSON.stringify(req),
					success: function(data){
						layer.open({
	                        content: data.desc,
	                        btn: ['确认'],
	                        yes: function(index, layero) {
	                        	layer.close(index);
	                        	table.reload('OM-member-level', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit'){
			view('OM-module-temp').render('member/component/level_edit', data).done(function(){
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
		    		
		    		if(field.rebate == '') {
		    			layer.msg('请输入折扣');
	      				return false;
		    		} else if(field.rebate < 0 || field.rebate > 100){
		    			layer.msg('请输入正确的折扣');
	      				return false;
		    		}
		    		
		    		if(field.amount == '') {
		    			layer.msg('请输入消费金额');
	      				return false;
		    		} else if(field.amount < 0){
		    			layer.msg('请输入正确的消费金额');
	      				return false;
		    		}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/member/level/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/member/level');
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
	
	//资金明细
	table.render({
		elem: '#OM-member-property',
		url: '/manager/member/financial/info', //模拟接口
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'oauth_nickname', title: '会员昵称'},
			{field: 'fee', title: '金额'},
			{field: 'account_fee', title: '剩余金额'},
			{field: 'info', title: '操作描述'},
			{field: 'type', title: '操作状态'},
			{field: 'add_date', title: '操作日期'}
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
  
	//会员提现
	table.render({
		elem: '#OM-member-withdraw',
		url: '/manager/member/withdrawal/info', //模拟接口
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号'},
			{field: 'oauth_nickname', title: '会员昵称'},
			{field: 'fee', title: '提现金额'},
			{field: 'account_fee', title: '剩余金额'},
			{field: 'info', title: '提现描述'},
			{field: 'add_date', title: '申请时间'},
			{field: 'pay_state', title: '提现状态'},
			{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#toolbar-member-withdraw'}
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
  
	//会员提现_监听工具条
	table.on('tool(OM-member-withdraw)', function(obj){
		var data = obj.data;
		if(obj.event === 'disagree'){
			layer.confirm('确定不同意此用户提现吗？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id, "state": -1};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/member/withdrawal/check",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: true,
					data: JSON.stringify(req),
					success: function(data){
						layer.open({
	                        content: data.desc,
	                        btn: ['确认'],
	                        yes: function(index, layero) {
	                        	layer.close(index);
	                        	table.reload('OM-member-withdraw', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'agree'){
			layer.confirm('确定同意此用户提现吗？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id, "state": 1};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/member/withdrawal/check",
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
	                        	table.reload('OM-member-withdraw', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		}
	});

	exports('member', {})
});