<%-- 
    Document   : viewCart
    Created on : Jun 20, 2020, 11:03:21 AM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Cart</title>
    </head>
    <body>
        <c:if test="${empty sessionScope.USER}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <c:if test="${not empty sessionScope.FULLNAME}">
            Welcome,
            <font color="red">${sessionScope.FULLNAME}</font>
        </c:if><br/>
        <h1>Your Cart</h1>


        <c:if test="${not empty sessionScope.TOUR_CART}">
            <c:set var="cartList" value="${sessionScope.TOUR_CART}"/>
            <c:set var="items" value="${cartList.items}"/>
            <c:if test="${not empty items}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>TourName</th>
                            <th>DateFrom</th>
                            <th>DateTo</th>
                            <th>Price</th>
                            <th>Quanity</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${items}" varStatus="counter">
                        <form action="updateAmount" name="updateForm">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${item.key.tourName}</td>
                                <td>${item.key.dateFrom}</td>
                                <td>${item.key.dateTo}</td>
                                <td>${item.key.priceShowing}</td>
                                <td>
                                    <input type="text" name="txtQuanity" value="${item.value}" />

                                </td>
                                <td>

                                    <input type="submit" value="Update Amount" />
                                    <input type="hidden" name="tourId" value="${item.key.tourId}" />                                  
                                </td>
                                <td>
                                    <c:url var="remove" value="removeItem">
                                        <c:param name="tourId" value="${item.key.tourId}"/>                                       
                                    </c:url>
                                    <a href="${remove}" onclick="return confirm('Are you sure to delete?')">Remove</a>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>



                </tbody>
            </table>
            <font color="red">
            Price: <input type="text" name="txtPrice" value="${sessionScope.TOUR_CART.priceShowing}" disabled style="border:none;" />            
            </font><br/>

            <form action="processDiscount">
                DiscountCode: <input type="text" name="txtDisocuntCode" value="${sessionScope.DISCOUNTCODE}" />
                <input type="submit" value="Apply" /><br/>
            </form>
            
            <c:if test="${not empty requestScope.NOCODE}">
                <font color="red">${requestScope.NOCODE}</font>               
            </c:if><br/>
            <c:if test="${not empty requestScope.ALREADYUSED}">
                <font color="red">${requestScope.ALREADYUSED}</font>               
            </c:if><br/>
            <form action="bookingTour">
                <input type="hidden" name="price" value="${sessionScope.TOUR_CART.priceShowing}" style="border:none;" /> 
                <input type="submit" value="Booking By Cash" onclick="return confirm('Are you sure to book?')"/><br/>
            </form>
            <c:if test="${not empty requestScope.NOACCOUNT}">
                <font color="red">${requestScope.NOACCOUNT}</font>
            </c:if>
            <c:if test="${not empty requestScope.NOT_ENOUGH_TOUR}">
                <font color="red">${requestScope.NOT_ENOUGH_TOUR}</font>
            </c:if><br/>
        </c:if>
        <c:if test="${empty items}">items empty</c:if>

    </c:if>
    <c:if test="${empty sessionScope.TOUR_CART}">
        Your cart is empty!
    </c:if>

    <c:if test="${not empty sessionScope.USER}">
        <a href="user.jsp">Continue Booking</a>
    </c:if>
    <c:if test="${empty sessionScope.USER}">
        <a href="showTours">Continue Booking</a>
    </c:if><br/>

</body>
</html>
