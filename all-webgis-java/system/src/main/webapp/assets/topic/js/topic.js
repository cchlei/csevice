
var path = contextPath+"/topic";
$(function(){
	initDataGrid();
});


function createDialog(url,serv){
	var title=(serv=='add')?'添加专题标签':'修改专题标签';
    $("#dialog").dialog({
    	title:title,
    	width: 400,    
        height: 200,    
        closed: false,    
        cache: false,
        model:true,
        resizable:true,
        closable:false,
        buttons:[
                 {
                 text:'保存',
                 iconCls:"icon-ok",
                 handler:function(){
                	 submitForm(url);
                 }
                 },
				{
                text:'取消',
                iconCls:"icon-cancel",
                handler:function(){
                	$("#dialog").dialog('close');
                }
				}
                 ]
    	
    });
    cleanDialog();
    $("#dialog").css("display","block");
}

function initDataGrid(){
	$("#dg").datagrid({
		height : '100%',
		width: '100%',
		url : path +'/topiclist',
		method : 'post',
		title : '专题标签管理',
		singleSelect : true,
		rownumbers : true,
		pageSize : 20,
		fitColumns: true,
		pagination : true,
		sortOrder : 'desc',
		onLoadSuccess : function(data,index){
			if(!data){
				$(this).datagrid('loadData', { total: 0, rows: [] });
			}
		},
		columns: [[
		          {field:'name',title:'名称',sortable:true,width:50},
		          {field:'sort',title:'排序',sortable:true,width:50},
		          ]],
		toolbar: [
		          {
		        	text:'新增',
		        	iconCls:'icon-add',
		        	handler:function(){
		        		addOne();
		        	}
		          },
		          {
		          	text:'修改',
		          	iconCls:'icon-edit',
		          	handler:function(){
		          		modfied();
		          	}
		          },
		          {
		          	text:'删除',
		          	iconCls:'icon-remove',
		          	handler:function(){
		          		deleteOne();
		          	}
		          }
		          ],
		striped: true,
		collapsible :true,
		

	});
	
	//var menuVal = "<div data-options=name:'all'>全部</div>"
	//$('#mm').html($('#mm').html()+menuVal);
	var fields =  $('#dg').datagrid('getColumnFields');
	for(var i=0; i<fields.length-1; i++){
	var opts = $('#dg').datagrid('getColumnOption', fields[i]);  
	var muit = "<div data-options=name:'"+  fields[i] +"'>"+ opts.title +"</div>";
	$('#mm').html($('#mm').html()+muit);
	}	

	$('#searchVal').searchbox({  
	menu:'#mm',
	searcher:function(value,name){
		var v= name+':'+value;
		doSearch(v);
	},
	prompt:'输出查询条件'
	});
	var serechbox_td = $("<td></td>").append($('.searchbox '));
	var serchVal_td =  $("<td></td>").append($('#searchVal'));
	$("#dg").parents(".datagrid:first").find(".datagrid-toolbar table td:last").after(serechbox_td).after(serchVal_td);
 
	
}

/**
 * 表单提交
 * @param url
 */
function submitForm(url){
	var form = $("#addfm").form({
		url:url,
		success:function(result){
				var res=eval('('+result+')');
				if(res.errorMsg){
					$.messager.show({
						title:"错误",
						msg:res.msg
					});
				}else{
					$("#dialog").dialog('close');
    				$('#dg').datagrid('reload');
    				$.messager.show({title:"提示",msg:res.msg});
				}
			} 
		
	});
	form.submit();
}
function doSearch(value) {
	$('#searchValhidden').val(value);
	$('#dg').datagrid('load',{  	
        searchVal: $('#searchValhidden').val()
    }); 
}

/**
 * 清除窗口内容
 */
function cleanDialog(){
	$("#addfm").form("clear");
	$("#dialog").dialog('refresh');
}

function addOne(){
	var url= path+"/addtopic";
	var serv= "add";
	createDialog(url,serv);
}

function modfied(){
	if(!checkSelectRow()){
		return;
	}
	var url= path+"/modfiedtopic";
	var serv = "update";
	var row = $('#dg').datagrid('getSelected');
	createDialog(url,serv);
	$("#addfm").form("load",row);
}

function deleteOne(){
	var url = path+"/deletetopic"
	if(checkSelectRow()){
		var row=$('#dg').datagrid("getSelected"); 
		if (row){
			$.messager.confirm('提示','确定要删除所选专题标签?',function(r){
				if (r){
					$.post(url, {id:row.id}, function(res){
						if (res.status==0){
							$('#dg').datagrid('reload');	
							$.messager.show({title: '提示', msg: res.msg });
						} else {
							$.messager.show({title: '提示', msg: res.msg });
						}
					});
				}
			});
		}
		
	}
}

function checkSelectRow(){
	var row = $("#dg").datagrid('getSelected');
	if(row){
		return true;
	}else{
		$.messager.alert('警告 ','亲，能先选择一条数据么？','warning');
		return false;
	}



}


