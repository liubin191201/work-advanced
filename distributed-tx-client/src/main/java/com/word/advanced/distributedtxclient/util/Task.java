package com.word.advanced.distributedtxclient.util;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//子事务的等待队列：基于此实现事务控制权
@Log4j2
public class Task {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //阻塞挂起线程的方法
    public void  waitTask(){
        log.info("事务控制权已经被拦截挂起");
        try {
            lock.lock();
            condition.await();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    //唤醒放下线程方法
    public void signalTask(){
        log.info("事务控制权已经被拦截放下");
        lock.lock();
        try {
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
