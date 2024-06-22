package com.Soganis.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;


@Entity
@Table(name="user_cash_collection")
public class UserCashCollection {
    @Id
    int sno;
    String userId;
    String userName;
    int cash_collection;
    Date date;            
    }
