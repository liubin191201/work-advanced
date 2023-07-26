package com.word.advanced.distributedtxclient.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * 事务参与着的核心处理器
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext channelHandlerContext;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        channelHandlerContext = ctx;
    }

    //所有服务端（事务管理者）返回的数据会被该方法监听到
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //业务逻辑实现
        System.out.println("接收服务端消息:" + JSONObject.toJSONString(msg));

    }

    //向服务端（事务管理者）发送数据方法
    public void sendData(JSONObject result){
        System.out.println("向事务管理者发送数据:" + result.toJSONString());
        channelHandlerContext.writeAndFlush(result.toJSONString());
    }



}
