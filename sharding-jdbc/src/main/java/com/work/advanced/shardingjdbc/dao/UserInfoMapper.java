package com.work.advanced.shardingjdbc.dao;

import com.work.advanced.shardingjdbc.annotation.BaseMapper;
import com.work.advanced.shardingjdbc.po.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}