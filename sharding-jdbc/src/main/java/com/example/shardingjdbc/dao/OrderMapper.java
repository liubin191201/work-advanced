package com.example.shardingjdbc.dao;

import com.example.shardingjdbc.annotation.BaseMapper;
import com.example.shardingjdbc.po.OrderMain;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<OrderMain> {

}