package com.example.distributed.server;


import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

@Log4j2
public class NettyServerHandler extends SimpleChannelInboundHandler {


    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //事物组中的书屋状态列表
    private static Map<String, List<String>> transactionTypeMap = MapUtil.newConcurrentHashMap();

    //事物组是否已经结束到结束标记
    private static Map<String, Boolean> isEndMap = MapUtil.newConcurrentHashMap();

    //事物中应该有的事物个数
    private static Map<String, Integer> transactionCountMap = MapUtil.newConcurrentHashMap();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("channel add");
        Channel channel = ctx.channel();
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("channel remove");
        Channel channel = ctx.channel();
        channelGroup.remove(channel);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    //发送处理结果
    private void sendResult(JSONObject result) {
        System.out.println("事物最终处理结果:" + result.toJSONString());
        channelGroup.forEach(channel -> {
            if (channel.isActive()) {
                channel.writeAndFlush(result.toJSONString());
            }
        });
    }



}
