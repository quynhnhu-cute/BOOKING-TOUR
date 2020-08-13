/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.Tours;

import java.io.Serializable;
import java.sql.Date;


/**
 *
 * @author USER
 */
public class ToursDTO implements Serializable{
   
    private int tourId, quota;
    private String tourName, imageLink, place;
    private Date dateFrom, dateTo, importDate;
    private double price;
    private String priceShowing;

    public ToursDTO(int tourId, int quota, String tourName, String imageLink, String place, Date dateFrom, Date dateTo, Date importDate, double price, String priceShowing) {
        this.tourId = tourId;
        this.quota = quota;
        this.tourName = tourName;
        this.imageLink = imageLink;
        this.place = place;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.importDate = importDate;
        this.price = price;
        this.priceShowing = priceShowing;
    }

    public String getPriceShowing() {
        return priceShowing;
    }

    public void setPriceShowing(String priceShowing) {
        this.priceShowing = priceShowing;
    }
    
            

    public ToursDTO() {
    }

    public ToursDTO(int tourId, int quota, String tourName, String imageLink, String place, Date dateFrom, Date dateTo, Date importDate, double price) {
        this.tourId = tourId;
        this.quota = quota;
        this.tourName = tourName;
        this.imageLink = imageLink;
        this.place = place;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.importDate = importDate;
        this.price = price;
    }

    public ToursDTO(int quota, String tourName, String imageLink, String place, Date dateFrom, Date dateTo, Date importDate, double price) {
        this.quota = quota;
        this.tourName = tourName;
        this.imageLink = imageLink;
        this.place = place;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.importDate = importDate;
        this.price = price;
    }
    

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
    
    
}
