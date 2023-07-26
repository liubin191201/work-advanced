package com.work.advanced.shardingjdbc.service.impl;

import com.work.advanced.shardingjdbc.dao.ShopingMapper;
import com.work.advanced.shardingjdbc.service.ShopingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopingServiceImpl implements ShopingService {

    @Autowired
    private ShopingMapper shopingMapper;
}
