package com.Soganis.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;


@Entity
@Table(name="billing_tab")
public class BillingModel {
   @Id
   @GeneratedValue(strategy= GenerationType.AUTO)        
   int sno;
   private String itemBarcodeID;
   private String itemType;
   private String itemColor;
   
    @Temporal(TemporalType.DATE)
    private Date bill_date;
   private String itemSize;
   private String itemCategory;
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name = "bill_no")
   @JsonIgnore  
   private Billing billing;
   private int  sellPrice;
   private int quantity;
   private  int total_amount=quantity*sellPrice;

    public BillingModel() {
    }

    public BillingModel(int sno, String itemBarcodeID, String itemType, String itemColor, String itemSize, String itemCategory, Billing billing, int sellPrice, int quantity) {
        this.sno = sno;
        this.itemBarcodeID = itemBarcodeID;
        this.itemType = itemType;
        this.itemColor = itemColor;
        this.itemSize = itemSize;
        this.itemCategory = itemCategory;
        this.billing = billing;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getItemBarcodeID() {
        return itemBarcodeID;
    }

    public void setItemBarcodeID(String itemBarcodeID) {
        this.itemBarcodeID = itemBarcodeID;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getBill_date() {
        return bill_date;
    }

    public void setBill_date(Date bill_date) {
        this.bill_date = bill_date;
    }
    
    

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    @Override
    public String toString() {
        return "BillingModel{" + "sno=" + sno + ", itemBarcodeID=" + itemBarcodeID + ", itemType=" + itemType + ", itemColor=" + itemColor + ", itemSize=" + itemSize + ", itemCategory=" + itemCategory  + ", sellPrice=" + sellPrice + ", quantity=" + quantity + ", total_amount=" + total_amount + '}';
    }


    
    

   
    
    
}
