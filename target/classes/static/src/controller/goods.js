/**

 @Name：order 商品模块控制
 @Author：偶木
 @Date：2018-08-28
    
 */


layui.define(['table', 'form', 'layedit'], function(exports){
	var $ = layui.$,
	admin = layui.admin,
	view = layui.view,
	table = layui.table,
	layedit = layui.layedit,
	form = layui.form;
	
	//商品列表
	table.render({
		elem: '#OM-goods-list',
		url: '/manager/goods/article/info', //模拟接口
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '订单号'},
			{field: 'title', title: '标题'},
			{field: 'sn', title: '货号'},
			{field: 'category', title: '所属分类'},
			{field: 'person', title: '人群分类'},
			{field: 'price', title: '价钱(元)'},
			{field: 'stock', title: '库存'},
			{field: 'is_sale', title: '是否上架'},
			{field: 'is_top', title: '是否推荐'},
			{field: 'is_hot', title: '是否热卖'},
			{field: 'is_new', title: '是否新品'},
			{title: '操作', width: 150, align:'center', fixed: 'right', toolbar: '#toolbar-goods-list'}
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
	table.on('tool(OM-goods-list)', function(obj) {
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/goods/article/delete",
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
	                        	table.reload('OM-goods-list', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			view('OM-module-temp').render('goods/component/goods-edit', data).done(function() {
				//动态加载
   				form.render();
				var data9 = '';
				//初始化类型
  				$('#m_cat_id').append("<option value=''>请选择商品分类</option>");
  				$.ajax({
					type: "POST",
					url: "/manager/goods/categroy/info/all",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify({}),
					success: function(data2){
						var fid = 0;
						for(var i=0; i < data2.data.length; i++) {
							var obj = data2.data[i];
							if(data.cat_id == obj.id || data.cat_id == obj.sid) {
								if(fid == 0 || obj.id != fid) {
									$('#m_cat_id').append("<option value="+obj.id+" selected>"+obj.title+"</option>");
									fid = obj.id;
								} else {
									$('#m_cat_id').append("<option value="+obj.sid+" selected> └ "+obj.stitle+"</option>");
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
  				//初始化人群
  				$('#m_person_id').append("<option value=''>请选择所属人群</option>");
  				$.ajax({
					type: "POST",
					url: "/manager/goods/person/info/all",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify({}),
					success: function(data2){
						var fid = 0;
						for(var i=0; i < data2.data.length; i++) {
							var obj = data2.data[i];
							if(data.person_id == obj.id || data.person_id == obj.sid) {
								if(fid == 0 || obj.id != fid) {
									$('#m_person_id').append("<option value="+obj.id+" selected>"+obj.title+"</option>");
									fid = obj.id;
								} else {
									$('#m_person_id').append("<option value="+obj.sid+" selected> └ "+obj.stitle+"</option>");
								}
							} else {
								if(fid == 0 || obj.id != fid) {
									$('#m_person_id').append("<option value="+obj.id+">"+obj.title+"</option>");
									fid = obj.id;
								} else {
									$('#m_person_id').append("<option value="+obj.sid+"> └ "+obj.stitle+"</option>");
								}
							}
						}
					}, error: function(){
						location.hash='/system/404';
					}
	    		});
  				//初始化品牌
  				$('#m_brand_id').append("<option value='0'>请选择所属品牌</option>");
  				$.ajax({
					type: "POST",
					url: "/manager/goods/brand/info",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify({"page":1,"size":1000}),
					success: function(data2){
						var fid = 0;
						for(var i=0; i < data2.data.length; i++) {
							var obj = data2.data[i];
							if(data.brand_id == obj.id) {
								$('#m_brand_id').append("<option value="+obj.id+" selected>"+obj.title+"</option>");
							} else {
								$('#m_brand_id').append("<option value="+obj.id+">"+obj.title+"</option>");
							}
						}
					}, error: function(){
						location.hash='/system/404';
					}
	    		});
  				//初始化商品模型
  				$('#m_type').append("<option value='0'>请选择商品模型</option>");
  				$.ajax({
					type: "POST",
					url: "/manager/goods/model/info",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify({"page":1,"size":1000}),
					success: function(data2){
						var fid = 0;
						for(var i=0; i < data2.data.length; i++) {
							var obj = data2.data[i];
							if(obj.id == data.spec_type) {
								$('#m_type').append("<option value="+obj.id+" selected>"+obj.title+"</option>");
								
								$(".spec_ctr").css("display","block");
								layui.sessionData('table', {
					     		  	key: 'goodsedd',
					     		  	value: ''
					     		});
								
								//重新加载
						  		$.ajax({
									type: "POST",
									url: "/manager/goods/spec/price",
									dataType:'json',//预期服务器返回的数据类型
									contentType: "application/json; charset=utf-8",
									async: false,
									data: JSON.stringify({"goods_id":data.id,"type_id":data.spec_type}),
									success: function(data2){
										if(data2.result != null) {
											var htmlstr = '';
											var val = '';
											var data9 = data2.result;
											for(var i=0; i < data2.result.length; i++) {
												var obj = data2.result[i];
												htmlstr = htmlstr + "<tr><td>"+obj.title+"</td><td>"
												var lli = obj.list;
												if(lli != null && lli.length > 0) {
													for(var j=0; j < lli.length; j++) {
														var obj2 = lli[j];
														if(obj2.res == "1") {
															var obj3 = obj2.price;
															htmlstr = htmlstr + "<button class='layui-btn layui-btn-small' type='button' value='"+obj2.id+"''>"+obj2.title+"</button>";
															$('#spec_input_tab_2').append("<tr><td>"+obj2.title+"</td><td>"
																	+ "<input name='prices' value="+obj3.price+" data2type='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
																	+ "</td><td><input name='stocks' value="+obj3.stock+" type='number'></td><td>"
																	+ "<input name='skus' value="+obj3.sku+">"
																	+ "<input name='spec_key_ids' type='hidden' value="+obj2.id+"></td></tr>");
															val = val + obj2.id + ',' ;
															$("#spec_item_ctr").css("display","block");
														} else {
															htmlstr = htmlstr + "<button class='layui-btn layui-btn-small layui-btn-primary' type='button' value='"+obj2.id+"''>"+obj2.title+"</button>";
														}
													}
													htmlstr = htmlstr + "</td>";
												}
											}
											if(htmlstr != '') {
												htmlstr = htmlstr + "</tr>";
												$('#spec_ctr1').append(htmlstr);
											}
											
											layui.sessionData('table', {
								     		  	key: 'goodsedit',
								     		  	value: val
								     		});
											layui.sessionData('table', {
								     		  	key: 'goodsval',
								     		  	value: val
								     		});
										} else {
											$(".spec_ctr").css("display","none");
										}
										
										layui.sessionData('table', {
							     		  	key: 'goodstype',
							     		  	value: data2.result
							     		});
										
										layui.sessionData('table', {
							     		  	key: 'keys',
							     		  	value: data.spec_type
							     		});
										layui.sessionData('table', {
							     		  	key: 'is_key',
							     		  	value: '1'
							     		});
									}, error: function(){
										location.hash='/system/404';
									}
					    		});
							} else {
								$('#m_type').append("<option value="+obj.id+">"+obj.title+"</option>");
							}
						}
						
						//重新加载
				  		$.ajax({
							type: "POST",
							url: "/manager/goods/spec/iteminfo",
							dataType:'json',//预期服务器返回的数据类型
							contentType: "application/json; charset=utf-8",
							async: false,
							data: JSON.stringify({"type_id":data.spec_type, "page":1,"size":1000}),
							success: function(data){
								if(data.result != null) {
									data9 = data.result;
								}
							}, error: function(){
								location.hash='/system/404';
							}
			    		});
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
				      					+ "<img src='/upload/pic/"+val+"'>"
				      					+ "<div class='pic_del' onclick=delpic('"+val+"')>删除</div>"
				      					+ "<input type='hidden' name='m_slide' value='"+val+"'></div>");
							}
						}
					}
				}
  				
   				/*//富文本框
  				layedit.set({
  				  	uploadImage: {
  				    	url: '/editor/upload', //接口url
  				    	type: 'post' //默认post
  				  	}
  				});
  				
  				//富文本框初始化
			  	var layedit_index = layedit.build('editor');*/ //建立编辑器
			  	//动态加载
   				form.render();
   				
			  	//select 监听事件
			  	form.on('select(m_type)', function(data){
			  		
			  		$('#spec_input_tab_2').empty();
			  		$('#spec_ctr1').empty();
			  		$(".spec_ctr").css("display","block");
			  		$("#spec_item_ctr").css("display","none");
			  		
			  		if(data.value == 0) {
			  			$(".spec_ctr").css("display","none");
			  			return false;
			  		}
			  		
			  		var session = layui.sessionData('table');
			  		if(session.keys != '' && parseInt(session.keys) == parseInt(data.value)) {
			  			var result = session.goodstype;
			  			if(result != null) {
			  				data9 = result;
							var htmlstr = '';
							var val = '';
							for(var i=0; i < result.length; i++) {
								var obj = result[i];
								htmlstr = htmlstr + "<tr><td>"+obj.title+"</td><td>"
								var lli = obj.list;
								if(lli != null && lli.length > 0) {
									for(var j=0; j < lli.length; j++) {
										var obj2 = lli[j];
										if(obj2.res == "1") {
											var obj3 = obj2.price;
											htmlstr = htmlstr + "<button class='layui-btn layui-btn-small' type='button' value='"+obj2.id+"''>"+obj2.title+"</button>";
											$('#spec_input_tab_2').append("<tr><td>"+obj2.title+"</td><td>"
													+ "<input name='prices' value="+obj3.price+" datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
													+ "</td><td><input name='stocks' value="+obj3.stock+" type='number'></td><td>"
													+ "<input name='skus' value="+obj3.sku+">"
													+ "<input name='spec_key_ids' type='hidden' value="+obj2.id+"></td></tr>");
											val = val + obj2.id + ',' ;
											$("#spec_item_ctr").css("display","block");
										} else {
											htmlstr = htmlstr + "<button class='layui-btn layui-btn-small layui-btn-primary' type='button' value='"+obj2.id+"''>"+obj2.title+"</button>";
										}
									}
									htmlstr = htmlstr + "</td>";
								}
							}
							if(htmlstr != '') {
								htmlstr = htmlstr + "</tr>";
								$('#spec_ctr1').append(htmlstr);
							}
							
							layui.sessionData('table', {
				     		  	key: 'goodsedit',
				     		  	value: val
				     		});
						} else {
							$(".spec_ctr").css("display","none");
						}
			  			
			  			layui.sessionData('table', {
			     		  	key: 'is_key',
			     		  	value: '1'
			     		});
			  		} else {
			  			//重新加载
				  		$.ajax({
							type: "POST",
							url: "/manager/goods/spec/iteminfo",
							dataType:'json',//预期服务器返回的数据类型
							contentType: "application/json; charset=utf-8",
							async: false,
							data: JSON.stringify({"type_id":data.value, "page":1,"size":1000}),
							success: function(data){
								if(data.result != null) {
									var htmlstr = '';
									data9 = data.result;
									for(var i=0; i < data.result.length; i++) {
										var obj = data.result[i];
										htmlstr = htmlstr + "<tr><td>"+obj.title+"</td><td>"
										var lli = obj.list;
										if(lli != null && lli.length > 0) {
											for(var j=0; j < lli.length; j++) {
												var obj2 = lli[j];
												htmlstr = htmlstr + "<button class='layui-btn layui-btn-small layui-btn-primary' type='button' value='"+obj2.id+"''>"+obj2.title+"</button>";
											}
										}
									}
									if(htmlstr != '') {
										htmlstr = htmlstr + "</td></tr>";
										$('#spec_ctr1').append(htmlstr);
									}
								} else {
									$(".spec_ctr").css("display","none");
								}
							}, error: function(){
								location.hash='/system/404';
							}
			    		});
				  		
				  		layui.sessionData('table', {
			     		  	key: 'is_key',
			     		  	value: '0'
			     		});
			  		}
			  	});
			  	
			  	//监听
			  	$('#spec_ctr').delegate('button', 'click', function() {
			  		var other = $(this);
					if(other.hasClass('layui-btn-primary')) {
						//点击加载规格详情
			 			other.removeClass('layui-btn-primary');
						
			 			var session = layui.sessionData('table');
			 			if(session.is_key == '1') {
			 				if(session.goodsval != '') {
			 					var vv = '-1';
			 					var acc = session.goodsval.split(",");
			 					for(var i=0; i < acc.length; i++) {
			 						if(parseInt(acc[i]) == parseInt(other.val())) {
			 							vv = acc[i];
			 						}
			 					}
			 					if(vv != '-1') {
			 						var list2 = session.goodstype;
			 						if(list2 != null) {
										var val = '';
										for(var i=0; i < list2.length; i++) {
											var obj = list2[i];
											var lli = obj.list;
											if(lli != null && lli.length > 0) {
												for(var j=0; j < lli.length; j++) {
													var obj2 = lli[j];
													if(obj2.id == parseInt(vv)) {
														var obj3 = obj2.price;
														$('#spec_input_tab_2').append("<tr><td>"+obj2.title+"</td><td>"
																+ "<input name='prices' value="+obj3.price+" datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
																+ "</td><td><input name='stocks' value="+obj3.stock+" type='number'></td><td>"
																+ "<input name='skus' value="+obj3.sku+">"
																+ "<input name='spec_key_ids' type='hidden' value="+obj2.id+"></td></tr>");
														val = val + obj2.id + ',';
														$("#spec_item_ctr").css("display","block");
													}
												}
											}
										}
										
										layui.sessionData('table', {
							     		  	key: 'goodsedit',
							     		  	value: session.goodsedit + val
							     		});
									} else {
										$(".spec_ctr").css("display","none");
									}
			 					} else {
			 						if(data9 != null) {
										for(var j=0; j < data9.length; j++) {
											var obj2 = data9[j];
											for(var i=0; i < obj2.list.length; i++) {
												var obj = obj2.list[i];
												if(parseInt(other.val()) == obj.id) {
													$('#spec_input_tab_2').append("<tr><td>"+obj.title+"</td><td>"
															+ "<input name='prices' value='0' datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
															+ "</td><td><input name='stocks' value='99' type='number'></td><td>"
															+ "<input name='skus' value=''>"
															+ "<input name='spec_key_ids' type='hidden' value="+obj.id+"></td></tr>");
													$("#spec_item_ctr").css("display","block");
													break;
												}
											}
										}
										
										if(session.goodsedit != '') {
											var rs = 1;
											var arr2 = session.goodsedit.split(",");
											for(var i=0; i < arr2.length; i++) {
												if(arr2[i] != '' && parseInt(arr2[i]) == other.val()) {
													rs = 0;
													break;
												}
											}
											
											if(rs == 1) {
												layui.sessionData('table', {
									     		  	key: 'goodsedit',
									     		  	value: session.goodsedit + other.val() + ','
									     		});
											}
										} else {
											layui.sessionData('table', {
								     		  	key: 'goodsedit',
								     		  	value: other.val() + ','
								     		});
										}
									} else {
										layui.sessionData('table', {
							     		  	key: 'goodsedit',
							     		  	value: ''
							     		});
									}
			 					}
			 				}
			 			} else {
			 				if(data9 != null) {
								for(var j=0; j < data9.length; j++) {
									var obj2 = data9[j];
									for(var i=0; i < obj2.list.length; i++) {
										var obj = obj2.list[i];
										if(parseInt(other.val()) == obj.id) {
											$('#spec_input_tab_2').append("<tr><td>"+obj.title+"</td><td>"
													+ "<input name='prices' value='0' datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
													+ "</td><td><input name='stocks' value='99' type='number'></td><td>"
													+ "<input name='skus' value=''>"
													+ "<input name='spec_key_ids' type='hidden' value="+obj.id+"></td></tr>");
											$("#spec_item_ctr").css("display","block");
											break;
										}
									}
								}
								
								if(session.goodsedit != '') {
									var rs = 1;
									var arr2 = session.goodsedit.split(",");
									for(var i=0; i < arr2.length; i++) {
										if(arr2[i] != '' && parseInt(arr2[i]) == other.val()) {
											rs = 0;
											break;
										}
									}
									
									if(rs == 1) {
										layui.sessionData('table', {
							     		  	key: 'goodsedit',
							     		  	value: session.goodsedit + other.val() + ','
							     		});
									}
								} else {
									layui.sessionData('table', {
						     		  	key: 'goodsedit',
						     		  	value: other.val() + ','
						     		});
								}
							}
			 			}
					} else {
						//点击移除规格详情
			            $(this).addClass('layui-btn-primary');
						
						var val = '';
						var session = layui.sessionData('table');
						if(session.goodsedit != '') {
							var arr2 = session.goodsedit.split(",");
							for(var i=0; i < arr2.length; i++) {
								if(arr2[i] != '' && parseInt(arr2[i]) != other.val()) {
									val = val + arr2[i]+",";
								}
							}
						}
						
			            $('#spec_input_tab_2').empty();
			            if(val != '') {
			            	if(session.is_key == '1') {
			            		var vv = '';
			            		var vc = '';
			            		if(session.goodsval != '') {
				 					var acc = session.goodsval.split(",");
				 					var arr5 = val.split(",");
				 					for(var j=0; j < arr5.length; j++) {
				 						var rr = '1';
				 						for(var i=0; i < acc.length; i++) {
					 						if(parseInt(acc[i]) == parseInt(arr5[j])) {
					 							vc = vc + arr5[j] + ',';
					 							rr = '0';
					 						} 
					 					}
				 						if(rr == '1') {
				 							if(arr5[j] != '') {
					 							vv = vv + arr5[j] + ',';
					 						}
				 						}
				 					}
			            		}
			            		//旧数据显示
			            		if(vc != '') {
			            			var list2 = session.goodstype;
			 						if(list2 != null) {
										var arr4 = vc.split(",");
										for(var i=0; i < list2.length; i++) {
											var obj = list2[i];
											var lli = obj.list;
											if(lli != null && lli.length > 0) {
												for(var j=0; j < lli.length; j++) {
													var obj2 = lli[j];
													for(var z=0; z < arr4.length; z++) {
														if(arr4[z] != '' && obj2.id == parseInt(arr4[z])) {
															var obj3 = obj2.price;
															$('#spec_input_tab_2').append("<tr><td>"+obj2.title+"</td><td>"
																	+ "<input name='prices' value="+obj3.price+" datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
																	+ "</td><td><input name='stocks' value="+obj3.stock+" type='number'></td><td>"
																	+ "<input name='skus' value="+obj3.sku+">"
																	+ "<input name='spec_key_ids' type='hidden' value="+obj2.id+"></td></tr>");
															$("#spec_item_ctr").css("display","block");
														}
													}
												}
											}
										}
										
									}
			            		}
			            		
			            		//新增显示
			            		if(vv != '') {
			            			if(data9 != null) {
					            		var val2 = vv.split(",");
										for(var j=0; j < data9.length; j++) {
											var obj2 = data9[j];
											for(var i=0; i < obj2.list.length; i++) {
												var obj = obj2.list[i];
												for(var z=0; z < val2.length; z++) {
													if(parseInt(val2[z]) == obj.id) {
														$('#spec_input_tab_2').append("<tr><td>"+obj.title+"</td><td>"
																+ "<input name='prices' value='0' datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
																+ "</td><td><input name='stocks' value='99' type='number'></td><td>"
																+ "<input name='skus' value=''>"
																+ "<input name='spec_key_ids' type='hidden' value="+obj.id+"></td></tr>");
														$("#spec_item_ctr").css("display","block");
														break;
													}
												}
											}
										}
									}
			            		}
			            		
			            		layui.sessionData('table', {
					     		  	key: 'goodsedit',
					     		  	value: val
					     		});
			            	} else {
			            		if(data9 != null) {
				            		var val2 = val.split(",");
									for(var j=0; j < data9.length; j++) {
										var obj2 = data9[j];
										for(var i=0; i < obj2.list.length; i++) {
											var obj = obj2.list[i];
											for(var z=0; z < val2.length; z++) {
												if(parseInt(val2[z]) == obj.id) {
													$('#spec_input_tab_2').append("<tr><td>"+obj.title+"</td><td>"
															+ "<input name='prices' value='0' datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
															+ "</td><td><input name='stocks' value='99' type='number'></td><td>"
															+ "<input name='skus' value=''>"
															+ "<input name='spec_key_ids' type='hidden' value="+obj.id+"></td></tr>");
													$("#spec_item_ctr").css("display","block");
													break;
												}
											}
										}
									}
								} else {
									$("#spec_item_ctr").css("display","none");
								}
			            	}
			            } else {
			            	$("#spec_item_ctr").css("display","none");
			            }
			            
			            layui.sessionData('table', {
			     		  	key: 'goodsedit',
			     		  	value: val
			     		});
			        }
				});
			  	
			  	//提交监听
   				form.on('submit(OM-form-submit)', function(data){
   		    		var field = data.field;
   		    		
   		    		//field.info = JSON.stringify(layedit.getContent(layedit_index));
   		    		
   		    		if(field.is_sale === 'on') {
   		    			field.is_sale = 1;
   		    		} else {
   		    			field.is_sale = 0;
   		    		}
   		    		if(field.is_free === 'on') {
   		    			field.is_free = 1;
   		    		} else {
   		    			field.is_free = 0;
   		    		}
   		    		if(field.is_top === 'on') {
   		    			field.is_top = 1;
   		    		} else {
   		    			field.is_top = 0;
   		    		}
   		    		if(field.is_new === 'on') {
   		    			field.is_new = 1;
   		    		} else {
   		    			field.is_new = 0;
   		    		}
   		    		if(field.is_hot === 'on') {
   		    			field.is_hot = 1;
   		    		} else {
   		    			field.is_hot = 0;
   		    		}
   		    		if(field.is_restrict === 'on') {
   		    			field.is_restrict = 1;
   		    		} else {
   		    			field.is_restrict = 0;
   		    		}
   		    		
   		    		var m_slideList=new Array();
   		    		m_slideList=$('input[name="m_slide"]').map(function(){ return $(this).val(); }).get();
   		    		field.slide = m_slideList.join(",");
   		    		
   		    		var stocksList=new Array();
   		    		stocksList=$('input[name="stocks"]').map(function(){ return $(this).val(); }).get();
   		    		field.stocks = stocksList.join(",");
   		    		
   		    		var spec_key_idsList=new Array();
   		    		spec_key_idsList=$('input[name="spec_key_ids"]').map(function(){ return $(this).val(); }).get();
   		    		field.spec_key_ids = spec_key_idsList.join(",");
   		    		
   		    		var pricesList=new Array();
   		    		pricesList=$('input[name="prices"]').map(function(){ return $(this).val(); }).get();
   		    		field.prices = pricesList.join(",");
   		    		
   		    		if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(field.cost_price))) {
   		    			layer.msg('成本价输入有误');
   		    			return false;
   		    		}
   		    		if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(field.price))) {
   		    			layer.msg('本站价输入有误');
   		    			return false;
   		    		}
   		    		if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(field.markey_price))) {
   		    			layer.msg('市场价输入有误');
   		    			return false;
   		    		}
   		    		if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(field.weight))) {
   		    			layer.msg('商品重量输入有误');
   		    			return false;
   		    		}
   		    		if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(field.tc))) {
   		    			layer.msg('商品提成输入有误');
   		    			return false;
   		    		}
   		    		if(field.stock < 1){
   		    			layer.msg('总库存输入有误');
   		    			return false;
   		    		}
   		    		if(field.jf < 0){
   		    			layer.msg('赠送积分输入有误');
   		    			return false;
   		    		}
   		    		if(field.is_hits < 0){
   		    			layer.msg('人气输入有误');
   		    			return false;
   		    		}
   		    		if(field.restrict_unit < 0){
   		    			layer.msg('限购数量输入有误');
   		    			return false;
   		    		}
   		    		if(field.initial_sales < 0){
   		    			layer.msg('原始销量输入有误');
   		    			return false;
   		    		}
   		    		if(field.sequence < 0){
   		    			layer.msg('排序输入有误');
   		    			return false;
   		    		}
   		    		
   		    		//提交
   		    		$.ajax({
   		    			type: "POST",
   						url: "/manager/goods/article/update",
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
	
	//积分商品
	table.render({
		elem: '#OM-goods-integral',
		url: '/manager/goods/integral/info', //模拟接口
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 100,},
			{field: 'title', title: '商品标题'},
			{field: 'sn', title: '货号'},
			{field: 'category', title: '所属分类'},
			{field: 'price', title: '赠送积分'},
			{field: 'stock', title: '库存'},
			{field: 'is_sale', title: '是否上架'},
			{field: 'is_new', title: '是否新品'},
			{field: 'is_top', title: '是否推荐'},
			{field: 'is_hot', title: '是否热卖'},
			{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#toolbar-goods-integral'}
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
  
	//积分商品_监听工具条
	table.on('tool(OM-goods-integral)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/goods/integral/delete",
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
	                        	table.reload('OM-goods-integral', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			view('OM-module-temp').render('goods/component/integral-goods-edit', data).done(function() {
				//动态加载
   				form.render();
				var data9 = '';
				//初始化类型
  				$('#m_cat_id').append("<option value=''>请选择商品分类</option>");
  				$.ajax({
					type: "POST",
					url: "/manager/goods/categroy/info/all",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify({}),
					success: function(data2){
						var fid = 0;
						for(var i=0; i < data2.data.length; i++) {
							var obj = data2.data[i];
							if(data.cat_id == obj.id || data.cat_id == obj.sid) {
								if(fid == 0 || obj.id != fid) {
									$('#m_cat_id').append("<option value="+obj.id+" selected>"+obj.title+"</option>");
									fid = obj.id;
								} else {
									$('#m_cat_id').append("<option value="+obj.sid+" selected> └ "+obj.stitle+"</option>");
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
  				//初始化人群
  				$('#m_person_id').append("<option value=''>请选择所属人群</option>");
  				$.ajax({
					type: "POST",
					url: "/manager/goods/person/info/all",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify({}),
					success: function(data2){
						var fid = 0;
						for(var i=0; i < data2.data.length; i++) {
							var obj = data2.data[i];
							if(data.person_id == obj.id || data.person_id == obj.sid) {
								if(fid == 0 || obj.id != fid) {
									$('#m_person_id').append("<option value="+obj.id+" selected>"+obj.title+"</option>");
									fid = obj.id;
								} else {
									$('#m_person_id').append("<option value="+obj.sid+" selected> └ "+obj.stitle+"</option>");
								}
							} else {
								if(fid == 0 || obj.id != fid) {
									$('#m_person_id').append("<option value="+obj.id+">"+obj.title+"</option>");
									fid = obj.id;
								} else {
									$('#m_person_id').append("<option value="+obj.sid+"> └ "+obj.stitle+"</option>");
								}
							}
						}
					}, error: function(){
						location.hash='/system/404';
					}
	    		});
  				//初始化品牌
  				$('#m_brand_id').append("<option value='0'>请选择所属品牌</option>");
  				$.ajax({
					type: "POST",
					url: "/manager/goods/brand/info",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify({"page":1,"size":1000}),
					success: function(data2){
						var fid = 0;
						for(var i=0; i < data2.data.length; i++) {
							var obj = data2.data[i];
							if(data.brand_id == obj.id) {
								$('#m_brand_id').append("<option value="+obj.id+" selected>"+obj.title+"</option>");
							} else {
								$('#m_brand_id').append("<option value="+obj.id+">"+obj.title+"</option>");
							}
						}
					}, error: function(){
						location.hash='/system/404';
					}
	    		});
  				//初始化商品模型
  				$('#m_type').append("<option value='0'>请选择商品模型</option>");
  				$.ajax({
					type: "POST",
					url: "/manager/goods/model/info",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify({"page":1,"size":1000}),
					success: function(data2){
						var fid = 0;
						for(var i=0; i < data2.data.length; i++) {
							var obj = data2.data[i];
							if(obj.id == data.spec_type) {
								$('#m_type').append("<option value="+obj.id+" selected>"+obj.title+"</option>");
								
								$(".spec_ctr").css("display","block");
								layui.sessionData('table', {
					     		  	key: 'inteedd',
					     		  	value: ''
					     		});
								
								//重新加载
						  		$.ajax({
									type: "POST",
									url: "/manager/goods/spec/price",
									dataType:'json',//预期服务器返回的数据类型
									contentType: "application/json; charset=utf-8",
									async: false,
									data: JSON.stringify({"inte_id":data.id,"type_id":data.spec_type}),
									success: function(data2){
										if(data2.result != null) {
											var htmlstr = '';
											var val = '';
											var data9 = data2.result;
											for(var i=0; i < data2.result.length; i++) {
												var obj = data2.result[i];
												htmlstr = htmlstr + "<tr><td>"+obj.title+"</td><td>"
												var lli = obj.list;
												if(lli != null && lli.length > 0) {
													for(var j=0; j < lli.length; j++) {
														var obj2 = lli[j];
														if(obj2.res == "1") {
															var obj3 = obj2.price;
															htmlstr = htmlstr + "<button class='layui-btn layui-btn-small' type='button' value='"+obj2.id+"''>"+obj2.title+"</button>";
															$('#spec_input_tab_2').append("<tr><td>"+obj2.title+"</td><td>"
																	+ "<input name='prices' value="+obj3.price+" data2type='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
																	+ "</td><td><input name='stocks' value="+obj3.stock+" type='number'></td><td>"
																	+ "<input name='skus' value="+obj3.sku+">"
																	+ "<input name='spec_key_ids' type='hidden' value="+obj2.id+"></td></tr>");
															val = val + obj2.id + ',' ;
															$("#spec_item_ctr").css("display","block");
														} else {
															htmlstr = htmlstr + "<button class='layui-btn layui-btn-small layui-btn-primary' type='button' value='"+obj2.id+"''>"+obj2.title+"</button>";
														}
													}
													htmlstr = htmlstr + "</td>";
												}
											}
											if(htmlstr != '') {
												htmlstr = htmlstr + "</tr>";
												$('#spec_ctr1').append(htmlstr);
											}
											
											layui.sessionData('table', {
								     		  	key: 'inteedit',
								     		  	value: val
								     		});
											layui.sessionData('table', {
								     		  	key: 'inteval',
								     		  	value: val
								     		});
										} else {
											$(".spec_ctr").css("display","none");
										}
										
										layui.sessionData('table', {
							     		  	key: 'intetype',
							     		  	value: data2.result
							     		});
										
										layui.sessionData('table', {
							     		  	key: 'keys',
							     		  	value: data.spec_type
							     		});
										layui.sessionData('table', {
							     		  	key: 'is_key',
							     		  	value: '1'
							     		});
									}, error: function(){
										location.hash='/system/404';
									}
					    		});
							} else {
								$('#m_type').append("<option value="+obj.id+">"+obj.title+"</option>");
							}
						}
						
						//重新加载
				  		$.ajax({
							type: "POST",
							url: "/manager/goods/spec/iteminfo",
							dataType:'json',//预期服务器返回的数据类型
							contentType: "application/json; charset=utf-8",
							async: false,
							data: JSON.stringify({"type_id":data.spec_type, "page":1,"size":1000}),
							success: function(data){
								if(data.result != null) {
									data9 = data.result;
								}
							}, error: function(){
								location.hash='/system/404';
							}
			    		});
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
				      					+ "<img src='/upload/pic/"+val+"'>"
				      					+ "<div class='pic_del' onclick=delpic('"+val+"')>删除</div>"
				      					+ "<input type='hidden' name='m_slide' value='"+val+"'></div>");
							}
						}
					}
				}
  				
   				//富文本框
  				/*layedit.set({
  				  	uploadImage: {
  				    	url: '/editor/upload', //接口url
  				    	type: 'post' //默认post
  				  	}
  				});
  				
  				//富文本框初始化
			  	var layedit_index = layedit.build('editor');*/ //建立编辑器
			  	//动态加载
   				form.render();
   				
			  	//select 监听事件
			  	form.on('select(m_type)', function(data){
			  		
			  		$('#spec_input_tab_2').empty();
			  		$('#spec_ctr1').empty();
			  		$(".spec_ctr").css("display","block");
			  		$("#spec_item_ctr").css("display","none");
			  		
			  		if(data.value == 0) {
			  			$(".spec_ctr").css("display","none");
			  			return false;
			  		}
			  		
			  		var session = layui.sessionData('table');
			  		if(session.keys != '' && parseInt(session.keys) == parseInt(data.value)) {
			  			var result = session.intetype;
			  			if(result != null) {
			  				data9 = result;
							var htmlstr = '';
							var val = '';
							for(var i=0; i < result.length; i++) {
								var obj = result[i];
								htmlstr = htmlstr + "<tr><td>"+obj.title+"</td><td>"
								var lli = obj.list;
								if(lli != null && lli.length > 0) {
									for(var j=0; j < lli.length; j++) {
										var obj2 = lli[j];
										if(obj2.res == "1") {
											var obj3 = obj2.price;
											htmlstr = htmlstr + "<button class='layui-btn layui-btn-small' type='button' value='"+obj2.id+"''>"+obj2.title+"</button>";
											$('#spec_input_tab_2').append("<tr><td>"+obj2.title+"</td><td>"
													+ "<input name='prices' value="+obj3.price+" datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
													+ "</td><td><input name='stocks' value="+obj3.stock+" type='number'></td><td>"
													+ "<input name='skus' value="+obj3.sku+">"
													+ "<input name='spec_key_ids' type='hidden' value="+obj2.id+"></td></tr>");
											val = val + obj2.id + ',' ;
											$("#spec_item_ctr").css("display","block");
										} else {
											htmlstr = htmlstr + "<button class='layui-btn layui-btn-small layui-btn-primary' type='button' value='"+obj2.id+"''>"+obj2.title+"</button>";
										}
									}
									htmlstr = htmlstr + "</td>";
								}
							}
							if(htmlstr != '') {
								htmlstr = htmlstr + "</tr>";
								$('#spec_ctr1').append(htmlstr);
							}
							
							layui.sessionData('table', {
				     		  	key: 'inteedit',
				     		  	value: val
				     		});
						} else {
							$(".spec_ctr").css("display","none");
						}
			  			
			  			layui.sessionData('table', {
			     		  	key: 'is_key',
			     		  	value: '1'
			     		});
			  		} else {
			  			//重新加载
				  		$.ajax({
							type: "POST",
							url: "/manager/goods/spec/iteminfo",
							dataType:'json',//预期服务器返回的数据类型
							contentType: "application/json; charset=utf-8",
							async: false,
							data: JSON.stringify({"type_id":data.value, "page":1,"size":1000}),
							success: function(data){
								if(data.result != null) {
									var htmlstr = '';
									data9 = data.result;
									for(var i=0; i < data.result.length; i++) {
										var obj = data.result[i];
										htmlstr = htmlstr + "<tr><td>"+obj.title+"</td><td>"
										var lli = obj.list;
										if(lli != null && lli.length > 0) {
											for(var j=0; j < lli.length; j++) {
												var obj2 = lli[j];
												htmlstr = htmlstr + "<button class='layui-btn layui-btn-small layui-btn-primary' type='button' value='"+obj2.id+"''>"+obj2.title+"</button>";
											}
										}
									}
									if(htmlstr != '') {
										htmlstr = htmlstr + "</td></tr>";
										$('#spec_ctr1').append(htmlstr);
									}
								} else {
									$(".spec_ctr").css("display","none");
								}
							}, error: function(){
								location.hash='/system/404';
							}
			    		});
				  		
				  		layui.sessionData('table', {
			     		  	key: 'is_key',
			     		  	value: '0'
			     		});
			  		}
			  	});
			  	
			  	//监听
			  	$('#spec_ctr').delegate('button', 'click', function() {
			  		var other = $(this);
					if(other.hasClass('layui-btn-primary')) {
						//点击加载规格详情
			 			other.removeClass('layui-btn-primary');
						
			 			var session = layui.sessionData('table');
			 			if(session.is_key == '1') {
			 				if(session.inteval != '') {
			 					var vv = '-1';
			 					var acc = session.inteval.split(",");
			 					for(var i=0; i < acc.length; i++) {
			 						if(parseInt(acc[i]) == parseInt(other.val())) {
			 							vv = acc[i];
			 						}
			 					}
			 					if(vv != '-1') {
			 						var list2 = session.intetype;
			 						if(list2 != null) {
										var val = '';
										for(var i=0; i < list2.length; i++) {
											var obj = list2[i];
											var lli = obj.list;
											if(lli != null && lli.length > 0) {
												for(var j=0; j < lli.length; j++) {
													var obj2 = lli[j];
													if(obj2.id == parseInt(vv)) {
														var obj3 = obj2.price;
														$('#spec_input_tab_2').append("<tr><td>"+obj2.title+"</td><td>"
																+ "<input name='prices' value="+obj3.price+" datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
																+ "</td><td><input name='stocks' value="+obj3.stock+" type='number'></td><td>"
																+ "<input name='skus' value="+obj3.sku+">"
																+ "<input name='spec_key_ids' type='hidden' value="+obj2.id+"></td></tr>");
														val = val + obj2.id + ',';
														$("#spec_item_ctr").css("display","block");
													}
												}
											}
										}
										
										layui.sessionData('table', {
							     		  	key: 'inteedit',
							     		  	value: session.inteedit + val
							     		});
									} else {
										$(".spec_ctr").css("display","none");
									}
			 					} else {
			 						if(data9 != null) {
										for(var j=0; j < data9.length; j++) {
											var obj2 = data9[j];
											for(var i=0; i < obj2.list.length; i++) {
												var obj = obj2.list[i];
												if(parseInt(other.val()) == obj.id) {
													$('#spec_input_tab_2').append("<tr><td>"+obj.title+"</td><td>"
															+ "<input name='prices' value='0' datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
															+ "</td><td><input name='stocks' value='99' type='number'></td><td>"
															+ "<input name='skus' value=''>"
															+ "<input name='spec_key_ids' type='hidden' value="+obj.id+"></td></tr>");
													$("#spec_item_ctr").css("display","block");
													break;
												}
											}
										}
										
										if(session.inteedit != '') {
											var rs = 1;
											var arr2 = session.inteedit.split(",");
											for(var i=0; i < arr2.length; i++) {
												if(arr2[i] != '' && parseInt(arr2[i]) == other.val()) {
													rs = 0;
													break;
												}
											}
											
											if(rs == 1) {
												layui.sessionData('table', {
									     		  	key: 'inteedit',
									     		  	value: session.inteedit + other.val() + ','
									     		});
											}
										} else {
											layui.sessionData('table', {
								     		  	key: 'inteedit',
								     		  	value: other.val() + ','
								     		});
										}
									} else {
										layui.sessionData('table', {
							     		  	key: 'inteedit',
							     		  	value: ''
							     		});
									}
			 					}
			 				}
			 			} else {
			 				if(data9 != null) {
								for(var j=0; j < data9.length; j++) {
									var obj2 = data9[j];
									for(var i=0; i < obj2.list.length; i++) {
										var obj = obj2.list[i];
										if(parseInt(other.val()) == obj.id) {
											$('#spec_input_tab_2').append("<tr><td>"+obj.title+"</td><td>"
													+ "<input name='prices' value='0' datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
													+ "</td><td><input name='stocks' value='99' type='number'></td><td>"
													+ "<input name='skus' value=''>"
													+ "<input name='spec_key_ids' type='hidden' value="+obj.id+"></td></tr>");
											$("#spec_item_ctr").css("display","block");
											break;
										}
									}
								}
								
								if(session.inteedit != '') {
									var rs = 1;
									var arr2 = session.inteedit.split(",");
									for(var i=0; i < arr2.length; i++) {
										if(arr2[i] != '' && parseInt(arr2[i]) == other.val()) {
											rs = 0;
											break;
										}
									}
									
									if(rs == 1) {
										layui.sessionData('table', {
							     		  	key: 'inteedit',
							     		  	value: session.inteedit + other.val() + ','
							     		});
									}
								} else {
									layui.sessionData('table', {
						     		  	key: 'inteedit',
						     		  	value: other.val() + ','
						     		});
								}
							}
			 			}
					} else {
						//点击移除规格详情
			            $(this).addClass('layui-btn-primary');
						
						var val = '';
						var session = layui.sessionData('table');
						if(session.inteedit != '') {
							var arr2 = session.inteedit.split(",");
							for(var i=0; i < arr2.length; i++) {
								if(arr2[i] != '' && parseInt(arr2[i]) != other.val()) {
									val = val + arr2[i]+",";
								}
							}
						}
						
			            $('#spec_input_tab_2').empty();
			            if(val != '') {
			            	if(session.is_key == '1') {
			            		var vv = '';
			            		var vc = '';
			            		if(session.inteval != '') {
				 					var acc = session.inteval.split(",");
				 					var arr5 = val.split(",");
				 					for(var j=0; j < arr5.length; j++) {
				 						var rr = '1';
				 						for(var i=0; i < acc.length; i++) {
					 						if(parseInt(acc[i]) == parseInt(arr5[j])) {
					 							vc = vc + arr5[j] + ',';
					 							rr = '0';
					 						} 
					 					}
				 						if(rr == '1') {
				 							if(arr5[j] != '') {
					 							vv = vv + arr5[j] + ',';
					 						}
				 						}
				 					}
			            		}
			            		//旧数据显示
			            		if(vc != '') {
			            			var list2 = session.intetype;
			 						if(list2 != null) {
										var arr4 = vc.split(",");
										for(var i=0; i < list2.length; i++) {
											var obj = list2[i];
											var lli = obj.list;
											if(lli != null && lli.length > 0) {
												for(var j=0; j < lli.length; j++) {
													var obj2 = lli[j];
													for(var z=0; z < arr4.length; z++) {
														if(arr4[z] != '' && obj2.id == parseInt(arr4[z])) {
															var obj3 = obj2.price;
															$('#spec_input_tab_2').append("<tr><td>"+obj2.title+"</td><td>"
																	+ "<input name='prices' value="+obj3.price+" datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
																	+ "</td><td><input name='stocks' value="+obj3.stock+" type='number'></td><td>"
																	+ "<input name='skus' value="+obj3.sku+">"
																	+ "<input name='spec_key_ids' type='hidden' value="+obj2.id+"></td></tr>");
															$("#spec_item_ctr").css("display","block");
														}
													}
												}
											}
										}
										
									}
			            		}
			            		
			            		//新增显示
			            		if(vv != '') {
			            			if(data9 != null) {
					            		var val2 = vv.split(",");
										for(var j=0; j < data9.length; j++) {
											var obj2 = data9[j];
											for(var i=0; i < obj2.list.length; i++) {
												var obj = obj2.list[i];
												for(var z=0; z < val2.length; z++) {
													if(parseInt(val2[z]) == obj.id) {
														$('#spec_input_tab_2').append("<tr><td>"+obj.title+"</td><td>"
																+ "<input name='prices' value='0' datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
																+ "</td><td><input name='stocks' value='99' type='number'></td><td>"
																+ "<input name='skus' value=''>"
																+ "<input name='spec_key_ids' type='hidden' value="+obj.id+"></td></tr>");
														$("#spec_item_ctr").css("display","block");
														break;
													}
												}
											}
										}
									}
			            		}
			            		
			            		layui.sessionData('table', {
					     		  	key: 'inteedit',
					     		  	value: val
					     		});
			            	} else {
			            		if(data9 != null) {
				            		var val2 = val.split(",");
									for(var j=0; j < data9.length; j++) {
										var obj2 = data9[j];
										for(var i=0; i < obj2.list.length; i++) {
											var obj = obj2.list[i];
											for(var z=0; z < val2.length; z++) {
												if(parseInt(val2[z]) == obj.id) {
													$('#spec_input_tab_2').append("<tr><td>"+obj.title+"</td><td>"
															+ "<input name='prices' value='0' datatype='/^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/'>"
															+ "</td><td><input name='stocks' value='99' type='number'></td><td>"
															+ "<input name='skus' value=''>"
															+ "<input name='spec_key_ids' type='hidden' value="+obj.id+"></td></tr>");
													$("#spec_item_ctr").css("display","block");
													break;
												}
											}
										}
									}
								} else {
									$("#spec_item_ctr").css("display","none");
								}
			            	}
			            } else {
			            	$("#spec_item_ctr").css("display","none");
			            }
			            
			            layui.sessionData('table', {
			     		  	key: 'inteedit',
			     		  	value: val
			     		});
			        }
				});
			  	
			  	//提交监听
   				form.on('submit(OM-form-submit)', function(data){
   		    		var field = data.field;
   		    		
   		    		//field.info = JSON.stringify(layedit.getContent(layedit_index));
   		    		
   		    		if(field.is_sale === 'on') {
   		    			field.is_sale = 1;
   		    		} else {
   		    			field.is_sale = 0;
   		    		}
   		    		if(field.is_free === 'on') {
   		    			field.is_free = 1;
   		    		} else {
   		    			field.is_free = 0;
   		    		}
   		    		if(field.is_top === 'on') {
   		    			field.is_top = 1;
   		    		} else {
   		    			field.is_top = 0;
   		    		}
   		    		if(field.is_new === 'on') {
   		    			field.is_new = 1;
   		    		} else {
   		    			field.is_new = 0;
   		    		}
   		    		if(field.is_hot === 'on') {
   		    			field.is_hot = 1;
   		    		} else {
   		    			field.is_hot = 0;
   		    		}
   		    		
   		    		var m_slideList=new Array();
   		    		m_slideList=$('input[name="m_slide"]').map(function(){ return $(this).val(); }).get();
   		    		field.slide = m_slideList.join(",");
   		    		
   		    		var stocksList=new Array();
   		    		stocksList=$('input[name="stocks"]').map(function(){ return $(this).val(); }).get();
   		    		field.stocks = stocksList.join(",");
   		    		
   		    		var spec_key_idsList=new Array();
   		    		spec_key_idsList=$('input[name="spec_key_ids"]').map(function(){ return $(this).val(); }).get();
   		    		field.spec_key_ids = spec_key_idsList.join(",");
   		    		
   		    		var pricesList=new Array();
   		    		pricesList=$('input[name="prices"]').map(function(){ return $(this).val(); }).get();
   		    		field.prices = pricesList.join(",");
   		    		
   		    		if(field.use_jf < 1) {
   		    			layer.msg('使用积分输入有误');
   		    			return false;
   		    		}
   		    		if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(field.weight))) {
   		    			layer.msg('商品重量输入有误');
   		    			return false;
   		    		}
   		    		if(field.stock < 1){
   		    			layer.msg('总库存输入有误');
   		    			return false;
   		    		}
   		    		if(field.jf < 0){
   		    			layer.msg('赠送积分输入有误');
   		    			return false;
   		    		}
   		    		if(field.initial_sales < 0){
   		    			layer.msg('原始销量输入有误');
   		    			return false;
   		    		}
   		    		if(field.sequence < 0){
   		    			layer.msg('排序输入有误');
   		    			return false;
   		    		}
   		    		
   		    		//提交
   		    		$.ajax({
   		    			type: "POST",
   						url: "/manager/goods/integral/update",
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
	
	//人群分类
	table.render({
		elem: '#OM-goods-person',
		url: '/manager/goods/person/info', //模拟接口
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号'},
			{field: 'title', title: '名称'},
			{field: 'pic', title: '图片', templet: '#imgTpl', width: 150},
			{field: 'sequence', title: '排序'},
			{field: 'is_top', title: '是否推荐'},
			{field: 'is_show', title: '是否显示'},
			{title: '操作', width: 350, align: 'center', fixed: 'right', toolbar: '#toolbar-goods-person-data'},
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
	table.on('tool(OM-goods-person)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				//数据封装
				var req = {"id": data.id};

				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/goods/person/delete",
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
	                        	table.reload('OM-goods-person', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit'){
			view('OM-module-temp').render('goods/component/person-edit', data).done(function() {
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		
		    		if(field.is_show === 'on') {
		    			field.is_show = 1;
		    		} else {
		    			field.is_show = 0;
		    		}
		    		if(field.is_top === 'on') {
		    			field.is_top = 1;
		    		} else {
		    			field.is_top = 0;
		    		}
		    		if(field.sequence < 0) {
	      				layer.msg('请填写正确的排序');
	      				return false;
	      			}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/goods/person/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/goods/person');
	                        	}
	                   	 	});
						}, error: function(){
							location.hash='/system/404';
						}
		    		});
		    		return false;
		  		});
			});
		} else if(obj.event === 'add-son'){
			view('OM-module-temp').render('goods/component/person-son-add', data).done(function() {
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		
		    		if(field.is_show === 'on') {
		    			field.is_show = 1;
		    		} else {
		    			field.is_show = 0;
		    		}
		    		if(field.is_top === 'on') {
		    			field.is_top = 1;
		    		} else {
		    			field.is_top = 0;
		    		}
		    		if(field.sequence < 0) {
	      				layer.msg('请填写正确的排序');
	      				return false;
	      			}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/goods/person/insert",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/goods/person');
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
			view('OM-module-temp').render('goods/component/person-son', data).done(function() {
				//人群分类
				table.render({
					elem: '#OM-goods-person-son',
					url: '/manager/goods/person/info',
					cols: [[
						{type: 'checkbox', fixed: 'left'},
						{field: 'id', title: '编号'},
						{field: 'title', title: '名称'},
						{field: 'pic', title: '图片', templet: '#imgTpl', width: 150},
						{field: 'sequence', title: '排序'},
						{field: 'is_top', title: '是否推荐'},
						{field: 'is_show', title: '是否显示'},
						{title: '操作', width: 350, align: 'center', fixed: 'right', toolbar: '#toolbar-goods-person-data-son'},
					]],
					where: {"id":data.id},
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
				table.on('tool(OM-goods-person-son)', function(obj){
					var data2 = obj.data;
					if(obj.event === 'del'){
						layer.confirm('确定删除此信息？', function(index){
							layer.close(index);
							//数据封装
							var req = {"id": data.id};
							//执行 Ajax 后重载
				          	$.ajax({
								type: "POST",
								url: "/manager/goods/person/delete",
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
				                        	table.reload('OM-goods-person', null);
				                        }
				                    });
								}, error: function(){
									location.hash='/system/404';
								}
					    	});
						});
					} else if(obj.event === 'edit'){
						data2.parent_name = data.title;
						view('OM-module-temp').render('goods/component/person-son-edit', data2).done(function() {
							//动态加载
							form.render();
							//提交监听
							form.on('submit(OM-form-submit)', function(data){
					    		var field = data.field;
					    		
					    		if(field.is_show === 'on') {
					    			field.is_show = 1;
					    		} else {
					    			field.is_show = 0;
					    		}
					    		if(field.is_top === 'on') {
					    			field.is_top = 1;
					    		} else {
					    			field.is_top = 0;
					    		}
					    		if(field.sequence < 0) {
				      				layer.msg('请填写正确的排序');
				      				return false;
				      			}
					    		
					    		//提交
					    		$.ajax({
					    			type: "POST",
									url: "/manager/goods/person/update",
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
				        	    				window.history.pushState(null, null, '/start/index.html#/goods/person');
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
	
	//商品分类
	table.render({
		elem: '#OM-goods-categroy',
		url: '/manager/goods/categroy/info', //模拟接口
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号'},
			{field: 'title', title: '品牌名称'},
			{field: 'pic', title: '品牌图片', templet: '#imgTpl'},
			{field: 'sequence', title: '排序', sort: true},
			{field: 'is_top', title: '是否推荐'},
			{field: 'is_show', title: '是否显示'},
			{title: '操作', width: 350, align: 'center', fixed: 'right', toolbar: '#toolbar-goods-categroy-data'},
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
	table.on('tool(OM-goods-categroy)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/goods/categroy/delete",
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
	                        	table.reload('OM-goods-categroy', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit'){
			view('OM-module-temp').render('goods/component/category-edit', data).done(function() {
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		
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
		    		if(field.sequence < 0){
		    			layer.msg('排序输入有误');
		    			return false;
		    		}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/goods/categroy/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/goods/category');
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
			view('OM-module-temp').render('goods/component/category-son-add', data).done(function() {
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		
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
		    		if(field.sequence < 0){
		    			layer.msg('排序输入有误');
		    			return false;
		    		}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/goods/categroy/insert",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/goods/model');
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
			view('OM-module-temp').render('goods/component/category-son', data).done(function() {
				//商品分类
				table.render({
					elem: '#OM-goods-categroy-son',
					url: '/manager/goods/categroy/info', //模拟接口
					cols: [[
						{type: 'checkbox', fixed: 'left'},
						{field: 'id', title: '编号'},
						{field: 'title', title: '品牌名称'},
						{field: 'pic', title: '品牌图片', templet: '#imgTpl'},
						{field: 'sequence', title: '排序', sort: true},
						{field: 'is_top', title: '是否推荐'},
						{field: 'is_show', title: '是否显示'},
						{title: '操作', width: 350, align: 'center', fixed: 'right', toolbar: '#toolbar-goods-categroy-son'},
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
				
				//监听工具条
				table.on('tool(OM-goods-categroy-son)', function(obj){
					var data2 = obj.data;
					if(obj.event === 'del'){
						layer.confirm('确定删除此信息？', function(index){
							layer.close(index);
							
							//数据封装
							var req = {"id": data2.id};
							//执行 Ajax 后重载
				          	$.ajax({
								type: "POST",
								url: "/manager/goods/categroy/delete",
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
				                        	table.reload('OM-goods-categroy', null);
				                        }
				                    });
								}, error: function(){
									location.hash='/system/404';
								}
					    	});
						});
					} else if(obj.event === 'edit'){
						data2.parent_name = data.title;
						view('OM-module-temp').render('goods/component/category-son-edit', data2).done(function() {
							//动态加载
							form.render();
							//提交监听
							form.on('submit(OM-form-submit)', function(data){
					    		var field = data.field;
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
					    		if(field.sequence < 0){
					    			layer.msg('排序输入有误');
					    			return false;
					    		}
					    		
					    		//提交
					    		$.ajax({
					    			type: "POST",
									url: "/manager/goods/categroy/update",
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
				        	    				window.history.pushState(null, null, '/start/index.html#/goods/category');
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
	
	//商品模型
	table.render({
		elem: '#OM-goods-model',
		url: '/manager/goods/model/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 150},
			{field: 'title', title: '模型名称'},
			{title: '操作', width: 300, align: 'center', fixed: 'right', toolbar: '#toolbar-goods-model'}
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
  
	//商品模型_监听工具条
	table.on('tool(OM-goods-model)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/goods/model/delete",
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
	                        	table.reload('OM-goods-model', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit'){
			view('OM-module-temp').render('goods/component/model-edit', data).done(function() {
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/goods/model/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/goods/model');
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
	
	//商品规格
	table.render({
		elem: '#OM-goods-spec',
		url: '/manager/goods/spec/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 100},
			{field: 'title', title: '规格名称', width: 150},
			{field: 'item', title: '规格项', templet: '#imgTpl'},
			{field: 'model', title: '所属模型', width: 200},
			{field: 'is_search', title: '是否筛选', width: 100},
			{field: 'sequence', title: '排序', sort: true, width: 100},
			{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#toolbar-goods-spec'}
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
  
	//商品规格_监听工具条
	table.on('tool(OM-goods-spec)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/goods/spec/delete",
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
	                        	table.reload('OM-goods-spec', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit'){
			view('OM-module-temp').render('goods/component/spec-edit', data).done(function() {
				console.log(JSON.stringify(data));
				//
				//初始化
	    		$.ajax({
	    			type: "POST",
					url: "/manager/goods/model/info",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					data: JSON.stringify({}),
					async: false,
					success: function(data2){
						var list = data2.data;
						for(var i=0; i < list.length; i++) {
							var obj = list[i];
							if(data.type_id === obj.id) {
								$("#m_type").append("<option value="+obj.id+" selected>"+obj.title+"</option>");
							} else {
								$("#m_type").append("<option value="+obj.id+">"+obj.title+"</option>");
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
		    		//校验
		    		if(field.is_search === 'on') {
		    			field.is_search=1;
	      			} else {
	      				field.is_search=0;
	      			}
		    		if(field.sequence < 0) {
	      				layer.msg('请填写正确的排序');
	      				return false;
	      			}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/goods/spec/update",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/goods/spec');
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
	
	//商品品牌
	table.render({
		elem: '#OM-goods-brand',
		url: '/manager/goods/brand/info', //模拟接口
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号'},
			{field: 'pic', title: '商标图片', templet: '#imgTpl'},
			{field: 'title', title: '商品名称'},
			{field: 'sequence', title: '排序', sort: true},
			{field: 'is_top', title: '是否推荐'},
			{field: 'is_show', title: '是否显示'},
			{title: '操作', align: 'center', fixed: 'right', toolbar: '#toolbar-goods-brand'}
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
  
	//商品品牌_监听工具条
	table.on('tool(OM-goods-brand)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/goods/brand/delete",
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
	                        	table.reload('OM-goods-brand', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit'){
			view('OM-module-temp').render('goods/component/brand-edit', data).done(function() {
				//动态加载
				form.render();
				//提交监听
				form.on('submit(OM-form-submit)', function(data){
		    		var field = data.field;
		    		//校验
		    		//校验
		    		if(field.title === '') {
	      				layer.msg('请填写名称');
	      				return false;
	      			}
		    		
		    		if(field.sequence < 0) {
	      				layer.msg('请填写正确的排序');
	      				return false;
	      			}
		    		if(field.is_show === 'on') {
		    			field.is_show = 1;
		    		} else {
		    			field.is_show = 0;
		    		}
		    		if(field.is_top === 'on') {
		    			field.is_top = 1;
		    		} else {
		    			field.is_top = 0;
		    		}
		    		
		    		//提交
		    		$.ajax({
		    			type: "POST",
						url: "/manager/goods/brand/insert",
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
	        	    				window.history.pushState(null, null, '/start/index.html#/goods/brand');
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

	exports('goods', {})
});