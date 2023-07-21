package com.example.shardingjdbc.service.impl;

import com.example.shardingjdbc.dao.OrderMapper;
import com.example.shardingjdbc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
}
