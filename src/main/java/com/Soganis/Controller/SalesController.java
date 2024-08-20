package com.Soganis.Controller;

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
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        {
            System.out.println("School and Date");
            List<SalesReportModel> billing = service.getSaleBySchoolAndDate(schoolCode, startDate, endDate);

            if (billing.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>(billing, HttpStatus.OK);
            }
        }
    }

    @GetMapping("/report/school_item_type_date")
    public ResponseEntity<List<SalesReportModel>> saleReportBySchoolAndItemTypeDate(
            @RequestParam("itemCode") String itemCode,
            @RequestParam("schoolCode") String schoolCode,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        {

            System.out.println("All Filter");

            List<SalesReportModel> billing = service.getSaleBySchoolAndItemTypeDate(itemCode, schoolCode, startDate, endDate);

            if (billing.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>(billing, HttpStatus.OK);
            }
        }
    }

    @GetMapping("/report/school_code")
    public ResponseEntity<List<SalesReportModel>> saleReportBySchool(
            @RequestParam("schoolCode") String schoolCode) {
        {

            System.out.println("School");

            List<SalesReportModel> billing = service.getSaleBySchool(schoolCode);

            if (billing.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>(billing, HttpStatus.OK);
            }
        }
    }

    @GetMapping("/report/item_code")
    public ResponseEntity<List<SalesReportModel>> saleReportByItemType(@RequestParam("itemCode") String itemCode) {
        {

            System.out.println("Item");

            List<SalesReportModel> billing = service.getSaleByItemType(itemCode);

            if (billing.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(billing, HttpStatus.OK);
            }
        }

    }

    @GetMapping("/report/sales_date")
    public ResponseEntity<List<SalesReportModel>> saleReportByCustomDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        {

            System.out.println("Custom Date");
            List<SalesReportModel> billing = service.getSaleByDateRange(startDate, endDate);

            if (billing.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(billing, HttpStatus.OK);
            }
        }
    }

    @GetMapping("/report/school_and_item_type")
    public ResponseEntity<List<SalesReportModel>> saleReportBySchoolAndItemType(
            @RequestParam("schoolCode") String schoolCode,
            @RequestParam("itemCode") String itemCode) {
        {

            System.out.println("School and Item");
            List<SalesReportModel> billing = service.getSaleBySchoolAndItemType(itemCode, schoolCode);
            if (billing.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>(billing, HttpStatus.OK);
            }
        }
    }

    @GetMapping("/report/item_code_and_date")
    public ResponseEntity<List<SalesReportModel>> saleReportByItemTypeDate(
            @RequestParam("itemCode") String itemCode,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        {

            System.out.println("Item and Date");
            List<SalesReportModel> billing = service.getSaleByItemTypeAndDate(itemCode, startDate, endDate);

            if (billing.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(billing, HttpStatus.OK);
            }
        }
    }
}
