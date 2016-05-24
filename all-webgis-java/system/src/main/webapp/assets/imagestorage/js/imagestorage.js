
var swfpath=contextPath;
var path= contextPath+'/imagestorage';
$(function(){
	initDataGrid();
});
//uplaodar 插件
var __uploader;
//上传文件队列下标
var _index = 0;
//oss显示大图服务地址 
var bigimageurl="http://oss.trmap.cn/download/";
var shrinkimageurl="http://oss.trmap.cn/thumb/40_40/";
//搜索框的值
var v;

function createDialog(uploadurl,addurl,serv){
	var title=(serv=='add')?'默认图库新增图片':'默认图库修改图片';
	$("#dialog").show();
	$("#dialog").dialog({
		title:title,
		width: 700,    
		height: 500,    
		closed: false,    
		cache: false,
		model:true,
		resizable:true,
        closable:false,
		close:function(){
			$(this).remove();
		},
		buttons:[
		         {
		        	 text:'新增并提交',
		        	 iconCls:"icon-ok",
		        	 disabled:true,
		        	 id:'submit',
		        	 handler:function(){
		        		 submitForm(addurl);
		        	 }
		         },
		         {
		        	 text:'取消并关闭 ',
		        	 iconCls:"icon-cancel",
		        	 handler:function(){
		        		 cleanDialog();
		        	 }

		         }

		         ]

	});
	var combobox=  $('#imagetype').combobox({
		valueField:'imagetype',
		textField:'imagetypename',
		editable:false,
		required:true,
		data: [
		       {
		    	   imagetype: '0',
		    	   imagetypename: '头像 '
		       },{
		    	   imagetype: '1',
		    	   imagetypename: '专题图片'
		       }]
	});
	createUploader(uploadurl);
	$("#dialog").css("margin-top","20");
	$("#dialog").css("display","block");
}
/**
 * 清除窗口内容
 */
function cleanDialog(){
	 $("#dialog").dialog('close');
	 $("#dialog").dialog('refresh','');
	 var files = __uploader.getFiles();
	 	if(files!=null&&files.length>0){
	 for(var i=0;i<files.length;i++){
		 __uploader.removeFile(files[i],true);
	 }
}
	 	$('.webuploader-pick + div').css("left",197.5);
	 	__uploader.reset();
}


function initDataGrid(){
	$("#dg").datagrid({ 
		height : '100%',
		width: '100%',
		url : path +'/imgstoragelist',
		method : 'post',
		title : '默认图库管理',
		singleSelect : true,
		pageSize : 20,
		fitColumns: true,
		pagination : true,
		sortOrder : 'desc',
		onLoadSuccess : function(data,index){
			if(!data){
				$(this).datagrid('loadData', { total: 0, rows: [] });
				$('#images').empty();
			}
		},
		columns: [[
		           {field:'name',title:'名称',sortable:true,width:50},
		           {field:'imageTypeName',title:'图片类型',sortable:true,width:50},
		           {field:'operation',title:'查看',sortable:true,width:50,align:'center',
		        	   formatter:function(value,rec,index){   //格式化函数添加一个操作列							
		        		   if(rec.ossid){
		        			   var btn = '<a id="fancy" href="#inline'+index+'"><img class="thembimg" src='+shrinkimageurl+rec.ossid+' style="padding-top: 5px,display:block" /></a>';
		        			   initFancyInnerDiv(rec.ossid,index);
		        			   imageMagnifier();
		        			   return btn; 
		        		   }
		        		   
		        	   }  
		           }
		           ]],
		           toolbar: [
		                     {
		                    	 text:'上传图片',
		                    	 iconCls:'icon-add',
		                    	 handler:function(){
		                    		 addOne();
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

	var fields =  $('#dg').datagrid('getColumnFields');
	for(var i=0; i<fields.length-2; i++){
		var opts = $('#dg').datagrid('getColumnOption', fields[i]);  
		var muit = "<div data-options=name:'"+  fields[i] +"'>"+ opts.title +"</div>";
		$('#mm').html($('#mm').html()+muit);
	}	

	$('#searchVal').searchbox({  
		menu:'#mm',
		searcher:function(value,name){
			v= name+':'+value;
			doSearch(v,$('input:radio:checked').val());
		},
		prompt:'输出查询条件'
	});
	var serechbox_td = $("<td></td>").append($('.searchbox '));
	var serchVal_td =  $("<td></td>").append($('#searchVal'));
	$("#dg").parents(".datagrid:first").find(".datagrid-toolbar table td:last").after(serechbox_td).after(serchVal_td);
	var radioH = $('#radioH').appendTo('.datagrid-toolbar table td:last');
	var radioP = $('#radioP').appendTo('.datagrid-toolbar table td:last');	
}
//单选按钮事件
function radioCheck(radio){
	   $('#images').empty();
		doSearch(v,radio.value);
}

/**
 * 图片放大器 
 */
function imageMagnifier(){

	$("#fancy").fancybox({
		'centerOnScroll':true,
		'opacity':true,
		'overlayShow':false,
		'overlayOpacity':0,
		'autoScale':true,
		'transitionIn':'elastic',
		'transitionOut':'elastic'
		
	});
}
/**
 * 初始化fancy inner div
 * @param url
 */
function initFancyInnerDiv(ossid,index){
   $("#images").append($('<div id="inline'+index+'" style=" overflow:auto" style="display: none;"><img src="'+bigimageurl+ossid+'" /></div>'));	
}



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
					cleanDialog();
					$('#images').empty();
    				$('#dg').datagrid('reload');
    				$.messager.show({title:"提示",msg:res.msg});
				}
				_index = 0;
			} 
	});
	form.submit();
}
function doSearch(value,radiovalue) {
	$('#searchValhidden').val(value);
	$('#dg').datagrid('loadData', { total: 0, rows: [] });
	$('#dg').datagrid('load',{  	
		searchVal: $('#searchValhidden').val(),
		imageTypeVal:radiovalue
	}); 
}




