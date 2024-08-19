package com.Soganis.Controller;

import com.Soganis.Entity.BillingModel;
import com.Soganis.Model.SalesReportModel;
import com.Soganis.Service.SalesReportService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalesController {

    @Autowired
    private SalesReportService service;

    @GetMapping("/report/school_code_and_date")
public ResponseEntity<List<SalesReportModel>> saleReportBySchoolAndDate(
        @RequestParam String schoolCode,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) { {

        List<SalesReportModel> billing = service.getSaleBySchoolAndDate(schoolCode, startDate, endDate);

        if (billing.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
     
            return new ResponseEntity<>(billing, HttpStatus.OK);
        }
    }

}
}
