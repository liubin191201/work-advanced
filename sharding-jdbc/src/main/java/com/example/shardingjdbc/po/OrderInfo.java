package com.example.shardingjdbc.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class OrderInfo implements Serializable {

    @Id
    //@GeneratedValue(generator = "JDBC")
    @Column(name = "order_info_id")
    private Long orderInfoId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "shoping_name")
    private String shopingName;

    @Column(name = "shoping_price")
    private Integer shopingPrice;


}