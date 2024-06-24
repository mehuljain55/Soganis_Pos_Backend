package com.Soganis.Service;

import com.Soganis.Entity.User;
import com.Soganis.Repository.ItemsRepository;
import org.springframework.stereotype.Service;
import com.Soganis.Repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private ItemsRepository itemRepo;

    public User getUserInfo(String userId) {
        Optional<User> opt = userRepo.findById(userId);
        if (opt.isPresent()) {
            User user = opt.get();
            return user;
        }
        return null;
    }
    
   
    

}
