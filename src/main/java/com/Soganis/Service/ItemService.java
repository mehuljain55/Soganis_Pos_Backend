package com.Soganis.Service;

import com.Soganis.Entity.Billing;
import com.Soganis.Entity.BillingModel;
import com.Soganis.Entity.Items;
import com.Soganis.Repository.BillingModelRepository;
import com.Soganis.Repository.BillingRepository;
import com.Soganis.Repository.ItemsRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemsRepository itemRepo;

    @Autowired
    private BillingRepository billRepo;

    @Autowired
    private BillingModelRepository billModelRepository;

    public List<Items> getAllItems() {
        List<Items> items = itemRepo.findAll();
        return items;
    }

    public List<Items> getAllItems(String searchTerm, int maxResults) {
        List<Items> items = itemRepo.findAllFiltered(searchTerm);
        return items.stream()
                .limit(maxResults)
                .collect(Collectors.toList());
    }

    public Billing saveBilling(Billing billing) {
        try {
            int final_amount = 0;
            billing.setBill_date(new Date());
            Billing savedBilling = billRepo.save(billing);

            if (billing.getBill() != null) {
                for (BillingModel billingModel : billing.getBill()) {
                    billingModel.setBilling(savedBilling);
                    final_amount = final_amount + billingModel.getTotal_amount();
                    billingModel.setBill_date(new Date());
                    billModelRepository.save(billingModel);
                }
                savedBilling.setFinal_amount(final_amount);
                billRepo.save(savedBilling);
                savedBilling.setBill(billing.getBill());
            }
            return savedBilling;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Billing getBill(int billNo) {
        Optional<Billing> opt = billRepo.findById(billNo);
        if (opt.isPresent()) {
            Billing bill = opt.get();
            return bill;
        } else {
            return null;
        }
    }

    public int getTodaysCollectionByUser(String userId, Date date) {
        List<Billing> bills = billRepo.findByUserIdAndBillDate(userId, date);
        int cash_collection = 0;

        for (Billing bill : bills) {
            cash_collection = cash_collection + bill.getFinal_amount();
        }
        return cash_collection;
    }
 
    
}
