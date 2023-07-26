package com.example.distributed.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.InetSocketAddress;

/**
 * Netty 服务端-事物管理者
 */
@Log4j2
public class NettyServer implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    public void start(int port) {
        //引导类
         ServerBootstrap serverBootstrap = new ServerBootstrap();
        //事件处理组
         EventLoopGroup bossGroup = new NioEventLoopGroup(1);
         EventLoopGroup workGroup = new NioEventLoopGroup(20);
        try {
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    //设置队列大小
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ServerInitializer());
            Channel channel = serverBootstrap.bind().sync().channel();
            log.info("服务端已启动");
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
