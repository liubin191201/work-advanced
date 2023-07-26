package com.work.advanced.shardingjdbc.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class UserInfo implements Serializable {

    @Id
    //@GeneratedValue(generator = "JDBC")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_sex")
    private String userSex;

    @Column(name = "user_age")
    private Integer userAge;
}