package com.Soganis.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name="order_book")
public class CustomerOrderBook {
@Id
 private int orderId;
 private String customerName;
 private String mobileNo;
 private String schoolName;
 private String orderDescription;
 private Date deliveryDate;
 private int advancePayment;

    public CustomerOrderBook() {
    }

    public CustomerOrderBook(String customerName, String mobileNo, String schoolName, String orderDescription, Date deliveryDate, int advancePayment) {
        this.customerName = customerName;
        this.mobileNo = mobileNo;
        this.schoolName = schoolName;
        this.orderDescription = orderDescription;
        this.deliveryDate = deliveryDate;
        this.advancePayment = advancePayment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment(int advancePayment) {
        this.advancePayment = advancePayment;
    }
 
 
 
 
 
}
