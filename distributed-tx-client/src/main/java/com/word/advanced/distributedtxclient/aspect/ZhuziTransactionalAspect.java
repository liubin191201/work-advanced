package com.word.advanced.distributedtxclient.aspect;

import com.example.core.enums.TransactionType;
import com.word.advanced.distributedtxclient.annotion.ZhuziTransactional;
import com.word.advanced.distributedtxclient.transactional.ZhuziTx;
import com.word.advanced.distributedtxclient.transactional.ZhuziTxParticaipant;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 负责拦截自定义注解的切面
 */
@Log4j2
@Component
@Aspect
public class ZhuziTransactionalAspect implements Ordered {

    @Around("@annotation(com.word.advanced.distributedtxclient.annotion.ZhuziTransactional)")
    public Integer invoke(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("分布式事务注解生效，切面成功拦截");
        //获取对应注解的业务方法，以及方法上的注解对象
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ZhuziTransactional zta = method.getAnnotation(ZhuziTransactional.class);

        //创建事务组
        String groupId = "";
        //如果目前触发切面的方法，是一组全局事务的第一个子事务
        if (zta.isStart()) {
            //则向事务管理者注册一个事务
            groupId = ZhuziTxParticaipant.createZhuziTransactionalManagerGroup();
        }else {
            //获取当前事务所属的事务组ID
            groupId = ZhuziTxParticaipant.getCurrentGroupId();
        }
        //创建子事务
        ZhuziTx zhuziTx = ZhuziTxParticaipant.createTransactional(groupId);

        //spring 会开始mysql事务
        try {
            //执行spring 切面 (dataSource切面)，执行具体的业务方法
            Object result = proceedingJoinPoint.proceed();
            //没有抛出异常证明事务可以提交，把子事物添加进事务组
            ZhuziTxParticaipant.addZhuziTransactional(zhuziTx, zta.isEnd(), TransactionType.commit);
            return (Integer) result;
        } catch (Throwable e) {
            e.printStackTrace();
            //抛出异常证明事务需要回滚，报子事物添加到事务组
            ZhuziTxParticaipant.addZhuziTransactional(zhuziTx, zta.isEnd(), TransactionType.rollback);
            return -1;
        }
    }


    @Override
    public int getOrder() {
        return 1000;
    }
}
