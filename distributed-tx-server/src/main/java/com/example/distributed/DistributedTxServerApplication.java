package com.example.distributed;

import com.example.distributed.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistributedTxServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedTxServerApplication.class, args);
        //启动netty服务
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(8700);
    }

}
