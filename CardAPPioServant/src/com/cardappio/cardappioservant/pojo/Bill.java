package com.cardappio.cardappioservant.pojo;

import java.sql.Timestamp;

public class Bill {
   private int bill_id;
   private String bill_table;
   private int bill_status;
   private Timestamp bill_open_time;
   private Timestamp bill_close_time;
   private String bill_payment_method;

   public int getBill_id() {
       return bill_id;
   }

   public void setBill_id(int bill_id) {
       this.bill_id = bill_id;
   }

   public String getBill_table() {
       return bill_table;
   }

   public void setBill_table(String bill_table) {
       this.bill_table = bill_table;
   }

   public int getBill_status() {
       return bill_status;
   }

   public void setBill_status(int bill_status) {
       this.bill_status = bill_status;
   }

   public Timestamp getBill_open_time() {
       return bill_open_time;
   }

   public void setBill_open_time(Timestamp bill_open_time) {
       this.bill_open_time = bill_open_time;
   }

   public Timestamp getBill_close_time() {
       return bill_close_time;
   }

   public void setBill_close_time(Timestamp bill_close_time) {
       this.bill_close_time = bill_close_time;
   }

   public String getBill_payment_method() {
       return bill_payment_method;
   }

   public void setBill_payment_method(String bill_payment_method) {
       this.bill_payment_method = bill_payment_method;
   }
   
   
}

