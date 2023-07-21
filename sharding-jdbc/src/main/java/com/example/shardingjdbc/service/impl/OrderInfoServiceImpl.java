package com.example.shardingjdbc.service.impl;

import com.example.shardingjdbc.dao.OrderInfoMapper;
import com.example.shardingjdbc.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
}
