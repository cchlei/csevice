<%@ page pageEncoding="UTF-8"%>
<div class="topbar">
	<a class="logo" href="/"> <i></i> <b></b>
	</a> <a href="#" class="user_header"><img
		src="http://oss.trmap.cn/rs/download/${headimg}"></a> <span
		class="user_name"> 欢迎， <a href="#">${username}</a> <b>|</b> <a href="<%=request.getContextPath()%>/logout">退出</a>
	</span>
</div>
<div class="nav col1">
	<a href="<%=request.getContextPath()%>/topic/tolist" class="cur"> <i><img
			src="<%=request.getContextPath()%>/assets/images/icon_zhuanti.png" alt=""></i> <b>专题</b>
	</a> <a href="<%=request.getContextPath()%>/topic/tocalendar"> <i><img
			src="<%=request.getContextPath()%>/assets/images/icon_rili.png" alt=""></i> <b>日历</b>
	</a> <a href="#"> <i><img
			src="<%=request.getContextPath()%>/assets/images/icon_haoyou.png" alt=""></i> <b>好友</b>
	</a> <a href="#"> <i><img
			src="<%=request.getContextPath()%>/assets/images/icon_xiaoxi.png" alt=""></i> <b>消息</b>
	</a> <a href="http://a.trmap.cn/e/toupdate"> <i><img
			src="<%=request.getContextPath()%>/assets/images/icon_zhanghu.png" alt=""></i> <b>账户</b>
	</a>
</div>