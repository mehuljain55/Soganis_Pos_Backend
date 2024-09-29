package com.Soganis.Repository;

import com.Soganis.Entity.Items;
import com.Soganis.Entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemsRepository extends JpaRepository<Items, Integer> {

    @Query("SELECT i FROM Items i WHERE (:searchTerm IS NULL OR i.itemCode LIKE CONCAT(:searchTerm, '%'))")
    List<Items> findAllFiltered(String searchTerm);

    @Query("SELECT i FROM Items i WHERE i.itemBarcodeID = :itemBarcodeID")
    Items getItemByItemBarcodeID(@Param("itemBarcodeID") String itemBarcodeID);

    @Query("SELECT i FROM Items i WHERE i.group_id = :groupId")
    List<Items> findItemsByGroupId(@Param("groupId") String groupId);
    
    @Query("SELECT i FROM Items i WHERE i.itemCode = :itemCode")
    Items findItemsByItemCode(@Param("itemCode") String itemCode);
    
    

    @Query("SELECT DISTINCT i.itemCategory FROM Items i")
    List<String> findDistinctItemCategories();
    
    

    @Query("SELECT DISTINCT i.itemType FROM Items i")
    List<String> findDistinctItemTypes();

    @Query("SELECT DISTINCT i.itemSize FROM Items i WHERE i.itemType = :itemType ORDER BY i.itemSize ASC")
    List<String> findDistinctItemSizeByItemType(@Param("itemType") String itemType);
    
    
    

    @Query("SELECT DISTINCT i.itemTypeCode FROM Items i WHERE i.itemType = :itemType")
    String findDistinctItemTypeCode(@Param("itemType") String itemType);
    
    
    
    
    @Query("SELECT DISTINCT i.schoolCode FROM Items i WHERE i.itemType = :itemType")
    List<String> findDistinctSchoolCodeByItemType(@Param("itemType") String itemType);

    
    
    @Query("SELECT DISTINCT i.itemType FROM Items i where i.itemCategory=:itemCategory")
    List<String> findDistinctItemTypesByScool(@Param("itemCategory") String itemCategory);
    
    @Query("SELECT DISTINCT i.itemCategory FROM Items i where i.itemType=:itemType")
    List<String> findDistinctSchoolByType(@Param("itemType") String itemType);
    
      @Query("SELECT  DISTINCT i.itemColor  FROM Items i where i.itemCategory = :itemCategory and i.itemType=:itemType")
    List<String> findDistinctItemColor(@Param("itemCategory") String itemCategory,
                                         @Param("itemType") String itemType);
    

    @Query("SELECT i FROM Items i WHERE i.itemCategory = :itemCategory ORDER BY i.itemType ASC,i.itemColor ASC, i.itemSize ASC")
    List<Items> findItemsBySchool(@Param("itemCategory") String itemCategory);

    @Query("SELECT i FROM Items i WHERE i.itemType = :itemType ORDER BY i.itemCategory ASC,i.itemColor ASC, i.itemSize ASC")
    List<Items> findItemsByItemType(@Param("itemType") String itemType);
    
      @Query("SELECT DISTINCT i.schoolCode FROM Items i where i.itemCategory=:itemCategory")
      String findDistinctSchoolCode(@Param("itemCategory") String itemCategory);
    
    @Query("SELECT i FROM Items i WHERE i.itemCategory = :itemCategory and i.itemType=:itemType")
    List<Items> findItemsBySchoolAndType(@Param("itemCategory") String itemCategory,
                                         @Param("itemType") String itemType);
    
    
    @Query("SELECT i FROM Items i WHERE i.itemCode = :itemCode")
    List<Items> checkItemCodeForNewItem(@Param("itemCode") String itemCode);

}
