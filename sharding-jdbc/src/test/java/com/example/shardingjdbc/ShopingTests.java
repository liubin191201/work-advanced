package com.example.shardingjdbc;

import com.example.shardingjdbc.dao.OrderInfoMapper;
import com.example.shardingjdbc.dao.OrderMapper;
import com.example.shardingjdbc.dao.ShopingMapper;
import com.example.shardingjdbc.dao.UserInfoMapper;
import com.example.shardingjdbc.po.OrderInfo;
import com.example.shardingjdbc.po.OrderMain;
import com.example.shardingjdbc.po.Shoping;
import com.example.shardingjdbc.po.UserInfo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Log4j2
public class ShopingTests extends ShardingJdbcApplicationTests {

    @Autowired
    private ShopingMapper shopingMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    void insert() {
        Shoping po = new Shoping();
        //po.setShopingId(11111111L);
        po.setShopingName("黄金零号竹子");
        po.setShopingPrice(8888);
        shopingMapper.insertSelective(po);
        log.info("sss");
    }

    @Test
    void databaseStrategyInsert() {
        for (int i = 1; i <= 20; i++) {
            Shoping po = new Shoping();
            po.setShopingId(Long.valueOf(i));
            po.setShopingName("黄金" + i + "号竹子");
            po.setShopingPrice(8888 * i);
            shopingMapper.insertSelective(po);
        }
    }

    @Test
    void findByShopingId() {
        //Shoping shoping = shopingMapper.selectByPrimaryKey(1L);
        //System.out.println(shoping);
        Shoping shoping = new Shoping();
        shoping.setShopingId(889176595204407296L);
        shoping.setShopingName("黄金1号竹子");
        List<Shoping> shopings = shopingMapper.select(shoping);
        System.out.println(shopings);
    }

    @Test
    void findAll() {
        List<Shoping> shopings =
                shopingMapper.selectAll();
        System.out.println(shopings);
    }

    /**
     * 测试分布式序列算法 - 雪花算法的效果
     **/
    @Test
    void insertSnowflake() {
        for (int i = 1; i <= 10; i++) {
            Shoping shoping = new Shoping();
            shoping.setShopingName("黄金" + i + "号竹子");
            shoping.setShopingPrice(8888);
            shopingMapper.insertSelective(shoping);
        }
    }

    /**
     * 测试绑定表的效果
     **/
    @Test
    void orderOrOrderInfoInsert() {
        // 插入一条订单数据
        OrderMain order = new OrderMain();
        order.setOrderId(100L);
        order.setUserId(111111L);
        order.setOrderPrice(100000);
        orderMapper.insertSelective(order);

        log.info("order:{}",order);

        //对同一笔订单插入三条订单详情数据
        for (int i = 1; i <= 3; i++) {
            OrderInfo orderInfo = new OrderInfo();
            // 前面插入订单的方法执行完成后会返回orderID
            orderInfo.setOrderId(order.getOrderId());
            orderInfo.setShopingName("黄金1号竹子");
            orderInfo.setShopingPrice(8888);
            orderInfoMapper.insertSelective(orderInfo);
        }
        //OrderInfo orderInfo = new OrderInfo();
        //orderInfo.setOrderId(111L);
        //orderInfo.setShopingPrice(90);
        //orderInfo.setShopingName("xxx");
        //orderInfoMapper.insertSelective(orderInfo);

    }

    @Test
    void batchInsertUserInfoTest(){
        // 插入三条性别为男的用户数据
        for (int i = 1; i <= 3; i++){
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName("竹子" + i + "号");
            userInfo.setUserAge(18 + i);
            userInfo.setUserSex("男");
            userInfoMapper.insertSelective(userInfo);
        }

        // 插入两条性别为女的用户数据
        for (int i = 1; i <= 2; i++){
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName("熊猫" + i + "号");
            userInfo.setUserAge(18 + i);
            userInfo.setUserSex("女");
            userInfoMapper.insertSelective(userInfo);
        }
    }


}
