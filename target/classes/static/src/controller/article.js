/**

 @Name：order 会员模块控制
 @Author：偶木
 @Date：2018-09-05
    
 */


layui.define(['table', 'form', 'layedit'], function(exports){
	var $ = layui.$,
	admin = layui.admin,
	view = layui.view,
	table = layui.table,
	layedit = layui.layedit,
	form = layui.form;
	
	table.render({
		elem: '#OM-article-details',
		url: '/manager/article/details/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号'},
			{field: 'title', title: '标题'},
			{field: 'cat_name', title: '所属栏目'},
			{field: 'zan', title: '人气'},
			{field: 'pic', title: '图片', templet: '#imgTpl'},
			{field: 'url', title: '外链'},
			{field: 'is_jp', title: '精品'},
			{field: 'is_show', title: '发布'},
			{title: '操作', width: 200, align:'center', fixed: 'right', toolbar: '#table-article-details'}
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
	table.on('tool(OM-article-details)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/article/details/delete",
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
	                        	table.reload('OM-article-details', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
		    view('OM-module-temp').render('article/component/article-edit', data).done(function(){
		    	//初始化
  		  		$.ajax({
  					type: "POST",
  					url: "/manager/article/details/info/all",
  					dataType:'json',//预期服务器返回的数据类型
  					contentType: "application/json; charset=utf-8",
  					async: false,
  					data: JSON.stringify({}),
  					success: function(data2){
  						var fid = 0;
						for(var i=0; i < data2.data.length; i++) {
							var obj = data2.data[i];
							if(obj.id == data.cat_id) {
								if(fid == 0 || obj.id != fid) {
									$('#m_cat_id').append("<option value="+obj.id+" selected >"+obj.title+"</option>");
									fid = obj.id;
								} else {
									$('#m_cat_id').append("<option value="+obj.sid+" selected > └ "+obj.stitle+"</option>");
								}
							} else {
								if(fid == 0 || obj.id != fid) {
									$('#m_cat_id').append("<option value="+obj.id+">"+obj.title+"</option>");
									fid = obj.id;
								} else {
									$('#m_cat_id').append("<option value="+obj.sid+"> └ "+obj.stitle+"</option>");
								}
							}
						}
  					}, error: function(){
  						location.hash='/system/404';
  					}
  				});
  				//富文本框
  				/*layedit.set({
  				  	uploadImage: {
  				    	url: '/editor/upload', //接口url
  				    	type: 'post' //默认post
  				  	}
  				});
  				//富文本框初始化
			  	var layedit_index = layedit.build('editor'); //建立编辑器*/
  				//加载页面
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//校验
		    		if(field.title === '') {
	      				layer.msg('请填写名称');
	      				return false;
	      			}
		    		
		    		if(field.sequence == '') {
		    			layer.msg('请输入排序');
	      				return false;
		    		} else if(field.sequence < 1){
		    			layer.msg('请输入正确的排序');
	      				return false;
		    		}

		    		if(field.is_jp === 'on') {
		    			field.is_jp = 1;
		    		} else {
		    			field.is_jp = 0;
		    		}
		    		
		    		if(field.is_show === 'on') {
		    			field.is_show = 1;
		    		} else {
		    			field.is_show = 0;
		    		}

		    		if(field.sequence < 0){
		    			layer.msg('排序输入有误');
		    			return false;
		    		}
		    		
		    		if(field.zan < 0){
		    			layer.msg('点赞输入有误');
		    			return false;
		    		}
		    		
		    		if(field.hits < 0){
		    			layer.msg('人气输入有误');
		    			return false;
		    		}
		    		//field.info = JSON.stringify(layedit.getContent(layedit_index));
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/article/details/update",
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
		elem: '#OM-article-categroy',
		url: '/manager/article/categroy/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 150},
			{field: 'title', title: '名称'},
			{field: 'wap_title', title: '手机名称'},
			{field: 'sequence', title: '排序'},
			{field: 'is_top', title: '是否推荐'},
			{field: 'is_show', title: '是否显示'},
			{title: '操作', width: 350, align:'center', fixed: 'right', toolbar: '#table-article-categroy'}
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
  
	table.on('tool(OM-article-categroy)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/article/categroy/delete",
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
	                        	table.reload('OM-article-categroy', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			view('OM-module-temp').render('article/component/category-edit', data).done(function(){
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
		    		
		    		if(field.wap_title === '') {
		    			layer.msg('请填写英文名称');
	      				return false;
		    		}
		    		
		    		if(field.sequence == '') {
		    			layer.msg('请输入排序');
	      				return false;
		    		} else if(field.sequence < 0){
		    			layer.msg('请输入正确的排序');
	      				return false;
		    		}

		    		if(field.is_top === 'on') {
		    			field.is_top = 1;
		    		} else {
		    			field.is_top = 0;
		    		}
		    		
		    		if(field.is_show === 'on') {
		    			field.is_show = 1;
		    		} else {
		    			field.is_show = 0;
		    		}
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/article/categroy/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/article/categroy');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
			});
		} else if(obj.event === 'add-son') {
			view('OM-module-temp').render('article/component/category-son-add', data).done(function() {
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
		    		
		    		if(field.wap_title === '') {
		    			layer.msg('请填写英文名称');
	      				return false;
		    		}
		    		
		    		if(field.sequence == '') {
		    			layer.msg('请输入排序');
	      				return false;
		    		} else if(field.sequence < 0){
		    			layer.msg('请输入正确的排序');
	      				return false;
		    		}

		    		if(field.is_top === 'on') {
		    			field.is_top = 1;
		    		} else {
		    			field.is_top = 0;
		    		}
		    		
		    		if(field.is_show === 'on') {
		    			field.is_show = 1;
		    		} else {
		    			field.is_show = 0;
		    		}
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/article/categroy/insert",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/article/categroy');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
			});
		} else if(obj.event === 'sel-son') {
			view('OM-module-temp').render('article/component/categroy-son', data).done(function(){
				table.render({
					elem: '#OM-article-categroy-son',
					url: '/manager/article/categroy/info',
					cols: [[
						{type: 'checkbox', fixed: 'left'},
						{field: 'id', title: '编号', width: 150},
						{field: 'title', title: '名称'},
						{field: 'wap_title', title: '手机名称'},
						{field: 'sequence', title: '排序'},
						{field: 'is_top', title: '是否推荐'},
						{field: 'is_show', title: '是否显示'},
						{title: '操作', width: 350, align:'center', fixed: 'right', toolbar: '#table-article-categroy-son'}
					]],
					where: {"parentId": data.id},
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
			});
			
			table.on('tool(OM-article-categroy-son)', function(obj){
				var data2 = obj.data;
				if(obj.event === 'del'){
					layer.confirm('确定删除此信息？', function(index){
						layer.close(index);
						
						//数据封装
						var req = {"id": data2.id};
						//执行 Ajax 后重载
			          	$.ajax({
							type: "POST",
							url: "/manager/article/categroy/delete",
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
			                        	table.reload('OM-article-categroy', null);
			                        }
			                    });
							}, error: function(){
								location.hash='/system/404';
							}
				    	});
					});
				} else if(obj.event === 'edit') {
					data2.ftitle = data.title;
					view('OM-module-temp').render('article/component/category-son-edit', data2).done(function(){
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
				    		
				    		if(field.wap_title === '') {
				    			layer.msg('请填写英文名称');
			      				return false;
				    		}
				    		
				    		if(field.sequence == '') {
				    			layer.msg('请输入排序');
			      				return false;
				    		} else if(field.sequence < 1){
				    			layer.msg('请输入正确的排序');
			      				return false;
				    		}

				    		if(field.is_top === 'on') {
				    			field.is_top = 1;
				    		} else {
				    			field.is_top = 0;
				    		}
				    		
				    		if(field.is_show === 'on') {
				    			field.is_show = 1;
				    		} else {
				    			field.is_show = 0;
				    		}
				    		//提交
				    		$.ajax({
				    			type: "POST",
								url: "/manager/article/categroy/update",
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
			        	    				window.history.pushState(null, null, '/start/index.html#/article/categroy');
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
		}
	});
	
	exports('article', {})
});