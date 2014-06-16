package com.cardappio.cardappiocustomer;

import com.cardappio.cardappiocustomer.pojo.Bill;

import android.app.Application;

public class CardAPPioCustomer extends Application {

    private Bill active_bill;

	public Bill getActive_bill() {
		return active_bill;
	}

	public void setActive_bill(Bill active_bill) {
		this.active_bill = active_bill;
	}
    

}