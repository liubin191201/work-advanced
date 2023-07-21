package com.example.shardingjdbc.service.impl;

import com.example.shardingjdbc.dao.UserInfoMapper;
import com.example.shardingjdbc.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
}
