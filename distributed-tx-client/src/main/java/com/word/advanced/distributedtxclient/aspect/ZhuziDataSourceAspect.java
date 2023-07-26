package com.word.advanced.distributedtxclient.aspect;

import com.word.advanced.distributedtxclient.connection.ZhuziConnection;
import com.word.advanced.distributedtxclient.transactional.ZhuziTxParticaipant;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Log4j2
@Component
@Aspect
public class ZhuziDataSourceAspect {

    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection dataSourceAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        log.info("事务切面成功拦截，正在接管控制");

        //如果当前调用事务接口的线程正在参与分布式事务
        //则返回自定义的connetion 对象接管事务控制权
        if (ZhuziTxParticaipant.getCurrent() != null) {
            log.info("返回自定义的Connection对象");
            Connection connection = (Connection) proceedingJoinPoint.proceed();
            return new ZhuziConnection(connection, ZhuziTxParticaipant.getCurrent());
        }
        //如果当前没有参与分布式事务，让其正常回滚
        log.info("返回JDBC 的connection 对象");
        return (Connection) proceedingJoinPoint.proceed();
    }


}
