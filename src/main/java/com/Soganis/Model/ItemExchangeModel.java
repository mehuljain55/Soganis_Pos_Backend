package com.Soganis.Model;

import com.Soganis.Entity.Billing;
import java.util.List;


public class ItemExchangeModel {
  
  private Billing bill;
    private List<ItemReturnModel> itemModel;

    // Getters and Setters
    public Billing getBill() {
        return bill;
    }

    public void setBill(Billing bill) {
        this.bill = bill;
    }

    public List<ItemReturnModel> getItemModel() {
        return itemModel;
    }

    public void setItemModel(List<ItemReturnModel> itemModel) {
        this.itemModel = itemModel;
    }
   
}
