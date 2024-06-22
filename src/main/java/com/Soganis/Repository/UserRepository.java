/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Soganis.Repository;

import com.Soganis.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author mehul
 */
public interface UserRepository extends JpaRepository<User,String> {
    
}
