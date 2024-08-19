package com.Soganis.Repository;

import com.Soganis.Entity.BillingModel;
import com.Soganis.Entity.Items;
import com.Soganis.Entity.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BillingModelRepository extends JpaRepository<BillingModel,Integer> {

    @Query("SELECT b FROM BillingModel b WHERE b.itemCategory = :itemCategory AND b.bill_date BETWEEN :startDate AND :endDate")
    List<BillingModel> findBySchoolAndDateRange(
            @Param("itemCategory") String itemCategory,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
    
}
