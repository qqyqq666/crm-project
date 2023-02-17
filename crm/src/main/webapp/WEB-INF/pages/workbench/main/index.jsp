<!--设置jsp的页面编码格式-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	//端口号后面斜杠可以不加
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +request.getContextPath()+"/" ;
%>

<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

</head>
<body>
	<img src="image/home.png" style="position: relative;top: -10px; left: -10px;"/>
</body>
</html>