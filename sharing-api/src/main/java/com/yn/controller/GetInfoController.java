package com.yn.controller;

import com.yn.common.annotation.LogAnnotation;
import com.yn.common.util.AddressUtils;
import com.yn.common.util.DateUtils;
import com.yn.common.util.IpUtils;
import com.yn.dao.AccessIpMapper;
import com.yn.entity.AccessIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class GetInfoController {

    @Autowired
    private AccessIpMapper dao;

    @RequestMapping(value = "/")
    @LogAnnotation(module = "获取浏览器信息", operation = "获取浏览器信息")
    public void getInfo(HttpServletRequest request, HttpServletResponse response){
        String sessionId = request.getSession().getId();
        System.out.println("request.getSession().getId()"+ sessionId);
        //通过NatApp获取访问者IP
        String nip = request.getHeader("X-Natapp-Ip");
        //判断是否已经存在ip记录，存在就加1
        if(dao.existIp(nip) > 0){
            dao.updateByIp(nip,sessionId);
        }else {
            String path = "\\"+ DateUtils.getcurrentdate4()+".txt";
            File f = new File(path);

            /**
             * 1.获得客户机信息
             */
            String requestUrl = request.getRequestURL().toString();//得到请求的URL地址
            String requestUri = request.getRequestURI();//得到请求的资源
            String queryString = request.getQueryString();//得到请求的URL地址中附带的参数
            String remoteAddr = IpUtils.getIpAddr(request);//得到来访者的IP地址
            String remoteHost = request.getRemoteHost();
            int remotePort = request.getRemotePort();
            String remoteUser = request.getRemoteUser();
            String method = request.getMethod();//得到请求URL地址时使用的方法
            String pathInfo = request.getPathInfo();
            String localAddr = request.getLocalAddr();//获取WEB服务器的IP地址
            String localName = request.getLocalName();//获取WEB服务器的主机名
            String agent = request.getHeader("USER-AGENT");//浏览器类型
            String addresses = AddressUtils.getAddresses("ip=" + nip, StandardCharsets.UTF_8);
            AccessIp accessIp = new AccessIp();
            accessIp.setIp(nip);
            accessIp.setTimes(1);
            accessIp.setAgent(agent);
            accessIp.setRemoteHost(remoteHost);
            accessIp.setRemotePort(remotePort);
            accessIp.setRemoteAddr(remoteAddr);
            accessIp.setRequestUri(requestUri);
            accessIp.setRequestUrl(requestUrl);
            accessIp.setAddress(addresses);
            accessIp.setSessionId(sessionId);
            dao.insertSelective(accessIp);
            try {
                FileWriter fw = new FileWriter(path);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("获取到的客户机信息如下：");
                bw.newLine();
                bw.write("请求的URL地址："+requestUrl);
                bw.newLine();
                bw.write("请求的资源："+requestUri);
                bw.newLine();
                bw.write("请求的URL地址中附带的参数："+queryString);
                bw.newLine();
                bw.write("来访者的IP地址："+remoteAddr);
                bw.newLine();
                bw.write("X-Natapp-Ip来访者的外网IP地址："+nip);
                bw.newLine();
                bw.write("来访者的主机名："+remoteHost);
                bw.newLine();
                bw.write("使用的端口号："+remotePort);
                bw.newLine();
                bw.write("remoteUser："+remoteUser);
                bw.newLine();
                bw.write("请求使用的方法："+method);
                bw.newLine();
                bw.write("pathInfo："+pathInfo);
                bw.newLine();
                bw.write("localAddr："+localAddr);
                bw.newLine();
                bw.write("localName："+localName);
                bw.newLine();
                bw.write("浏览器类型："+agent);
                bw.newLine();
                bw.write("地址："+ addresses);
                bw.flush();
                System.out.println("绝对路径--------------------" + f.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            request.getRequestDispatcher("/index.html").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/addPosition")
    @LogAnnotation(module = "保存定位", operation = "保存定位")
    public void addPosition(@RequestBody AccessIp accessIp, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if("JSESSIONID".equals(cookie.getName())) {
                String sessionId = cookie.getValue();
                System.out.println("cookie.getValue()"+ sessionId);
                accessIp.setSessionId(sessionId);
                dao.updatePosition(accessIp);
                break;
            }
        }
    }
}
