package com.word.advanced.distributedtxclient.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义分布式竹节
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZhuziTransactional {

    //标注当前是全局事务的开启者
    boolean isStart() default false;

    //标注当前是全局事务的结束者
    boolean isEnd() default false;

}
