package com.work.advanced.shardingjdbc.service.impl;

import com.work.advanced.shardingjdbc.dao.OrderInfoMapper;
import com.work.advanced.shardingjdbc.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
}
