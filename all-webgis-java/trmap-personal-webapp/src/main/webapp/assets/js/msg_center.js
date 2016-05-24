/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2016/2/25
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
define(function(require, exports, module) {
	var co = require("__/js/comm");
	var u = require("_/tr_util");
	var ht = $("html");
	require("_/avalon.min");


	var msgcenter;
	var msg_list;
	$(function(){

		setTimeout(function () {
			$(".col2>ul>li a").css("display","inline-block");
		}, 100);

		msgcenter =avalon.define({
			$id:"msgcenter",
			list:{
				all:"",
				yyd:"",
				wyd:""
			},
			cureent_name_index:-1,
			name:"",
			nameid:"-1",
			togg:function(el,index){
				msgcenter.nameid=el;
				if(el===-1){
					msg_list.msgname = "所有消息";
				}else if(el===1){
					msg_list.msgname = "已读消息";
				}else if(el ===0){
					msg_list.msgname = "未读消息";
				}

				msgpage.doo();

			}
		})

		msgcenter.$watch("list.wyd",function(v){
			if(top.nav){
				if(v!=0){
					top.nav.show_notice(3);
				}else{
					top.nav.show_notice(3,true);
				}
			}

		});


		function fresh_counter(){
			u.ajax(
				u.pagevar("msgcenter","../data/msgcenter.json"),function(t,data){
					if(t.vl()){
						msgcenter.list=data;
						/*msgcenter.list.wyd=data.wyd;
						
						msgcenter.$watch("list.wyd",function(v){
							if(data.wyd !=0) {
								if(top.nav){
									top.nav.show_notice(3,v == 0);
								}
							}
						});*/
	                    
					}
				}
			)
		}

		fresh_counter();

		setTimeout(fresh_counter,10 * 1000);


		msg_list =avalon.define({
			$id:"msg_list",
			msglist:[],
			totalCount:"",
			msgname:"",
			cureent_msg_index:"",
			msgmsg:function(el,index){
				
				msg_list.cureent_msg_index=index;
				msgpop_up.list.msgType = el.msgType;
				msgpop_up.list.username = el.username;
				msgpop_up.list.messageContent = el.messageContent;
				var comment_deleted = false;
				
				if(el.msgType==2){
					if(el.comment){
						msgpop_up.list.title = el.comment.title;
						msgpop_up.list.description=el.comment.description;
						msgpop_up.list.comcontent=el.comment.comcontent;
						msgpop_up.list.comtime=el.comment.comtime;
						msgpop_up.list.id=el.comment.id;
						msgpop_up.list.cuser_id=el.comment.cuser_id;
						msgpop_up.list.cusername=el.comment.cusername;
						msgpop_up.list.rec_user_id=el.comment.rec_user_id;
						msgpop_up.list.rid=el.comment.user_rid;
						msgpop_up.list.parentid=el.comment.parent_id;
						msgpop_up.list.hasDeleted=el.comment.hasDeleted;
						msgpop_up.list.cuser_headimg=el.comment.cuser_headimg;
						msgpop_up.list.child=[];
					}else{
						comment_deleted = true;
					}
				}
				u.ajax(
					u.pagevar("clickmsg","../data/success.json"),
					{
						id:el.id,
						readflag:el.readflag,
						recordId:el.recordId,
						msgType:el.msgType
					},
					function(t,data){
						if(t.vl()){

							if(comment_deleted){
								
								u.msg("亲，来迟了哦,该条评论已被删除！")
								return;
							}
							//弹出层
							layer.open({
								type: 1,
								title: false,
								closeBtn: 1,
								area: '564px',
								shadeClose: true,
								content: $(".msgpop_up")
							});

							//处理消息状态改变
							if(el.readflag!=1){
								fresh_counter();
								msgpage.doo();
							}

							el.readflag=1;


							if(msgcenter.nameid == 0){
								/*msg_list.msglist.remove(el);*/
								if(msg_list.list == 1){
									msgcenter.togg(0);
								}
							}
						}
					}
				)
			}
		})

		var msgpage = new (require("$/splitReq"))({
			reqPath:u.pagevar("msg_list","../data/meg_list.json"),
			container:".pageno",
			rows:10,
			firstReqAuto:false,
			continuous_page: 5,
			onReq:function(p){
				p.readflag = msgcenter.nameid;
			},
			onData:function(data){
				msg_list.msglist =data.rows;
				msg_list.totalCount = data.totalCount;
				ht.addClass("no_cl_loading");
			}

		})
		


		msgcenter.togg(-1);



		/*回复消息框*/
		var replay=$("textarea.messages");
		//消息弹出框
		var msgpop_up;

		msgpop_up =avalon.define({
			$id:"msgpop_up",
			default_img:co.theme_thumb_default,
			ibpath:co.media_image_path,
			list:{
				hasDeleted:false,
				msgType:"",
				username:"",
				messageContent:"",//消息内容
				comcontent:"",//评论内容
				id : "",
				parentid:"",
				title:"",
				description:"",
				comtime:"刚刚",
				cuser_id:"",//评论者的id
				cusername:"",//评论者的id
				cuser_headimg:"",
				rec_user_id:"",//当前写记录人id
				rid:"",//当前记录的id
				current_user_id:current_user_id,//当前用户id
				current_user_name:current_user_name,//当前用户name
				child:[]
			},
			publish : function(el) {
				var newmess = $(".messages")
				var newmessage = $.trim(newmess.val());
				replay.attr("placeholder","")
				var msg = {
					id:"",
					rid:msgpop_up.list.rid,
					comcontent:newmessage,
					cuser_id:msgpop_up.list.current_user_id,
					buser_id:msgpop_up.list.cuser_id,
					parent_id:msgpop_up.list.parentid?msgpop_up.list.parentid:msgpop_up.list.id,
					ruserid:msgpop_up.list.cuser_id
				}
				if (!newmessage) {
					u.tips(newmess, "请输入回复内容", 100);
					return false;
				} else if(newmessage.length>100){
					u.tips(newmess, "评论内容不能超过100字", 100);
					return false;
				}else{
					msg.id = "";
					u.ajax(u.pagevar("huifu_comment","../data/success.json"), msg, function(t,data) {
						if (t.vl()) {
							msgpop_up.list.child.push({
								id:data.commentId,
								cusername:current_user_name,
								busername:msgpop_up.list.cusername,
								comcontent:newmessage,
								comtime:new Date()
							});
						}
					})
					newmess.val("");
				}

			},
			/*回复*/
			onReplay:function(el){
				replay.attr("placeholder",'回复：'+el.cusername)
			},

			del:function(el){
				u.confirm("确认删除",function(cancel){
					if(cancel) return;
					u.ajax(u.pagevar("del_comment","../data/success.json"),
						{
							id:el.id
						},
						function(t){
							if(t.vl()){

								if(el.child){
									msgpop_up.list.hasDeleted = true;
								}else{
									msgpop_up.list.child.remove(el);

								}


							}
						}
					)
				})
			}


		});




		avalon.scan();

	})



	return {};
});

