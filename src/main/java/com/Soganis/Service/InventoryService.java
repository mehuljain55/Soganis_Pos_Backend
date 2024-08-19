package com.Soganis.Service;

import com.Soganis.Entity.BillingModel;
import com.Soganis.Entity.Items;
import com.Soganis.Repository.ItemsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private ItemsRepository itemRepo;

    public String updateInventory(BillingModel billing) {

        try {

            Items item = itemRepo.getItemByItemBarcodeID(billing.getItemBarcodeID());

            if (item.getGroup_id().equals("NA")) {
                int item_sold = billing.getQuantity();
                int updatedInventoryQuantity = item.getQuantity() - item_sold;

                if (updatedInventoryQuantity <= 0) {
                    updatedInventoryQuantity = 0;
                }
                item.setQuantity(updatedInventoryQuantity);
                itemRepo.save(item);
                return "Success";
            } else {

                List<Items> groupList = itemRepo.findItemsByGroupId(item.getGroup_id());
                for (Items sale : groupList) {
                    int item_sold = billing.getQuantity();
                    int updatedInventoryQuantity = item.getQuantity() - item_sold;

                    if (updatedInventoryQuantity <= 0) {
                        updatedInventoryQuantity = 0;
                    }
                    sale.setQuantity(updatedInventoryQuantity);
                    itemRepo.save(sale);

                }
                return "Success";

            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }

}