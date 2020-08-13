/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author USER
 */
public class TourBean implements Serializable{
    private String tourName;
    private Date dateFrom, dateTo;
   
    private String priceShowing;
    private int tourId;

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public TourBean() {
    }

   

    public TourBean(String tourName, Date dateFrom, Date dateTo, String priceShowing, int tourId) {
        this.tourName = tourName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.priceShowing = priceShowing;
        this.tourId = tourId;
    }

    
   

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
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

   

    public String getPriceShowing() {
        return priceShowing;
    }

    public void setPriceShowing(String priceShowing) {
        this.priceShowing = priceShowing;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.tourName);
        hash = 29 * hash + Objects.hashCode(this.dateFrom);
        hash = 29 * hash + Objects.hashCode(this.dateTo);
        hash = 29 * hash + this.tourId;
        return hash;
    }
    
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        TourBean other = (TourBean)obj;
        if(this.tourId != other.tourId){
            return false;
        } 
        return true;
    }
    
}
