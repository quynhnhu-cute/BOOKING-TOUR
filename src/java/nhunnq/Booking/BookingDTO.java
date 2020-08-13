/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.Booking;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author USER
 */
public class BookingDTO implements Serializable {

    private int bookingId;
    private Timestamp orderDate;

    private double totalMoney;
    private String userId;
    private int status;

    public BookingDTO() {
    }

    public BookingDTO(Timestamp orderDate, double totalMoney, String userId, int status) {
        this.orderDate = orderDate;
        this.totalMoney = totalMoney;
        this.userId = userId;
        this.status = status;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
