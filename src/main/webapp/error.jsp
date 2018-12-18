<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8"%>
 错误页面： <%=exception.getMessage()%>

<%--
<%
    for(StackTraceElement stack : exception.getStackTrace()){
        out.println(stack);
    }
%>--%>
