package com.word.advanced.distributedtxclient;

import com.alibaba.fastjson.JSONObject;
import com.word.advanced.distributedtxclient.netty.NettyClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootTest
class DistributedTxClientApplicationTests {

    @Autowired
    private NettyClient client;

    @Test
    void contextLoads() {
    }

    @Test
    void sendDataTest(){

        JSONObject object = new JSONObject();
        object.put("name", "xx");
        client.send(object);
    }

}
