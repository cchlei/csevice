var delColumn;//定义方法句柄
var colsmap = {}; //定义临时存储列名的map
$(function(){
	
	/*
	 * 点击新增按钮，隐藏列表，显示新增表单
	 */
	$('#addform').click(function(){
		$('#tableform').hide('fast',function(){
			$('#addtable').show('normal');
		});
	});
	
	/*
	 * 点击返回按钮
	 */
	$('#tableformback').click(function(){
		$('#addtable').hide('fast',function(){
			$('#tableform').show('normal');
			colsmap = {};// 返回后清空临时存储列名的map
		});
	});
	
	
	/*
	 * 添加列数据
	 */
	$('#coladd').click(function(){
		var name = $('#name').val();
		if(colsmap[name]) {
			alert("列名不能重复")
			return;
		}
		var alias = $('#alias').val();
		var type = $('#type').val();
		var value = "{\"name\":\""+name+"\",\"alias\":\""+alias+"\",\"valueType\":\""+type+"\"}"
		colsmap[name]=value;
		var table = $('#colstable');
			table.append(
				"<tr id='"+name+"' value="+value+">"
					+"<td>"+name+"</td>"
					+"<td>"+alias+"</td>"
					+"<td>"+type+"</td>"
					+"<td><a href='javascript:delColumn("+name+")' data-toggle='tooltip' title='删除'><span class='glyphicon glyphicon-remove' ></span></a></td>"
				+"</tr>"
		);
	});
	
	$('#submitBtn').click(function(){
		var tableName = $('#tableName').val();
		var cols = [];
		var i = 0;
		for(var c in colsmap){
			cols[i++]=colsmap[c];
		}
		console.log(cols);
		$.post(ctx+"/table/save", {'tableName':tableName , 'columns': cols },function(){
			window.location.href=ctx+"/table/list";
		});
	});
	
	/*
	 * 删除当前行数据
	 */
	delColumn = function(name){
		$('#'+name+'').remove();
		colsmap[name]=null;
	}
	
});