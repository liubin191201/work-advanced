package com.example.shardingjdbc.dao;

import com.example.shardingjdbc.annotation.BaseMapper;
import com.example.shardingjdbc.po.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}