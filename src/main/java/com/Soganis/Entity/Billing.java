package com.Soganis.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "billing")
@SequenceGenerator(name = "billing_sequence", sequenceName = "billing_sequence", initialValue = 1, allocationSize = 1)
public class Billing {

    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billing_sequence")
    int billNo;
    String userId;
    @Temporal(TemporalType.DATE)
    private Date bill_date;
    String customerName;
    String customerMobileNo;
    String paymentMode;

    int item_count;
   
    @OneToMany(mappedBy = "billing", cascade = CascadeType.ALL)
    private List<BillingModel> bill;
    int final_amount;

    public int getBillNo() {
        return billNo;
    }

    public void setBillNo(int billNo) {
        this.billNo = billNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getBill_date() {
        return bill_date;
    }

    public void setBill_date(Date bill_date) {
        this.bill_date = bill_date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobileNo() {
        return customerMobileNo;
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        this.customerMobileNo = customerMobileNo;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public List<BillingModel> getBill() {
        return bill;
    }

    public void setBill(List<BillingModel> bill) {
        this.bill = bill;
    }

    public int getFinal_amount() {
        return final_amount;
    }

    public void setFinal_amount(int final_amount) {
        this.final_amount = final_amount;
    }  

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
    
    
}
