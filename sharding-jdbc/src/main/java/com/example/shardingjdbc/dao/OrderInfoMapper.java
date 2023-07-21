package com.example.shardingjdbc.dao;

import com.example.shardingjdbc.annotation.BaseMapper;
import com.example.shardingjdbc.po.OrderInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
}