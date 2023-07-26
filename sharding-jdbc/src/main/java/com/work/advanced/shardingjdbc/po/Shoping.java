package com.work.advanced.shardingjdbc.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Shoping implements Serializable {
    @Id
    //@GeneratedValue(generator = "JDBC")
    @Column(name = "shoping_id")
    private Long shopingId;

    @Column(name = "shoping_name")
    private String shopingName;

    @Column(name = "shoping_price")
    private Integer shopingPrice;

}