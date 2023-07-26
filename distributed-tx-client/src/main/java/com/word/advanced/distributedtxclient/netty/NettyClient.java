package com.word.advanced.distributedtxclient.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class NettyClient implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    // 这个是事务参与者的核心处理器
    private NettyClientHandler client = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("\n\n>>>>>>事务参与者启动成功<<<<<<\n\n");
        start("localhost", 8700);
    }


    public void start(String ip,int port){
        try {
            client = new NettyClientHandler();
            Bootstrap bootstrap = new Bootstrap();
            NioEventLoopGroup group = new NioEventLoopGroup();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ClientInitializer(client));
            bootstrap.connect(ip, port).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送数据
    public void send(JSONObject sendData) {
        try {
            client.sendData(sendData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
