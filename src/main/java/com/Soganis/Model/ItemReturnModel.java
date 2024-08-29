package com.Soganis.Model;


public class ItemReturnModel {
  
   private int sno;
   private String barcodedId;
   private int return_quantity;

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getBarcodedId() {
        return barcodedId;
    }

    public void setBarcodedId(String barcodedId) {
        this.barcodedId = barcodedId;
    }

    public int getReturn_quantity() {
        return return_quantity;
    }

    public void setReturn_quantity(int return_quantity) {
        this.return_quantity = return_quantity;
    }

    @Override
    public String toString() {
        return "ItemReturnModel{" + "sno=" + sno + ", barcodedId=" + barcodedId + ", return_quantity=" + return_quantity + '}';
    }

    
   
}
