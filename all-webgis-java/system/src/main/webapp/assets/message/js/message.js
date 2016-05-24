
//获取用户地址 
var userpath=contextPath+'/user';
//消息地址
var messagepath= contextPath+'/message';

$(function(){
	initDataGrid();
});

/**
 * 初始化用户列表
 */
function initDataGrid(){
	$("#dg").datagrid({ 
		height : '100%',
		width: '100%',
		url : userpath +'/userlist',
		method : 'post',
		title : '用户列表',
		checkOnSelect:true,
		rownumbers : true,
		pageSize : 20,
		fitColumns: true,
		pagination : true,
		sortOrder : 'desc',
		idField :'id',
		onLoadSuccess : function(){
		},
		columns: [[
		           {field:'ck',checkbox:true,title:'全选/全不选'},
		           {field:'username',title:'用户名',sortable:true,width:50},
		           {field:'name',title:'姓名',sortable:true,width:50},
		           {field:'mobile',title:'联系电话',sortable:true,width:50},
		           {field:'email',title:'邮箱',sortable:true,width:50},
		           {field:'status',title:'状态',sortable:true,width:50}
		           ]],
		           toolbar: [
		                     {
		                    	 text:'全体发送',
		                    	 iconCls:'icon-redo',
		                    	 handler:function(){
		                    		$("#dg").datagrid('checkAll');
		                    		sendMesageALL();
		                    	 }
		                     },
		                     {
		                    	 text:"发送消息",
		                    	 iconCls:'icon-redo',
		                    	 handler:function(){
		                    		 selectSend();
		                    	 }
		                     }
		                     ],
		                     striped: true,
		                     collapsible :true,
	});

	var menuVal = "<div data-options=name:'all'>全部</div>"
		$('#mm').html($('#mm').html()+menuVal);
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

function createDialog(addurl,users){
	$("#dialog").show();
	$("#dialog").dialog({
		title:'发送消息',
		width: 500,    
		height: 260,    
		closed: false,    
		cache: false,
		model:true,
		resizable:true,
        closable:false,
		buttons:[
		         {
		        	 text:'发送',
		        	 iconCls:"icon-ok",
		        	 handler:function(){
		        		 submitForm(addurl);
                 		$("#dg").datagrid('uncheckAll');
		        	 }
		         },
		         {
		        	 text:'取消 ',
		        	 iconCls:"icon-cancel",
		        	 handler:function(){
		        			cleanDialog();
		        			$("#dialog").dialog('close');
                    		$("#dg").datagrid('uncheckAll');
		        	 }
		         }
		         ]
	});
	if(users!=null &&users.length>0){
		for(var i=0;i<users.length;i++){
			var username = users[i].username;
			var hid=$("<input type='hidden' />").attr("name","usernames["+i+"]").val (username);
			hid.appendTo("#addfm");
		}
	}
	$("#dialog").css("margin-top","20");
	$("#dialog").css("display","block");
}


/**
 * 发送消息
 */
function sendMesageALL(){
	var url = messagepath+"/sendMessage";
	createDialog(url,null);
}

/**
 * 选择用户发送 
 */
function selectSend(){
	if(checkSelectRow()){
		var url = messagepath+"/sendMessage";
		var rows = $("#dg").datagrid('getSelections');
		createDialog(url,rows);
	}

}

/**
 * 消息提交 
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
				cleanDialog();
				$('#dg').datagrid('reload');
				$.messager.show({title:"提示",msg:res.msg});
			}
		} 
	});
	form.submit();
}

/**
 * 清除窗口内容
 */
function cleanDialog(){
	$("#addfm").form("clear");
	$("#dialog").dialog('refresh');
}
/**
 *  搜索
 * @param value
 */
function doSearch(value) {
	$('#searchValhidden').val(value);
	$('#dg').datagrid('load',{  	
		searchVal: $('#searchValhidden').val()
	}); 
}

/**
 * 效验选择行
 */
function checkSelectRow(){
	var row = $("#dg").datagrid('getSelections');
	if(row.length>0){
		return true;
	}else{
		$.messager.alert('警告 ','亲，能先选择一条数据么？','warning');
		return false;
	}
}





