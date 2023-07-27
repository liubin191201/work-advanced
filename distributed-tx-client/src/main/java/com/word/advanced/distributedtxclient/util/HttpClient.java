package com.word.advanced.distributedtxclient.util;


import cn.hutool.core.text.CharSequenceUtil;
import com.word.advanced.distributedtxclient.transactional.ZhuziTxParticaipant;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

/**
 * httpClient 远程调用工具
 */
public class HttpClient {

    //get 请求方法
    public static String get(String url) {
        String result = "";
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Header[] allHeaders = httpResponse.getAllHeaders();
                //从中找到上游服务传递的组ID,事务数量，并赋值给自己的子事务
                for (Header header : allHeaders) {
                    if ("groupId".equals(header.getName())) {
                        String groupId = header.getValue();
                        ZhuziTxParticaipant.setCurrentGroupId(groupId);

                    }
                    if ("transactionalCount".equals(header.getName())) {
                        String transactionalCount = header.getValue();
                        ZhuziTxParticaipant.setTransactionalCount(CharSequenceUtil.isNotBlank(transactionalCount) ? Integer.valueOf(transactionalCount) : 0);
                    }
                }
                result = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
            }
            httpResponse.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
