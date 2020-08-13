/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.DiscountCode;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author USER
 */
public class DiscountCodeDTO implements Serializable{
    private String discountCode, name;
    private Date expiryDate;
    private int percentage; 

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public DiscountCodeDTO(String discountCode, String name, Date expiryDate, int percentage) {
        this.discountCode = discountCode;
        this.name = name;
        this.expiryDate = expiryDate;
        this.percentage = percentage;
    }
    

    public DiscountCodeDTO() {
    }

    public DiscountCodeDTO(String discountCode, String name, Date expiryDate) {
        this.discountCode = discountCode;
        this.name = name;
        this.expiryDate = expiryDate;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    
    
}
