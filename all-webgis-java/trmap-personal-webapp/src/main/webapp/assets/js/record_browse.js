/**
 * Created with PhpStorm. User: Shinelon Date: 2016/2/25 Time: 14:44 To change
 * this template use File | Settings | File Templates.
 */
define(function(require, exports, module) {
	require("_/avalon.min");
	var co = require("__/js/comm");
	var u = require("_/tr_util");
	var ht = $("html");
	var para = u.cl.urlParas();

	ht.addClass("no_cl_loading");

	var ha = top.hash_parse;
	if(ha){
		ha.hash("#|" + co.getPath())
	}

	if(para.from){
		$(function(){
			$(".page_name>a").attr("href","script:;").click(function(){
				top.location.href = decodeURIComponent(para.from);
			});
		});
	}




	var isopen;
	var browse;
	$(function() {

		browse = avalon.define({
			$id : "record_browse",
			data : []
		})

		u.ajax(
			u.pagevar("recode_brows", "../data/recode_brows.json"),
			function (t, data) {
				if (t.vl()) {
					browse.data = data;
				}
			}
		)

		/**
		 * 附件管理
		 * 
		 * @type {{init: Function}}
		 */
		var filemgr = {
			init : function() {
				var m = this;
				m.el = $(".uploadthing");

				m.vm = avalon.define({
					$id: "file_man",
					list: [],
					// 图片文件路径前缀
					ibpath: co.media_image_path
				});

				u.ajax(
						u.pagevar("imgs_data", "../data/imgs_data.json"), {}, function (t, data) {
							if (t.vl()) {
								$.each(data.rows, function (k, el) {
									var f = {
										id: el.id,
										name: el.name,
										ext:el.ext
									};
									if (co.is_img(el.ext)) {
										f.thumb = co.getThumbPath(120) + el.id;
									} else {
										f.thumb = co.filetype_thumb[el.ext] || co.filetype_thumb["default"];
									}
									f.isimg = co.is_img(el.ext);
									m.vm.list.push(f);
								})

							}
						}
				)


			},
            appendFile:function(file){
                var m = this;
                m.vm.list.push(file);
            }
        };

        filemgr.init();


		//附件预览
		var pic_shower = ({
			init:function(){
				var m = this;

				m.el = $(".uploadthing");

				var data ;
				var siblings;
				m.el.delegate(">.img","click",function(e){
					var cur = $(this);
					require.async("$/calbum",function(cal){
						if(!data){
							siblings = m.el.find(".img");
							data = siblings.map(function(){
								return {
									src:$("b",this)     .attr("title")
								};
							}).toArray();
						}

						cal.create().open().setData(data,siblings.index(cur));



					})
				});


			}
		}).init();


        /*$(".uploadthing").delegate(".file","click",function(e){
        	var fileid = $("b",this).attr("title");
        	window.open(co.media_image_path + fileid);
        })*/


		/**
		 * 管理留言的部分
		 */
		var msg_element = ({
		    init:function(){
		        var m = this;

				m.el = $(".comments-main");


				/*留言*/
				var msg = {
					id:"",
					rid : recordId,
					cuser_id :cuserId,
					cusername:current_user_name,
					busername:"",
					buser_id:"",	
					cuser_headimg:"",
					comcontent:"",
					parent_id:"",
					ruserid:buser_id,
					comtime:"刚刚",
					child:[]
				}

				m.message = avalon.define({
					$id:"message",
					list:[],
					msg:msg,
					moren:co.media_image_path+current_user_img,//默认当前登陆者的头像
					comcontent:"",
					imgpath:co.media_image_path,
					static_info:{
						c_user_id:cuserId,
						rec_user_id:buser_id
					},

					/**
					 * 清除回复给
					 */
					clear_replay_to : function() {
						m.message.msg.buser_id = "";
						m.message.msg.busername = "";
						m.message.msg.parent_id = "";
						//m.message.msg.ruserid = "";		
					},


					/**
					 * 单击回复
					 * @param msg
					 * @param parent_msg
					 */
					onReplay : function(msg, parent_msg) {

						m.message.msg.buser_id = msg.cuser_id;
						m.message.msg.busername = msg.cusername;
						// 存在 parent_msg|点的是第二层评论|
						if (parent_msg) {
							m.message.msg.parent_id = parent_msg.id;
							
						} else {
							m.message.msg.parent_id = msg.id;
						}

					},

					/**
					 * 删除评论或者回复
					 * @param msg
					 * @param pmsg
					 */
					del:function(msg,pmsg){
						u.confirm("确认删除",function(cancel){
							if(cancel) return;
							u.ajax(u.pagevar("del_comment","../data/success.json"),
									{
										id:msg.id
									},
									function(t){
										if(t.vl()){

											if(pmsg){
												pmsg.child.remove(msg)
											}else{
												m.message.list.remove(msg);
											}
											m.message.clear_replay_to();
										}
									}
							)
						})

					},


					/**
					 * 发表评论
					 * @param el
					 * @returns {boolean}
					 */
					publish : function(el) {
						var newmess = $(".comment-area")
						var newmessage = $.trim(newmess.val());
						var msg = m.message.msg;
						if (!newmessage) {
							u.tips(newmess, "请输入评论内容", 100);
							return false;
						} else if(newmessage.length>100){
							u.tips(newmess, "评论内容长度不能超过100", 100);
							return false;
						}else{
							msg.id = "";
							u.ajax(u.pagevar("recode_addcomment","../data/success.json"), msg.$model, function(t) {
								if (t.vl()) {
									msg.id = t.data.commentId;
									if (!msg.id) {
										msg.id = Math.random();
									}

									var clone_msg = $.extend({}, msg.$model);


									msg.buser_id = "";
									msg.busername = "";
									msg.comcontent = "";
									msg.parent_id = "";
									msg.parent_id = "";

									if (clone_msg.parent_id) {
										$.each(m.message.list.$model, function(k, ms) {
											if (ms.id == clone_msg.parent_id) {
												m.message.list[k].child.push(clone_msg);
											}
										})
									} else {
										m.message.list.push(clone_msg);
									}

									!function(){
										var msg_dom = m.el.find("[commid=" + msg.id + "]");
										if(msg_dom.length) {
											m.el.animate({
												scrollTop:msg_dom.position().top + m.el.prop("scrollTop")
											})

											//最新留言的高亮效果
											//msg_dom.addClass("light");
											!function(){
												msg_dom.removeClass("light");
											}.delayCall(30*1000);
										}
									}.delayCall(100);
								}
							})
						}

					}
				})



				m.loadmsg = function() {
					 var ac = arguments.callee;
					 u.ajax(
							u.pagevar("recode_comments", "../data/message.json"), {},
							function(t, data) {
								if (t.vl()) {
									$.each(data.rows,function(k,msg){
										if(!msg.child){
											msg.child = [];
										}
									})
									m.message.list = data.rows
								}
								ac.delayCall(10*1000);
							}
							
					)
				}

				m.loadmsg();



		        return m;
		    }
		}).init();




		avalon.scan();
	})

	return {};
});