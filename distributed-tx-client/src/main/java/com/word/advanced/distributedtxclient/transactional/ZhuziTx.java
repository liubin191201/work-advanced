package com.word.advanced.distributedtxclient.transactional;

import com.example.core.enums.TransactionType;
import com.word.advanced.distributedtxclient.util.Task;
import lombok.Data;

//分布式事务-子事务对象
@Data
public class ZhuziTx {

    //当前子事物属于哪个组
    private String groupId;

    //当前子事物的事务id
    private String transactionalId;

    //当前子事物的事务类型
    private TransactionType transactionType;

    //当前子事务的任务等待队列（基于此实现事务控制）
    private Task task;

    public ZhuziTx(String groupId, String transactionalId, TransactionType transactionType, Task task) {
        this.groupId = groupId;
        this.transactionalId = transactionalId;
        this.transactionType = transactionType;
        this.task = new Task();
    }
}
