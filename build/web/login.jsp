<%-- 
    Document   : index
    Created on : Jun 7, 2020, 10:41:22 AM
    Author     : USER
--%>

<%@page import="nhunnq.wrapper.APIWrapper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <c:set var="error" value="${requestScope.LOGINERROR}"/>
        <h1>Login Page</h1>
        <form action="login" method="POST">
            Username: <input type="text" name="txtUsername" value="" /><br/>
            Password: <input type="password" name="txtPassword" value="" /><br/>
            <input type="submit" value="Login" />
            <input type="reset" value="Reset" /><br/>

        </form>
         <a href="<%= APIWrapper.getDialogLink() %>">Login With Facebook</a>
        <c:if test="${not empty error}">
            <font color="red">${requestScope.LOGINERROR}</font>
        </c:if>
    </body>
</html>
