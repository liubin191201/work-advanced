package com.example.distributed.server;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
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

    //事物组中的事务状态列表
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
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        //创建事务组，并且添加保存事务
        //并且需要判断所有事务都已经执行了（有结果了，要么提交，要么回滚）
        //如果有一个事务需要回滚，那么通知所有客户进行回滚，否则则通知所有客户端进行提交
        log.info("接收数据：" + msg.toString());
        JSONObject data = JSON.parseObject(msg.toString());

        //create :创建一个事务组，add:添加事务
        String command = data.getString("command");
        //事务组id
        String groupId = data.getString("groupId");
        //子事务类型（commit：待提交、rollback：待回滚）
        String transactionalType = data.getString("transactionalType");
        //事务数量（当前这个全局事务的总参与者数量）
        Integer transactionalCount = data.getInteger("transactionalCount");
        // 是否结束事务（是否为最后一个事务）
        Boolean isEnd = data.getBoolean("isEnd");

        //如果参与者发来的create 指令，则创建一个事务组
        if ("create".equals(command)) {
            transactionTypeMap.put(groupId, CollUtil.newLinkedList());
        } else if ("add".equals(command)) {
            //如果参与者是add操作，则将对应子事物加入事务组
            transactionTypeMap.get(groupId).add(transactionalType);

            if (isEnd) {
                isEndMap.put(groupId, true);
                transactionCountMap.put(groupId, transactionalCount);
            }else {
                isEndMap.put(groupId, false);
                transactionCountMap.put(groupId, transactionalCount);
            }
        }
        //调试信息
        log.info("isEndMap长度:" + isEndMap.size());
        log.info("transactionalCountMap长度:" + transactionCountMap.get(groupId));
        log.info("transactionalTypeMap长度:" + transactionTypeMap.get(groupId).size());

        JSONObject result = new JSONObject();
        result.put("groupId", groupId);
        //如果已经接收到事务结束的标记，则判断事务事务是否全部到达
        if (isEndMap.get(groupId) &&
                transactionCountMap.get(groupId).equals(transactionTypeMap.get(groupId).size())) {
            //如果已经全部到达则看是否需要回滚
            if (transactionTypeMap.get(groupId).contains("rollback")) {
                log.info("事务最终回滚");
                result.put("command", "rollback");
                sendResult(result);
            }else {
                log.info("事务最终提交");
                result.put("command", "commit");
                sendResult(result);
            }
        }
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
