package com.Soganis.Service;

import com.Soganis.Entity.BillingModel;
import com.Soganis.Model.SalesReportModel;
import com.Soganis.Repository.BillingModelRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SalesReportService {
    
    @Autowired
    private BillingModelRepository billModelRepo;
    
    public List<SalesReportModel> getSaleBySchoolAndDate(String schoolCode,Date startDate,Date endDate)
    {
         List<BillingModel> billing=billModelRepo.findBySchoolAndDateRange(schoolCode, startDate, endDate);
        
         List<SalesReportModel> salesReportList=billingSummary(billing);
         return salesReportList;
         
    }
    
    
    public List<SalesReportModel> billingSummary(List<BillingModel> billingModels) {
        List<SalesReportModel> salesReportList = billingModels.stream()
            .collect(Collectors.groupingBy(
                BillingModel::getItemBarcodeID,
                Collectors.collectingAndThen(
                    Collectors.reducing(
                        (b1, b2) -> {
                            BillingModel combined = new BillingModel();
                            combined.setItemBarcodeID(b1.getItemBarcodeID());
                            combined.setDescription(b1.getDescription());
                            combined.setItemType(b1.getItemType());
                            combined.setItemColor(b1.getItemColor());
                            combined.setSellPrice(b1.getSellPrice());
                            combined.setQuantity(b1.getQuantity() + b2.getQuantity());
                            combined.setTotal_amount(b1.getTotal_amount() + b2.getTotal_amount());
                            return combined;
                        }
                    ),
                    b -> new SalesReportModel(
                        b.get().getItemBarcodeID(),
                        b.get().getDescription(),
                        b.get().getItemType(),
                        b.get().getItemColor(),
                        b.get().getSellPrice(),
                        b.get().getQuantity(),
                        b.get().getTotal_amount()
                    )
                )
            ))
            .values()
            .stream()
            .collect(Collectors.toList());

        return salesReportList;
    }
    
   
    
}
