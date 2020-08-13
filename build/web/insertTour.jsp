<%-- 
    Document   : insertTour
    Created on : Jun 14, 2020, 10:07:27 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Tour</title>
    </head>
    <body>
         <c:if test="${empty sessionScope.ADMIN}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <h1>Insert Page</h1>
        <form action="insertTours" name="insForm" method="post" onsubmit="return checkValidation()" enctype="multipart/form-data">
            Name:<input type="text" name="txtName" value="" /><br/>
            <font color="red">
            <span id="name"></span><br/>
            </font>           
            Date From: <input type="date" name="txtDateFrom" value="" /><br/>
            <font color="red">
            <span id="dateFrom"></span><br/>
            </font>              
            Date To: <input type="date" name="txtDateTo" value="" /><br/>
            <font color="red">
            <span id="dateTo"></span><br/>
            </font>           
            Price: <input type="text" name="txtPrice" value=""/><br/>
            <font color="red">
            <span id="priceNum"></span><br/>
            </font>          
            Quota: <input type="text" name="txtQuota" value="" /><br/>
            <font color="red">
            <span id="quotaNum"></span><br/>
            </font>            
            Place: <input type="text" name="txtPlace" value="" /><br/>
            <font color="red">
            <span id="place"></span><br/>
            </font>           
            Image: <input type="file" name="file" value="Upload image here" /><br/>
            <font color="red">
            <span id="image"></span><br/>
            </font>
            <font color="red">
            <span id="compareDate"></span><br/>
            </font>            
            <input type="submit" value="Insert"/> 
            <input type="reset" value="Reset" />
        </form>
   
    <script>
        function checkValidation() {
            var name = document.insForm.txtName.value;
            var dateFrom = document.insForm.txtDateFrom.value;
            var dateTo = document.insForm.txtDateTo.value;
            var price = document.insForm.txtPrice.value;
            var quota = document.insForm.txtQuota.value;
            var place = document.insForm.txtPlace.value;

            if (name.length < 3 || name.length > 50) {
                document.getElementById("name").innerHTML = "Name must be from 3 to 50 characters";
                return false;
            }
            if (dateFrom == "" || dateFrom == null) {
                document.getElementById("dateFrom").innerHTML = "You must choose date from!";
                return false;
            }
            if (dateTo == "" || dateTo == null) {
                document.getElementById("dateTo").innerHTML = "You must choose date to!";
                return false;
            }
            if (isNaN(price) || price.length < 6) {
                document.getElementById("priceNum").innerHTML = "Enter number which is equal or larger than 100000";
                return false;
            }
            if (place.length < 3 || place.length > 50) {
                document.getElementById("place").innerHTML = "Place must be from 3 to 50 characters";
                return false;
            }
            if (isNaN(quota) || quota.length < 2) {
                document.getElementById("quotaNum").innerHTML = "Enter number which is equal or larger than 10";
                return false;
            }
            var date1 = new Date(dateFrom);
            var date2 = new Date(dateTo);
            var today = new Date();
            if (date1 > date2 || date1 < today) {
                document.getElementById("compareDate").innerHTML = "Date from must be before or equals date to and must be before today";
                return false;
            }
        }
    </script>
</body>
</html>
