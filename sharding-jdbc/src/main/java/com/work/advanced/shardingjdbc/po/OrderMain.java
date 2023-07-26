package com.work.advanced.shardingjdbc.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class OrderMain implements Serializable {

    @Id
    @Column(name = "order_id")
    //@GeneratedValue(generator = "JDBC")
    private Long orderId;

    @Column(name = "order_price")
    private Integer orderPrice;

    @Column(name = "user_id")
    private Long userId;


}