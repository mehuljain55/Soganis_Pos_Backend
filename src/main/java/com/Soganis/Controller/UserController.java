package com.Soganis.Controller;

import com.Soganis.Entity.Billing;
import com.Soganis.Entity.BillingModel;
import com.Soganis.Entity.Items;
import com.Soganis.Entity.User;
import com.Soganis.Service.ItemService;
import com.Soganis.Service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<List<Items>> getAllItems(
            @RequestParam(required = false) String searchTerm,
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
    public String generateBill(@RequestBody Billing bill) {
        try {
            Billing createBill = itemService.saveBilling(bill);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
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

}
