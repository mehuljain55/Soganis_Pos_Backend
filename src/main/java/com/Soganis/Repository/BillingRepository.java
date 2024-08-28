package com.Soganis.Repository;

import com.Soganis.Entity.Billing;
import com.Soganis.Entity.Items;
import com.Soganis.Entity.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BillingRepository extends JpaRepository<Billing,Integer> {
    
    @Query("SELECT b FROM Billing b WHERE b.userId = :userId AND b.bill_date = :billDate")
    List<Billing> findByUserIdAndBillDate(@Param("userId") String userId, @Param("billDate") Date billDate);
    
    @Query("SELECT b FROM Billing b WHERE b.billNo = :billNo")
    Billing getBillByNo(@Param("billNo") int billNo);
    
}
