/**

 @Name：order 会员模块控制
 @Author：偶木
 @Date：2018-09-05
    
 */


layui.define(['table', 'form', 'layedit','laydate'], function(exports){
	var $ = layui.$,
	admin = layui.admin,
	view = layui.view,
	table = layui.table,
	layedit = layui.layedit,
	laydate = layui.laydate,
	form = layui.form;
	
	table.render({
		elem: '#OM-popularize-flashsale',
		url: '/manager/popularize/flashsale/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 150},
			{field: 'title', title: '促销标题'},
			{field: 'goods_name', title: '商品名称'},
			{field: 'amount', title: '总数量'},
			{field: 'sales', title: '已售量'},
			{field: 'price', title: '价钱'},
			{field: 'sale_bdate', title: '开始时间'},
			{field: 'sale_edate', title: '结束时间'},
			{title: '操作', width: 200, align:'center', fixed: 'right', toolbar: '#table-popularize-flashsale'}
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
  
	table.on('tool(OM-popularize-flashsale)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/popularize/flashsale/delete",
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
	                        	table.reload('OM-popularize-flashsale', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
  			view('OM-module-temp').render('popularize/component/flash-sale-edit', data).done(function() {
  				
	            $('#spec_key').val(data.id);
	            $('#spec_name').val(data.key_name);
	            
	            laydate.render({
	    	      	elem: '#test-laydate-normal-cn',
	    	      	type: 'datetime',
	    	      	value: data.sale_bdate
	    	    });
	    		laydate.render({
	    	      	elem: '#test-laydate-normal-cn2',
	    	      	type: 'datetime',
	    	      	value: data.sale_edate
	    	    });
  				//动态加载
				form.render();
				
				$(document).on('click','#select_goods',function(){
					admin.popup({
						title: '发货信息填写',
				        area: ['800px', '500px'],
				        id: 'OM-popup-order-add',
				        success: function(layero, index){
				        	view(this.id).render('popularize/component/flash-sale-goods', null).done(function(){
				        		//商品评论
				        		table.render({
				        			elem: '#OM-flash-goods',
				        			url: '/manager/popularize/flashsale/goods',
				        			//设置表格
				        			cols: [[
				        				{field: 'goodsname', title: '标题'},
				        				{field: 'cname', title: '所属分类'},
				        				{field: 'price', title: '价格'},
				        				{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-flash-goods'}
				        			]],
				        			page: true,
				        			limit: 10,
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
				        		
				        		//工具条监听
				        		table.on('tool(OM-flash-goods)', function(obj){
				        			var data = obj.data;
				        			if(obj.event === 'sel'){
				        				$('#goods').val(data.goodsname);
				        	            $('#goods_id').val(data.goods_id);
				        	            $('#spec_key').val(data.id);
				        	            $('#spec_name').val(data.key_name);
				        			}
				        			layer.close(index);
			        				return false;
				        		});
				        	});
				        }
					});
				});
				
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		
		    		if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(field.price))) {
		    			layer.msg('日期输入有误');
	      				return false;
		    		}
		    		if(field.amount < 1){
		    			layer.msg('抢购数量输入有误');
	      				return false;
		    		}
		    		if(field.per_amount < 1){
		    			layer.msg('限购数量输入有误');
	      				return false;
		    		}
		    		if(field.sequence < 0){
		    			layer.msg('排序输入有误');
	      				return false;
		    		}
		    		field.sale_bdate = new Date(field.sale_bdate).getTime()/1000;
		    		field.sale_edate = new Date(field.sale_edate).getTime()/1000;
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/popularize/flashsale/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/popularize/flash-sale');
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
		elem: '#OM-popularize-categroy',
		url: '/manager/popularize/categroy/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 150},
			{field: 'pic', title: '图片', templet: '#imgTpl'},
			{field: 'title', title: '名称'},
			{field: 'sequence', title: '排序'},
			{field: 'is_top', title: '是否推荐'},
			{field: 'is_show', title: '是否显示'},
			{title: '操作', width: 350, align:'center', fixed: 'right', toolbar: '#table-popularize-categroy'}
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
  
	table.on('tool(OM-popularize-categroy)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/popularize/categroy/delete",
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
	                        	table.reload('OM-popularize-categroy', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			view('OM-module-temp').render('popularize/component/subject-category-edit', data).done(function() {
				//动态加载
				form.render();
				//选择商品
				$(document).on('click','#select_goods',function(){
					admin.popup({
						title: '发货信息填写',
				        area: ['800px', '500px'],
				        id: 'OM-popup-order-add',
				        success: function(layero, index){
				        	view(this.id).render('popularize/component/flash-sale-goods', null).done(function(){
				        		//商品评论
				        		table.render({
				        			elem: '#OM-flash-goods',
				        			url: '/manager/popularize/flashsale/goods',
				        			//设置表格
				        			cols: [[
				        				{field: 'goodsname', title: '标题'},
				        				{field: 'cname', title: '所属分类'},
				        				{field: 'price', title: '价格'},
				        				{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-flash-goods'}
				        			]],
				        			page: true,
				        			limit: 10,
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
				        		
				        		//工具条监听
				        		table.on('tool(OM-flash-goods)', function(obj){
				        			var data = obj.data;
				        			if(obj.event === 'sel'){
				        				$("#span_goods").append("<span id='m_goods"+data.id+"'>"
				        						+ "<div class='select_list box'>" + data.title
				        						+ "<i class='iconfont' onclick='remove_goods("+id+")'>&#xe63d;</i>"
				        						+ "</div><input type='hidden' name='m_goods' value='"+data.id+"'></span>");
				        			}
				        			layer.close(index);
			        				return false;
				        		});
				        	});
				        }
					});
				});
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		if(field.is_show == 'on') {
		    			field.is_show = 1;
		    		} else {
		    			field.is_show = 0;
		    		}
		    		if(field.is_top == 'on') {
		    			field.is_top = 1;
		    		} else {
		    			field.is_top = 0;
		    		}
		    		if(field.sequence < 0){
		    			layer.msg('排序输入有误');
	      				return false;
		    		}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/popularize/categroy/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/popularize/subject-categroy');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
			});
		} else if(obj.event === 'son-add') {
			view('OM-module-temp').render('popularize/component/subject-category-son-add', data).done(function() {
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		if(field.is_show == 'on') {
		    			field.is_show = 1;
		    		} else {
		    			field.is_show = 0;
		    		}
		    		if(field.is_top == 'on') {
		    			field.is_top = 1;
		    		} else {
		    			field.is_top = 0;
		    		}
		    		if(field.sequence < 0){
		    			layer.msg('排序输入有误');
	      				return false;
		    		}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/popularize/categroy/insert",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/popularize/subject-categroy');
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
			view('OM-module-temp').render('popularize/component/subject-category-son', data).done(function() {
				//动态加载
				table.render({
					elem: '#OM-popularize-categroy-son',
					url: '/manager/popularize/categroy/info',
					cols: [[
						{type: 'checkbox', fixed: 'left'},
						{field: 'id', title: '编号', width: 150},
						{field: 'pic', title: '图片', templet: '#imgTpl'},
						{field: 'title', title: '名称'},
						{field: 'sequence', title: '排序'},
						{field: 'is_top', title: '是否推荐'},
						{field: 'is_show', title: '是否显示'},
						{title: '操作', width: 350, align:'center', fixed: 'right', toolbar: '#table-popularize-categroy-son'}
					]],
					where:{"parent_id": data.id},
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
				table.on('tool(OM-popularize-categroy-son)', function(obj){
					var data2 = obj.data;
					if(obj.event === 'del'){
						layer.confirm('确定删除此信息？', function(index){
							layer.close(index);
							
							//数据封装
							var req = {"id": data2.id};
							//执行 Ajax 后重载
				          	$.ajax({
								type: "POST",
								url: "/manager/popularize/categroy/delete",
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
				                        	table.reload('OM-popularize-categroy-son', null);
				                        }
				                    });
								}, error: function(){
									location.hash='/system/404';
								}
					    	});
						});
					} else if(obj.event === 'edit') {
						data2.ftitle = data.title;
						view('OM-module-temp').render('popularize/component/subject-category-son-edit', data2).done(function() {
							//动态加载
							form.render();
							//提交监听
							form.on('submit(OM-form-submit)', function(data){
					    		var field = data.field;
					    		if(field.is_show == 'on') {
					    			field.is_show = 1;
					    		} else {
					    			field.is_show = 0;
					    		}
					    		if(field.is_top == 'on') {
					    			field.is_top = 1;
					    		} else {
					    			field.is_top = 0;
					    		}
					    		if(field.sequence < 0){
					    			layer.msg('排序输入有误');
				      				return false;
					    		}
					    		
					    		//提交
					    		$.ajax({
					    			type: "POST",
									url: "/manager/popularize/categroy/update",
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
				        	    				window.history.pushState(null, null, '/start/index.html#/popularize/subject-categroy');
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
		}
	});
	
	table.render({
		elem: '#OM-popularize-subject',
		url: '/manager/popularize/subject/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 150},
			{field: 'title', title: '标题'},
			{field: 'source', title: '作者'},
			{field: 'url', title: '外链'},
			{field: 'hits', title: '人气'},
			{field: 'is_top', title: '是否推荐'},
			{field: 'is_show', title: '是否发布'},
			{title: '操作', width: 200, align:'center', fixed: 'right', toolbar: '#table-popularize-subject'}
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
  
	table.on('tool(OM-popularize-subject)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/popularize/subject/delete",
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
	                        	table.reload('OM-popularize-subject', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			view('OM-module-temp').render('popularize/component/subject-edit', data).done(function() {
				$.ajax({
					type: "POST",
					url: "/manager/popularize/categroy/info/all",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify({}),
					success: function(data){
						var fid = 0;
						for(var i=0; i < data.data.length; i++) {
							var obj = data.data[i];
							if(fid == 0 || obj.id != fid) {
								$('#m_cat_id').append("<option value="+obj.id+">"+obj.title+"</option>");
								fid = obj.id;
							} else {
								$('#m_cat_id').append("<option value="+obj.sid+"> └ "+obj.stitle+"</option>");
							}
						}
					}, error: function(){
						location.hash='/system/404';
					}
	    		});
				
				//图片集
				if(data.slide != null) {
					var arr = data.slide.split(",");
					if(arr != '') {
						for(var i=0; i < arr.length; i++) {
							var val = arr[i];
							if(val != '') {
								$("#slide").append("<div class='slide_ctr'>"
				      					+ "<img src="+val+">"
				      					+ "<div class='pic_del' onclick=delpic('"+val+"')>删除</div>"
				      					+ "<input type='hidden' name='m_slide' value='"+val+"'></div>");
							}
						}
					}
				}
				
				//商品
				if(data.goods_ids != null) {
					$.ajax({
						type: "POST",
						url: "/manager/goods/article/ids",
						dataType:'json',//预期服务器返回的数据类型
						contentType: "application/json; charset=utf-8",
						async: false,
						data: JSON.stringify({"ids": data.goods_ids}),
						success: function(data){
							if(data.result != null) {
								for(var i=0; i < data.result.length; i++) {
									var obj = data.result[i];
									var flag = 1;
		        		            $m_str = '<span id="m_goods'+obj.id+'"><div class="select_list box">'+obj.title+' <i class="iconfont" onclick="remove_goods('+obj.id+')">&#xe63d;</i></div><input type="hidden" name="m_goods" value="'+obj.id+'"></span>';
		        		            var array=$("[name='m_goods[]']");
		        		            for(var j=0;j<array.length;j++) {
		        		                var value = $(array[i]).val();
		        		                if(id==value){
		        		                    flag = 0;
		        		                    break;
		        		                }
		        		            }
		        		            if(flag) $('#span_goods').append($m_str);
								}
							}
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
				}
				
  				//动态加载
				form.render();
  				/*//富文本框
  				layedit.set({
  				  	uploadImage: {
  				    	url: '/editor/upload', //接口url
  				    	type: 'post' //默认post
  				  	}
  				});
  				//富文本框初始化
			  	var layedit_index = layedit.build('editor'); //建立编辑器*/
				
			  	$(document).on('click','#select_goods',function(){
					admin.popup({
						title: '发货信息填写',
				        area: ['800px', '500px'],
				        id: 'OM-popup-order-add',
				        success: function(layero, index){
				        	view(this.id).render('popularize/component/flash-sale-goods', null).done(function(){
				        		//商品评论
				        		table.render({
				        			elem: '#OM-flash-goods',
				        			url: '/manager/popularize/flashsale/goods',
				        			//设置表格
				        			cols: [[
				        				{field: 'goodsname', title: '标题'},
				        				{field: 'cname', title: '所属分类'},
				        				{field: 'price', title: '价格'},
				        				{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-flash-goods'}
				        			]],
				        			page: true,
				        			limit: 10,
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
				        		
				        		//工具条监听
				        		table.on('tool(OM-flash-goods)', function(obj){
				        			var data = obj.data;
				        			if(obj.event === 'sel'){
				        				layer.closeAll('iframe');
			        		            var flag = 1;
			        		            $m_str = '<span id="m_goods'+data.id+'"><div class="select_list box">'+data.title+' <i class="iconfont" onclick="remove_goods('+data.id+')">&#xe63d;</i></div><input type="hidden" name="m_goods" value="'+data.id+'"></span>';
			        		            var array=$("[name='m_goods[]']");
			        		            for(var i=0;i<array.length;i++) {
			        		                var value = $(array[i]).val();
			        		                if(id==value){
			        		                    flag = 0;
			        		                    break;
			        		                }
			        		            }
			        		            if(flag) $('#span_goods').append($m_str);
				        			}
				        			layer.close(index);
			        				return false;
				        		});
				        	});
				        }
					});
				});
			  	
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
					var field = data.field;
					if(field.is_top == 'on') {
						field.is_top = 1;
					} else {
						field.is_top = 0;
					}
					if(field.is_show == 'on') {
						field.is_show = 1;
					} else {
						field.is_show = 0;
					}
					if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(field.starting_price))) {
						layer.msg('起步价输入有误');
						return false;
					}
					if(field.hits < 0){
						layer.msg('人气输入有误');
						return false;
					}
					if(field.zan < 0){
						layer.msg('点赞输入有误');
						return false;
					}
					
					var m_goodsList=new Array();
					m_goodsList=$('input[name="m_goods"]').map(function(){ return $(this).val(); }).get();
   		    		field.goods_ids = m_goodsList.join(",");
   		    		
   		    		var m_slideList=new Array();
   		    		m_slideList=$('input[name="m_slide"]').map(function(){ return $(this).val(); }).get();
   		    		field.slide = m_slideList.join(",");
   		    		
					//执行 Ajax 后重载
	          		$.ajax({
						type: "POST",
						url: "/manager/popularize/subject/update",
						dataType:'json',//预期服务器返回的数据类型
						contentType: "application/json; charset=utf-8",
						async: true,
						data: JSON.stringify(field),
						success: function(data){
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
		elem: '#OM-popularize-coupon',
		url: '/manager/popularize/coupon/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 150},
			{field: 'title', title: '优惠名称'},
			{field: 'yh_price', title: '优惠额'},
			{field: 'min_price', title: '最小订单额'},
			{field: 'amount', title: '发放量'},
			{field: 'gain', title: '领取量'},
			{field: 'use_amount', title: '使用量'},
			{field: 'use_edate', title: '停止使用日期'},
			{field: 'type', title: '类型'},
			{title: '操作', width: 200, align:'center', fixed: 'right', toolbar: '#table-popularize-coupon'}
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
  
	table.on('tool(OM-popularize-coupon)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/popularize/coupon/delete",
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
	                        	table.reload('OM-popularize-coupon', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			view('OM-module-temp').render('popularize/component/discount-coupon-edit', data).done(function() {
				laydate.render({
			      	elem: '#test-laydate-normal-cn',
			      	type: 'datetime',
			      	value: data.send_bdate
			    });
				laydate.render({
			      	elem: '#test-laydate-normal-cn2',
			      	type: 'datetime',
			      	value: data.send_edate
			    });
				laydate.render({
			      	elem: '#test-laydate-normal-cn3',
			      	type: 'datetime',
			      	value: data.use_bdate
			    });
				laydate.render({
			      	elem: '#test-laydate-normal-cn4',
			      	type: 'datetime',
			      	value: data.use_edate
			    });
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		field.send_bdate = new Date(field.send_bdate).getTime()/1000;
		    		field.send_edate = new Date(field.send_edate).getTime()/1000;
		    		field.use_bdate = new Date(field.use_bdate).getTime()/1000;
		    		field.use_edate = new Date(field.use_edate).getTime()/1000;
		    		
		    		if(field.send_edate <= field.send_bdate) {
		    			layer.msg('日期输入有误');
	      				return false;
		    		}
		    		if(field.use_edate <= field.use_bdate) {
		    			layer.msg('日期输入有误');
	      				return false;
		    		}
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/popularize/coupon/update",
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
		} else if(obj.event === 'sel') {
			view('OM-module-temp').render('popularize/component/discount-coupon-user', data).done(function() {
				//动态加载
				form.render();
				//
				table.render({
					elem: '#OM-popularize-coupon-user',
					url: '/manager/popularize/coupon/coupon',
					cols: [[
						{field: 'id', title: '编号', width: 150},
						{field: 'coupon_sn', title: '优惠券SN'},
						{field: 'nickname', title: '领取人'},
						{field: 'use_date', title: '使用时间'},
						{field: 'order_sn', title: '使用订单'},
						{field: 'is_use', title: '状态'},
					]],
					where:{"coupon_id":data.id},
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
		}
	});
	
	exports('popularize', {})
});