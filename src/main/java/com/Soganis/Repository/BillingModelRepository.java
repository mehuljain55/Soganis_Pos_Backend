package com.Soganis.Repository;

import com.Soganis.Entity.BillingModel;
import com.Soganis.Entity.Items;
import com.Soganis.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingModelRepository extends JpaRepository<BillingModel,Integer> {
    
}
