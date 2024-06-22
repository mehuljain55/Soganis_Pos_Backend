package com.Soganis.Controller;

import com.Soganis.Entity.Items;
import com.Soganis.Entity.User;
import com.Soganis.Service.ItemService;
import com.Soganis.Service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<List<Items>> getAllItems() {
        
        System.out.println("All items fetched");
        List<Items> items = itemService.getAllItems();

        if (items.size() > 0 && items != null) {
            
            return ResponseEntity.ok(items);
        } else {
            System.out.println("Data Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
