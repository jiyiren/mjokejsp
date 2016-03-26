<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jiyi.joke.bean.SingleJoke"%>
<%@ page import="com.jiyi.joke.dao.UserDao"%>
<%@ page import="com.jiyi.joke.util.Base64Util"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Mjoke</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" /> -->
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
body {
	text-align: center;
	vertical-align: middle;
}

table {
	border: 50px solid white;
	border-collapse: collapse;
}
</style>

</head>

<body>
	<center>
		<img src="imglogo/mjokelogo.png" height="70" width="70" />
		<table cellpadding="0" cellspacing="0" border="0" align="center">
			</tr>
			<%
				SingleJoke joke = new SingleJoke();
				joke = new UserDao().getJokeByJokeId(request
						.getParameter("1459050189joke_id"));
				if (joke != null) {
			%>
			<tr width="250" align="center">
				<table align="center">
					<tr align="center">
						<td align="center"><%=joke.getContent()%></td>
					<tr>
				</table>

			</tr>
			<%
				if (joke.getIshasing().equals("1")) {
			%>
			<tr>
				<td><img
					src="http://mjoke.oss-cn-shanghai.aliyuncs.com/<%=joke.getImgurl()%>.jpg"
					height="300" width="250" /></td>
			</tr>
			<%
				}
			%>
			<%
				} else {
			%>
			访问的页面木有啦！
			<%
				}
			%>
		</table>
	</center>
</body>
</html>
