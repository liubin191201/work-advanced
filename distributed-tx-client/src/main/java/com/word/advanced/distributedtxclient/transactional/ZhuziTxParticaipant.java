package com.word.advanced.distributedtxclient.transactional;

import com.alibaba.fastjson.JSONObject;
import com.example.core.enums.TransactionType;
import com.word.advanced.distributedtxclient.netty.NettyClient;
import com.word.advanced.distributedtxclient.util.ApplicationContextProvider;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//事务参与者核心实现类
@Data
public class ZhuziTxParticaipant {

    private static NettyClient nettyClient = ApplicationContextProvider.getBean(NettyClient.class);

    //存储当前线程在执行的子任务对象
    private static ThreadLocal<ZhuziTx> current = new ThreadLocal<>();

    //存储当前子事物所属的事务组ID值
    private static ThreadLocal<String> currentGroupId = new ThreadLocal<>();

    //存储当前子事物所属事务组子事物总量
    private static ThreadLocal<Integer> transactionalCount = new ThreadLocal<>();

    //事务id 和子事物对象映射组
    private static Map<String, ZhuziTx> ZHUZI_TRANSACTIONAL_MAP = new HashMap<>();


    /**
     * 向事务管理者中发送一个创建事务组命令
     * @return
     */
    public static String createZhuziTransactionalManagerGroup(){
        //随机产生一个uuid作为事务ID
        String groupId = UUID.randomUUID().toString();
        //通过json做序列化
        JSONObject sendData = new JSONObject();
        // 传入前面产生的事务组ID，以及本次操作为create创建指令
        sendData.put("groupId", groupId);
        sendData.put("command", "create");
        // 调用客户端的send()方法向服务端发送数据
        nettyClient.send(sendData);
        System.out.println(">>>>>向管理者发送创建事务组命令成功<<<<<");
        // 把事务组ID存在currentGroupId当中
        currentGroupId.set(groupId);
        // 对外返回事务组ID值
        return groupId;
    }

    /**
     * 创建一个子事务
     * @param groupId
     * @return
     */
    public static ZhuziTx createTransactional(String groupId) {
        String transactionalId = UUID.randomUUID().toString();
        //创建一个子事物对象
        ZhuziTx zhuziTransactional = new ZhuziTx(groupId, transactionalId, null, null);
        //将创建的子事物保存到相关变量中
        ZHUZI_TRANSACTIONAL_MAP.put(groupId, zhuziTransactional);
        current.set(zhuziTransactional);
        //对事务组数量+1
        Integer transactionCount = addTransactionCount();
        System.out.println("创建子事务,目前事务组长度为：" + transactionCount);
        return zhuziTransactional;
    }

    /**
     * 注册事务（向事务管理者的事务组中添加子事务）
     */
    public static ZhuziTx addZhuziTransactional(ZhuziTx ztp,
                                                Boolean isEnd, TransactionType type){
        // 通过JSON序列化一个对象
        JSONObject sendData = new JSONObject();
        // 传入当前子事务的组ID、事务ID、事务类型、操作类型....信息
        sendData.put("groupId", ztp.getGroupId());
        sendData.put("transactionalId", ztp.getTransactionalId());
        sendData.put("transactionalType", type);
        sendData.put("command", "add");
        sendData.put("isEnd", isEnd);
        sendData.put("transactionalCount", ZhuziTxParticaipant.getTransactionalCount());
        // 将封装好的JSON发送给事务管理者
        nettyClient.send(sendData);
        System.out.println(">>>>>向管理者发送添加子事务命令成功<<<<<");
        return ztp;
    }




    /**
     * 增加事务组的数量
     * @return
     */
    public static Integer addTransactionCount(){
        System.out.println(transactionalCount.get());
        int i = null == transactionalCount.get() ? 0 : transactionalCount.get() + 1;
        transactionalCount.set(i);
        return i;

    }

    public static Integer getTransactionalCount(){
        return transactionalCount.get();
    }

    public static void setTransactionalCount(Integer i) {
        transactionalCount.set(i);
    }

    public static NettyClient getNettyClient() {
        return nettyClient;
    }

    public static ZhuziTx getCurrent() {
        return current.get();
    }

    public static String getCurrentGroupId() {
        return currentGroupId.get();
    }

    public static void setCurrentGroupId(String groupId) {
        currentGroupId.set(groupId);
    }

    public static ZhuziTx getZhuziTransactionalMap(String groupId) {
        return ZHUZI_TRANSACTIONAL_MAP.get(groupId);
    }


}
