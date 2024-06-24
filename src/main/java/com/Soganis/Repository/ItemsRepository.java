package com.Soganis.Repository;

import com.Soganis.Entity.Items;
import com.Soganis.Entity.User;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemsRepository extends JpaRepository<Items,Integer> {
    
   @Query("SELECT i FROM Items i WHERE (:searchTerm IS NULL OR i.itemCode LIKE CONCAT(:searchTerm, '%'))")
    List<Items> findAllFiltered(String searchTerm);
}
    

