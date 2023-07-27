package com.word.advanced.distributedtxclient.netty;

import com.alibaba.fastjson.JSONObject;
import com.example.core.enums.TransactionType;
import com.word.advanced.distributedtxclient.transactional.ZhuziTx;
import com.word.advanced.distributedtxclient.transactional.ZhuziTxParticaipant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j2;


/**
 * 事务参与着的核心处理器
 */
@Log4j2
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext channelHandlerContext;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        channelHandlerContext = ctx;
    }

    //所有服务端（事务管理者）返回的数据会被该方法监听到
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接收到事务管理者的最终决断:" + msg.toString());
        //反序列化解析json
        JSONObject data = JSONObject.parseObject(msg.toString());
        String groupId = data.getString("groupId");
        String command = data.getString("command");
        log.info("接收command:" + command);
        ZhuziTx zhuziTx = ZhuziTxParticaipant.getZhuziTransactionalMap(groupId);
        //如果事务管理者最终决定提交事务
        if (command.equals(TransactionType.commit.name())) {
            zhuziTx.setTransactionType(TransactionType.commit);
        }else {
            zhuziTx.setTransactionType(TransactionType.rollback);
        }
        zhuziTx.getTask().signalTask();
    }

    //向服务端（事务管理者）发送数据方法
    public void sendData(JSONObject result){
        System.out.println("向事务管理者发送数据:" + result.toJSONString());
        channelHandlerContext.writeAndFlush(result.toJSONString());
    }



}
