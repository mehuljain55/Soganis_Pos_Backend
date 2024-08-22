package com.Soganis.Repository;

import com.Soganis.Entity.PurchaseOrderBook;
import com.Soganis.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PurchaseOrderBookRepo extends JpaRepository<PurchaseOrderBook,Integer> {
    
}
