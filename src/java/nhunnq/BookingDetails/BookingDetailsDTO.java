/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.BookingDetails;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class BookingDetailsDTO implements Serializable{
    private int bookingDetailsId;
    private int tourId;
    private int quanity;
    private int bookingId;

    public BookingDetailsDTO() {
    }

    public BookingDetailsDTO(int tourId, int quanity, int bookingId) {
        this.tourId = tourId;
        this.quanity = quanity;
        this.bookingId = bookingId;
    }

    public int getBookingDetailsId() {
        return bookingDetailsId;
    }

    public void setBookingDetailsId(int bookingDetailsId) {
        this.bookingDetailsId = bookingDetailsId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    
    
}
