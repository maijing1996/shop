
layui.define(function(exports){
  
  //区块轮播切换
  layui.use(['admin', 'carousel'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,carousel = layui.carousel
    ,element = layui.element
    ,device = layui.device();

    //轮播切换
    $('.layadmin-carousel').each(function(){
      var othis = $(this);
      carousel.render({
        elem: this
        ,width: '100%'
        ,arrow: 'none'
        ,interval: othis.data('interval')
        ,autoplay: othis.data('autoplay') === true
        ,trigger: (device.ios || device.android) ? 'click' : 'hover'
        ,anim: othis.data('anim')
      });
    });
    
    element.render('progress');
    
  });

  //会员统计数据
  layui.use(['carousel', 'echarts'], function(){
    var $ = layui.$
    ,carousel = layui.carousel
    ,echarts = layui.echarts;
    
    var data = {};
    var subtext2 = '';
    //提交
	$.ajax({
		type: "POST",
		url: "/manager/sell/member/statistic",
		dataType:'json',//预期服务器返回的数据类型
		contentType: "application/json; charset=utf-8",
		async: false,
		data: JSON.stringify({}),
		success: function(data2){
			if(data2.code == 200) {
				subtext2 = "[今日新增："+ data2.today+"当前总数："+data2.total;
				data = data2;
			}
			data = data2;
		}, error: function(){
			location.hash='/system/404';
		}
	});
    
    var echartsApp = [], options = [
      //今日流量趋势
      {
        title: {
          text: '会员统计数据',
          subtext: subtext2,
          textStyle: {
            fontSize: 14
          }
        },
        tooltip : {
          trigger: 'axis'
        },
        legend: {
          data:['新增会员']
        },
        calculable : true,
        xAxis : [{
          type : 'category',
          boundaryGap : false,
          data: data.date
        }],
        yAxis : [{
          type : 'value'
        }],
        series : [{
          name:'新增会员',
          type:'line',
          smooth:true,
          itemStyle: {normal: {areaStyle: {type: 'default'}}},
          data: data.amount
        }]
      }
    ]
    ,elemDataView = $('#LAY-index-dataview').children('div')
    ,renderDataView = function(index){
      echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
      echartsApp[index].setOption(options[index]);
      window.onresize = echartsApp[index].resize;
    };
    
    
    //没找到DOM，终止执行
    if(!elemDataView[0]) return;
    
    renderDataView(0);
    
    //监听数据概览轮播
    var carouselIndex = 0;
    carousel.on('change(LAY-index-dataview)', function(obj){
      renderDataView(carouselIndex = obj.index);
    });
    
    /*//监听侧边伸缩
    layui.admin.on('side', function(){
      setTimeout(function(){
        renderDataView(carouselIndex);
      }, 300);
    });
    
    //监听路由
    layui.admin.on('hash(tab)', function(){
      layui.router().path.join('') || renderDataView(carouselIndex);
    });*/
  });

  exports('console-shop', {})
});

