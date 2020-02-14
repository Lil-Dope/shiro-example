package com.cnaidun.police.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private static HttpClient httpClient;

    private static HttpParams httpParams;
    private static ClientConnectionManager connectionManager;

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 80000;
    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = 5000;
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 8000;
    /**
     * 连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 5000;
    /**
     * 读取超时时间
     */
    public final static int READ_TIMEOUT = 5000;

    static {
        httpParams = new BasicHttpParams();
        // 设置最大连接数
        ConnManagerParams.setMaxTotalConnections(httpParams, MAX_TOTAL_CONNECTIONS);
        // 设置获取连接的最大等待时间
        ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);
        // 设置每个路由最大连接数
        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);
        // 设置连接超时时间
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
        // 设置读取超时时间
        HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);

        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
        sslSocketFactory.setHostnameVerifier(new AllowAllHostnameVerifier());
        registry.register(new Scheme("https", sslSocketFactory, 443));

        connectionManager = new ThreadSafeClientConnManager(httpParams, registry);
        httpClient = new DefaultHttpClient(connectionManager, httpParams);
    }

    public static String get(String url, Map<String, String> params) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            pairs.add(nameValuePair);
        }

        return get(url, pairs);
    }


    /**
     * Get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, List<NameValuePair> params) {
        String body = null;
        try {
            // Get请求
            HttpGet httpget = new HttpGet(url);
            // 设置参数
            String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
            // 发送请求
            HttpResponse httpresponse = httpClient.execute(httpget);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);
            if (entity != null) {
                entity.consumeContent();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static String post(String url, Map<String, String> params,String token) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            pairs.add(nameValuePair);
        }

        return post(url, pairs,token);
    }

    /**
     * // Post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, List<NameValuePair> params, String token) {
        String body = null;
        try {
            // Post请求
            HttpPost httppost = new HttpPost(url);
            // 设置请求头
            if(!(token == null)){
                httppost.addHeader("Authorization", "Bearer "+token);
                //httppost.addHeader("methodkey","07c43856-d967-43ca-a94b-170e6b268898");
            }
            // 设置参数
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            // 发送请求
            HttpResponse httpresponse = httpClient.execute(httppost);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);
            if (entity != null) {
                entity.consumeContent();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static String postXml(String url, String xmlData) {
        HttpPost post = new HttpPost(url);
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            StringEntity entity = new StringEntity(xmlData);
            post.setEntity(entity);
            post.setHeader("Content-Type", "text/xml;charset=UTF-8");

            HttpResponse response = httpClient.execute(post);
            is = response.getEntity().getContent();
            baos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int count = is.read(data);
            while (count != -1) {
                baos.write(data, 0, count);
                count = is.read(data);
            }
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (Exception ignore) {
                }
            if (baos != null)
                try {
                    baos.close();
                } catch (Exception ignore) {
                }
        }
        return null;
    }

    public static void main(String[] args){
//        HttpUtil.postXml("http://127.0.0.1:8090/trade/order/payment/callback/wechat_notify", "<xml>\n" +
//                "   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
//                "   <attach><![CDATA[支付测试]]></attach>\n" +
//                "   <bank_type><![CDATA[CFT]]></bank_type>\n" +
//                "   <fee_type><![CDATA[CNY]]></fee_type>\n" +
//                "   <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
//                "   <mch_id><![CDATA[10000100]]></mch_id>\n" +
//                "   <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n" +
//                "   <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n" +
//                "   <out_trade_no><![CDATA[1409811653]]></out_trade_no>\n" +
//                "   <result_code><![CDATA[SUCCESS]]></result_code>\n" +
//                "   <return_code><![CDATA[SUCCESS]]></return_code>\n" +
//                "   <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n" +
//                "   <sub_mch_id><![CDATA[10000100]]></sub_mch_id>\n" +
//                "   <time_end><![CDATA[20140903131540]]></time_end>\n" +
//                "   <total_fee>1</total_fee>\n" +
//                "   <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
//                "   <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n" +
//                "</xml>");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("topic", "user-addRawLog-msg"));
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("user_id", 92L);
        params.add(new BasicNameValuePair("data", JsonUtil.toJson(data)));

        String response = HttpUtil.post("http://115.236.182.108:7081/YDX-ERP/message/notify", params,null);
        System.out.println("response:"+response);
    }



    /**
     * 获取token
     * @return
     */
    public static String getTokenRequest(String userID,String userKey,String accessUrl) {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig).build();
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig).build();
        //实例化post方法
        HttpPost httpPost = new HttpPost(accessUrl);
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Accept","application/x-www-form-urlencoded");
        //处理参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("userID",userID));
        nvps.add(new BasicNameValuePair("userKey",userKey));
        //请求结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            //提交的参数
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
            //将参数给post方法
            httpPost.setEntity(uefEntity);
            //执行post方法
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return content;
    }


    /**
     * 获取业务部门信息，表单元素，提交表单通用方法
     * @return
     */
    public static String getAccessTokenRequest(String accessToken,String methodkey,String params,String accessUrl) {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig).build();
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig).build();
        //实例化post方法
        HttpPost httpPost = new HttpPost(accessUrl);
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Accept","application/x-www-form-urlencoded");

        if(accessToken != null){
            httpPost.setHeader("Authorization","Bearer "+" "+accessToken);
        }
        //处理参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("methodkey",methodkey));
        nvps.add(new BasicNameValuePair("postDataJson",params));


        //请求结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            //提交的参数
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
            //将参数给post方法
            httpPost.setEntity(uefEntity);
            //执行post方法
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content;
    }
}
