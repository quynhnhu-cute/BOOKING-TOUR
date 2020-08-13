<%-- 
    Document   : admin
    Created on : Jun 7, 2020, 4:53:10 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    </head>
    <body>
        
        <c:if test="${empty sessionScope.ADMIN}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <c:if test="${not empty sessionScope.FULLNAME}">
            Welcome,
            <font color="red">${sessionScope.FULLNAME}</font>
        </c:if>
        <a href="logout">Logout</a>
        
        <jsp:include page="showTour.jsp"/>
         <c:if test="${not empty requestScope.USERREQUIRE}">
            <font color="red">${requestScope.USERREQUIRE}</font>
        </c:if>
       
       
        
    </body>
</html>
