<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'upload.jsp' starting page</title>

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
	<form action="servlet/ReceiveUserPic" method="post" enctype="multipart/form-data">
		文件名：<input type="text" name="filename" id="filename"><br/>
		选择文件：<input type="file" name="mfile"><br />
		<br /> 
		<input type="submit" value="submit">
	</form>
	<form action="http://mjokeapp.duapp.com/servlet/PublishJoke" method="post" enctype="multipart/form-data">
		userid：<input type="text" name="token" id="token"><br/>
		type：<input type="text" name="type" id="type"><br/>
		content：<input type="text" name="content" id="content"><br/>
		选择文件：<input type="file" name="jokepic"><br />
		<br /> 
		<input type="submit" value="submit">
	</form>
</body>
</html>
