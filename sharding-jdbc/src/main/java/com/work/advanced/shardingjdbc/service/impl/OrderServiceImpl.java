package com.work.advanced.shardingjdbc.service.impl;

import com.work.advanced.shardingjdbc.dao.OrderMapper;
import com.work.advanced.shardingjdbc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
}
