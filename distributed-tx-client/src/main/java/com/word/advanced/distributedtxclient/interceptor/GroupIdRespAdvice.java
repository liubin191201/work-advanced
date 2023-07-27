package com.word.advanced.distributedtxclient.interceptor;

import com.word.advanced.distributedtxclient.transactional.ZhuziTxParticaipant;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class GroupIdRespAdvice implements ResponseBodyAdvice {


    //钩子类的前置方法：必须为true 才会执行beforeBodyWrite()方法
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //如果threadLocal中的书屋组ID不能为空，代表当前请求参与了分布式事务
        //会获取对应的事务组ID放入到相应头中 （对于普通请求不会改写响应头）
        if (ZhuziTxParticaipant.getCurrentGroupId() != null) {
            //把需要传递的事务组id 子事务数量放入响应头中
            response.getHeaders().set("groupId", ZhuziTxParticaipant.getCurrentGroupId());
            response.getHeaders().set("transactionalCount", String.valueOf(ZhuziTxParticaipant.getTransactionalCount()));
        }
        return body;
    }
}
