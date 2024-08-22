package com.Soganis.Controller;

import com.Soganis.Entity.Billing;
import com.Soganis.Entity.BillingModel;
import com.Soganis.Entity.CustomerOrderBook;
import com.Soganis.Entity.Items;
import com.Soganis.Entity.PurchaseOrderBook;
import com.Soganis.Entity.User;
import com.Soganis.Entity.UserCashCollection;
import com.Soganis.Entity.UserMonthlySalary;
import com.Soganis.Entity.User_Salary;
import com.Soganis.Service.InventoryService;
import com.Soganis.Service.ItemService;
import com.Soganis.Service.UserService;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ItemService itemService;
    
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/login")
    public ResponseEntity<User> getUserInfo(@RequestBody User userRequest) {
        System.out.println("User controller accessed");

        String userid = userRequest.getUserId();
        User user = service.getUserInfo(userid);

        if (user != null && user.getPassword().equals(userRequest.getPassword())) {
            System.out.println("User validated");
            return ResponseEntity.ok(user);
        } else {
            System.out.println("Incorrect Credential");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/getAllItems")
    public ResponseEntity<List<Items>> getAllItems(@RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "20") int maxResults) {
        List<Items> items = itemService.getAllItems(searchTerm, maxResults);
        if (!items.isEmpty()) {
            return ResponseEntity.ok(items);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getBill/{bill_no}")
    public ResponseEntity<Billing> getBill(@PathVariable int bill_no) {

        Billing bill = itemService.getBill(bill_no);
        if (bill != null) {

            return ResponseEntity.ok(bill);
        } else {
            System.out.println("Data Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/billRequest")
    public ResponseEntity<Billing> generateBill(@RequestBody Billing bill) {
        try {
            Billing createBill = itemService.saveBilling(bill);
            createBill.setBill(bill.getBill());
              String status = print_bill(createBill.getBillNo());
                System.out.println(status);
            return ResponseEntity.ok(createBill);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/getTodayUserCashCollection")
    public ResponseEntity<Integer> getTodayUserCashCollection(@RequestParam("userId") String userId) {

        int todaysCollection = itemService.getTodaysCollectionByUser(userId, new Date());

        if (todaysCollection >= 0) {

            return ResponseEntity.ok(todaysCollection);
        } else {
            System.out.println("Data Not Found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/getUserList")
    public ResponseEntity<List<User>> getUserList() {

        List<User> user_info = service.getUserList();

        if (user_info != null) {

            return ResponseEntity.ok(user_info);
        } else {
            System.out.println("Data Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getUserSalaryAmount")
    public int getUserSalaryAmount(@RequestParam("userId") String userId,
            @RequestParam("type") String type,
            @RequestParam("hours") int hours) {

        int amount = service.salaryDeduction(userId, type, hours);
        System.out.println(amount);
        return amount;
    }

    @PostMapping("/salary/update")
    public ResponseEntity<String> generateBill(@RequestBody List<User_Salary> salaries) {

        String status = "";
        try {
            System.out.println(salaries.size());
            status = service.userSalaryUpdate(salaries);

            return ResponseEntity.ok(status);
        } catch (Exception e) {
            e.printStackTrace();
            status = "FAILED";
            return ResponseEntity.ok(status);
        }
    }

    @GetMapping("/getUserCashCollection")
    public ResponseEntity<List<UserCashCollection>> getUserCashCollection() {

        try {

            List<UserCashCollection> userCashList = service.userCashCollectionReport();
            return ResponseEntity.ok(userCashList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/salary/generate")
    public ResponseEntity<List<UserMonthlySalary>> generateUserMonthlySalary(@RequestParam("month_fy") String month_fy) {

        try {

            List<UserMonthlySalary> userMonthlySalary = service.generateUserMonthlySalaries(month_fy);
            return ResponseEntity.ok(userMonthlySalary);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/salary/paid")
    public ResponseEntity<String> salaryStatusUpdate(@RequestBody UserMonthlySalary usermonthlySalary) {

        String status = "";
        try {

            status = service.userMonthlySalaryChangeStatus(usermonthlySalary);
            return ResponseEntity.ok(status);

        } catch (Exception e) {
            e.printStackTrace();
            status = "Failed";
            return ResponseEntity.ok(status);
        }
    }

    @GetMapping("/salary/user_salary_statement")
    public ResponseEntity<List<User_Salary>> generateUserSalaryStatement(@RequestParam("userId") String userId,
            @RequestParam("month_fy") String month_fy) {

        try {

            List<User_Salary> userSalaryStatement = service.getUserSalaryStatement(userId, month_fy);
            return ResponseEntity.ok(userSalaryStatement);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/filter/getSchool")
    public ResponseEntity<List<String>> getSchoolName() {

        try {

            List<String> schoolList = service.getSchoolList();
            return ResponseEntity.ok(schoolList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/filter/item_type")
    public ResponseEntity<List<String>> itemType() {

        try {

            List<String> items = service.itemTypeList();
            return ResponseEntity.ok(items);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/filter/school/item_type")
    public ResponseEntity<List<String>> itemType(@RequestParam("schoolCode") String schoolCode) {

        try {

            List<String> items = service.itemTypeList(schoolCode);
            return ResponseEntity.ok(items);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/filter/item_list_school_code")
    public ResponseEntity<List<Items>> itemListBySchoolCode(@RequestParam("schoolCode") String schoolCode) {

        try {

            List<Items> items = service.itemListBySchool(schoolCode);
            return ResponseEntity.ok(items);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/filter/item_list_type")
    public ResponseEntity<List<Items>> itemListByItemType(@RequestParam("itemType") String itemType) {

        try {

            List<Items> items = service.itemListByItemType(itemType);
            return ResponseEntity.ok(items);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/filter/item_category/item_type")
    public ResponseEntity<List<Items>> itemListBySchoolAndType(@RequestParam("schoolCode") String schoolCode,
            @RequestParam("itemType") String itemType) {

        try {

            List<Items> items = service.itemListBySchoolAndType(schoolCode, itemType);
            return ResponseEntity.ok(items);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/customer_order_details")
    public ResponseEntity<String> customer_order_detail(@RequestBody CustomerOrderBook order) {
        try {
            String status = service.updateCustomerOrder(order);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
     @PostMapping("/view/customer_order_details")
    public ResponseEntity<List<CustomerOrderBook>> customer_order_detail(@RequestParam("status") String status) {
        try {
            List<CustomerOrderBook> lst=service.customerOrderDetails(status);
            return ResponseEntity.ok(lst);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PostMapping("/update/customer_order_details/delivered")
    public ResponseEntity<String> updateOrderDetailDelivered(@RequestParam("orderId") int orderId) {
        try {
            String status=service.updateOrderDetailDelivered(orderId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PostMapping("/update/customer_order_details/cancelled")
    public ResponseEntity<String> updateOrderDetailCancelled(@RequestParam("orderId") int orderId) {
        try {
            String status=service.updateOrderDetailCancelled(orderId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
     @PostMapping("/create_order")
    public ResponseEntity<String>  order_detail(@RequestParam("barcodedId") String barcodedId) {
        try {
            String status=inventoryService.generate_order(barcodedId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
     @GetMapping("/view-order")
    public ResponseEntity<List<PurchaseOrderBook>>  viewOrder() {
        try {
            List<PurchaseOrderBook>  order=inventoryService.view_order();
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    

        @PostMapping("/generate_order")
    public ResponseEntity<String>  generateOrder(@RequestBody List<PurchaseOrderBook> orders) {
        try {
            String status=inventoryService.generate_order("454");
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

  

    
    
    
    public String print_bill(int bill_no) {
        Billing bill = itemService.getBill(bill_no);

        List<BillingModel> bills = bill.getBill();
        List<BillingModel> newBill = new ArrayList<>();
        int count = 1;
        for (BillingModel billModel : bills) {
            String desciption = billModel.getItemCategory() + " " + billModel.getItemType() + " " + billModel.getItemColor();
            billModel.setDescription(desciption);
            billModel.setSno(count);
            newBill.add(billModel);
            count = count + 1;
        }
        System.out.println(newBill.size());

        try {

            InputStream reportTemplate = UserController.class.getResourceAsStream("/static/Soganis.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplate);
            Map<String, Object> parameters = new HashMap<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String bill_date = dateFormat.format(bill.getBill_date());

            parameters.put("bill_no", bill.getBillNo());
            parameters.put("customer_name", bill.getCustomerName());
            parameters.put("mobile_no", bill.getCustomerMobileNo());
            parameters.put("date", bill_date);
            parameters.put("final_amount", bill.getFinal_amount());

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(newBill);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            String destination = "C:\\Users\\mehul\\Desktop\\Invoice\\" + bill.getCustomerName() + "_" + bill.getBillNo() + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, destination);
            //printPDF(destination);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Success";

    }

    public String printPDF(String filePath) {
        try {

            File pdfFile = new File(filePath);
            if (!pdfFile.exists()) {
                return "File not found: " + filePath;
            }
            FileInputStream fileInputStream = new FileInputStream(pdfFile);
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable(new PDFPrintable(fileInputStream));
            printerJob.print();

            fileInputStream.close();

            return "PDF printed successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error printing PDF: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unexpected error: " + e.getMessage();
        }
    }

}
