//统计矢量数据
function getCount(){
	$.ajax({
		url:ctx+"/graphics/countGraphics",
		success:function(data){
			if(data.result=="success"){
				 $("#totalCount").text(data.totalCount);
				 $("#usedCount").text(data.usedCount);
			}else{
				layer.close();
				layer.msg(data.msg, {
					icon : 2
				});
				
			}
		}
	})
	
}
$(function(){
	getCount();
	showProgress();
})
$(function(){
		table = $('#graphicsList').DataTable({
			"pagingType":   "full_numbers",
			"sLoadingRecords": "正在加载数据...",
			"sZeroRecords": "暂无数据",
			"processing":true,
			"serverSide":true,
			"lengthChange":false,
			"stateSave": true,
			"searching": false,
			"language": {
                "processing": "玩命加载中...",
				"lengthMenu": "显示 _MENU_ 项结果",
				"zeroRecords": "没有匹配结果",
				"info": "共 _TOTAL_ 条记录",
				"infoEmpty": "共 0 条记录",
				"infoFiltered": "(由 _MAX_ 项结果过滤)",
				"infoPostFix": "",
				"url": "",
				"paginate": {
					"first":    "首页",
					"previous": "上一页",
					"next":     "下一页",
					"last":     "末页"
				}
           },
           "ajax":{
        	   "url":ctx+"/graphics/graphicsList",
        	   "method":"POST",
        	   "data":function(d){
        		   d["searchText"]=$('#searchText').val();
        		   d["filterName"]=$('#filterName').html();
        		   d["point"]=$('#point').is(":checked")?1:"";
        		   d["line"]=$('#line').is(":checked")?2:"";
        		   d["surface"]=$('#surface').is(":checked")?3:"";
        		   d["startDate"]=$('#startDate').val();
        		   d["endDate"]=$('#endDate').val();
        		   d["column"]=d.columns[d.order[0].column].data;
        		   d["dir"]=d.order[0].dir
        	   }
           },
           "columns":[
            	{"data":"gname","style":"text-align:left;"},
            	{"data":"gtype"},
            	{"data":"gcreatedate"},
            	{"data": null},
            	{"data": null}
            ],
            columnDefs: [
				{
					targets: 0,
					orderable:false
				},
				{
					targets: 4,
					render: function (a, b, c, d) {
						var html = "<img src=\""+ctx+"/static/images/iconfont-shanchu.png\" class=\"img-circle\" onclick=\"javascript:deleteGraphics('"+c.id+"','"+c.userMap.isshare+"')\">";
						return html;
					},
					orderable:false
				},
				{
					targets: 3,
					render: function (a, b, c, d) {
						var html = "<img src=\""+ctx+"/static/images/iconfont-guanlianyuan.png\" class=\"img-circle\" onclick=\"javascript:lookMap('"+c.id+"','"+c.userMap.id+"')\">";
						return html;
					},
					orderable:false
				},
				{
					targets: 1,
					render: function (a) {
						if(a=="1"){
							return "<span class=\"label label-info\">点</span>"
						}
						if(a=="2"){
							return "<span class=\"label label-warning\">线</span>"
						}
						if(a=="3"){
							return "<span class=\"label label-success\">面</span>"
						}
					},
					orderable:false
				},
           ]
		});
		table.order( [ 2, 'desc' ]).draw();
		//设置搜索条件
		$('#filterGroup li a').on('click',function(){
			$('#filterName').html($(this).html());
		});
		
		//点击搜索按钮触发表格搜索操作
		$('#search').on('click',function(){
   		   table.ajax.reload();
		})
		$('#point').on('click',function(){
   		   table.ajax.reload();
		})
		$('#line').on('click',function(){
   		   table.ajax.reload();
		})
		$('#surface').on('click',function(){
   		   table.ajax.reload();
		})
		deleteGraphics = function(obj,isshare) {
			
			seajs.use("$/layer", function(layer) {
				if(isshare=="1"){
					layer.msg("当前数据所在的地图正处在分享状态，您不能删除！", {
						icon : 1
					});
					return;
				}
				layer.confirm('您确定要删除当前矢量信息？', {
					btn : [ '确定', '取消' ]
				// 按钮
				}, function() {
					$.ajax({
	            		url:ctx+"/graphics/deleteGraphics?gid=" + obj,
	            		success:function(data){
	            			if(data.result=="success"){
	            				layer.close();
	            				layer.msg(data.msg, {
	            					icon : 1
	            				});
	            				table.ajax.reload();
	            				getCount();
	            				showProgress();
	            			}else{
	            				layer.close();
	            				layer.msg(data.msg, {
	            					icon : 2
	            				});
	            				
	            			}
	            		}
	            	})
				}, function() {
					layer.close();
				});
			})
	}
		lookMap=function (a,b){
			window.open(ctx+"/urlFilter/showdata/"+b+"/"+a);
		}
		/*监听输入框的回车操作*/  
		$('#searchText').on('keypress',function(event){  
		    if(event.keyCode == 13) 
		    	$('#search').click();  
		}); 
		$.pureClearButton.setDefault({
			icon: 'glyphicon-remove'
		});
		
		$("#searchText").pureClearButton("create").pureClearButton("hide").on('input',function(){
			if($(this).val() != ""){
				$(this).pureClearButton("show");
			} else {
				$(this).pureClearButton("hide");
			}
		});
		
	});
//进度条

  // Simple wrapper around jQuery animate to simplify animating progress from your app
  // Inputs: Progress as a percent, Callback
  // TODO: Add options and jQuery UI support.
  $.fn.animateProgress = function(progress, callback) {    
    return this.each(function() {
    	progress=progress+4;
      $(this).animate({
        width: progress+'%'
      }, {
        duration: 2000, 
        
        // swing or linear
        easing: 'linear',

        // this gets called every step of the animation, and updates the label
        step: function( progress ){
          var labelEl = $('.ui-label', this),
              valueEl = $('.value', labelEl);
          
          if (Math.ceil(progress) < 20 && $('.ui-label', this).is(":visible")) {
            labelEl.hide();
          }else{
            if (labelEl.is(":hidden")) {
              labelEl.fadeIn();
            };
          }
          
          if (Math.ceil(progress) == 100) {
            labelEl.text('Done');
            setTimeout(function() {
              labelEl.fadeOut();
            }, 1000);
          }else{
            valueEl.text(Math.ceil(progress-4) + '%');
          }
        },
        complete: function(scope, i, elem) {
          if (callback) {
            callback.call(this, i, elem );
          };
        }
      });
    });
  };

  function showProgress(){
	  // Hide the label at start
	  $('#progress_bar .ui-progress .ui-label').hide();
	  // Set initial value
	  $('#progress_bar .ui-progress').css('width', '0%');

	  // Simulate some progress
	  setTimeout(function() {
		  var usedCount=$("#usedCount").text();
		  var totalCount=$("#totalCount").text();
		  var numpercent=(usedCount/totalCount).toFixed(2)*100;
	      $('#progress_bar .ui-progress').animateProgress(numpercent, function() {
	        $('#main_content').slideDown();
	        $('#fork_me').fadeIn();
	      });
	    }, 2000);
  }