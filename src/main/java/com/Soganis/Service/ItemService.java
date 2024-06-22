package com.Soganis.Service;

import com.Soganis.Entity.Items;
import com.Soganis.Repository.ItemsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ItemService {
    
    @Autowired
    private ItemsRepository itemRepo;
    
     public List<Items> getAllItems()
    {
      List<Items> items=itemRepo.findAll();
      return items;
    }
    
    
}
