package com.work.advanced.shardingjdbc.service.impl;

import com.work.advanced.shardingjdbc.dao.UserInfoMapper;
import com.work.advanced.shardingjdbc.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
}
