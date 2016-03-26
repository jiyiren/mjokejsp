<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'reg.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
 <body>
	用户注册：<br/>
<form action="servlet/UserRegister" method="post">
用户名:<input type="text" name="username" id="username">
密码:<input type="password" name="userpwd" id="userpwd">
<input type="submit" value="注册">
</form>

用户登录：<br/>
<form name="登录" action="servlet/UserLogin" method="post">
类型:<br/>
<input type="radio" name="type" id="type" value="0">用户名登录
<input type="radio" name="type" id="type" value="1">QQ登录<br/>
用户名:<input type="text" name="username" id="username"><br/>
密码:<input type="password" name="userpwd" id="userpwd">
<input type="submit" value="登录">
</form>  

用户修改：<br/>
<form name="修改" action="servlet/ModifyUserInfo" method="post">
用户token:<input type="text" name="token" id="token"><br/>
用户名:<input type="text" name="username" id="username"><br/>
用户性别:<input type="text" name="sex" id="sex"><br>
用户座右铭:<input type="text" name="motto" id="motto"><br>
<input type="submit" value="修改">
</form>  

获取jokes：<br/>
<form name="获取" action="http://127.0.0.1:8088/joke/servlet/GetJokes" method="post">
curjoke_id:<input type="text" name="joke_id" id="joke_id"><br/>
fragtype:<input type="text" name="fragtype" id="fragtype"><br>
scroll_type:
<input type="radio" name="scroll_type" id="scroll_type" value="1">下拉刷新
<input type="radio" name="scroll_type" id="scroll_type" value="2">上拉加载<br/>
count:<input type="text" name="count" id="count"><br>
<input type="submit" value="Get">
</form>  
	用户查询：
<form action="servlet/GetInfoById" method="post">
toke或者user_id:<input type="text" name="token" id="token"><br>
<input type="submit" value="查询">
</form>

发表无图说说
<form action="servlet/PublishNoImgJoke" method="post">
toke或者user_id:<input type="text" name="token" id="token"><br>
type：<input type="text" name="type" id="type"><br/>
content：<input type="text" name="content" id="content"><br/>
<input type="submit" value="发表">
</form>

收藏
<form action="servlet/UserCollect" method="post">
joke_id:<input type="text" name="joke_id" id="joke_id"><br>
user_id或者token：<input type="text" name="token" id="token"><br/>
type:<input type="text" name="type" id="type"><br>
<input type="submit" value="收藏">
</form>
查看记录
<form action="servlet/UserLook" method="post">
joke_id:<input type="text" name="joke_id" id="joke_id"><br>
user_id或者token：<input type="text" name="token" id="token"><br/>
<input type="submit" value="插入查看记录">
</form>

获取类型joke
<form action="servlet/GetUserAboutJokes" method="post">
joke_id:<input type="text" name="joke_id" id="joke_id"><br>
user_id或者token：<input type="text" name="token" id="token"><br/>
count:<input type="text" name="count" id="count"><br/>
joketype:<input type="text" name="joketype" id="joketype"><br/>
<input type="submit" value="获取类型joke">
</form>

反馈问题
<form action="servlet/Question" method="post">
content:<input type="text" name="quescontent" id="quescontent"><br>
email：<input type="text" name="quesemail" id="quesemail"><br/>
<input type="submit" value="反馈问题">
</form>


获取高点击joke
<form action="servlet/GetTopJokes" method="post">
look_count(赞):<input type="text" name="look_count" id="look_count"><br>
scroll_type:<br/>
<input type="radio" name="scroll_type" id="scroll_type" value="1">下拉刷新
<input type="radio" name="scroll_type" id="scroll_type" value="2">上拉加载<br/>
count:<input type="text" name="count" id="count"><br>
fragtype:<input type="text" name="fragtype" id="fragtype"><br>
<input type="submit" value="top">
</form>

插入评论
<form action="servlet/InsertComment" method="post">
joke_id:<input type="text" name="joke_id" id="joke_id"><br>
token/user_id：<input type="text" name="token" id="token"><br/>
username:<input type="text" name="username" id="username"><br>
content：<input type="text" name="com_content" id="com_content"><br/>
<input type="submit" value="插入评论">
</form>


查询评论
<form action="servlet/GetComments" method="post">
joke_id:<input type="text" name="joke_id" id="joke_id"><br>
cur_lou:<input type="text" name="cur_lou" id="cur_lou"><br>
count：<input type="text" name="count" id="count"><br/>
<input type="submit" value="查询">
</form>
赞一下
<form action="servlet/Zan" method="post">
joke_id:<input type="text" name="joke_id" id="joke_id"><br>
type：<input type="text" name="zan_type" id="zan_type"><br/>
<input type="submit" value="点一下">
</form>

</body>
</html>
