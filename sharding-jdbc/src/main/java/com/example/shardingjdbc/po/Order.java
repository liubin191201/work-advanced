package com.example.shardingjdbc.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Order implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long orderId;

    @Column(name = "order_price")
    private Integer orderPrice;

    @Column(name = "user_id")
    private Long userId;


}