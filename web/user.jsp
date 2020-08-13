<%-- 
    Document   : user
    Created on : Jun 21, 2020, 10:57:51 AM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
    </head>
    <body>
        <c:if test="${empty sessionScope.USER}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <c:if test="${not empty sessionScope.FULLNAME}">
            Welcome,<font color="red">${sessionScope.FULLNAME}</font>
        </c:if>
        <a href="logout">Logout</a><br/>
        
        <jsp:include page="showTour.jsp"/>
        
    </body>
</html>
