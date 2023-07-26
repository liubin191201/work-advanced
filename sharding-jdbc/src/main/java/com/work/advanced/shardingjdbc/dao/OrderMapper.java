package com.work.advanced.shardingjdbc.dao;

import com.work.advanced.shardingjdbc.annotation.BaseMapper;
import com.work.advanced.shardingjdbc.po.OrderMain;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<OrderMain> {

}