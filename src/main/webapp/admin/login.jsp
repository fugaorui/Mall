<%--
  Created by IntelliJ IDEA.
  User: 32642
  Date: 2018/12/4
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../base.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/admin/login.do" method="post">
    账号：<input type="text" name="username" id="username"><br>
    密码：<input type="password" name="password" id="password"><br>
    <input type="submit" value="确认" >
    admin文件夹下返回错误信息：<%=session.getAttribute("loginFailure")%>
</form>
</body>
</html>
