package com.Soganis.Repository;

import com.Soganis.Entity.User_Salary;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserSalaryRepository extends JpaRepository<User_Salary,Integer> {
    
}
