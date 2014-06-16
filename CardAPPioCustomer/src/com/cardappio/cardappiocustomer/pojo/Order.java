package com.cardappio.cardappiocustomer.pojo;

import java.sql.Timestamp;

public class Order {
    private int ord_id;
    private int bill_id;
    private int prod_id;
    private int ord_status;
    private int ord_quantity;
    private float ord_unity_price;
    private Timestamp ord_time;
    private Bill bill;
    private Product product;

    public int getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(int ord_id) {
        this.ord_id = ord_id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public int getOrd_status() {
        return ord_status;
    }

    public void setOrd_status(int ord_status) {
        this.ord_status = ord_status;
    }

    public int getOrd_quantity() {
        return ord_quantity;
    }

    public void setOrd_quantity(int ord_quantity) {
        this.ord_quantity = ord_quantity;
    }

    public float getOrd_unity_price() {
        return ord_unity_price;
    }

    public void setOrd_unity_price(float ord_unity_price) {
        this.ord_unity_price = ord_unity_price;
    }

    public Timestamp getOrd_time() {
        return ord_time;
    }

    public void setOrd_time(Timestamp ord_time) {
        this.ord_time = ord_time;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
    
}
