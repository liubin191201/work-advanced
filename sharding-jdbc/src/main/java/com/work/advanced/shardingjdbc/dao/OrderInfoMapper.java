package com.work.advanced.shardingjdbc.dao;

import com.work.advanced.shardingjdbc.annotation.BaseMapper;
import com.work.advanced.shardingjdbc.po.OrderInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
}