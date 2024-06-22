package com.Soganis.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="billing_tab")
public class BillingModel {
   @Id
   int sno;
   private String itemBarcodeID;
   private String itemType;
   private String itemColor;
   private String itemSize;
   private String itemCategory;
   @ManyToOne
   @JoinColumn(name = "billing_id")
   private Billing billing;
   private double  sellPrice;
   private int quantity;
   private final double total_amount=quantity*sellPrice;

   
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

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getTotal_amount() {
        return total_amount;
    }   
}
