/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhunnq.carts;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import nhunnq.beans.TourBean;

/**
 *
 * @author USER
 */
public class TourCart implements Serializable{
    private String customer;
    private Map<TourBean, Integer> items;
    private double price;
    private String priceShowing;
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Map<TourBean, Integer> getItems() {
        return items;
    }
    
    public void addItemToCart(TourBean tour){
        if(this.items == null){
            this.items = new HashMap<>();
        }
        int quanity = 1;
        if(this.items.containsKey(tour)){
            quanity = this.items.get(tour) + 1;
        }
        this.items.put(tour, quanity);
        calculatePrice(0);
    }
    
    public void removeItemFromCart(TourBean tour){
        if(this.items == null){
            return;
        }
        if(this.items.containsKey(tour)){
            this.items.remove(tour);
            if(this.items.isEmpty()){
                this.items = null;
            }
        }
        calculatePrice(0);
    }
    
   
    
    public void calculatePrice(int percentage){
        if(this.items == null){
            return;
        }
        this.price = 0;
        for (TourBean bean : items.keySet()) {
            double prices = Double.parseDouble(bean.getPriceShowing());
            this.price += prices * items.get(bean);
        }
        price = price * (1-((double)percentage)/100);
    }

    public String getPriceDisplay(double price){
        DecimalFormat formatter = new DecimalFormat("### ### ###");
        return formatter.format(price);
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceShowing() {
        return getPriceDisplay(price);
    }

    public void setPriceShowing(String priceShowing) {
        this.priceShowing = priceShowing;
    }
    
}
