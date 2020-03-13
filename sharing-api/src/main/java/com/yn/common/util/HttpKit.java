package com.yn.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

public class HttpKit {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpKit.class);

    public static String getIp(){
       return HttpKit.getRequest().getRemoteHost();
    }

    /**
     * 获取所有请求的值
     */
    @SuppressWarnings("rawtypes")
	public static Map<String, String> getRequestParameters() {
        HashMap<String, String> values = new HashMap<>();
        HttpServletRequest request = HttpKit.getRequest();
        Enumeration enums = request.getParameterNames();
        while ( enums.hasMoreElements()){
            String paramName = (String) enums.nextElement();
            String paramValue = request.getParameter(paramName);
            values.put(paramName, paramValue);
        }
        return values;
    }

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取 包装防Xss Sql注入的 HttpServletRequest
     * @return request
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return new WafRequestWrapper(request);
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param param 请求参数
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String, Object> param, Map<String, String> headParams) {
        String result = "";
        BufferedReader in = null;
        try {
            StringBuilder para = new StringBuilder();
            if(param != null){
	            for (String key : param.keySet()) {
	                para.append(key).append("=").append(param.get(key)).append("&");
	            }
            }
            if (para.lastIndexOf("&") > 0) {
                para = new StringBuilder(para.substring(0, para.length() - 1));
            }
            
            String urlNameString;
            if(!"".equals(para.toString())){
            	urlNameString = url + "?" + para;
            }else{
            	urlNameString = url;
            }
            
            //跳过SSL证书验证 	begin
            SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");//第一个参数为协议,第二个参数为提供者(可以缺省)
            TrustManager[] tm = {new MyX509TrustManager()};
            sslcontext.init(null, tm, new SecureRandom());
            HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
            	public boolean verify(String s, SSLSession sslsession) {
            		return true;
            	}
            };
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
            //跳过SSL证书验证 	end
            
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
            connection.setRequestMethod("GET");// 提交模式
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type", "application/json");
            // 头部参数
            if(headParams!=null){  
                for(String key : headParams.keySet()){  
                	connection.setRequestProperty(key, headParams.get(key));  
                }  
            }
            
            /*Map<String,List<String>> reqPro = connection.getRequestProperties();
            for (String key : reqPro.keySet()) {
                System.out.println(key + ":" + reqPro.get(key));
            }*/
            
            //必须设置false，否则会自动redirect到重定向后的地址  
            connection.setInstanceFollowRedirects(false);  
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                System.out.println(key + ":" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
//            System.out.println("发送GET请求出现异常！" + e);
        	e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param param  请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, Object> param, Map<String, String> headParams,String reqMethod) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            //跳过SSL证书验证 	begin
            SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");//第一个参数为协议,第二个参数为提供者(可以缺省)
            TrustManager[] tm = {new MyX509TrustManager()};
            sslcontext.init(null, tm, new SecureRandom());
            HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
            	public boolean verify(String s, SSLSession sslsession) {
            		return true;
            	}
            };
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
            //跳过SSL证书验证 	end
        	
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            if(reqMethod == null || "".equals(reqMethod)){
            	reqMethod = "POST";
            }
            conn.setRequestMethod(reqMethod);// 提交模式
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36)"); //防止报403错误。
            conn.setRequestProperty("Content-Type", "application/json");
            // 头部参数
            if(headParams!=null){
                for(String key : headParams.keySet()){
                    conn.setRequestProperty(key, headParams.get(key));
                }
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            String bodyParam = JsonUtil.toJson(param);
//            System.out.println(bodyParam);
            out.print(bodyParam);//body内容
            // flush输出流的缓冲
            out.flush();
            int responseCode = conn.getResponseCode();
            String line;
            if(responseCode >= 400){
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                throw new Exception(result.toString());
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * @Desc 请求调用参数格式为form-data的接口
     * @Author yn
     * @Date 17:30 2020/3/11 0011
     */
    public static String doPost(String url, Map<String, String> map) {
        String result = "";
        CloseableHttpResponse response;
        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(550000).setConnectTimeout(550000)
                .setConnectionRequestTimeout(550000).setStaleConnectionCheckEnabled(true).build();
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        // client = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            httpPost.setHeader("Connection", "Keep-Alive");
            httpPost.setHeader("Charset", "UTF-8");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            List<NameValuePair> params = new ArrayList<>();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                params.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            response = client.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "UTF-8");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("创建连接失败" + e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Object sendXmlRpc(String url, String methodName, List params){
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(url));
            config.setBasicUserName("");
            config.setBasicPassword("");
            XmlRpcClient client = new XmlRpcClient();
            client.setConfig(config);

            return client.execute(methodName, params);
        } catch (MalformedURLException | XmlRpcException e) {
            e.printStackTrace();
            return null;
        }
    }
}
