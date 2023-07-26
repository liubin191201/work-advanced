package com.word.advanced.distributedtxclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class DistributedTxClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedTxClientApplication.class, args);
    }

}
