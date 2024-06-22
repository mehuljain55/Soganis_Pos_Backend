package com.Soganis.Repository;

import com.Soganis.Entity.Billing;
import com.Soganis.Entity.Items;
import com.Soganis.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing,Integer> {
    
}
