package com.Soganis.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;


@Entity
@Table(name="billing")
public class Billing {

    @Id
    int billNo;
    String userId;
    String customerName;
    String customerMobileNo;
    int item_count;
    @OneToMany(mappedBy = "billing", cascade = CascadeType.ALL)
    private List<BillingModel> bill;
    double final_amount;

    public int getBillNo() {
        return billNo;
    }

    public void setBillNo(int billNo) {
        this.billNo = billNo;
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

    public double getFinal_amount() {
        return final_amount;
    }

    public void setFinal_amount(double final_amount) {
        this.final_amount = final_amount;
    }

}
