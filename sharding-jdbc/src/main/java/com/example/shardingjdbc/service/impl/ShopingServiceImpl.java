package com.example.shardingjdbc.service.impl;

import com.example.shardingjdbc.dao.ShopingMapper;
import com.example.shardingjdbc.service.ShopingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopingServiceImpl implements ShopingService {

    @Autowired
    private ShopingMapper shopingMapper;
}
