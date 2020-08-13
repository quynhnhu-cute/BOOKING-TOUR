/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.DiscountWithUsers;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class DiscountWithUsersDTO implements Serializable{
    private String userId, discountCode;

    public DiscountWithUsersDTO() {
    }

    public DiscountWithUsersDTO(String userId, String discountCode) {
        this.userId = userId;
        this.discountCode = discountCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }
    
    
    
}