function addOne(){
	var uploadurl= path+"/upload";
	var addurl=path+"/addimagestorm";
	var serv= "add";
	createDialog(uploadurl,addurl,serv);
}

function deleteOne(){
	var url = path+"/removeimageStorage"
	
	if(checkSelectRow()){
		var row=$('#dg').datagrid("getSelected"); 
		if (row){
			$.messager.confirm('提示','确定要删除所选默认图库?',function(r){
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
//初始化webuploader 插件

function  createUploader(url){
	var $ = jQuery; 
	
	var $wrap = $('#uploader');

	// 图片容器
	var  $queue = $('<ul class="filelist"></ul>')
	.appendTo( $wrap.find('.queueList') );

	// 状态栏，包括进度和控制按钮
	var    $statusBar = $wrap.find('.statusBar');

	// 文件总体选择信息。
	var  $info = $statusBar.find('.info');

	// 上传按钮
	var  $upload = $wrap.find('.uploadBtn');

	// 没选择文件之前的内容。
	var  $placeHolder = $wrap.find('.placeholder');

	// 总体进度条
	var $progress = $statusBar.find('.progress').hide();

	// 添加的文件数量
	var fileCount = 0;

	// 添加的文件总大小
	var  fileSize = 0;

	// 优化retina, 在retina下这个值是2
	var  ratio = window.devicePixelRatio || 1;

	// 缩略图大小
	var  thumbnailWidth = 110 * ratio;
	var   thumbnailHeight = 110 * ratio;

	// 可能有pedding, ready, uploading, confirm, done.
	var    state = 'pedding';

	// 所有文件的进度信息，key为file id
	var  percentages = {};

	var  supportTransition = (function(){
		var s = document.createElement('p').style,
		r = 'transition' in s ||
		'WebkitTransition' in s ||
		'MozTransition' in s ||
		'msTransition' in s ||
		'OTransition' in s;
		s = null;
		return r;
	})();

	// WebUploader实例

	if ( !WebUploader.Uploader.support() ) {
		alert( '上传暂不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
		throw new Error( 'WebUploader does not support the browser you are using.' );
	}

	if(__uploader)return __uploader;
	// 实例化
	__uploader =WebUploader.create({
		pick: {
			id: '#filePicker',
			label: '点击选择图片'
		},
		dnd: '#uploader .queueList',
		paste: document.body,

		accept: {
			title: 'Images',
			extensions: 'gif,jpg,jpeg,bmp,png',
			mimeTypes: 'image/*'
		}
		,
		// swf文件路径
		swf: swfpath + '/assets/webuploader/Uploader.swf',
		disableGlobalDnd: true,
		chunked: true,
		server: url,
		fileNumLimit: 10,
		fileSizeLimit: 10 * 1024 * 1024,    // 200 M
		fileSingleSizeLimit: 1 * 1024 * 1024    // 50 M
	});

	// 添加“添加文件”的按钮，
	__uploader.addButton({
		id: '#filePicker2',
		label: '继续添加'
	});


	// 当有文件添加进来时执行，负责view的创建
	function addFile( file ) {
		var $li  = $( '<li id="' + file.id + '">' +
				'<p class="title">' + file.name + '</p>' +
				'<p class="imgWrap"></p>'+
				'<p class="progress"><span></span></p>' +
		'</li>' ),

		$btns = $('<div class="file-panel">' +
				'<span class="cancel">删除</span>' +
				'<span class="rotateRight">向右旋转</span>' +
		'<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
		$prgress = $li.find('p.progress span'),
		$wrap = $li.find( 'p.imgWrap' ),
		$info = $('<p class="error"></p>'),

		showError = function( code ) {
			switch( code ) {
			case 'exceed_size':
				text = '文件大小超出';
				break;

			case 'interrupt':
				text = '上传暂停';
				break;

			default:
				text = '上传失败，请重试';
			break;
			}

			$info.text( text ).appendTo( $li );
		};

		if ( file.getStatus() === 'invalid' ) {
			showError( file.statusText );
		} else {
			// @todo lazyload
			$wrap.text( '预览中' );
			__uploader.makeThumb( file, function( error, src ) {
				if ( error ) {
					$wrap.text( '不能预览' );
					return;
				}

				var img = $('<img src="'+src+'">');
				$wrap.empty().append( img );
			}, thumbnailWidth, thumbnailHeight );

			percentages[ file.id ] = [ file.size, 0 ];
			file.rotation = 0;
		}

		file.on('statuschange', function( cur, prev ) {
			if ( prev === 'progress' ) {
				$prgress.hide().width(0);
			} else if ( prev === 'queued' ) {
//				$li.off( 'mouseenter mouseleave' );
				$btns.remove();
				$btns = $('<div class="file-panel">' + '<span class="cancel">删除</span>').appendTo( $li );
				$btns.on( 'click', 'span', function() {
					__uploader.removeFile( file );
				});
			}

			// 成功
			if ( cur === 'error' || cur === 'invalid' ) {
				showError( file.statusText );
				percentages[ file.id ][ 1 ] = 1;
			} else if ( cur === 'interrupt' ) {
				showError( 'interrupt' );
			} else if ( cur === 'queued' ) {
				percentages[ file.id ][ 1 ] = 0;
			} else if ( cur === 'progress' ) {
				$info.remove();
				$prgress.css('display', 'block');
			} else if ( cur === 'complete' ) {
				$li.append( '<span class="success"></span>' );
			}

			$li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
		});

		$li.on( 'mouseenter', function() {
			$btns.stop().animate({height: 30});
		});

		$li.on( 'mouseleave', function() {
			$btns.stop().animate({height: 0});
		});

		$btns.on( 'click', 'span', function() {
			var index = $(this).index(),
			deg;

			switch ( index ) {
			case 0:
				__uploader.removeFile( file );
				return;

			case 1:
				file.rotation += 90;
				break;

			case 2:
				file.rotation -= 90;
				break;
			}

			if ( supportTransition ) {
				deg = 'rotate(' + file.rotation + 'deg)';
				$wrap.css({
					'-webkit-transform': deg,
					'-mos-transform': deg,
					'-o-transform': deg,
					'transform': deg
				});
			} else {
				$wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
			}
		});

		$li.appendTo( $queue );
	}

	// 负责view的销毁
	function removeFile( file ) {
		var $li = $('#'+file.id);
		delete percentages[ file.id ];
		updateTotalProgress();
		$li.off().find('.file-panel').off().end().remove();
	}

	function updateTotalProgress() {
		var loaded = 0,
		total = 0,
		spans = $progress.children(),
		percent;

		$.each( percentages, function( k, v ) {
			total += v[ 0 ];
			loaded += v[ 0 ] * v[ 1 ];
		} );

		percent = total ? loaded / total : 0;

		spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
		spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
		updateStatus();
	}

	function updateStatus() {
		var text = '', stats;

		if ( state === 'ready' ) {
			text = '选中' + fileCount + '张图片，共' +
			WebUploader.formatSize( fileSize ) + '。';
		} else if ( state === 'confirm' ) {
			stats = __uploader.getStats();
			if ( stats.uploadFailNum ) {
				text = '已成功上传' + stats.successNum+ '张图片至天润云服务器，'+
				stats.uploadFailNum + '张图片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
			}

		} else {
			stats = __uploader.getStats();
			text = '共' + fileCount + '张（' +
			WebUploader.formatSize( fileSize )  +
			'），已上传' + stats.successNum + '张';

			if ( stats.uploadFailNum ) {
				text += '，失败' + stats.uploadFailNum + '张';
			}
		}

		$info.html( text );
	}

	function setState( val ) {
		var file, stats;

		if ( val === state ) {
			return;
		}

		$upload.removeClass( 'state-' + state );
		$upload.addClass( 'state-' + val );
		state = val;

		switch ( state ) {
		case 'pedding':
			$placeHolder.removeClass( 'element-invisible' );
			$queue.parent().removeClass('filled');
			$queue.hide();
			$statusBar.addClass( 'element-invisible' );
			__uploader.refresh();
			break;

		case 'ready':
			$placeHolder.addClass( 'element-invisible' );
			$( '#filePicker2' ).removeClass( 'element-invisible');
			$queue.parent().addClass('filled');
			$queue.show();
			$statusBar.removeClass('element-invisible');
			$upload.removeClass( 'disabled' );
			__uploader.refresh();
			break;

		case 'uploading':
			$( '#filePicker2' ).addClass( 'element-invisible' );
			$progress.show();
			$upload.text( '暂停上传' );
			break;

		case 'paused':
			$progress.show();
			$upload.text( '继续上传' );
			break;

		case 'confirm':
			$progress.hide();
			$upload.text( '开始上传' ).addClass( 'disabled' );

			stats = __uploader.getStats();
			if ( stats.successNum && !stats.uploadFailNum ) {
				setState( 'finish' );
				return;
			}
			break;
		case 'finish':
			$( '#filePicker2' ).removeClass( 'element-invisible');
			stats = __uploader.getStats();
			if ( stats.successNum ) {
				//提交后台插入记录
				var files = __uploader.getFiles("complete");
				ids = ""; 
				$.each(files, function(key, val) {   
					ids += ","+files[key].ossid;
				});
				$("#filesUrl").val(ids);
				alert( '上传成功' );
			} else {
				// 没有成功的图片，重设
				state = 'done';
				location.reload();
			}
			break;
		}
		updateStatus();
	}

	__uploader.onUploadProgress = function( file, percentage ) {
		var $li = $('#'+file.id),
		$percent = $li.find('.progress span');

		$percent.css( 'width', percentage * 100 + '%' );
		percentages[ file.id ][ 1 ] = percentage;
		updateTotalProgress();
	};

	__uploader.onFileQueued = function( file ) {
		fileCount++;
		fileSize += file.size;
		if ( fileCount === 1 ) {
			$placeHolder.addClass( 'element-invisible' );
			$statusBar.show();
		}

		addFile( file );
		setState( 'ready' );
		updateTotalProgress();
	};

	__uploader.onFileDequeued = function( file ) {
		fileCount--;
		fileSize -= file.size;

		if ( !fileCount ) {
			setState( 'pedding' );
		}

		removeFile( file );
		updateTotalProgress();

	};

	//上传成功触发
	__uploader.on( 'uploadSuccess', function( file, response ) {
		file.ossid = response.ossid;
		var filename = file.name;
		createOssidHidden(file.ossid,_index);
		createFileNameHidden(filename,_index);
		_index++;
		$('#submit').linkbutton('enable');
	});

	__uploader.on( 'all', function( type ) {
		var stats;
		switch( type ) {
		case 'uploadFinished':
			setState( 'confirm' );
			break;

		case 'startUpload':
			setState( 'uploading' );
			break;

		case 'stopUpload':
			setState( 'paused' );
			break;

		}
	});

	__uploader.onError = function( code ) {
		alert( 'Eroor: ' + code );
	};

	$upload.on('click', function() {
		if ( $(this).hasClass( 'disabled' ) ) {
			return false;
		}

		if ( state === 'ready' ) {
			__uploader.upload();
		} else if ( state === 'paused' ) {
			__uploader.upload();
		} else if ( state === 'uploading' ) {
			__uploader.stop();
		}
	});

	$info.on( 'click', '.retry', function() {
		__uploader.retry();
	} );

	$info.on( 'click', '.ignore', function() {

	} );

	$upload.addClass( 'state-' + state );
	updateTotalProgress();

}

function createOssidHidden(ossid,index){
	$("<input type='hidden' name='storages["+index+"].ossid' value='"+ossid+"' data-options='required:true'/>").appendTo("#addfm");

}
function createFileNameHidden(filename,index){
	$("<input type='hidden' name='storages["+index+"].name' value='"+filename+"' data-options='required:true'/>").appendTo("#addfm");

}


