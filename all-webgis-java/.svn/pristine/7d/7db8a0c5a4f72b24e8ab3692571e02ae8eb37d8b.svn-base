// 图片上传demo
jQuery(function() {
    var $ = jQuery,
        $list = $('#fileList4'),
        // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,

        // 缩略图大小
        thumbnailWidth = 100 * ratio,
        thumbnailHeight = 100 * ratio,

        // Web Uploader实例
        uploader4;

    // 初始化Web Uploader
    uploader4 = WebUploader.create({

    	 // 自动上传。
        auto: true,
        // 可以重复上传
        duplicate: true,
        
        swf: ctx + '/assets/Uploader.swf',

        // 文件接收服务端。
        server: ctx + '/entfile/upload',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: {
        	id: '#filePicker4',
        	multiple: false
        },

        // 只允许选择图片文件，可选。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    });

    // 当有文件添加进来的时候
    uploader4.on( 'fileQueued', function( file ) {
        var $li = $(
                '<div id="' + file.id + '" class="file-item thumbnail">' +
                    '<img>' +
                    '<div class="info">' + file.name + '</div>' +
                '</div>'
                ),
            $img = $li.find('img');
        
        $list.children().remove();

        $list.append( $li );

        // 创建缩略图
        uploader4.makeThumb( file, function( error, src ) {
            if ( error ) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }

            $img.attr( 'src', src );
        }, thumbnailWidth, thumbnailHeight );
    });

    // 文件上传过程中创建进度条实时显示。
    uploader4.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress span');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<p class="progress"><span></span></p>')
                    .appendTo( $li )
                    .find('span');
        }

        $percent.css( 'width', percentage * 100 + '%' );
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader4.on( 'uploadSuccess', function( file, response) {
        $( '#'+file.id ).addClass('upload-state-done');
        file.ossid = response._raw;
    });

    // 文件上传失败，现实上传出错。
    uploader4.on( 'uploadError', function( file ) {
        var $li = $( '#'+file.id ),
            $error = $li.find('div.error');

        // 避免重复创建
        if ( !$error.length ) {
            $error = $('<div class="error"></div>').appendTo( $li );
        }

        $error.text('上传失败');
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader4.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').remove();
        //提交后台插入记录
    	var files = uploader4.getFiles("complete");
    	var size = files.length;
        ids = ""; 
//    	$.each(files, function(key, val) {   
//    	    ids += ","+files[key].ossid;
//    	});
    	ids = files[size - 1].ossid;
//    	alert(ids);
    	$("#u4").val(ids);
    });
});