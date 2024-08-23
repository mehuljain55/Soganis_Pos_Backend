package com.Soganis.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;



@Entity
@Table(name="purshase_order_book")
@SequenceGenerator(name = "purshase_order_sequence", sequenceName = "purshase_order_sequence", initialValue = 1, allocationSize = 1)
public class PurchaseOrderBook {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purshase_order_sequence")
    private int orderId;
    private String barcodedId;
    private String description;
    private String size;
    private int currentStock;
    private int quantity;
    private String itemType;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String school;
    private String status;

    public PurchaseOrderBook() {
    }

    public PurchaseOrderBook(int orderId, String description, String size, int quantity, String itemType, String school, String status) {
        this.orderId = orderId;
        this.description = description;
        this.size = size;
        this.quantity = quantity;
        this.itemType = itemType;
        this.school = school;
        this.status = status;
    }

    
    

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    
    
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public String getBarcodedId() {
        return barcodedId;
    }

    public void setBarcodedId(String barcodedId) {
        this.barcodedId = barcodedId;
    }
    
    
    
   
    
}
