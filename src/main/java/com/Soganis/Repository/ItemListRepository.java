package com.Soganis.Repository;

import com.Soganis.Entity.ItemList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemListRepository extends JpaRepository<ItemList, Integer> {
   @Query("SELECT i.itemTypeCode FROM ItemList i WHERE i.description = :description")
    String findItemTypeCodeByDescription(@Param("description") String description);
    
    @Query("SELECT description FROM ItemList")
    List<String> findItemType();
}
