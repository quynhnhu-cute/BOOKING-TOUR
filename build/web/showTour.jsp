<%-- 
    Document   : showTour
    Created on : Jun 8, 2020, 6:10:26 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <title>Show Tour</title>
    </head>
    <body>
         
        <h1>
            <font color="red">WELCOME TO OUR BOOKING TOUR WEBSITE</font>
        </h1>        
        <c:if test="${empty sessionScope.FULLNAME}">
            <a href="login.jsp">Login here</a>
        </c:if><br/>
       

        <form action="search">

            Place: <input type="text" name="txtPlace" value="${param.txtPlace}" /><br/>
            Date From: <input type="date" name="txtDateFrom" value="${param.txtDateFrom}"/><br/>
            Date To: <input type="date" name="txtDateTo" value="${param.txtDateTo}"/><br/>            
            Price From: <select name="txtPriceFrom">
                <option>1 000 000</option>
                <option>3 000 000</option>
                <option>5 000 000</option>
                <option>7 000 000</option>
                <option>10 000 000</option>
                <option>20 000 000</option>
                <option>50 000 000</option>
            </select>
            Price To: <select name="txtPriceTo">               
                <option>3 000 000</option>
                <option>5 000 000</option>
                <option>7 000 000</option>
                <option>10 000 000</option>
                <option>20 000 000</option>
                <option>50 000 000</option>
                <option>100 000 000</option>
            </select><br/>
            <input type="submit" value="Search"/>
        </form>
        <form action="showTours">
            <input type="submit" value="Show All Tours" />
        </form>
        <c:if test="${not empty sessionScope.ADMIN}">
            <form action="insertTour.jsp">
                <input type="submit" value="Insert Tour" />
            </form>
        </c:if><br/>
        <c:if test="${not empty sessionScope.USER}">
            <a href="viewCart.jsp">View Your Cart</a>
        </c:if><br/>

        <c:set var="searchlist" value="${requestScope.SEARCHLIST}"/>
        <c:set var="tourlist" value="${requestScope.TOURLIST}"/>

        <c:if test="${not empty searchlist}">
            <table border="1">
                <thead>
                    <tr>
                        <th>TourName</th>
                        <th>Date From</th>
                        <th>Date To</th>
                        <th>Price</th>
                        <th>Date Import</th>
                        <th>Quota</th>
                        <th>Image</th>
                        <th>Place</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${searchlist}">
                    <form action="addToCart">

                        <tr>
                            <td style="width:200px;">${item.tourName}</td>
                            <td style="width:200px;">${item.dateFrom}</td>
                            <td style="width:200px;">${item.dateTo}</td>
                            <td style="width:200px;">${item.priceShowing}VNĐ</td>
                            <td style="width:200px;">${item.importDate}</td>
                            <td style="width:200px;">${item.quota}</td>
                            <td style="width:200px;">
                                <img src="${item.imageLink}" width="300" height="200"/>
                            </td>
                            <td style="width:200px;">${item.place}</td>
                            <td>
                                <input type="submit" value="Add To Cart" />
                                <input type="hidden" name="tourId" value="${item.tourId}" />
                                <input type="hidden" name="tourName" value="${item.tourName}" />
                                <input type="hidden" name="dateFrom" value="${item.dateFrom}" />
                                <input type="hidden" name="dateTo" value="${item.dateTo}" />
                                <input type="hidden" name="priceShowing" value="${item.priceShowing}" />
                            </td>
                        </tr>   
                    </form>
                </c:forEach>                    
            </tbody>
        </table> 
        <c:forEach begin="1" end="${requestScope.NUMBER}" step="1" varStatus="counter">
            <c:url var="number" value="${search}">
                <c:param name="txtPageNum" value="${counter.count}"/>
                <c:param name="place" value="${requestScope.PLACE}"/>
                <c:param name="dateFrom" value="${requestScope.DATEFROM}"/>
                <c:param name="dateTo" value="${requestScope.DATETO}"/>
                <c:param name="priceFrom" value="${requestScope.FROMPRICE}"/>
                <c:param name="priceTo" value="${requestScope.TOPRICE}"/>
            </c:url>
            <a href="${number}">${counter.count}</a>
        </c:forEach>
    </c:if>
    <c:if test="${not empty requestScope.EMPTYSEARCH}">
        <font color="red">${requestScope.EMPTYSEARCH}</font>
    </c:if>

    <c:if test="${not empty tourlist}">
        <table border="1">
            <thead>
                <tr>
                    <th style="width:200px;">TourName</th>
                    <th style="width:200px;">Date From</th>
                    <th style="width:200px;">Date To</th>
                    <th style="width:200px;">Price</th>
                    <th style="width:200px;">Date Import</th>
                    <th style="width:200px;">Quota</th>
                    <th style="width:200px;">Image</th>
                    <th style="width:200px;">Place</th>
                    <th style="width:200px;"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${tourlist}">
                <form action="addToCart">

                    <tr>
                        <td style="width:200px;">${item.tourName}</td>
                        <td style="width:200px;">${item.dateFrom}</td>
                        <td style="width:200px;">${item.dateTo}</td>
                        <td style="width:200px;">${item.priceShowing}VNĐ</td>
                        <td style="width:200px;">${item.importDate}</td>
                        <td style="width:200px;">${item.quota}</td>
                        <td style="width:200px;">
                            <img src="${item.imageLink}" width="300" height="200"/>


                        </td>
                        <td style="width:200px;">${item.place}</td>
                        <td>
                            <input type="submit" value="Add To Cart" />
                            <input type="hidden" name="tourId" value="${item.tourId}" />
                            <input type="hidden" name="tourName" value="${item.tourName}" />
                            <input type="hidden" name="dateFrom" value="${item.dateFrom}" />
                            <input type="hidden" name="dateTo" value="${item.dateTo}" />
                            <input type="hidden" name="priceShowing" value="${item.priceShowing}" />
                        </td>
                    </tr>  
                </form>
            </c:forEach>                    
        </tbody>
    </table>
    <c:forEach begin="1" end="${requestScope.NUMBERMAX}" step="1" varStatus="counter">
        <c:url var="number" value="${showTours}">
            <c:param name="numberPage" value="${counter.count}"/>
        </c:url>
        <a href="${number}">${counter.count}</a>
    </c:forEach>

</c:if>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>        

</body>
</html>
