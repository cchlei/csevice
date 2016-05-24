$(function(){
		table = $('#userList').DataTable({
			"pagingType":   "full_numbers",
			"bAutoWidth": false,
			"sLoadingRecords": "正在加载数据...",
			"sZeroRecords": "暂无数据",
			"processing":false,
			"serverSide":true,
			"stateSave": true,
			"lengthChange":false,
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
        	   "url":"/system/user/list",
        	   "method":"POST",
        	   "data":function(d){
        		   d["searchText"]=$('#searchText').val();
        		   d["filterName"]=$('#filterName').html();
        		   d["userStatus"]=$('#userStatus').val();
        		   d["startDate"]=$('#startDate').val();
        		   d["endDate"]=$('#endDate').val();
        		   d["column"]=d.columns[d.order[0].column].data;
        		   d["dir"]=d.order[0].dir
        	   }
           },
           "columns":[
                {"data":"id","bSortable": false},
            	{"data":"name"},
            	{"data":"username"},
            	{"data":"email"},
            	{"data":"createDate"},
            	{"data":"status"},
            	{"data": null}
            ],
            columnDefs: [
				{
					targets: 0,
					render: function (a, b, c, d) {
						return "<img src=\"http://oss.trmap.cn/rs/download/2034c24d-ca61-446b-9adf-e82f236490ec-d1442538290056\" class=\"img-circle\" style=\"width:30px;\">"
					},
					orderable:false
				},
				{
					targets: 5,
					render: function (a, b, c, d) {
						if(a=="ACTIVE"){
							return "<span class=\"label label-info\">已激活</span>"
						}
						if(a=="INACTIVE"){
							return "<span class=\"label label-warning\">未激活</span>"
						}
						if(a=="LOCKED"){
							return "<span class=\"label label-success\">已锁定</span>"
						}
						if(a=="CLOSED"){
							return "<span class=\"label label-success\">已关闭</span>"
						}
					},
					orderable:false
				},
				{
					targets: 6,
					render: function (a, b, c, d) {
						var html = "<button type=\"button\" class=\"btn btn-xs btn-primary\" onclick=\"javascript:validation('"+c+"')\">锁定</button> "
						       	 + "<button type=\"button\" class=\"btn btn-xs btn-info\">查看</button> "
						       	 + "<button type=\"button\" class=\"btn btn-xs btn-success\">消息</button>"
						return html;
					},
					orderable:false
				}
           ]
		});
		
		//设置搜索条件
		$('#filterGroup li a').on('click',function(){
			$('#filterName').html($(this).html());
		});
		
		//点击搜索按钮触发表格搜索操作
		$('#search').on('click',function(){
			table.ajax.reload();
		})
		
		$('#userStatus').on('change',function(){
			table.ajax.reload();
		})
		
		$('#startDate').datepicker({
			language:'zh-CN',
			autoclose: true,
			clearBtn:true,
			todayHighlight: true,
			todayBtn:true
	    });
		
		$('#endDate').datepicker({
			language:'zh-CN',
			autoclose: true,
			clearBtn:true,
			todayHighlight: true,
			todayBtn:true
	    });
		
		validation = function(obj){
			$("#myModal").modal("show");
		}
		
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
		
	})

